package com.example.googlemap.googlemap;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by SCITMASTER on 2018-02-27.
 */

public class chatActivity extends Activity {
    ListView listView;
    EditText editText;
    Button sendButton;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final int[] count = {0};

        listView = (ListView) findViewById(R.id.chatlist);
        editText = (EditText) findViewById(R.id.sendtext);
        sendButton = (Button) findViewById(R.id.sendbutton);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("id");
        String coupleid = intent.getStringExtra("coupleid");

// 기본 Text를 담을 수 있는 simple_list_item_1을 사용해서 ArrayAdapter를 만들고 listview에 설정
        final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        listView.setAdapter(adapter);


        sendButton.setOnClickListener((view) -> {
            if (editText.getText().toString().equals("")) {
            } else {
                Log.d("채팅채팅", userName);
                Log.d("채팅채팅", coupleid);
                //Unique한 파일명을 만들자.
                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                String nowdate = formatter.format(date);

                ChatData chatData = new ChatData(userName, editText.getText().toString(), coupleid, 0, nowdate);  // 유저 이름과 메세지로 chatData 만들기
                databaseReference.child("message").push().setValue(chatData);  // 기본 database 하위 message라는 child에 chatData를 list로 만들기
                editText.setText("");
                listView.setSelection(adapter.getCount() );
            }
        });

        ChildEventListener childEventListener = databaseReference.child("message").addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("계속 실행되는거지?", "응그럴걸");
                ChatData chatData = dataSnapshot.getValue(ChatData.class);// chatData를 가져오고
                String readcheck = "";


                if (chatData.getCoupleID().equals(coupleid)) {//커플아이디가 같아야 보이며

                    if (chatData.getReadcheck() == 1) {
                        readcheck = "";//읽음

                    } else {
                        readcheck = "1";//읽지 않음
                    }

                    if (chatData.getUserName().equals(userName)) {
                        //여긴 내 메세지
                        Log.d("단말기",userName);
                        Log.d("디비",chatData.getUserName());


                    } else {
                        if (chatData.getReadcheck() == 1) {
                            //읽은건 알람이 필요없지
                        } else {


                            try{
                                //수정부분분
                               //안읽은거 알람처리해야함 1으로 바꿔주자
                                String key = dataSnapshot.getKey();
                                databaseReference.child("message").child(key).child("readcheck").setValue(1);  // 기본 database 하위 message라는 child에 chatData를 list로 만들기

                                Log.d("거창하기는","아니네 여기옴??"+key);

                            }
                            catch (Exception e){
                                e.printStackTrace();
                                Log.d("거창하기는","여기온거지?");
                            }
                            count[0] = count[0] + 1;
                            NotificationManager notificationManager = (NotificationManager) chatActivity.this.getSystemService(chatActivity.this.NOTIFICATION_SERVICE);
                            Intent intent1 = new Intent(chatActivity.this.getApplicationContext(), MainActivity.class); //인텐트 생성.


                            Notification.Builder builder = new Notification.Builder(getApplicationContext());
                            intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);//현재 액티비티를 최상으로 올리고, 최상의 액티비티를 제외한 모든 액티비티를


                            PendingIntent pendingNotificationIntent = PendingIntent.getActivity(chatActivity.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                            //PendingIntent는 일회용 인텐트 같은 개념입니다.
                            builder.setSmallIcon(R.drawable.on)
                                    .setNumber(count[0]).setContentTitle(chatData.getUserName()).setContentText(chatData.getMessage())
                                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingNotificationIntent);
                            //해당 부분은 API 4.1버전부터 작동합니다.


                            notificationManager.notify(1, builder.build()); // Notification send

                            count[0] = 0;
                        }
                    }
                    adapter.add(chatData.getUserName() + ": " + chatData.getMessage() + " " + readcheck + " " + chatData.getDate());  // adapter에 추가합니다.
                    listView.setSelection(adapter.getCount() );


                }
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


    }


}
