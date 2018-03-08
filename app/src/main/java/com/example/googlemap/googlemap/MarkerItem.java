package com.example.googlemap.googlemap;

import android.net.Uri;

/**
 * Created by TedPark on 16. 4. 26..
 */
public class MarkerItem {


    double lat;
    double lon;
    Uri uri;
    String Title;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public MarkerItem(double lat, double lon, String title) {

        this.lat = lat;
        this.lon = lon;
        Title = title;
    }

    public MarkerItem(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }



}
