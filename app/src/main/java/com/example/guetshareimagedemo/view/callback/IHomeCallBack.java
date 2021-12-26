package com.example.guetshareimagedemo.view.callback;

import com.example.guetshareimagedemo.model.bean.ImageBean;

/**
 * Created by ZhangSilong on 2021/12/18.
 */
public interface IHomeCallBack<T> {

    void onSuccess(T imageBean);

    void onFailure(String msg);

}
