package com.example.googlemap.googlemap;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class ReadmainActivity extends AppCompatActivity {

    ListView listView;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    ArrayList<BoardData> boardlist = new ArrayList<>();
    String id;
    String coupleid;
    String date;
    String title;
    TextView tvtitle;
    TextView tvid;
    TextView tvdate;
    TextView tvcontent;
    ImageView ivimg;
    String key;
    EditText commentet;
    Button commentbt;
    ListView commentlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readmain);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        coupleid = intent.getStringExtra("coupleid");
        date = intent.getStringExtra("date");
        title = intent.getStringExtra("title");
        key = intent.getStringExtra("key");
        tvtitle = findViewById(R.id.readmaintitle);
        tvid = findViewById(R.id.readmainusername);
        tvdate = findViewById(R.id.readmaindate);
        tvcontent = findViewById(R.id.readmaincontent);
        ivimg = findViewById(R.id.readmainpic);
        commentet = findViewById(R.id.sendcomment);
        commentbt = findViewById(R.id.commentbutton);
        commentlistView = findViewById(R.id.comentlist);

        databaseReference.child("board").addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                BoardData boardData = dataSnapshot.getValue(BoardData.class);// chatData를 가져오고
                if (boardData.getID().equals(id) && boardData.getDate().equals(date) && boardData.getTitle().equals(title)) {
                    tvtitle.setText(boardData.getTitle());
                    tvid.setText(boardData.getID());
                    tvdate.setText(boardData.getDate());
                    tvcontent.setText(boardData.getContent());

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageReference = storage.getReferenceFromUrl("gs://couplememories-196504.appspot.com");

                    //다운로드할 파일을 가르키는 참조 만들기
                    StorageReference pathReference = storageReference.child(boardData.getPicture());
                    //Url을 다운받기
                    pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(ReadmainActivity.this)
                                    .load(uri)
                                    .into(ivimg);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "다운로드 실패", Toast.LENGTH_SHORT).show();
                        }
                    });

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


        final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        commentlistView.setAdapter(adapter);


        commentbt.setOnClickListener((view) -> {
            if (commentet.getText().toString().equals("")) {
            } else {

                Log.d("댓글댓글", id);
                Log.d("댓글댓글", coupleid);
                //Unique한 파일명을 만들자.
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_hh:mm");
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                String nowdate = formatter.format(date);

                ChatData chatData = new ChatData(id, commentet.getText().toString(), coupleid, 0, nowdate);  // 유저 이름과 메세지로 chatData 만들기
                databaseReference.child("board").child(key).child("comment").push().setValue(chatData);  // 기본 database 하위 message라는 child에 chatData를 list로 만들기
                commentet.setText("");
//                listView.setSelection(adapter.getCount()-1);
            }
        });

            ChildEventListener childEventListener = databaseReference.child("board").child(key).child("comment").addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Log.d("계속 실행되는거지?", "응그럴걸");
                    ChatData chatData = dataSnapshot.getValue(ChatData.class);// chatData를 가져오고
                    String readcheck = "";
                    adapter.add(chatData.getDate() + ") " + chatData.getUserName() + ": " + chatData.getMessage());  // adapter에 추가합니다.



                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    ChatData chatData = dataSnapshot.getValue(ChatData.class);// chatData를 가져오고
                    String readcheck = "";



                        adapter.add(chatData.getDate() + ") " + chatData.getUserName() + ": " + chatData.getMessage());  // adapter에 추가합니다.




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
