package com.tourkiev.chernobyltours;

import android.graphics.Bitmap;

/**
 * Created by hp on 029 29.11.2017.
 */

public class ModelMarker {
    private double latitude, longitude;
    private String title;
    private Bitmap bitmapImage;
    private String description;
    private double radius;

    public void setTitle(String title) {
        this.title = title;
    }


    private Bitmap bitmapMarker;


    public Bitmap getBitmapImage() {
        return bitmapImage;
    }

    public ModelMarker(double latitude, double longitude, String title, String description, Bitmap bitmapImage, double radius, Bitmap bitmapMarker) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.description = description;
        this.bitmapImage = bitmapImage;
        this.radius = radius;
        this.bitmapMarker = bitmapMarker;
    }

    public ModelMarker(double latitude, double longitude, String title, String description, Bitmap bitmapImage, Bitmap bitmapMarker) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.description = description;
        this.bitmapImage = bitmapImage;
        this.bitmapMarker = bitmapMarker;
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

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }


    public void setBitmapImage(Bitmap bitmapImage) {
        this.bitmapImage = bitmapImage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getBitmapMarker() {

        return  Bitmap.createScaledBitmap(bitmapMarker, 90, 122, false);
    }

    public void setBitmapMarker(Bitmap bitmapMarker) {
        this.bitmapMarker = bitmapMarker;
    }

}
