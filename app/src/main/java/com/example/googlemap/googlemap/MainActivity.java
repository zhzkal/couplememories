package com.example.googlemap.googlemap;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.Tag;
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

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
    int ddaycount=0;
    int nextdday=0;
    TextView tvdday;
    TextView tvdday2;
    int firstday;
    String nextdaytext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        gpslist = (ArrayList<Gpsdata>) getIntent().getSerializableExtra("gpslist");

        id=intent.getStringExtra("id");
        coupleid=intent.getStringExtra("coupleid");


        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        String nowdate = formatter.format(date);
//지금날짜
        String year=nowdate.substring(0,4);
        String month= nowdate.substring(4,6);
        String day= nowdate.substring(6,8);


        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Calendar todaCal = Calendar.getInstance(); //오늘날자 가져오기
            Calendar ddayCal = Calendar.getInstance(); //오늘날자를 가져와 변경시킴
            String year2=coupleid.substring(0,4);
            String month2= coupleid.substring(4,6);
            String day2= coupleid.substring(6,8);

            ddayCal.set(Integer.parseInt(year2),Integer.parseInt(month2)-1,Integer.parseInt(day2));// D-day의 날짜를 입력
            Log.e("테스트",simpleDateFormat.format(todaCal.getTime()) + "");
            Log.e("테스트",simpleDateFormat.format(ddayCal.getTime()) + "");

            long today = todaCal.getTimeInMillis()/86400000; //->(24 * 60 * 60 * 1000) 24시간 60분 60초 * (ms초->초 변환 1000)
            long dday = ddayCal.getTimeInMillis()/86400000;
            long count = today - dday; // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.
            ddaycount= (int) count;

            if(ddaycount%100>ddaycount%365){
                firstday=ddaycount/365;
                nextdday=  ddaycount%365;
                nextdday=365-nextdday;
                nextdaytext=firstday+"주년 -";
            }else{
                firstday=ddaycount/100;
                firstday=firstday+1;
                nextdday=ddaycount%100;
                nextdday=100-nextdday;
                nextdaytext=firstday+"00일 -";
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        tvdday=findViewById(R.id.ddayplus);
        tvdday.setText("D-day+"+ddaycount);
        tvdday2=findViewById(R.id.nextdday);
        tvdday2.setText(nextdaytext+nextdday);

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

