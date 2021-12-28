package com.example.guetshareimagedemo.view.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.guetshareimagedemo.model.bean.UserAttention;
import com.example.guetshareimagedemo.presenter.UserDataPresenter;
import com.example.guetshareimagedemo.utils.StatusBarUtil;
import com.example.guetshareimagedemo.view.adapter.AttentionImageRvAdapter;
import com.example.guetshareimagedemo.view.adapter.AttentionUserRvAdapter;
import com.example.guetshareimagedemo.view.callback.IUserView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongFunction;

public class AttentionFragment extends Fragment implements IUserView {

    private static final String TAG = "AttentionFragment";

    private Toolbar toolbar;
    private RecyclerView rvAttentionHor;
    private RecyclerView rvAttentionVer;
    private AttentionUserRvAdapter userRvAdapter;
    private AttentionImageRvAdapter imageRvAdapter;
    private UserDataPresenter userDataPresenter;
    private List<UserAttention> userAttentionList = new ArrayList<>();
    private LinearLayoutManager horLinearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StatusBarUtil.transparencyBar(getActivity());
        StatusBarUtil.StatusBarLightMode(getActivity());
        View view = inflater.inflate(R.layout.fragment_attention, container, false);
        initView(view);
        userDataPresenter = new UserDataPresenter();
        userDataPresenter.registerView(this);
        userDataPresenter.getUserAttentionData();
        return view;
    }

    private void initView(View view) {
        toolbar = view.findViewById(R.id.attention_toolbar);
        rvAttentionHor = view.findViewById(R.id.rv_attention_hor);
        rvAttentionVer = view.findViewById(R.id.rv_attention_ver);
        horLinearLayoutManager = new LinearLayoutManager(getActivity());
        horLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        userRvAdapter = new AttentionUserRvAdapter();
        imageRvAdapter = new AttentionImageRvAdapter();
    }

    @Override
    public void onShowUserData(User user) {

    }

    @Override
    public void onShowUpLoad(List<UpLoadImage> upLoadImageList) {

    }

    @Override
    public void onShowLikeImage(List<LikeImage> imageList) {

    }

    @Override
    public void onShowAttention(List<UserAttention> attentionList) {
        Log.d(TAG, "list----->" + attentionList.toString());
        userAttentionList = attentionList;
        userRvAdapter.setAttentionList(userAttentionList);
        imageRvAdapter.setAttentionList(userAttentionList);
        rvAttentionHor.setLayoutManager(horLinearLayoutManager);
        rvAttentionVer.setLayoutManager(gridLayoutManager);
        rvAttentionHor.setAdapter(userRvAdapter);
        rvAttentionVer.setAdapter(imageRvAdapter);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void loadMore() {

    }
}