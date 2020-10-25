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

public class RegisterViewFragment extends Fragment implements View.OnClickListener {
    FirebaseAuth mAuth;
    EditText mEmail, mPassword, mConfirmPassword;
    ImageButton mRegisterButton;
    ProgressBar mProgressBar;

    public static RegisterViewFragment newInstance() {
        return new RegisterViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        mEmail = view.findViewById(R.id.email_input);
        mPassword = view.findViewById(R.id.password_input);
        mConfirmPassword = view.findViewById(R.id.confirm_password_input);
        mRegisterButton = view.findViewById(R.id.register_button);
        mProgressBar = view.findViewById(R.id.progressBar);

        mRegisterButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String cPassword = mConfirmPassword.getText().toString().trim();

        if (v.getId() == R.id.register_button) {

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

            if(TextUtils.isEmpty(cPassword)) {
                Toast.makeText(getContext(), "You must confirm your password.", Toast.LENGTH_LONG).show();
                return;
            }

            if(!password.equals(cPassword)) {
                Toast.makeText(getContext(), "Passwords do not match.", Toast.LENGTH_LONG).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                mProgressBar.setVisibility(View.VISIBLE);
                                Toast.makeText(getContext(), "Account created.", Toast.LENGTH_LONG).show();
                                Objects.requireNonNull(getActivity()).finish();
                            } else {
                                mProgressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getContext(), "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }
    }
}
