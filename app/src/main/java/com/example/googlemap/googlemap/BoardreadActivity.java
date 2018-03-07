package com.example.googlemap.googlemap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class BoardreadActivity extends AppCompatActivity {

    ListView listView;
    Adapter adapter;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readboard);

        listView = (ListView) findViewById(R.id.listView);

        adapter = new Adapter();
        listView.setAdapter(adapter);
        databaseReference.child("board").addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                BoardData boardData = dataSnapshot.getValue(BoardData.class);// chatData를 가져오고

                Log.d("asdf",boardData.toString());
                adapter.addItem(boardData);
                listView.setAdapter(adapter);
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





        /*editText = (EditText) findViewById(R.id.editText);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString();
                String mobile = "010-1000-1000";
                int age = 20;

                   adapter.notifyDataSetChanged();
            }
        });*/


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                BoardData item = (BoardData) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 : " + item.getTitle(), Toast.LENGTH_LONG).show();
            //미리보기 누르면 일로옴
                //인텐트 이동해서 다 보여주고 댓글달까?? 생각해보자
                Intent readmain = new Intent(BoardreadActivity.this, com.example.googlemap.googlemap.ReadmainActivity.class);

                readmain.putExtra("coupleid", item.getCoupleid());
                readmain.putExtra("id",item.getID());
                readmain.putExtra("date",item.getDate());
                readmain.putExtra("title",item.getTitle());
                startActivity(readmain);


            }
        });

    }

    class Adapter extends BaseAdapter {
        ArrayList<BoardData> items = new ArrayList<BoardData>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(BoardData item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            BoardItemView view = new BoardItemView(getApplicationContext());

            BoardData item = items.get(position);
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReferenceFromUrl("gs://couplememories-196504.appspot.com");

            //다운로드할 파일을 가르키는 참조 만들기
            StorageReference pathReference = storageReference.child(item.getPicture());
            //Url을 다운받기
            pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Toast.makeText(getApplicationContext(), "다운로드 성공 : "+ uri, Toast.LENGTH_SHORT).show();

                    view.setImage(uri);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "다운로드 실패", Toast.LENGTH_SHORT).show();
                }
            });


            view.setName(item.getTitle());
            view.setMobile(item.getContent());
            view.setAge(item.getID());


            return view;
        }
    }

}
