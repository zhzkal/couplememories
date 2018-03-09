package com.example.googlemap.googlemap;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by SCITMASTER on 2018-03-08.
 */


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        //Log.e("imgClick: ", "권한체크 하는지 ");
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            Log.e("imgClick: ", "권한 없음");
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    1000);
        }


        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            Log.e("imgClick: ", "권한 없음");
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1001);
        }
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            Log.e("imgClick: ", "권한 없음");
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    1001);
        }
        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
        finish();
    }






}

