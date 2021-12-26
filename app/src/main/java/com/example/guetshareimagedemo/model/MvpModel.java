package com.example.guetshareimagedemo.model;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.guetshareimagedemo.model.api.API;
import com.example.guetshareimagedemo.model.api.DataAPI;
import com.example.guetshareimagedemo.model.bean.HomeLoadMoreImageBean;
import com.example.guetshareimagedemo.model.bean.ImageBean;
import com.example.guetshareimagedemo.model.bean.LikeImage;
import com.example.guetshareimagedemo.model.bean.UpLoadImage;
import com.example.guetshareimagedemo.model.bean.User;
import com.example.guetshareimagedemo.utils.RetrofitManager;
import com.example.guetshareimagedemo.utils.UrlUtils;
import com.example.guetshareimagedemo.view.callback.IHomeCallBack;
import com.example.guetshareimagedemo.view.callback.IUserCallBack;
import com.example.guetshareimagedemo.view.callback.IUserView;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import cn.leancloud.LCObject;
import cn.leancloud.LCQuery;
import cn.leancloud.LCUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ZhangSilong on 2021/12/18.
 */
public class MvpModel {

    private static final String TAG = "MvpModel";

    /**
     *请求主页初始化图片数据
     */
    public static void homeImageResponse(IHomeCallBack<ImageBean> iHomeCallBack){
        /**
         * 使用Retrofit进行网络请求
         */
        Retrofit retrofit = RetrofitManager.getRetrofit();
        DataAPI api = retrofit.create(DataAPI.class);
        Call<ImageBean> task = api.getImgUrl();
        task.enqueue(new Callback<ImageBean>() {
            @Override
            public void onResponse(Call<ImageBean> call, Response<ImageBean> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    Log.d("HomeImage", response.body().toString());
                    iHomeCallBack.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ImageBean> call, Throwable t) {
                iHomeCallBack.onFailure(t.toString());
            }
        });
    }

    /**
     * 请求主页上拉加载更多的图片数据
     */
    public static void homeLoadMoreResponse(IHomeCallBack<HomeLoadMoreImageBean> iHomeCallBack, String typeId){
        String url = UrlUtils.homeLoadMoreImageUrl(typeId);
        Retrofit retrofit = RetrofitManager.getRetrofit();
        DataAPI api = retrofit.create(DataAPI.class);
        Call<HomeLoadMoreImageBean> task = api.getHomeLoadMoreImage(url);
        task.enqueue(new Callback<HomeLoadMoreImageBean>() {
            @Override
            public void onResponse(Call<HomeLoadMoreImageBean> call, Response<HomeLoadMoreImageBean> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    Log.d("homeLoadMoreResponse", typeId);
                    iHomeCallBack.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<HomeLoadMoreImageBean> call, Throwable t) {
                    iHomeCallBack.onFailure(t.toString());
                Log.d("homeLoadMoreResponse", call.toString());
            }
        });
    }

    /**
     * 获取用户个人信息
     */
    public static void userSelfDataResponse(IUserCallBack<User> iUserCallBack){
        LCUser currentUser = LCUser.getCurrentUser();
        //Log.d("MVPModel", "test1----->" + currentUser.toString());
        if (currentUser != null) {
            LCQuery<LCObject> query = new LCQuery<>("_User");
            query.whereEqualTo("username", currentUser.getUsername());
            query.findInBackground().subscribe(new Observer<List<LCObject>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull List<LCObject> lcObjects) {
                    User user = new User();
                    String account = (String) lcObjects.get(0).get("username");
                    String userImage = (String) lcObjects.get(0).get("userImage");
                    String nickName = (String) lcObjects.get(0).get("nickname");
                    int attentionCount = (Integer) lcObjects.get(0).get("attentionCount");
                    int awardedCount = (Integer) lcObjects.get(0).get("awardedCount");
                    int fans = (Integer) lcObjects.get(0).get("fans");
                    String gender = (String) lcObjects.get(0).get("gender");
                    String selfInf0 = (String) lcObjects.get(0).get("selfInfo");
                    user.setAccount(account);
                    user.setUserImage(userImage);
                    user.setNickname(nickName);
                    user.setAttentionCount(attentionCount);
                    user.setAwardedCount(awardedCount);
                    user.setFans(fans);
                    user.setGender(gender);
                    user.setSelfInfo(selfInf0);
                    user.setAccount(account);
                    Log.d("MVPModel", "test2----->" + user.toString());
                    iUserCallBack.onSuccess(user);
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    Log.d("MVPModel", "test3----->" + e.toString());
                }

                @Override
                public void onComplete() {

                }
            });
        }

    }

    /**
     * 获取点赞的图片
     */
    public static void userLikeImageResponse(IUserCallBack<List<LikeImage>> iUserCallBack){
        LCUser currentUser = LCUser.getCurrentUser();
        Log.d(TAG, "userLikeImageResponse------>" + currentUser);
        if (currentUser != null) {
            LCQuery<LCObject> query = new LCQuery<>("LikeImageUrl");
            query.whereEqualTo("account", currentUser.getUsername());
            query.findInBackground().subscribe(new Observer<List<LCObject>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull List<LCObject> lcObjects) {
                    User user = new User();
                    List<LikeImage> imageList = new ArrayList<>();
                    for (LCObject lcObject : lcObjects) {
                        LikeImage likeImage = new LikeImage();
                        likeImage.setAccount(currentUser.getUsername());
                        likeImage.setImageUrl((String) lcObject.get("imageUrl"));
                        imageList.add(likeImage);
                    }
                    user.setLikeImageUrl(imageList);
                    iUserCallBack.onSuccess(imageList);
                    Log.d(TAG, "userLikeImageResponse------>" + imageList.toString());
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

    /**
     * 获取当前用户已发布的照片
     */
    public static void userUpLoadImageResponse(IUserCallBack<List<UpLoadImage>> iUserCallBack){
        LCUser currentUser = LCUser.getCurrentUser();
        if (currentUser != null) {
            LCQuery<LCObject> query = new LCQuery<>("UpLoadImageUrl");
            query.whereEqualTo("account", currentUser.getUsername());
            query.findInBackground().subscribe(new Observer<List<LCObject>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull List<LCObject> lcObjects) {
                    User user = new User();
                    List<UpLoadImage> upLoadImageList = new ArrayList<>();
                    for (LCObject lcObject : lcObjects) {
                        UpLoadImage upLoadImage = new UpLoadImage();
                        upLoadImage.setAccount(currentUser.getUsername());
                        upLoadImage.setImageUrl((String) lcObject.get("imageUrl"));
                        upLoadImage.setNickName((String) lcObject.get("nickname"));
                        upLoadImage.setImageTitle((String) lcObject.get("imageTitle"));
                        upLoadImage.setImageMsg((String) lcObject.get("imageMsg"));
                        upLoadImage.setUserImage((String) lcObject.get("userImage"));
                        upLoadImageList.add(upLoadImage);
                    }
                    user.setUpLoadImageUrl(upLoadImageList);
                    iUserCallBack.onSuccess(upLoadImageList);
                    Log.d(TAG, "upLoadImageList------>" + upLoadImageList.toString());
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


}
