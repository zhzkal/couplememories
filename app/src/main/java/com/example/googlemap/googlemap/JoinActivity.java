package com.example.googlemap.googlemap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by SCITMASTER on 2018-02-28.
 */

public class JoinActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    EditText id;
    EditText pw;
    EditText name;
    EditText coupleid;
    boolean result=true;
    int count = 0;
    ArrayList<MemberData> memberdata = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);


        FirebaseDatabase.getInstance().getReference().child("member").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                try {
                    MemberData a = dataSnapshot.getValue(MemberData.class);

                    memberdata.add(a);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("MainActivity", "ChildEventListener - onChildAdded : " + dataSnapshot.getValue());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("MainActivity", "ChildEventListener - onChildChanged : " + s);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("MainActivity", "ChildEventListener - onChildRemoved : " + dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d("MainActivity", "ChildEventListener - onChildMoved" + s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("MainActivity", "ChildEventListener - onCancelled" + databaseError.getMessage());
            }
        });
    }

    public void buttonClick(View v) {
        switch (v.getId()) {
            case R.id.joinbt:

                id = findViewById(R.id.joinid);
                pw = findViewById(R.id.joinpw);
                name = findViewById(R.id.joinname);
                coupleid = findViewById(R.id.joincoupleid);
                if(String.valueOf(id.getText()).equals("")){result=false;}
                for (int i = 0; i < memberdata.size(); i++) {
                    if (String.valueOf(id.getText()).equals(memberdata.get(i).getId())) {
                        Toast.makeText(this, "아이디가 이미 존재합니다.", Toast.LENGTH_SHORT).show();
                        result=false;
                    }else
                    if (String.valueOf(coupleid.getText()).equals(memberdata.get(i).getCoupleid())) {

                        count++;

                        if (count >= 2) {
                            Toast.makeText(this, "이미 커플 아이디가 2개 존재합니다.", Toast.LENGTH_SHORT).show();
                            result=false;
                        }

                    }

                }

                if(result==true){
                    MemberData memberdata = new MemberData(String.valueOf(name.getText()), String.valueOf(pw.getText()),String.valueOf(id.getText()) , String.valueOf(coupleid.getText()));
                    databaseReference.child("member").push().setValue(memberdata);  // 기본 database 하위 message라는 child에 chatData를 list로 만들기

                    Intent gologin = new Intent(this, LoginActivity.class);
                    startActivity(gologin);
                }

                break;

          /*  case R.id.bt2:
                Intent goback = new Intent(this, LoginActivity.class);
                startActivity(goback);

                break;*/
        }
    }
}
