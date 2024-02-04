package com.example.collegescheduler.ui.logout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.collegescheduler.LoginActivity;
import com.example.collegescheduler.R;
import com.example.collegescheduler.db.SharedViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogoutFragment extends Fragment {
    private SharedViewModel sharedViewModel;
    private TextView welcomeHeader;


    public LogoutFragment() {
        // Required empty public constructor
    }

    public static LogoutFragment newInstance() {
        return new LogoutFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        welcomeHeader = view.findViewById(R.id.welcome_header);
        Button logoutButton = view.findViewById(R.id.logout_button);

        sharedViewModel.getUsernameData().observe(getViewLifecycleOwner(), username -> {
            if (username != null) {
                assignWelcomeHeader(username);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutButtonClick();
            }
        });
    }

    private void navigateToLoginActivity() {
        sharedViewModel.setUsernameValue(null);
        Activity activity = getActivity();
        if (activity != null) {
            Intent intent = new Intent(activity, LoginActivity.class);
            startActivity(intent);
            activity.finish();
        }
    }

    private void logoutButtonClick() {
        navigateToLoginActivity();
    }

    private void assignWelcomeHeader(String username) {
        welcomeHeader.setText(String.format("Welcome, %s!", username));
    }
}