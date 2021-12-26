package com.example.guetshareimagedemo.view;

import android.app.Application;

import cn.leancloud.LCObject;
import cn.leancloud.LeanCloud;

/**
 * Created by ZhangSilong on 2021/12/23.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeanCloud.initialize(this, "k3d2yY5Uut53BLb2vyqtQ0Ia-gzGzoHsz",
                "MuL0q4PiQuSkofsn7g9aPqdb",
                "https://k3d2yy5u.lc-cn-n1-shared.com");

        /*LCObject testObject = new LCObject("TestObject");
        testObject.put("words", "Hello world!");
        testObject.saveInBackground().blockingSubscribe();*/
    }
}
