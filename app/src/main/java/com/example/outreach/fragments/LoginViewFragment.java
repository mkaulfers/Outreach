package com.example.outreach.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.outreach.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginViewFragment extends Fragment implements ImageButton.OnClickListener {
    EditText mEmail, mPassword;
    ImageButton mLoginButton, mCreateAccountButton;
    ProgressBar mProgressBar;

    FirebaseAuth mAuth;

    public static LoginViewFragment newInstance() {
        LoginViewFragment fragment = new LoginViewFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEmail = view.findViewById(R.id.email_input);
        mPassword = view.findViewById(R.id.password_input);
        mLoginButton = view.findViewById(R.id.login_button);
        mCreateAccountButton = view.findViewById(R.id.create_account_button);

        mLoginButton.setOnClickListener(this);
        mCreateAccountButton.setOnClickListener(this);

        mProgressBar = view.findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "You must enter an email.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "You must enter an password.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(password.length() < 7) {
                    Toast.makeText(getContext(), "Your password must have a length of 7 or more.", Toast.LENGTH_LONG).show();
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Successfully logged in.", Toast.LENGTH_LONG).show();
                                    Objects.requireNonNull(getActivity()).finish();
                                } else {
                                    mProgressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getContext(), "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                break;
            case R.id.create_account_button:
                Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("")
                        .replace(R.id.fragment_frame, RegisterViewFragment.newInstance())
                        .commit();
                break;
        }
    }
}
