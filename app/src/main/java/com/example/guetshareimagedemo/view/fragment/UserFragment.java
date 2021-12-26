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

import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.model.bean.LikeImage;
import com.example.guetshareimagedemo.model.bean.UpLoadImage;
import com.example.guetshareimagedemo.model.bean.User;
import com.example.guetshareimagedemo.presenter.UserDataPresenter;
import com.example.guetshareimagedemo.view.activity.LoginActivity;
import com.example.guetshareimagedemo.view.activity.TestActivity;
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

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;

    private TabLayout userTab;
    private ViewPager userViewPager;
    private UserViewPagerAdapter userViewPagerAdapter;
    private ShapeableImageView userImage;
    private TextView userName;
    private TextView userAttention;
    private RefreshLayout viewpagerRefresh;

    private TextView beLikedCount;
    private TextView attentionCount;
    private TextView fansCount;

    private List<String> titleList = new ArrayList<>();
    private List<LikeImage> pagerImageList = new ArrayList<>();
    private List<UpLoadImage> upLoadImageList = new ArrayList<>();

    private UserDataPresenter userDataPresenter;
    private UserViewPagerFragment userViewPagerFragment;

    private boolean isInit = false;


    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("UserFragment", "test---->" + "onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        isInit = true;
        userViewPagerAdapter = new UserViewPagerAdapter(getChildFragmentManager());
        initTitleList();
        initView(view);
        userDataPresenter = new UserDataPresenter();
        userDataPresenter.registerView(this);
        userDataPresenter.getUserSelfData();
        //userDataPresenter.getUserUpLoadImageData();
        //userDataPresenter.getUserLikeImageData();

        Log.d(TAG, "onCreateView");
        return view;
    }


    private void initView(View view) {
        collapsingToolbarLayout = view.findViewById(R.id.collapsingToolbarLayout);
        //toolbar = view.findViewById(R.id.user_toolbar);
        userTab = view.findViewById(R.id.user_tab);
        userImage = view.findViewById(R.id.user_image);
        userName = view.findViewById(R.id.user_name);
        userAttention = view.findViewById(R.id.user_attention);
        userViewPager = view.findViewById(R.id.user_view_pager);
        viewpagerRefresh = view.findViewById(R.id.view_pager_refresh);
        beLikedCount = view.findViewById(R.id.be_liked_count);
        attentionCount = view.findViewById(R.id.attention_count);
        fansCount = view.findViewById(R.id.fans_count);
        userViewPagerAdapter.setTitleList(titleList);
        //userViewPager.setOffscreenPageLimit(2);
        //userViewPager.setAdapter(userViewPagerAdapter);
        userTab.setupWithViewPager(userViewPager);
        userImage.setOnClickListener(new View.OnClickListener() {
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

        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserDetailsActivity.class));
            }
        });

        userAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TestActivity.class));
            }
        });

        viewpagerRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                userDataPresenter.getUserSelfData();
                userDataPresenter.getUserLikeImageData();
                userViewPagerAdapter.notifyDataSetChanged();
                refreshLayout.finishRefresh(2000);
            }
        });

//        userViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if (position == 0) {
//                    userDataPresenter.getUserUpLoadImageData();
//
//                }else {
//                    userDataPresenter.getUserLikeImageData();
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });



    }


    private void initTitleList(){
        titleList.add("动态");
        titleList.add("收藏");
        //titleList.add("点赞");
    }

    @Override
    public void onShowUserData(User user) {
        Log.d("UserFragment", "user----->" + user.toString());
        userName.setText(user.getNickname());
        beLikedCount.setText(Integer.toString(user.getAwardedCount()));
        attentionCount.setText(Integer.toString(user.getAttentionCount()));
        fansCount.setText(Integer.toString(user.getFans()));
    }

    @Override
    public void onShowLikeImage(List<LikeImage> imageList) {
        pagerImageList = imageList;
        Log.d("UserFragment", "list----->" + imageList.size());
        //userViewPagerAdapter.setLikeImageList(pagerImageList);
        userViewPagerAdapter.setAllDataList(upLoadImageList, pagerImageList);
        userViewPager.setAdapter(userViewPagerAdapter);
        //userViewPagerAdapter.notifyDataSetChanged();

    }


    @Override
    public void onShowUpLoad(List<UpLoadImage> upLoadImageList) {
        this.upLoadImageList = upLoadImageList;
        Log.d("UserFragment", "upLoadList----->" + upLoadImageList.size());
        //userViewPagerAdapter.setUpLoadImageList(this.upLoadImageList);
        userViewPagerAdapter.setAllDataList(this.upLoadImageList, pagerImageList);
        userViewPager.setAdapter(userViewPagerAdapter);
        //userViewPagerAdapter.notifyDataSetChanged();
    }


    public void onRefresh() {

    }

    @Override
    public void loadMore() {

    }



}