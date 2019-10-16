package com.faisaljaved.myapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        LoginActivity loginFragment = new LoginActivity();
        ProfileActivity profileFragment = new ProfileActivity();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null){
            replaceFragment(profileFragment);
        }else {
            replaceFragment(loginFragment);
        }
    }

    public void replaceFragment(Fragment fragment) {

        try {
            fragmentManager.beginTransaction().
                    replace(R.id.fragmment_container, fragment).
                    commitAllowingStateLoss();

        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
