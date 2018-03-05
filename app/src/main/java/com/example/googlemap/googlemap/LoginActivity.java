package com.example.googlemap.googlemap;

import android.content.Intent;
import android.graphics.Paint;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by SCITMASTER on 2018-02-28.
 */

public class LoginActivity extends AppCompatActivity {


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("member");
    EditText id;
    EditText pw;
    int check = 0;
    ArrayList<MemberData> memberdata = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // 데이터베이스 읽기 #3. ChildEventListener

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
            case R.id.loginbt:
                id = findViewById(R.id.loginid);
                String temp = String.valueOf(id.getText());
                pw = findViewById(R.id.loginpw);
                String temppw = String.valueOf(pw.getText());
                Log.d("버튼안에 들어옴", "ㅇㅇ");
                // Read from the database
                for (int i = 0; i < memberdata.size(); i++) {

                    if (memberdata.get(i).getId().equals(temp)) {

                        if (memberdata.get(i).getPw().equals(temppw)) {
                            Intent gomain = new Intent(this, MainActivity.class);
                            gomain.putExtra("id", String.valueOf(id.getText()));
                            gomain.putExtra("coupleid", memberdata.get(i).getCoupleid());
                            startActivity(gomain);
                            check = 1;
                        }

                    }
                }
                if (check == 0) {
                    Toast.makeText(this, "id or PW가 다릅니다.", Toast.LENGTH_SHORT).show();

                }

                break;

            case R.id.gojoinbt:
                Intent gojoin = new Intent(this, JoinActivity.class);
                startActivity(gojoin);
                break;
        }
    }


}
