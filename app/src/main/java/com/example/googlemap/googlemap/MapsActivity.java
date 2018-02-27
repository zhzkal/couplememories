package com.example.googlemap.googlemap;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng seoul = new LatLng( 37.56, 126.97 );
        mMap.addMarker( new MarkerOptions().position(seoul).title( "Marker in Seoul" ) );
        mMap.moveCamera( CameraUpdateFactory.newLatLng(seoul) );
    }


    public void buttonClick(View v) {
        switch (v.getId()) {
            case R.id.bt1:
                finish();
              /*  Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);*/
                break;
        }
    }

}
