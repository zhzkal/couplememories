package com.example.googlemap.googlemap;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

/**
 * Created by SCITMASTER on 2018-02-27.
 */

public class chatActivity extends Activity{
    ListView listView;
    EditText editText;
    Button sendButton;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        listView = (ListView) findViewById(R.id.chatlist);
        editText = (EditText) findViewById(R.id.sendtext);
        sendButton = (Button) findViewById(R.id.sendbutton);

       String userName = "user" + new Random().nextInt(10000);  // 랜덤한 유저 이름 설정 ex) user1234

// 기본 Text를 담을 수 있는 simple_list_item_1을 사용해서 ArrayAdapter를 만들고 listview에 설정
        final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        listView.setAdapter(adapter);

        sendButton.setOnClickListener((view) -> {
            ChatData chatData = new ChatData(userName, editText.getText().toString());  // 유저 이름과 메세지로 chatData 만들기
            databaseReference.child("message").push().setValue(chatData);  // 기본 database 하위 message라는 child에 chatData를 list로 만들기
            editText.setText("");
        });
        sendButton.setOnClickListener((view) -> {
            ChatData chatData = new ChatData(userName, editText.getText().toString());  // 유저 이름과 메세지로 chatData 만들기
            databaseReference.child("message").push().setValue(chatData);  // 기본 database 하위 message라는 child에 chatData를 list로 만들기
            editText.setText("");
        });
        databaseReference.child("message").addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);  // chatData를 가져오고
                adapter.add(chatData.getUserName() + ": " + chatData.getMessage());  // adapter에 추가합니다.
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


    }


}
