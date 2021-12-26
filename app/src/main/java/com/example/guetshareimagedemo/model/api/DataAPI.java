package com.example.guetshareimagedemo.model.api;

import com.example.guetshareimagedemo.model.bean.HomeLoadMoreImageBean;
import com.example.guetshareimagedemo.model.bean.ImageBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by ZhangSilong on 2021/12/16.
 */
public interface DataAPI {

    @GET("v1/vertical/vertical?limit=30&skip=180&adult=false&first=0&order=hot")
    Call<ImageBean> getImgUrl();

    @GET
    Call<HomeLoadMoreImageBean> getHomeLoadMoreImage(@Url String url);

}
