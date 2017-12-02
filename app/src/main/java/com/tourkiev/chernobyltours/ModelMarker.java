package com.tourkiev.chernobyltours;

import android.graphics.Bitmap;

/**
 * Created by hp on 029 29.11.2017.
 */

public class ModelMarker {
    private double latitude, longitude;
    private String title;
    private Bitmap bitmap;
    private String description;




    public Bitmap getBitmap() {
        return bitmap;
    }

    public ModelMarker(double latitude, double longitude, String title, String description, Bitmap bitmap) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.description = description;
        this.bitmap = bitmap;

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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
