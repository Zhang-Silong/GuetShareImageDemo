package com.example.guetshareimagedemo.view.callback;

import com.example.guetshareimagedemo.model.bean.LikeImage;
import com.example.guetshareimagedemo.model.bean.UpLoadImage;
import com.example.guetshareimagedemo.model.bean.User;
import com.example.guetshareimagedemo.model.bean.UserAttention;

import java.util.List;

/**
 * Created by ZhangSilong on 2021/12/24.
 */
public interface IUserView {

    void onShowUserData(User user);

    void onShowUpLoad(List<UpLoadImage> upLoadImageList);

    void onShowLikeImage(List<LikeImage> imageList);

    void onShowAttention(List<UserAttention> attentionList);

    void onRefresh();

    void loadMore();

}
