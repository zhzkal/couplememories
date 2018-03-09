package com.example.googlemap.googlemap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.NumberFormat;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    Marker selectedMarker;
    View marker_root_view;
    TextView tv_marker;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    ArrayList<BoardData> boardlist = new ArrayList<>();
    View tempinfo;
    ArrayList<Gpsdata> gpslist;
    private GoogleMap mMap;
    String userName;
    String coupleid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        Intent intent = getIntent();
        userName = intent.getStringExtra("id");
        coupleid = intent.getStringExtra("coupleid");

        gpslist = (ArrayList<Gpsdata>) getIntent().getSerializableExtra("gpslist");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        databaseReference.child("board").addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                BoardData boardData = dataSnapshot.getValue(BoardData.class);// chatData를 가져오고
                boardData.setKey(dataSnapshot.getKey());
                Log.d("왜 다 같음?",boardData.getKey());
                boardlist.add(boardData);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.537523, 126.96558), 10));
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);

        setCustomMarkerView();
        getSampleMarkerItems();


    }

    private void setCustomMarkerView() {

        marker_root_view = LayoutInflater.from(this).inflate(R.layout.marker_layout, null);
        tv_marker = (TextView) marker_root_view.findViewById(R.id.tv_marker);
    }


    private void getSampleMarkerItems() {
        ArrayList<MarkerItem> sampleList = new ArrayList();
        for (int i = 0; i < gpslist.size(); i++) {
            //  Log.d("uri없는데?", String.valueOf(gpslist.get(i).getUri()));
            MarkerItem item = new MarkerItem(gpslist.get(i).getLatitude(), gpslist.get(i).getLongitude(), gpslist.get(i).getTitle());
            sampleList.add(item);
        }

        for (MarkerItem markerItem : sampleList) {
            addMarker(markerItem, false);
        }

    }


    private Marker addMarker(MarkerItem markerItem, boolean isSelectedMarker) {

        LatLng position = new LatLng(markerItem.getLat(), markerItem.getLon());
        Log.d("좌표", String.valueOf(markerItem.getLat()));
        Log.d("좌표", String.valueOf(markerItem.getLon()));
        Log.d("제목", String.valueOf(markerItem.getTitle()));

        String title = markerItem.getTitle();

        tv_marker.setText(title);

        if (isSelectedMarker) {
            tv_marker.setBackgroundResource(R.drawable.ic_marker_phone_blue);
            tv_marker.setTextColor(Color.WHITE);
        } else {
            tv_marker.setBackgroundResource(R.drawable.ic_marker_phone);
            tv_marker.setTextColor(Color.BLACK);
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(title);
        markerOptions.position(position);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker_root_view)));


        return mMap.addMarker(markerOptions);

    }


    // View를 Bitmap으로 변환
    private Bitmap createDrawableFromView(Context context, View view) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }


    private Marker addMarker(Marker marker, boolean isSelectedMarker) {
        double lat = marker.getPosition().latitude;
        double lon = marker.getPosition().longitude;
        String title = marker.getTitle();
        MarkerItem temp = new MarkerItem(lat, lon, title);
        return addMarker(temp, isSelectedMarker);

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        String pic=null;
        String title = null;
        String content=null;String date=null;
        String id = null;String coupleid = null;
        String key ="";
        CameraUpdate center = CameraUpdateFactory.newLatLng(marker.getPosition());
        mMap.animateCamera(center);

        changeSelectedMarker(marker);

        //마커에서 제목가져오고
        for (int i = 0; i < boardlist.size(); i++) {

            if (boardlist.get(i).getTitle().equals(marker.getTitle())&&boardlist.get(i).getLatitude()==marker.getPosition().latitude) {
                pic = boardlist.get(i).getPicture();
                title = boardlist.get(i).getTitle();
                content = boardlist.get(i).getContent();
                id = boardlist.get(i).getID();
                coupleid= boardlist.get(i).getCoupleid();
                date = boardlist.get(i).getDate();
                key =boardlist.get(i).getKey();
                Log.d("키값!!",key);
                break;
            }
        }

       Intent readmain = new Intent(MapsActivity.this, com.example.googlemap.googlemap.ReadmainActivity.class);

        readmain.putExtra("coupleid", coupleid);
        readmain.putExtra("id",id);
        readmain.putExtra("date",date);
        readmain.putExtra("title",title);
        readmain.putExtra("key",key);
        startActivity(readmain);

       /* BoardItemView view = new BoardItemView(getApplicationContext());
        view.setName(title);//title
        view.setAge(id);//id
        view.setMobile(content);//content
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl("gs://couplememories-196504.appspot.com");

        //다운로드할 파일을 가르키는 참조 만들기
        StorageReference pathReference = storageReference.child(pic);
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
        tempinfo=view;*/
/*
        tempinfo=   LayoutInflater.from(this).inflate(R.layout.board_item, null);
        tempinfo.findViewById(R.id.imageView);
        tempinfo.findViewById(R.id.textView);
        tempinfo.findViewById(R.id.textView2);
        tempinfo.findViewById(R.id.textView3);*/
        //그 제목가지고 board데이터에서 글 찾고
        //사진 제목 내용 작성자 정도 가져오자
        //그리고 쏴주자
        return true;
    }


    private void changeSelectedMarker(Marker marker) {
        // 선택했던 마커 되돌리기
        if (selectedMarker != null) {
            addMarker(selectedMarker, false);
            selectedMarker.remove();
        }
        // 선택한 마커 표시
        if (marker != null) {
            selectedMarker = addMarker(marker, true);
            marker.remove();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        changeSelectedMarker(null);
    }

    //뒤로가기
    public void buttonClick(View v) {
        switch (v.getId()) {
            case R.id.bt1:
                finish();
              /*  Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);*/
                break;
        }
    }
}
