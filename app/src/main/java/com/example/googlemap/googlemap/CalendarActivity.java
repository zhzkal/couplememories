package com.example.googlemap.googlemap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by SCITMASTER on 2018-03-09.
 */

public class CalendarActivity extends Activity {
    String spday;
    Button btnEnd;
    EditText calcontent;
    CalendarView calendar;
    ArrayList<MemberData> memberlist = new ArrayList<>();
    ArrayList<SpdayData> spdaylist = new ArrayList<>();
    ArrayList<String> keylist = new ArrayList<>();
    Intent intent = getIntent();
    String key;
    String userName;
    String coupleid;
    SpdayData spdayData;
    TimePicker time;
    TextView tvYear, tvMonth, tvDay, tvHour, tvMinute;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        setTitle("시간 예약");
        Intent intent = getIntent();
        userName = intent.getStringExtra("id");
        coupleid = intent.getStringExtra("coupleid");
        btnEnd = (Button) findViewById(R.id.btnEnd);
        calendar = (CalendarView) findViewById(R.id.calendarView1);
        time = (TimePicker) findViewById(R.id.timePicker1);
        tvYear = (TextView) findViewById(R.id.tvYear);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        tvDay = (TextView) findViewById(R.id.tvDay);
        tvHour = (TextView) findViewById(R.id.tvHour);
        tvMinute = (TextView) findViewById(R.id.tvMinute);
        calcontent=(EditText)findViewById(R.id.calcontent);
        databaseReference.child("member").addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MemberData memberData = dataSnapshot.getValue(MemberData.class);// chatData를 가져오고
                memberlist.add(memberData);
                keylist.add(dataSnapshot.getKey());
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        btnEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                java.util.Calendar curDate = java.util.Calendar.getInstance();
                curDate.setTimeInMillis(calendar.getDate());
                spday = Integer.toString(curDate.get(Calendar.YEAR));


         if (curDate.get(Calendar.MONTH) + 1 < 10) {
                    spday = spday + "0" + Integer.toString(curDate.get(Calendar.MONTH) + 1);
                } else {
                    spday = spday + Integer.toString(curDate.get(Calendar.MONTH) + 1);
                }
                if (curDate.get(Calendar.DATE) < 10) {
                    spday = spday + "0" + Integer.toString(curDate.get(Calendar.DATE));
                } else {
                    spday = spday + Integer.toString(curDate.get(Calendar.DATE));
                }
                Log.d("날짜 정보", spday);

                if (calcontent.getText()==null){}else{
                    for (int i = 0; i < memberlist.size(); i++) {
                        if (memberlist.get(i).getId().equals(userName)) {
                            spdayData=new SpdayData(spday,String.valueOf(calcontent.getText()));
                            key=keylist.get(i);
                        }
                    }

                    databaseReference.child("member").child(key).child("spdaylist").push().setValue(spdayData);

                    tvYear.setText(Integer.toString(curDate.get(Calendar.YEAR)));
                    tvMonth.setText(Integer.toString(curDate.get(Calendar.MONTH) + 1));
                    tvDay.setText(Integer.toString(curDate.get(Calendar.DATE)));
                    tvHour.setText(Integer.toString(time.getCurrentHour()));
                    tvMinute.setText(Integer.toString(time.getCurrentMinute()));


                }


            }
        });
    }
}
