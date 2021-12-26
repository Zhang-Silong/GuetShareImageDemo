package com.example.guetshareimagedemo.view.callback;

import com.example.guetshareimagedemo.model.bean.HomeLoadMoreImageBean;
import com.example.guetshareimagedemo.model.bean.ImageBean;

/**
 * Created by ZhangSilong on 2021/12/18.
 */
public interface IHomeView {

    void onShowImg(ImageBean imageBean);

    void onLoad();

    void onLoadOver();

    void onRefresh(ImageBean imageBean);

    void onLoadMore(HomeLoadMoreImageBean imageBean);
}
