package com.example.guetshareimagedemo.view.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.utils.StatusBarUtil;

import cn.leancloud.LCUser;

public class UserDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ImageView back;
    private Button quitLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        StatusBarUtil.transparencyBar(this);
        StatusBarUtil.StatusBarLightMode(this);
        initView();
    }

    private void initView(){
        toolbar = findViewById(R.id.user_details_toolbar);
        back = findViewById(R.id.back);
        quitLogin = findViewById(R.id.quit_login);

        back.setOnClickListener(this);
        quitLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.quit_login:
                LCUser.logOut();
                finish();
        }
    }
}