package com.example.guetshareimagedemo.view.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import com.example.guetshareimagedemo.model.bean.UpLoadImage;
import com.example.guetshareimagedemo.view.adapter.UpLoadRvAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.LCObject;
import cn.leancloud.LCUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class UpLoadFragment extends Fragment {

    private static final int CODE_PICK_IMAGE = 1;
    private static final String TAG = "UpLoadFragment";

    private RelativeLayout rlAdd;
    private TextView imageTitle;
    private TextView imageMsg;
    private Button upLoadImage;
    private RecyclerView rvUpLoad;
    private UpLoadRvAdapter upLoadRvAdapter;
    private ProgressBar uploadProgress;

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
                                object.put("imageTitle", title);
                                object.put("imageMsg", msg);
                                object.put("imageUrl", image.getImageUrl());
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
     * 使用ContentProvider访问手机相册数据
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (data != null) {
                    String imagePath = handleResponseImage(data);
                    Log.d(TAG, "path----->" + imagePath);
                    UpLoadImage upLoadImage = new UpLoadImage();
                    upLoadImage.setImageUrl(imagePath);
                    upLoadImageList.add(upLoadImage);
                    upLoadRvAdapter.setUpLoadImageList(upLoadImageList);
                    rvUpLoad.setAdapter(upLoadRvAdapter);

                }
                break;
        }
    }

    /**
     * 处理获得的图片
     */
    private String handleResponseImage(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri, null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    private String getImagePath(Uri uri, String selection){
        String path = null;
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
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
}