package com.example.guetshareimagedemo.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.model.bean.LikeImage;
import com.example.guetshareimagedemo.model.bean.UpLoadImage;
import com.example.guetshareimagedemo.model.bean.User;
import com.example.guetshareimagedemo.presenter.UserDataPresenter;
import com.example.guetshareimagedemo.utils.StatusBarUtil;
import com.example.guetshareimagedemo.view.adapter.SpaceRvAdapter;
import com.example.guetshareimagedemo.view.callback.IUserView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


public class SpaceFragment extends Fragment implements IUserView {

    private Toolbar toolbar;
    private RecyclerView rvSpace;

    private SpaceRvAdapter spaceRvAdapter;
    private UserDataPresenter userDataPresenter;
    private RefreshLayout spaceRefresh;

    private List<UpLoadImage> upLoadImageList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_space, container, false);
        toolbar = view.findViewById(R.id.space_toolbar);
        rvSpace = view.findViewById(R.id.rv_space);
        spaceRefresh = view.findViewById(R.id.space_refresh);
        spaceRvAdapter = new SpaceRvAdapter();
        StatusBarUtil.transparencyBar(getActivity());
        StatusBarUtil.StatusBarLightMode(getActivity());
        userDataPresenter = new UserDataPresenter();
        userDataPresenter.registerView(this);
        userDataPresenter.getUserUpLoadImageData();

        spaceRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                userDataPresenter.getUserUpLoadImageData();
                refreshLayout.finishRefresh(1000);
            }
        });


        Log.d("SpaceFragment", "onCreateView");
        return view;
    }

    @Override
    public void onShowUserData(User user) {

    }

    @Override
    public void onShowUpLoad(List<UpLoadImage> upLoadImageList) {
        this.upLoadImageList = upLoadImageList;
        Log.d("SpaceFragment", "space------>" + upLoadImageList.toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvSpace.setLayoutManager(linearLayoutManager);
        spaceRvAdapter.setUpLoadImageList(this.upLoadImageList);
        rvSpace.setAdapter(spaceRvAdapter);



    }

    @Override
    public void onShowLikeImage(List<LikeImage> imageList) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void loadMore() {

    }
}