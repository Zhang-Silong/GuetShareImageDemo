package com.example.guetshareimagedemo.presenter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.example.guetshareimagedemo.model.MvpModel;
import com.example.guetshareimagedemo.model.bean.LikeImage;
import com.example.guetshareimagedemo.model.bean.UpLoadImage;
import com.example.guetshareimagedemo.model.bean.User;
import com.example.guetshareimagedemo.view.callback.IUserCallBack;
import com.example.guetshareimagedemo.view.callback.IUserView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

import kotlin.jvm.internal.PropertyReference0Impl;

/**
 * Created by ZhangSilong on 2021/12/24.
 */
public class UserDataPresenter implements IHomeImagePresenter<IUserView> {

    private static final String TAG = "UserDataPresenter";

    private IUserView iUserView;

    /**
     * 将用户个人信息传回view层
     */
    public void getUserSelfData(){
        MvpModel.userSelfDataResponse(new IUserCallBack<User>() {
            @Override
            public void onSuccess(User response) {
                iUserView.onShowUserData(response);
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 将用户点赞的照片传回view层
     */
    public void getUserLikeImageData(){
        MvpModel.userLikeImageResponse(new IUserCallBack<List<LikeImage>>() {
            @Override
            public void onSuccess(List<LikeImage> response) {
                Log.d(TAG, "getUserLikeImageData--->" + response.toString());
                iUserView.onShowLikeImage(response);
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 将用户发布的照片传回view层
     */
    public void getUserUpLoadImageData(){
        MvpModel.userUpLoadImageResponse(new IUserCallBack<List<UpLoadImage>>() {
            @Override
            public void onSuccess(List<UpLoadImage> response) {
                Log.d(TAG, "getUserUpLoadImageData--->" + response.toString());
                iUserView.onShowUpLoad(response);
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    @Override
    public void registerView(IUserView iUserView) {
        this.iUserView = iUserView;
    }

    @Override
    public void unregisterView() {
        this.iUserView = null;
    }
}
