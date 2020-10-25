package com.example.outreach.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.outreach.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LogoutViewFragment extends Fragment implements ImageButton.OnClickListener{
    ImageButton mLogoutButton;

    public static LogoutViewFragment newInstance() {
        return new LogoutViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.logout_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLogoutButton = view.findViewById(R.id.logout_button);
        mLogoutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FirebaseAuth.getInstance().signOut();
        Objects.requireNonNull(getActivity()).finish();
    }
}
