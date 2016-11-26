package com.kazan.gdg.simple_chat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String MESSAGES_CHILD = "messages";

    private RecyclerView mRecyclerView;
    private Button mSendButton;
    private EditText mMessageEditText;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mCurrentUser;

    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<Message, MessageHolder> mAdapter;

    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.messages_recycler_view);
        mSendButton = (Button) findViewById(R.id.send_button);
        mMessageEditText = (EditText) findViewById(R.id.message_edit_text);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mCurrentUser = mFirebaseAuth.getCurrentUser();

        if (mCurrentUser == null) {
            startSignInActivity();
        }

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mAdapter = new FirebaseRecyclerAdapter<Message, MessageHolder>(
                Message.class,
                R.layout.item_message,
                MessageHolder.class,
                mDatabaseReference.child(MESSAGES_CHILD)) {
            @Override
            protected void populateViewHolder(MessageHolder viewHolder, Message model, int position) {
                viewHolder.mText.setText(model.getText());
                viewHolder.mName.setText(model.getName());
            }
        };
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                mRecyclerView.smoothScrollToPosition(positionStart);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message(mCurrentUser.getEmail(), mMessageEditText.getText().toString());

                mDatabaseReference.child(MESSAGES_CHILD)
                        .push().setValue(message);
                mMessageEditText.setText("");
            }
        });

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings =
                new FirebaseRemoteConfigSettings.Builder()
                        .setDeveloperModeEnabled(true)
                        .build();

        Map<String, Object> defaultConfigMap = new HashMap<>();
        defaultConfigMap.put("message_length", 10L);

        mFirebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);
        mFirebaseRemoteConfig.setDefaults(defaultConfigMap);

        fetchConfig();
    }

    public void fetchConfig() {
        long cacheExpired = 3600;

        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpired = 0;
        }
        mFirebaseRemoteConfig.fetch(cacheExpired)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mFirebaseRemoteConfig.activateFetched();
                        updateLimit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        updateLimit();
                    }
                });
    }

    private void updateLimit() {
        Long messageLength = mFirebaseRemoteConfig.getLong("message_length");

        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(messageLength.intValue())});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sign_out_menu) {
            mFirebaseAuth.signOut();
            startSignInActivity();
            return true;
        } else if (item.getItemId() == R.id.fresh_config_menu) {
            fetchConfig();
            return true;
        } else if (item.getItemId() == R.id.crash_menu) {
            causeCrash();
        }
        return super.onOptionsItemSelected(item);
    }

    private void causeCrash() {
        throw new NullPointerException();
    }

    private void startSignInActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}
