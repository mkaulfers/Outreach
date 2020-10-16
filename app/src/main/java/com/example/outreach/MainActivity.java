package com.example.outreach;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.outreach.fragments.EventViewFragment;
import com.example.outreach.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public static BottomNavigationView bottomNavigationView;
    Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_action:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.profile_action:
                break;
        }

//        ActionBar actionBar = getSupportActionBar();
//
//        if (item.getItemId() == R.id.settings_action) {
//            FrameLayout placeHolderView = findViewById(R.id.settingsFrame);
//            placeHolderView.setVisibility(View.VISIBLE);
//
//            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//            bottomNavigationView.setVisibility(View.INVISIBLE);
//
//            if (actionBar != null) {
//                actionBar.setDisplayHomeAsUpEnabled(true);
//                MenuItem settings = menu.findItem(R.id.settings_action);
//                MenuItem profile = menu.findItem(R.id.profile_action);
//
//                settings.setVisible(false);
//                profile.setVisible(false);
//            }
//
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.settingsFrame, SettingsFragment.newInstance())
//                    .commit();
//        } else {
//
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .remove(getSupportFragmentManager().findFragmentById(R.id.settingsFrame))
//                    .commit();
//        }
//

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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentFrame, EventViewFragment.newInstance())
                .commit();

        switch (item.getItemId()) {
            case R.id.all_page:
                return true;
            case R.id.music_page:
                return true;
            case R.id.religious_page:
                return true;
            case R.id.community_page:
                return true;
            case R.id.favorite_page:
                return true;
        }
        return false;
    }
}