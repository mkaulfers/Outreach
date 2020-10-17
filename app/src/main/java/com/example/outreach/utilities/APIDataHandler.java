package com.example.outreach.utilities;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.outreach.models.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class APIDataHandler {
    ///FINGERPRINT SHA1 - BC:84:86:68:BE:1F:4B:15:AF:A0:A1:E1:1E:5B:B9:BD:AD:7C:B7:51
    private static final String MOCK_API_REQUEST = "https://api.mocki.io/v1/81ac7720";
    private static ArrayList<Event> allEvents = new ArrayList<>();

    public static void parseJSON(Context context) {
        RequestQueue mQueue = Volley.newRequestQueue(context);

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MOCK_API_REQUEST, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray events = response.getJSONArray("events");

                            for (int i = 0; i < events.length(); i++) {
                                JSONObject event = events.getJSONObject(i);
                                int id = event.getInt("id");
                                double latitude = event.getDouble("lat");
                                int cost = event.getInt("cost");
                                String date = event.getString("date");
                                double longitude = event.getDouble("long");
                                String time = event.getString("time");
                                String title = event.getString("title");
                                String address = event.getString("address");
                                String category = event.getString("category");
                                String coverURL = event.getString("cover_url");
                                String description = event.getString("description");

                                allEvents.add(new Event(id, latitude, cost, date, longitude, time, title, address, category, coverURL, description));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}
