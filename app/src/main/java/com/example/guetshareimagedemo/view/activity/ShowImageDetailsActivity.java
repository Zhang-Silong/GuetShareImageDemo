package com.example.guetshareimagedemo.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.utils.Constant;

public class ShowImageDetailsActivity extends AppCompatActivity {

    private ImageView showImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image_details);
        showImg = findViewById(R.id.img_details);

        Intent intent = getIntent();
        String url = intent.getStringExtra(Constant.KEY_SHOW_IMG_DETAILS);
        Glide.with(this).load(url).into(showImg);
    }
}