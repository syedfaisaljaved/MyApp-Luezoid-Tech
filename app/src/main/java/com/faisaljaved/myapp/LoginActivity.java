package com.faisaljaved.myapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends Fragment {

    private EditText mEmail, mPassword;
    private Button mLogin, mSignup;
    private TextView mForgotPassword;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View loginFragment = inflater.inflate(R.layout.activity_login,container,false);

        mProgressDialog = new ProgressDialog(getContext());

        firebaseAuth = FirebaseAuth.getInstance();

        mEmail = (EditText) loginFragment.findViewById(R.id.email_edit_text_view);
        mPassword = (EditText) loginFragment.findViewById(R.id.password_edit_text_view);

        mLogin = (Button) loginFragment.findViewById(R.id.login_button);
        mLogin.setOnClickListener(mlistener);

        mSignup = (Button) loginFragment.findViewById(R.id.signup_button);
        mSignup.setOnClickListener(mlistener);

        mForgotPassword = (TextView) loginFragment.findViewById(R.id.forgot_password_text_view);
        mForgotPassword.setOnClickListener(mlistener);

        return loginFragment;
    }

    View.OnClickListener mlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.login_button:
                    String enteredEmail = mEmail.getText().toString();
                    String enteredPassword = mPassword.getText().toString();

                    if (TextUtils.isEmpty(enteredEmail)) {
                        Toast.makeText(getContext(), "Please enter email", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(enteredPassword)) {
                        Toast.makeText(getContext(), "Please enter password", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mProgressDialog.setMessage("Logging in...");
                    mProgressDialog.show();

                    firebaseAuth.signInWithEmailAndPassword(enteredEmail,enteredPassword).addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mProgressDialog.hide();
                            if (task.isSuccessful()){

                                /**
                                 * Hide the keyboard after clicking login button.
                                 * Avoids the keyboard from appearing onto the next Fragment.
                                 */
                                InputMethodManager inputManager = (InputMethodManager)
                                        getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);

                                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);

                                /**
                                 * User is loggged in.
                                 * ProfileActivity Fragment class is replaced with LoginActivity Fragment class.
                                 */
                                ProfileActivity profileFragment = new ProfileActivity();
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragmment_container, profileFragment)
                                        .commit();
                            }else{
                                Toast.makeText(getContext(),"This email is not registered.",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    break;
                case R.id.signup_button:
                    Intent intent = new Intent(getActivity(), SignUpActivity.class);
                    startActivity(intent);
                    break;
                case R.id.forgot_password_text_view:
                    startActivity(new Intent(getActivity(), ForgotPassword.class));
                    break;
            }

        }
    };

    public LoginActivity() {

    }
}
