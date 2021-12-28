package com.example.guetshareimagedemo.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.utils.BitmapUtils;
import com.example.guetshareimagedemo.utils.Constant;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import cn.leancloud.LCFile;
import cn.leancloud.LCObject;
import cn.leancloud.LCQuery;
import cn.leancloud.LCUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TestActivity extends AppCompatActivity {

    private List<String> name = new ArrayList<>();
    private List<String> name2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button testButton = findViewById(R.id.testButton);
        ImageView imageView = findViewById(R.id.imageView);
        String url = "/storage/emulated/0/DCIM/Screenshots/Screenshot_2021-12-23-11-10-36-448_com.sina.weibo.jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(url);
        String base64 = BitmapUtils.bitmapToBase64(bitmap);
        Glide.with(this).load(Constant.HEAD_BASE_64 + base64).into(imageView);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LCQuery<LCObject> query = new LCQuery<>("UpLoadImageUrl");
                LCObject object = new LCObject("UpLoadImageUrl");
                object.put("account", LCUser.getCurrentUser().getUsername());
                object.put("base64", base64);
                object.saveInBackground().subscribe(new Observer<LCObject>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull LCObject lcObject) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        });




    }

    private Bitmap getImage(byte[] bytes, BitmapFactory.Options options){
        if (bytes != null) {
            if (options != null) {
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
            }else {
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        }
        return null;
    }
}