package com.example.googlemap.googlemap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    // ...
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference myRef = database.child("couple1").child("object").child("title");
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void buttonClick(View v) {
        switch (v.getId()) {
            case R.id.bt1:
                Intent gomap = new Intent(this, MapsActivity.class);
                startActivity(gomap);
                break;

            case R.id.bt2:
                Intent insertpicture = new Intent(this, com.example.googlemap.googlemap.insertpicture.class);
                startActivity(insertpicture);

                break;

            case R.id.bt3:

                Intent intent3 = new Intent(this, com.example.googlemap.googlemap.chatActivity.class);
                startActivity(intent3);
                break;

            case R.id.bt4:
                // Write a message to the database
                mDatabase.child("couple1").child("object").child("title").setValue("바뀌니?");

                /*
                myRef.setValue("Hello, World!");

                // Read from the database
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getValue(String.class);
                        Log.d("태그", "Value is: " + value);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("태그", "Failed to read value.", error.toException());
                    }
                });*/
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String title = dataSnapshot.getValue(String.class);
                tv = findViewById(R.id.textview1);
                tv.setText(title);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                String s = data.getStringExtra("returnData");
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

            }
        }


    }
}

