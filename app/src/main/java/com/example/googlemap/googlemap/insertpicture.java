package com.example.googlemap.googlemap;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.app.Activity;

import android.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;

import android.content.Intent;

import android.content.SharedPreferences;

import android.database.Cursor;

import android.graphics.Bitmap;

import android.media.ExifInterface;
import android.net.Uri;

import android.nfc.Tag;
import android.os.Bundle;

import android.os.Environment;

import android.provider.MediaStore;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.View;

import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedOutputStream;

import java.io.File;

import java.io.FileOutputStream;


public class insertpicture extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private static final String TAG = "InsertPicture";
    private Button btChoose;
    private Button btUpload;
    private ImageView ivPreview;
    private EditText ETtitle;
    private EditText ETtext;

    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertpic);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("id");
        String coupleid = intent.getStringExtra("coupleid");


        btChoose = (Button) findViewById(R.id.bt_choose);
        btUpload = (Button) findViewById(R.id.bt_upload);
        ivPreview = (ImageView) findViewById(R.id.iv_preview);
        ETtitle = findViewById(R.id.pictitle);
        ETtext = findViewById(R.id.pictext);
        //버튼 클릭 이벤트
        btChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이미지를 선택
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);
            }
        });

        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //업로드
                Log.d(TAG,userName);
                Log.d(TAG,coupleid);

                uploadFile(userName,coupleid);
            }
        });

    }
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };

        CursorLoader cursorLoader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    //결과 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request코드가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면
        if (requestCode == 0 && resultCode == RESULT_OK) {
            filePath = data.getData();
            Log.d(TAG, "uri:" + String.valueOf(filePath));
            try {
                //Uri 파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ivPreview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //upload the file
    private void uploadFile(String userName,String coupleid) {
        //업로드할 파일이 있으면 수행
        if (filePath != null) {
            //업로드 진행 Dialog 보이기
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();
            ExifInterface exifInterface = null;
            Log.d("성공", getRealPathFromURI(filePath));

            try {
                 exifInterface = new ExifInterface(getRealPathFromURI(filePath));

                Log.d("성공","어 오네");
            } catch (IOException e) {
                Log.d("실패","실패잼");
                e.printStackTrace();
            }
            GeoDegree geoDegree = new GeoDegree(exifInterface);
            //storage
            FirebaseStorage storage = FirebaseStorage.getInstance();

            //Unique한 파일명을 만들자.
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_hhmmss");
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            String filename = formatter.format(date) + ".png";
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd_hh:mm");
            long now2 = System.currentTimeMillis();
            Date date2 = new Date(now2);
            String nowdate2 = formatter2.format(date2);
            double la=0;
            double lo=0;
            try {
                la=geoDegree.getLatitude();
                lo=geoDegree.getLongitude();
            }catch (Exception e){
                Log.d("la", String.valueOf(la));
                Log.d("lo", String.valueOf(lo));
            }





            BoardData boardData = new BoardData(String.valueOf(ETtitle.getText()),userName , "images/" + filename, String.valueOf(ETtext.getText()), coupleid,nowdate2,la,lo);
            databaseReference.child("board").push().setValue(boardData);  // 기본 database 하위 message라는 child에 chatData를 list로 만들기

            //storage 주소와 폴더 파일명을 지정해 준다.
            StorageReference storageRef = storage.getReferenceFromUrl("gs://couplememories-196504.appspot.com").child("images/" + filename);
            //올라가거라...
            storageRef.putFile(filePath)
                    //성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                            Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") //이걸 넣어 줘야 아랫줄에 에러가 사라진다. 넌 누구냐?
                                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            //dialog에 진행률을 퍼센트로 출력해 준다
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }

}