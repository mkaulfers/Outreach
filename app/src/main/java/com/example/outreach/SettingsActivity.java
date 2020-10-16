package com.example.outreach;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends AppCompatActivity {
    SwitchCompat cloudSaving;
    SeekBar distance;
    TextView dateRangeLabel;
    CalendarView calendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_view);

        cloudSaving = findViewById(R.id.cloud_switch);
        distance = findViewById(R.id.distance_bar);
        dateRangeLabel = findViewById(R.id.date_time_label);
        calendar = findViewById(R.id.calendarView);
    }
}
