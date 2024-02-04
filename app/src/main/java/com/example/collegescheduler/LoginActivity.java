package com.example.collegescheduler;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.collegescheduler.ui.login.LoginFragment;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity);

        if (savedInstanceState == null) {
            LoginFragment loginFragment = new LoginFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.layout_container, loginFragment); // Replace the container with LoginFragment
            transaction.commit(); // Commit the transaction
        }
    }
}
