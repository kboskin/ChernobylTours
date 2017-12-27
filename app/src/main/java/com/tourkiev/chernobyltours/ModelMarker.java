package com.tourkiev.chernobyltours;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;

/**
 * Created by hp on 029 29.11.2017.
 */

public class ModelMarker implements Serializable {
    private double latitude, longitude;
    private String title;
    private int bitmapMarkerId;
    private String description;
    private double radius;
    private int audioId;
    private int bitmapId;

    public void setTitle(String title) {
        this.title = title;
    }


    public int getBitmapId() {
        return bitmapId;
    }

    public ModelMarker(double latitude, double longitude, String title, String description, int bitmapId, double radius, int bitmapMarkerId, int audioId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.description = description;
        this.bitmapId = bitmapId;
        this.radius = radius;
        this.bitmapMarkerId = bitmapMarkerId;
        this.audioId = audioId;
    }

    public ModelMarker(double latitude, double longitude, String title, String description, int bitmapId, int bitmapMarkerId, int audioId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.description = description;
        this.bitmapId = bitmapId;
        this.bitmapMarkerId = bitmapMarkerId;
        this.audioId = audioId;
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


    public void setBitmapId(int bitmapMarkerId) {
        this.bitmapMarkerId = bitmapMarkerId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBitmapMarkerId() {

        return bitmapMarkerId;
    }

    public void setBitmapMarkerId(int bitmapMarkerId) {
        this.bitmapMarkerId = bitmapMarkerId;
    }

    public int getAudioId() {
        return audioId;
    }

    public void setAudioId(int audioId) {
        this.audioId = audioId;
    }

    public static Bitmap convertToBitmap(Context context, int resId) {
        return BitmapFactory.decodeResource(context.getResources(),
                resId);
    }

}
