package com.example.brandonmayle.giftisrael;

public class Activity {

    private String name, type, date, time, location, price, description;
    private long startTime, endTime;
    private double latitude, longitude;

    public Activity(String name, String type, String date, String time, String location, String price, String description, long startTime, long endTime, double latitude, double longitude) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.time = time;
        this.location = location;
        this.price = price;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}