package com.example.googlemap.googlemap;

import android.net.Uri;

import java.io.Serializable;


/**
 * Created by SCITMASTER on 2018-03-07.
 */

public class Gpsdata implements Serializable {
    float Latitude;
    float Longitude;
    Uri uri;
    String Title;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Gpsdata(float latitude, float longitude, String title) {

        Latitude = latitude;
        Longitude = longitude;
        this.Title= title;
    }

    public Gpsdata(float latitude, float longitude) {
        Latitude = latitude;
        Longitude = longitude;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float longitude) {
        Longitude = longitude;
    }
}
