package com.example.guetshareimagedemo.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.model.bean.LikeImage;
import com.example.guetshareimagedemo.model.bean.UpLoadImage;
import com.example.guetshareimagedemo.model.bean.User;
import com.example.guetshareimagedemo.model.bean.UserAttention;
import com.example.guetshareimagedemo.presenter.UserDataPresenter;
import com.example.guetshareimagedemo.utils.BitmapUtils;
import com.example.guetshareimagedemo.utils.Constants;
import com.example.guetshareimagedemo.utils.RealImagePathUtil;
import com.example.guetshareimagedemo.utils.StatusBarUtil;
import com.example.guetshareimagedemo.view.callback.IUserView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import cn.leancloud.LCObject;
import cn.leancloud.LCUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 用户个人中心界面（王禧玉）
 */
public class UserDetailsActivity extends AppCompatActivity implements View.OnClickListener, IUserView {

    private Toolbar toolbar;
    private ImageView back;
    private Button quitLogin;
    private ShapeableImageView userCenterImage;//头像
    private LinearLayout userCenterNickname;
    private LinearLayout userCenterGender;
    private LinearLayout userCenterSelfInfo;
    private TextView myNickname;//昵称
    private TextView myGender;//性别
    private TextView myInfo;//个性签名
    private AlertDialog alertDialog;//修改个人资料的对话框
    private View view;
    private EditText editUserMsg;
    private LCUser currentUser;//当前用户
    private UserDataPresenter userDataPresenter;//接收数据的Presenter层

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        //状态栏沉浸
        StatusBarUtil.transparencyBar(this);
        StatusBarUtil.StatusBarLightMode(this);
        //初始化控件
        initView();
        //获取当前已登录的用户
        currentUser = LCUser.getCurrentUser();
        userDataPresenter = new UserDataPresenter();
        userDataPresenter.registerView(this);
        //Presenter层将数据回调到View层
        userDataPresenter.getUserSelfData();
    }

    private void initView(){
        toolbar = findViewById(R.id.user_details_toolbar);
        back = findViewById(R.id.back);
        quitLogin = findViewById(R.id.quit_login);
        userCenterImage = findViewById(R.id.user_center_image);
        userCenterNickname = findViewById(R.id.user_center_name);
        userCenterGender = findViewById(R.id.user_center_gender);
        userCenterSelfInfo = findViewById(R.id.user_center_self_info);
        myNickname = findViewById(R.id.my_nickname);
        myGender = findViewById(R.id.my_gender);
        myInfo = findViewById(R.id.my_info);
        back.setOnClickListener(this);
        userCenterImage.setOnClickListener(this);
        userCenterNickname.setOnClickListener(this);
        userCenterGender.setOnClickListener(this);
        userCenterSelfInfo.setOnClickListener(this);
        quitLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击头像
            case R.id.user_center_image:
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
                View dialogView = LayoutInflater.from(this).inflate(R.layout.my_boottom_dialog, null);
                //TextView byCamera = dialogView.findViewById(R.id.by_camera);
                TextView byGallery = dialogView.findViewById(R.id.by_gallery);
                byGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //动态请求权限
                        if (ContextCompat.checkSelfPermission(UserDetailsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(UserDetailsActivity.this, new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                            }, 1);
                        }else {
                            //如果获取的权限，则打开系统相册
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            startActivityForResult(intent, 1);
                        }
                        bottomSheetDialog.dismiss();
                    }

                });
                bottomSheetDialog.setContentView(dialogView);
                bottomSheetDialog.show();
                break;
                //退出个人中心界面
            case R.id.back:
                finish();
                break;
                //点击昵称所在的布局触发修改
            case R.id.user_center_name:
                view = LayoutInflater.from(this).inflate(R.layout.my_dialog, null);
                editUserMsg = view.findViewById(R.id.edit_user_msg);
                AlertDialog.Builder nicknameBuilder = new AlertDialog.Builder(this)
                        .setView(view)
                        .setTitle("修改昵称")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String newNickname = editUserMsg.getText().toString();
                                if (!TextUtils.isEmpty(newNickname)) {
                                    myNickname.setText(newNickname);
                                }
                                dialogInterface.dismiss();
                                LCObject object = LCObject.createWithoutData("_User", currentUser.getObjectId());
                                object.put("nickname", newNickname);
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
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                alertDialog = nicknameBuilder.create();
                alertDialog.show();
                break;
                //点击性别所在的布局出发修改
            case R.id.user_center_gender:
                view = LayoutInflater.from(this).inflate(R.layout.my_dialog, null);
                //editUserGender = view.findViewById(R.id.edit_user_gender);
                String[] items = new String[]{"男", "女"};
                final String[] newGender = {""};
                AlertDialog.Builder genderBuilder = new AlertDialog.Builder(this)
                        .setTitle("修改性别")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                newGender[0] = items[i];
                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (!TextUtils.isEmpty(newGender[0])) {
                                    myGender.setText(newGender[0]);
                                }
                                //将修改结果上传到服务器
                                LCObject object = LCObject.createWithoutData("_User", currentUser.getObjectId());
                                object.put("gender", newGender[0]);
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
                                dialogInterface.dismiss();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                alertDialog = genderBuilder.create();
                alertDialog.show();
                break;
            //点击个性签名所在的布局出发修改
            case R.id.user_center_self_info:
                view = LayoutInflater.from(this).inflate(R.layout.my_dialog, null);
                editUserMsg = view.findViewById(R.id.edit_user_msg);
                AlertDialog.Builder infoBuilder = new AlertDialog.Builder(this)
                        .setView(view)
                        .setTitle("修改个性签名")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String newInfo = editUserMsg.getText().toString();
                                if (!TextUtils.isEmpty(newInfo)) {
                                    myInfo.setText(newInfo);
                                }
                                //将修改结果上传到服务器
                                LCObject object = LCObject.createWithoutData("_User", currentUser.getObjectId());
                                object.put("selfInfo", newInfo);
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
                                dialogInterface.dismiss();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                alertDialog = infoBuilder.create();
                alertDialog.show();
                break;
            case R.id.quit_login:
                LCUser.logOut();
                finish();
        }
    }

    /**
     * 访问手机相册数据
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (data != null) {
                    String imagePath = RealImagePathUtil.handleResponseImage(data, this);
                    Glide.with(this).load(imagePath).into(userCenterImage);
                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                    String base64 = BitmapUtils.bitmapToBase64(bitmap);
                    Log.d("onActivityResult", "path----->" + base64);
                    //将头像上传至数据库
                    currentUser = LCUser.getCurrentUser();
                    if (currentUser != null) {
                        LCObject object = LCObject.createWithoutData("_User", currentUser.getObjectId());
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

                }
                break;
        }
    }

    /**
     *动态请求权限的回调处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "无权限", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onShowUserData(User user) {
        myNickname.setText(user.getNickname());
        myGender.setText(user.getGender());
        myInfo.setText(user.getSelfInfo());
        Glide.with(this).load(Constants.HEAD_BASE_64 + user.getUserImageBase64()).into(userCenterImage);
    }

    @Override
    public void onShowUpLoad(List<UpLoadImage> upLoadImageList) {

    }

    @Override
    public void onShowLikeImage(List<LikeImage> imageList) {

    }

    @Override
    public void onShowAttention(List<UserAttention> attentionList) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void loadMore() {

    }
}