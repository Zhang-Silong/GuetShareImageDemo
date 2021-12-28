package com.example.guetshareimagedemo.view.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Printer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.model.bean.LikeImage;
import com.example.guetshareimagedemo.model.bean.UpLoadImage;
import com.example.guetshareimagedemo.model.bean.User;
import com.example.guetshareimagedemo.model.bean.UserAttention;
import com.example.guetshareimagedemo.presenter.UserDataPresenter;
import com.example.guetshareimagedemo.utils.BitmapUtils;
import com.example.guetshareimagedemo.utils.RealImagePathUtil;
import com.example.guetshareimagedemo.view.adapter.UpLoadRvAdapter;
import com.example.guetshareimagedemo.view.callback.IUserView;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.LCObject;
import cn.leancloud.LCUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class UpLoadFragment extends Fragment implements IUserView {

    private static final int CODE_PICK_IMAGE = 1;
    private static final String TAG = "UpLoadFragment";

    private RelativeLayout rlAdd;
    private TextView imageTitle;
    private TextView imageMsg;
    private Button upLoadImage;
    private RecyclerView rvUpLoad;
    private UpLoadRvAdapter upLoadRvAdapter;
    private ProgressBar uploadProgress;
    private UserDataPresenter userDataPresenter;
    private User user;

    private List<UpLoadImage> upLoadImageList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_up_load, container, false);
        initView(view);
        userDataPresenter = new UserDataPresenter();
        userDataPresenter.registerView(this);
        userDataPresenter.getUserSelfData();
        return view;
    }

    private void initView(View view) {
        rlAdd = view.findViewById(R.id.rl_add);
        imageTitle = view.findViewById(R.id.image_title);
        imageMsg = view.findViewById(R.id.image_msg);
        upLoadImage = view.findViewById(R.id.upload_image);
        rvUpLoad = view.findViewById(R.id.rv_upload);
        uploadProgress = view.findViewById(R.id.upload_progress);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvUpLoad.setLayoutManager(linearLayoutManager);
        upLoadRvAdapter = new UpLoadRvAdapter();

        rlAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(), new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 1);
                }else {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, CODE_PICK_IMAGE);
                }
            }
        });

        upLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = imageTitle.getText().toString();
                String msg = imageMsg.getText().toString();
                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(msg)) {
                    LCUser currentUser = LCUser.getCurrentUser();
                    if (currentUser != null) {
                        if (upLoadImageList != null) {
                            uploadProgress.setVisibility(View.VISIBLE);
                            LCObject object = new LCObject("UpLoadImageUrl");
                            for (UpLoadImage image : upLoadImageList) {
                                object.put("account", currentUser.getUsername());
                                object.put("parentId", currentUser.getObjectId());
                                object.put("userImage", user.getUserImage());
                                object.put("userImageBase64", user.getUserImageBase64());
                                object.put("nickname", user.getNickname());
                                object.put("imageTitle", title);
                                object.put("imageMsg", msg);
                                object.put("imageUrl", image.getImageUrl());
                                object.put("base64", image.getBase64());
                                object.put("flag", image.isFlag());
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

                        }else {
                            Toast.makeText(getActivity(), "图片不能为空！", Toast.LENGTH_SHORT).show();
                        }
                        imageTitle.setText("");
                        imageMsg.setText("");
                        upLoadImageList.clear();
                        upLoadRvAdapter.setUpLoadImageList(upLoadImageList);
                        rvUpLoad.setAdapter(upLoadRvAdapter);
                        uploadProgress.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "发布成功！", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "内容不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

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
                    String imagePath = RealImagePathUtil.handleResponseImage(data, getActivity());
                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                    String base64 = BitmapUtils.bitmapToBase64(bitmap);
                    Log.d(TAG, "path----->" + imagePath);
                    UpLoadImage upLoadImage = new UpLoadImage();
                    upLoadImage.setImageUrl(imagePath);
                    upLoadImage.setBase64(base64);
                    upLoadImage.setFlag(true);
                    upLoadImageList.add(upLoadImage);
                    upLoadRvAdapter.setUpLoadImageList(upLoadImageList);
                    rvUpLoad.setAdapter(upLoadRvAdapter);

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
                    Toast.makeText(getActivity(), "无权限", Toast.LENGTH_SHORT).show();
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
        this.user = user;
        Log.d(TAG, "user----->" + this.user.toString());
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