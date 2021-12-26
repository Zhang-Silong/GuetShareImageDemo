package com.example.guetshareimagedemo.presenter;

/**
 * Created by ZhangSilong on 2021/12/18.
 */
public interface IHomeImagePresenter<V> {

    void registerView(V v);

    void unregisterView();

}
