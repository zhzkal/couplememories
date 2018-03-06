package com.example.googlemap.googlemap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // ...

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
    }

    public void buttonClick(View v) {
        switch (v.getId()) {
            case R.id.bt1:
                Intent gomap = new Intent(this, MapsActivity.class);
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

