package com.example.outreach.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.outreach.MainActivity;
import com.example.outreach.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EventViewFragment extends Fragment {
    ListView listView;

    public static EventViewFragment newInstance() {
        return new EventViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.events_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listView);

        //TEMP Empty Adapter
        ArrayList<String> emptyList = new ArrayList<>();
        listView.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, emptyList));

        if (MainActivity.bottomNavigationView.getSelectedItemId() == R.id.favorite_page) {
            listView.setEmptyView(view.findViewById(R.id.no_favorite_events));
            listView.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, emptyList));
        } else {
            listView.setEmptyView(view.findViewById(R.id.no_events_found));
        }


    }
}
