package com.example.guetshareimagedemo.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.guetshareimagedemo.R;

import java.util.ArrayList;
import java.util.List;

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
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LCQuery<LCObject> query = new LCQuery<>("LikeImageUrl");
                        LCObject object = new LCObject("LikeImageUrl");
                        LCUser user = LCUser.getCurrentUser();
                        Log.d("TestActivity", "user----->" + user.getObjectId());
                        query.whereEqualTo("account", user.getUsername());
                        query.findInBackground().subscribe(new Observer<List<LCObject>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull List<LCObject> lcObjects) {
                                Log.d("TestActivity", "url------>" + lcObjects.get(0).get("account"));
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                    }
                }).start();

            }
        });
    }
}