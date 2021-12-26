package com.example.guetshareimagedemo.presenter;

import android.util.Log;

import com.example.guetshareimagedemo.model.MvpModel;
import com.example.guetshareimagedemo.model.bean.HomeLoadMoreImageBean;
import com.example.guetshareimagedemo.model.bean.ImageBean;
import com.example.guetshareimagedemo.view.callback.IHomeCallBack;
import com.example.guetshareimagedemo.view.callback.IHomeView;

/**
 * Created by ZhangSilong on 2021/12/18.
 */
public class HomeImagePresenter implements IHomeImagePresenter<IHomeView> {

    private IHomeView iHomeView;

    public void getImageData(){
        iHomeView.onLoad();
        MvpModel.homeImageResponse(new IHomeCallBack<ImageBean>() {
            @Override
            public void onSuccess(ImageBean imageBean) {
                iHomeView.onShowImg(imageBean);
                iHomeView.onLoadOver();
            }

            @Override
            public void onFailure(String msg) {
                Log.d("HomeImagePresenter", "onFailure------>" + msg);
                iHomeView.onLoadOver();
            }
        });
    }

    /**
     * 下拉刷新
     */
    public void refreshHomeData(){
        MvpModel.homeImageResponse(new IHomeCallBack<ImageBean>() {
            @Override
            public void onSuccess(ImageBean imageBean) {
                if (imageBean != null) {
                    iHomeView.onRefresh(imageBean);
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     *上拉加载更多
     */
    public void homeLoadMore(String typeId){
        MvpModel.homeLoadMoreResponse(new IHomeCallBack<HomeLoadMoreImageBean>() {
            @Override
            public void onSuccess(HomeLoadMoreImageBean imageBean) {
                if (imageBean != null) {
                    iHomeView.onLoadMore(imageBean);
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        }, typeId);
    }

    @Override
    public void registerView(IHomeView iHomeView) {
        this.iHomeView = iHomeView;
    }

    @Override
    public void unregisterView() {
        this.iHomeView = null;
    }
}
