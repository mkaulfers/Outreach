package com.example.outreach.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.outreach.R;
import com.example.outreach.models.Event;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AllAdapter extends BaseAdapter {
    private ArrayList<Event> allEvents;
    private Context context;

    public AllAdapter(ArrayList<Event> allEvents, Context context) {
        this.allEvents = allEvents;
        this.context = context;
    }

    @Override
    public int getCount() {
        return allEvents.size();
    }

    @Override
    public Object getItem(int position) {
        return allEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0x0102 + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        Picasso.get().load(allEvents.get(position).getCoverURL()).into(imageView);

        TextView title = convertView.findViewById(R.id.titleLabel);
        title.setText(allEvents.get(position).getTitle());

        TextView dateTimeLabel = convertView.findViewById(R.id.date_time_label);
        String formattedString = allEvents.get(position).getDate() + " | " + allEvents.get(position).getTime();
        dateTimeLabel.setText(formattedString);

        TextView costLabel = convertView.findViewById(R.id.costLabel);

        if (allEvents.get(position).getCost() > 0) {
            String cost = "$" + allEvents.get(position).getCost() + "0";
            costLabel.setText(cost);
        } else {
            costLabel.setText(R.string.free);
        }



        return convertView;
    }
}
