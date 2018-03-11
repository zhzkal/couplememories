package com.example.googlemap.googlemap;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * Created by SCITMASTER on 2018-02-28.
 */
public class BoardItemView extends LinearLayout {
    TextView textView;
    TextView textView2;
    TextView textView3;
    ImageView imageView;

    public BoardItemView(Context context) {
        super(context);

        init(context);
    }

    public BoardItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.board_item, this, true);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void setName(String name) {
        textView.setText("작성자:"+name);
    }

    public void setMobile(String mobile) {
        textView2.setText(mobile);
    }

    public void setAge(String age) {
        textView3.setText(age);
    }

    public void setImage(Uri uri) {
        Glide.with(this)
                .load(uri)
                .into(imageView);
    }
}
