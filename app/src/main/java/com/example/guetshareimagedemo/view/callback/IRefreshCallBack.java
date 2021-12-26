package com.example.guetshareimagedemo.view.callback;

import com.example.guetshareimagedemo.model.bean.LikeImage;
import com.example.guetshareimagedemo.model.bean.UpLoadImage;
import com.example.guetshareimagedemo.model.bean.User;

import java.util.List;

/**
 * Created by ZhangSilong on 2021/12/26.
 */
public interface IRefreshCallBack {

    void onRefreshUserData(User user);
    void onRefreshLikeImage(List<LikeImage> likeImage);
    void onUpLoad(UpLoadImage upLoadImage);
}
