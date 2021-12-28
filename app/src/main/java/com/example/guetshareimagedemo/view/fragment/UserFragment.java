package com.example.guetshareimagedemo.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.databinding.FragmentUserBinding;
import com.example.guetshareimagedemo.model.bean.LikeImage;
import com.example.guetshareimagedemo.model.bean.UpLoadImage;
import com.example.guetshareimagedemo.model.bean.User;
import com.example.guetshareimagedemo.model.bean.UserAttention;
import com.example.guetshareimagedemo.presenter.UserDataPresenter;
import com.example.guetshareimagedemo.utils.Constants;
import com.example.guetshareimagedemo.view.activity.LoginActivity;
import com.example.guetshareimagedemo.view.activity.UserDetailsActivity;
import com.example.guetshareimagedemo.view.adapter.UserViewPagerAdapter;
import com.example.guetshareimagedemo.view.callback.IUserView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.LCUser;


public class UserFragment extends Fragment implements IUserView{

    private static final String TAG = "UserFragment";

    private FragmentUserBinding userBinding;

    private UserViewPagerAdapter userViewPagerAdapter;


    private List<String> titleList = new ArrayList<>();
    private List<LikeImage> pagerImageList = new ArrayList<>();
    private List<UpLoadImage> upLoadImageList = new ArrayList<>();

    private UserDataPresenter userDataPresenter;

    private boolean isInit = false;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("UserFragment", "test---->" + "onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userBinding = FragmentUserBinding.inflate(getLayoutInflater());
        isInit = true;
        initTitleList();
        initView(userBinding.getRoot());
        userDataPresenter = new UserDataPresenter();
        userDataPresenter.registerView(this);
        userDataPresenter.getUserSelfData();
        userDataPresenter.getUserUpLoadImageData();
        userDataPresenter.getUserLikeImageData();

        Log.d(TAG, "onCreateView");
        return userBinding.getRoot();
    }


    private void initView(View view) {
        userBinding.userViewPager.setOffscreenPageLimit(2);
        userViewPagerAdapter = new UserViewPagerAdapter(getChildFragmentManager());
        userViewPagerAdapter.setTitleList(titleList);
        //userTab.setupWithViewPager(userViewPager);
        userBinding.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LCUser currentUser = LCUser.getCurrentUser();
                if (currentUser != null) {
                    startActivity(new Intent(getActivity(), UserDetailsActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });

        userBinding.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserDetailsActivity.class));
            }
        });


        userBinding.viewPagerRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (LCUser.getCurrentUser() == null) {
                    Glide.with(getActivity()).load(R.drawable.ic_baseline_android_24).into(userBinding.userImage);
                    userBinding.userName.setText("未设置");
                    userBinding.fansCount.setText("0");
                    userBinding.attentionCount.setText("0");
                    userBinding.beLikedCount.setText("0");
                }
                userDataPresenter.getUserSelfData();
                userDataPresenter.getUserLikeImageData();
                userViewPagerAdapter.notifyDataSetChanged();
                refreshLayout.finishRefresh(2000);
            }
        });



    }


    private void initTitleList(){
        titleList.add("动态");
        titleList.add("收藏");
    }

    @Override
    public void onShowUserData(User user) {
        //Log.d("UserFragment", "user----->" + user.toString());
        userBinding.userName.setText(user.getNickname());
        userBinding.beLikedCount.setText(Integer.toString(user.getAwardedCount()));
        userBinding.attentionCount.setText(Integer.toString(user.getAttentionCount()));
        userBinding.fansCount.setText(Integer.toString(user.getFans()));
        Glide.with(getActivity()).load(Constants.HEAD_BASE_64 + user.getUserImageBase64()).into(userBinding.userImage);
    }

    @Override
    public void onShowLikeImage(List<LikeImage> imageList) {
        pagerImageList = imageList;
        Log.d("UserFragments", "likelist----->" + imageList.size());
        //userViewPagerAdapter.setLikeImageList(pagerImageList);
        if (pagerImageList != null) {
            userViewPagerAdapter.setLikeImageList(pagerImageList);
            userBinding.userViewPager.setAdapter(userViewPagerAdapter);
            userBinding.userTab.setupWithViewPager(userBinding.userViewPager);
        }

        //userViewPagerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onShowAttention(List<UserAttention> attentionList) {

    }


    @Override
    public void onShowUpLoad(List<UpLoadImage> upLoadImageList) {
        List<UpLoadImage> tempList = new ArrayList<>();
        for (UpLoadImage upLoadImage : upLoadImageList) {
            upLoadImage.getAccount().equals(LCUser.getCurrentUser().getUsername());
            tempList.add(upLoadImage);
        }
        this.upLoadImageList = tempList;
        Log.d("UserFragment", "upLoadList----->" + tempList.size());
        if (this.upLoadImageList != null) {
            userViewPagerAdapter.setUpLoadImageList(this.upLoadImageList);
            userViewPagerAdapter.notifyDataSetChanged();
            userBinding.userViewPager.setAdapter(userViewPagerAdapter);
            userBinding.userTab.setupWithViewPager(userBinding.userViewPager);
        }

    }


    public void onRefresh() {

    }

    @Override
    public void loadMore() {

    }



}