package com.example.outreach.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.outreach.AdapterInterface;
import com.example.outreach.MainActivity;
import com.example.outreach.R;
import com.example.outreach.adapters.AllAdapter;
import com.example.outreach.models.Event;
import com.example.outreach.utilities.APIDataHandler;

import java.util.ArrayList;
import java.util.Objects;

public class EventViewFragment extends Fragment implements AdapterInterface, ListView.OnItemClickListener {
    public static AdapterInterface adapterInterface;
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

        if (MainActivity.bottomNavigationView.getSelectedItemId() == R.id.favorite_page) {
            listView.setEmptyView(view.findViewById(R.id.no_favorite_events));
        } else {
            listView.setEmptyView(view.findViewById(R.id.no_events_found));
        }

        listView.setOnItemClickListener(this);
        adapterInterface = this;
    }

    @Override
    public void onResume() {
        super.onResume();
        switch (MainActivity.bottomNavigationView.getSelectedItemId()) {
            case R.id.all_page:
                setAdapter(APIDataHandler.allEvents);
                break;
            case R.id.music_page:
                setAdapter(MainActivity.getMusicEvents());
                break;
            case R.id.religious_page:
                setAdapter(MainActivity.getReligiousEvents());
                break;
            case R.id.community_page:
                setAdapter(MainActivity.getCommunityEvents());
                break;
            case R.id.favorite_page:
                setAdapter(MainActivity.getFavoritedEvents());
                break;
        }
    }

    @Override
    public void setAdapter(ArrayList<Event> events) {
        listView.setAdapter(new AllAdapter(events, getContext()));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ArrayList<Event> filteredEvents = new ArrayList<>();

        switch (MainActivity.bottomNavigationView.getSelectedItemId()) {
            case R.id.all_page:
                filteredEvents = APIDataHandler.allEvents;
                break;
            case R.id.music_page:
                filteredEvents = MainActivity.getMusicEvents();
                break;
            case R.id.religious_page:
                filteredEvents = MainActivity.getReligiousEvents();
                break;
            case R.id.community_page:
                filteredEvents = MainActivity.getCommunityEvents();
                break;
            case R.id.favorite_page:
                filteredEvents = MainActivity.getFavoritedEvents();
                break;
        }

        if (MainActivity.bottomNavigationView.getSelectedItemId() != R.id.favorite_page) {
            Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("")
                    .replace(R.id.fragmentFrame, DetailViewFragment.newInstance(filteredEvents.get(position)))
                    .commit();
        }
    }
}
