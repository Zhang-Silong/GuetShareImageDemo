package com.example.guetshareimagedemo.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.databinding.ActivityRegisterBinding;
import com.google.android.material.textfield.TextInputEditText;

import cn.leancloud.LCUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding registerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(registerBinding.getRoot());
        initView();
    }

    private void initView() {
        registerBinding.userRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = registerBinding.userAccounts.getText().toString();
                String password = registerBinding.userPasswords.getText().toString();
                String confirmPassword = registerBinding.userConfirmPassword.getText().toString();
                if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password) &&
                        !TextUtils.isEmpty(confirmPassword) && password.equals(confirmPassword)) {
                    registerBinding.registerProgress.setVisibility(View.VISIBLE);
                    LCUser user = new LCUser();
                    user.setUsername(account);
                    user.setPassword(password);
                    user.signUpInBackground().subscribe(new Observer<LCUser>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull LCUser lcUser) {
                            registerBinding.registerProgress.setVisibility(View.GONE);
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            registerBinding.registerProgress.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, "注册失败，请重新尝试！", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                }else {
                    Toast.makeText(RegisterActivity.this, "账号或密码输入错误，请重新输入！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}