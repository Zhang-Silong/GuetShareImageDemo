package com.example.guetshareimagedemo.view.callback;

/**
 * Created by ZhangSilong on 2021/12/24.
 */
public interface IUserCallBack<T>{

    void onSuccess(T response);

    void onFailure(String msg);

}
