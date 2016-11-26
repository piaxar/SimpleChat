package com.kazan.gdg.simple_chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * @author ravil
 */

public class SignUpActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordConfirmation;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEmail = (EditText) findViewById(R.id.email_edit_text);
        mPassword = (EditText) findViewById(R.id.password_edit_text);
        mPasswordConfirmation = (EditText) findViewById(R.id.password_confirm_edit_text);
        Button signUpButton = (Button) findViewById(R.id.sign_up_button);

        mFirebaseAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    private void signUp() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String passwordConfirmation = mPasswordConfirmation.getText().toString();

        if (TextUtils.equals(password, passwordConfirmation)) {
            mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignUpActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
