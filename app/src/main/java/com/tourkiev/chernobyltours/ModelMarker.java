package com.tourkiev.chernobyltours;

/**
 * Created by hp on 029 29.11.2017.
 */

public class ModelMarker {
    private double latitude, longitude;
    private String title;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    private String description;

    public ModelMarker(double latitude, double longitude, String title, String description) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
