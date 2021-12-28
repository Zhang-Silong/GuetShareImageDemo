package com.example.guetshareimagedemo.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.databinding.ActivityLoginBinding;
import com.example.guetshareimagedemo.view.fragment.UserFragment;
import com.google.android.material.textfield.TextInputEditText;

import cn.leancloud.LCUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
        initView();
    }

    private void initView() {
        loginBinding.registerTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        loginBinding.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        loginBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = loginBinding.userLoginAccount.getText().toString();
                String password = loginBinding.userLoginPassword.getText().toString();
                if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)) {
                    LCUser.logIn(account, password).subscribe(new Observer<LCUser>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull LCUser lcUser) {
                            Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Toast.makeText(LoginActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                }else {
                    Toast.makeText(LoginActivity.this, "账号或密码输入有误，请重新输入！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}