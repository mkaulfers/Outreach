package com.example.outreach.models;

import java.io.Serializable;

public class Event implements Serializable {
    private final int id;
    private final double latitude;
    private final double cost;
    private final String date;
    private final double longitude;
    private final String time;
    private final String title;
    private final String address;
    private final String category;
    private final String coverURL;
    private final String description;

    public Event(int id, double latitude, double cost, String date, double longitude, String time,
                 String title, String address, String category, String coverURL, String description) {
        this.id  = id;
        this.latitude = latitude;
        this.cost = cost;
        this.date = date;
        this.longitude = longitude;
        this.time = time;
        this.title = title;
        this.address = address;
        this.category = category;
        this.coverURL = coverURL;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public double getCost() {
        return cost;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public String getDescription() {
        return description;
    }

    public String getJsonString() {
        return "{\n" +
                "  \"id\": " + id + ",\n" +
                "  \"lat\": " + latitude + ",\n" +
                "  \"cost\": " + cost + ",\n" +
                "  \"date\": \"" + date + "\",\n" +
                "  \"long\": " + longitude + ",\n" +
                "  \"time\": \"" + time + "\",\n" +
                "  \"title\": \"" + title + "\",\n" +
                "  \"address\": \"" + address + "\",\n" +
                "  \"category\": \"" + category + "\",\n" +
                "  \"cover_url\": \"" + coverURL + "\",\n" +
                "  \"description\": \"" + description + "\"\n" +
                "}";
    }
}
