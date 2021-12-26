package com.example.guetshareimagedemo.utils;

import com.example.guetshareimagedemo.model.api.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ZhangSilong on 2021/12/16.
 */
public class RetrofitManager {

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API.BASE_IMAGE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
