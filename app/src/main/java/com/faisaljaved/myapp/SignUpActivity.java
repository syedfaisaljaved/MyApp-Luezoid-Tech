package com.faisaljaved.myapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText mSignupEmail, mSignupPassword, mConfirmPassword;
    private Button mCreateAccount, mLoginButton;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        firebaseAuth = FirebaseAuth.getInstance();
        mSignupEmail = (EditText) findViewById(R.id.signup_email);
        mSignupPassword = (EditText) findViewById(R.id.signup_password);
        mConfirmPassword = (EditText) findViewById(R.id.signup_confirm_password);

        mCreateAccount = (Button) findViewById(R.id.create_account);
        mCreateAccount.setOnClickListener(mlistener);

        mLoginButton = (Button) findViewById(R.id.back_to_login);
        mLoginButton.setOnClickListener(mlistener);
    }

    View.OnClickListener mlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.create_account:
                    String enteredEmail = mSignupEmail.getText().toString();
                    String enteredPassword = mSignupPassword.getText().toString();

                    if (TextUtils.isEmpty(enteredEmail)) {
                        Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(enteredPassword)) {
                        Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    firebaseAuth.createUserWithEmailAndPassword(enteredEmail, enteredPassword)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Successfully registered. Please check your email for verification", Toast.LENGTH_LONG).show();
                                        mSignupEmail.setText("");
                                        mSignupPassword.setText("");
                                        mConfirmPassword.setText("");
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    break;

                case R.id.back_to_login:
//                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
//                    startActivity(intent);
                    finish();
                    break;

            }

        }
    };

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
