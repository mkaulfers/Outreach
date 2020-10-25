package com.example.outreach.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.outreach.R;
import com.example.outreach.models.Event;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Objects;

public class DetailViewFragment extends Fragment implements ImageButton.OnClickListener{
    Event selectedEvent;
    ImageView mImageView;
    TextView mTitle, mTime, mAddress, mCost, mDetails;
    ImageButton mFavoriteButton;
    StorageReference mStorageReference;

    public static DetailViewFragment newInstance(Event clickedEvent) {
        Bundle args = new Bundle();
        args.putSerializable("Event", clickedEvent);
        DetailViewFragment fragment = new DetailViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mImageView = view.findViewById(R.id.event_image);
        mTitle = view.findViewById(R.id.event_title);
        mTime = view.findViewById(R.id.event_time);
        mAddress = view.findViewById(R.id.event_address);
        mCost = view.findViewById(R.id.event_cost);
        mDetails = view.findViewById(R.id.event_details);
        mFavoriteButton = view.findViewById(R.id.favorite_button);
        mFavoriteButton.setOnClickListener(this);

        if (getArguments() != null) {
            selectedEvent = (Event) getArguments().getSerializable("Event");
            setData();
        }

        mStorageReference = FirebaseStorage.getInstance().getReference();
    }

    private void setData() {
        Picasso.get().load(selectedEvent.getCoverURL()).into(mImageView);
        mTitle.setText(selectedEvent.getTitle());
        mTime.setText(selectedEvent.getTime());
        mAddress.setText(selectedEvent.getAddress());
        String cost = "$" + selectedEvent.getCost();
        mCost.setText(cost);
        mDetails.setText(selectedEvent.getDescription());
    }

    @Override
    public void onClick(View v) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            saveEventFile();
            Uri uri = Uri.fromFile(getEventFile());
            StorageReference storageReference = mStorageReference.child("users/" + user.getUid() + "/favorites/" + "event_" + selectedEvent.getId() + ".json");

            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Favorite saved.", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Failed to save favorite.", Toast.LENGTH_LONG).show();
                }
            });

        } else {
            Toast.makeText(getContext(), "You must be logged in to favorite.", Toast.LENGTH_LONG).show();
        }
    }

    private void saveEventFile() {
        try {
            Writer output;
            String path = Objects.requireNonNull(getContext()).getFilesDir().toString();
            path += "/event_" + selectedEvent.getId() + ".json";

            File file = new File(path);
            output = new BufferedWriter(new FileWriter(file));
            output.write(selectedEvent.getJsonString());
            output.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File getEventFile() {
        return new File(Objects.requireNonNull(getContext()).getFilesDir().toString() + "/event_" + selectedEvent.getId() + ".json");
    }
}
