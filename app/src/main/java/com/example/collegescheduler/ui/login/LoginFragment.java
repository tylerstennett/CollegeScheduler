package com.example.collegescheduler.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.collegescheduler.MainActivity;
import com.example.collegescheduler.R;
import com.example.collegescheduler.db.SharedViewModel;
import com.example.collegescheduler.db.entities.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    private SharedViewModel sharedViewModel;
    private EditText inputUsername;
    private EditText inputPassword;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() { return new LoginFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputUsername = view.findViewById(R.id.username_input);
        inputPassword = view.findViewById(R.id.password_input);
        Button enterButton = view.findViewById(R.id.enter_button);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButtonClick();
            }
        });
    }

    private void invalidUsernamePassword() {
        Toast.makeText(getContext(), "Username or password cannot be empty.", Toast.LENGTH_LONG).show();
    }

    private void invalidPassword() {
        Toast.makeText(getContext(), "Incorrect password.", Toast.LENGTH_LONG).show();
    }

    private void navigateToMainActivityForUser(String username) {
        Activity activity = getActivity();
        if (activity != null) { // just quick NPE checking
            Intent intent = new Intent(activity, MainActivity.class);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
            activity.finish();
        }
    }

    private void processLogin(String username, String password, User user) {
        if (user.getPassword().equals(password)) {
            navigateToMainActivityForUser(username);
        } else {
            invalidPassword();
        }
    }

    private void processRegister(String username, String password) {
        User newUser = new User(username, password);
        sharedViewModel.insertUser(newUser);
        navigateToMainActivityForUser(username);
    }

    private void loginButtonClick() {
        String username = inputUsername.getText().toString().trim();
        String password = inputPassword.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            invalidUsernamePassword();
            return;
        }

        sharedViewModel.getUserByUsername(username).observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                processLogin(username, password, user);
            } else {
                processRegister(username, password);
            }
        });
    }
}