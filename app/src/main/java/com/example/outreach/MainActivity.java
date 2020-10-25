package com.example.outreach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.outreach.fragments.DetailViewFragment;
import com.example.outreach.fragments.EventViewFragment;
import com.example.outreach.models.Event;
import com.example.outreach.utilities.APIDataHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public static BottomNavigationView bottomNavigationView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_action:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.profile_action:
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                startActivity(profileIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the title for the menu.
        Objects.requireNonNull(getSupportActionBar())
                .setDisplayShowTitleEnabled(false);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorMenuBars)));

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentFrame, EventViewFragment.newInstance())
                .commit();

        APIDataHandler.parseJSON(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentFrame);
        if (fragment instanceof DetailViewFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentFrame, EventViewFragment.newInstance())
                    .commit();
            return true;
        }

        switch (item.getItemId()) {
            case R.id.all_page:
                EventViewFragment.adapterInterface.setAdapter(APIDataHandler.allEvents);
                return true;
            case R.id.music_page:
                EventViewFragment.adapterInterface.setAdapter(getMusicEvents());
                return true;
            case R.id.religious_page:
                EventViewFragment.adapterInterface.setAdapter(getReligiousEvents());
                return true;
            case R.id.community_page:
                EventViewFragment.adapterInterface.setAdapter(getCommunityEvents());
                return true;
            case R.id.favorite_page:
                EventViewFragment.adapterInterface.setAdapter(getFavoritedEvents());
                return true;
        }
        return false;
    }

    public static ArrayList<Event> getMusicEvents() {
        ArrayList<Event> events = new ArrayList<>();
        for (Event event : APIDataHandler.allEvents) {
            if (event.getCategory().equals("music")) {
                events.add(event);
            }
        }
        return events;
    }

    public static ArrayList<Event> getReligiousEvents() {
        ArrayList<Event> events = new ArrayList<>();
        for (Event event : APIDataHandler.allEvents) {
            if (event.getCategory().equals("religious")) {
                events.add(event);
            }
        }
        return events;
    }

    public static ArrayList<Event> getCommunityEvents() {
        ArrayList<Event> events = new ArrayList<>();
        for (Event event : APIDataHandler.allEvents) {
            if (event.getCategory().equals("volunteer")) {
                events.add(event);
            }
        }
        return events;
    }

    public static ArrayList<Event> getFavoritedEvents() {
        final ArrayList<Event> events = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("users/" + user.getUid() + "/favorites/");
            storageReference.list(100)
                    .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                        @Override
                        public void onSuccess(ListResult listResult) {
                            List<StorageReference> references = listResult.getItems();

                            for (StorageReference reference : references) {
                                File localFile = null;
                                try {
                                    localFile = File.createTempFile("Event", ".json");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                final File finalLocalFile = localFile;
                                assert localFile != null;
                                reference.getFile(localFile).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                                            try {
                                                InputStream fis = new FileInputStream(finalLocalFile);
                                                int size = fis.available();
                                                byte[] buffer = new byte[size];
                                                fis.read(buffer);
                                                fis.close();
                                                String json = new String(buffer, StandardCharsets.UTF_8);
                                                events.add(parseJSONtoEvent(json));
                                                EventViewFragment.adapterInterface.setAdapter(events);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                            }

                        }
                    });
        }

        return events;
    }

    private static Event parseJSONtoEvent(String json) {
        Event event = null;
        try {
            JSONObject obj = new JSONObject(json);
            int id = obj.getInt("id");
            double lat = obj.getDouble("lat");
            double cost = obj.getDouble("cost");
            String date = obj.getString("date");
            double longitude = obj.getDouble("long");
            String time = obj.getString("time");
            String title = obj.getString("title");
            String address = obj.getString("address");
            String category = obj.getString("category");
            String coverURL = obj.getString("cover_url");
            String description = obj.getString("description");
            event = new Event(id, lat, cost, date, longitude, time, title, address, category, coverURL, description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return event;
    }
}