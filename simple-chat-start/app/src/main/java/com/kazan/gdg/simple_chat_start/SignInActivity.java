package com.kazan.gdg.simple_chat_start;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author ravil
 */

public class SignInActivity extends AppCompatActivity {

    private EditText mEmail;

    private EditText mPassword;

    private Button mSignInButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mEmail = (EditText) findViewById(R.id.email_edit_text);
        mPassword = (EditText) findViewById(R.id.password_edit_text);
        mSignInButton = (Button) findViewById(R.id.log_in_button);
    }
}
