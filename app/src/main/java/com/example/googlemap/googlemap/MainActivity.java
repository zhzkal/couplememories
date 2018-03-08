package com.example.googlemap.googlemap;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    // ...
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    Gpsdata gpsdata;
    ArrayList<Gpsdata> gpslist=new ArrayList<>();
    ArrayList<Gpsdata> gpslist2=new ArrayList<>();
    ArrayList<Uridata> urilist=new ArrayList<>();
    TextView tv;
    String id;
    String coupleid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        coupleid=intent.getStringExtra("coupleid");
        gpslist = (ArrayList<Gpsdata>) getIntent().getSerializableExtra("gpslist");
        //urilist = (ArrayList<Uridata>) getIntent().getParcelableExtra("urilist");


       /* for (int i=0;i<gpslist.size();i++){
            Log.d("결과는?", String.valueOf(gpslist.get(i).getLatitude()));
            Log.d("결과는?", String.valueOf(gpslist.get(i).getLongitude()));
           // Log.d("결과는?", String.valueOf(urilist.get(i).getUri()));
           // gpsdata=new Gpsdata(gpslist.get(i).getLatitude(),gpslist.get(i).getLongitude(),urilist.get(i).getUri());
            gpslist2.add(gpsdata);
        }*/
    }

    public void buttonClick(View v) {
        switch (v.getId()) {
            case R.id.bt1:
                Intent gomap = new Intent(this, MapsActivity.class);
                gomap.putExtra("gpslist", gpslist);
                startActivity(gomap);
                break;

            case R.id.bt2:
                Intent insertpicture = new Intent(this, com.example.googlemap.googlemap.insertpicture.class);

                insertpicture.putExtra("coupleid", coupleid);
                insertpicture.putExtra("id",id);
                startActivity(insertpicture);
                break;

            case R.id.bt3:

                Intent chat = new Intent(this, com.example.googlemap.googlemap.chatActivity.class);
                chat.putExtra("coupleid", coupleid);
                chat.putExtra("id",id);
                startActivity(chat);
                break;

            case R.id.bt4:
                // Write a message to the database
                Intent intent5 = new Intent(this, BoardreadActivity.class);
                startActivity(intent5);
                break;
            case R.id.btlogout:
                // Write a message to the database

                Intent intent4 = new Intent(this, LoginActivity.class);
                startActivity(intent4);
                break;


        }
    }

}

