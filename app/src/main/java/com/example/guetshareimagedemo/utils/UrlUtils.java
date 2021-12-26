package com.example.guetshareimagedemo.utils;

import com.example.guetshareimagedemo.model.api.API;

/**
 * Created by ZhangSilong on 2021/12/21.
 */
public class UrlUtils {

    public static String homeLoadMoreImageUrl(String typeId){
        return "v1/vertical/category/" + typeId + "/vertical?limit=10";
    }

}
