package com.example.guetshareimagedemo.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.databinding.ActivityShowImageDetailsBinding;
import com.example.guetshareimagedemo.utils.BitmapUtils;
import com.example.guetshareimagedemo.utils.Constants;
import com.example.guetshareimagedemo.utils.StatusBarUtil;

import java.io.IOException;

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
        String path = intent.getStringExtra("search");
        String spaceImagePath = intent.getStringExtra("spaceImage");
        if (url != null) {
            Glide.with(this).load(url).into(showImageDetailsBinding.imgDetails);
        }
        if (path != null) {
            Glide.with(this).load(path).into(showImageDetailsBinding.imgDetails);
        }
        if (spaceImagePath != null) {
            Glide.with(this).load(spaceImagePath).into(showImageDetailsBinding.imgDetails);
        }

        showImageDetailsBinding.imgDetails.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (url != null) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, url);
                    intent.setType("text/plain");
                    startActivity(intent);

                }
                if (path != null) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, url);
                    intent.setType("text/plain");
                    startActivity(intent);
                }
                return false;
            }
        });
    }

}