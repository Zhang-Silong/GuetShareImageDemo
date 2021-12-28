package com.example.guetshareimagedemo.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.databinding.ActivityShowImageDetailsBinding;
import com.example.guetshareimagedemo.utils.Constants;
import com.example.guetshareimagedemo.utils.StatusBarUtil;

public class ShowImageDetailsActivity extends AppCompatActivity {


    private ActivityShowImageDetailsBinding showImageDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showImageDetailsBinding = ActivityShowImageDetailsBinding.inflate(getLayoutInflater());
        setContentView(showImageDetailsBinding.getRoot());
        StatusBarUtil.StatusBarLightMode(this);
        StatusBarUtil.transparencyBar(this);
        Intent intent = getIntent();
        String url = intent.getStringExtra(Constants.KEY_SHOW_IMG_DETAILS);
        Glide.with(this).load(url).into(showImageDetailsBinding.imgDetails);
    }
}