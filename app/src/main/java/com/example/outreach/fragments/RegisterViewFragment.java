package com.example.outreach.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.outreach.R;

public class RegisterViewFragment extends Fragment implements View.OnClickListener {
    Context mContext;

    EditText mEmail, mPassword, mConfirmPassword;
    ImageButton mRegisterButton;

    public static RegisterViewFragment newInstance() {
        RegisterViewFragment fragment = new RegisterViewFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEmail = view.findViewById(R.id.email_input);
        mPassword = view.findViewById(R.id.password_input);
        mConfirmPassword = view.findViewById(R.id.confirm_password_input);
        mRegisterButton = view.findViewById(R.id.register_button);

        mRegisterButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
