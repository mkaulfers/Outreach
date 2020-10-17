package com.example.outreach.models;

public class Event {
    private int id;
    private double latitude;
    private double cost;
    private String date;
    private double longitude;
    private String time;
    private String title;
    private String address;
    private String category;
    private String coverURL;
    private String description;

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

    public double getLatitude() {
        return latitude;
    }

    public double getCost() {
        return cost;
    }

    public String getDate() {
        return date;
    }

    public double getLongitude() {
        return longitude;
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
}
