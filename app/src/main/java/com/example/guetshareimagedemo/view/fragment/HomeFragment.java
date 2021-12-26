package com.example.guetshareimagedemo.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.model.bean.HomeLoadMoreImageBean;
import com.example.guetshareimagedemo.model.bean.ImageBean;
import com.example.guetshareimagedemo.presenter.HomeImagePresenter;
import com.example.guetshareimagedemo.utils.Constant;
import com.example.guetshareimagedemo.view.MyNestedScrollView;
import com.example.guetshareimagedemo.view.adapter.BannerViewAdapter;
import com.example.guetshareimagedemo.view.adapter.HomeImageAdapter;
import com.example.guetshareimagedemo.view.callback.IHomeView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class HomeFragment extends Fragment implements IHomeView {

    private HomeImagePresenter homeImagePresenter;
    private RecyclerView recyclerView;
    private HomeImageAdapter adapter;
    private Toolbar toolbar;
    private ViewPager bannerView;
    private FloatingActionButton top;

    private RelativeLayout homeLayout;
    private MyNestedScrollView myNestedScrollView;
    private LinearLayout nestedLayout;

    /**
     * 主界面下拉刷新
     */
    private RefreshLayout homeRefresh;

    private final Handler handler = new Handler();

    private ProgressBar progressBar;

    /**
     * 主页rv数据
     */
    private List<ImageBean.ResBean.VerticalBean> verticalBeanList = new ArrayList<>();

    /**
     * 轮播图数据
     */
    private List<ImageBean.ResBean.VerticalBean> bannerList = new ArrayList<>();

    /**
     * 上拉加载更多数据
     */
    private List<HomeLoadMoreImageBean.ResBean.VerticalBean> loadMoreList = new ArrayList<>();

    /**
     *创建一个线程，控制Viewpager的自动轮播
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            bannerView.setCurrentItem(bannerView.getCurrentItem() + 1);
            handler.postDelayed(runnable, 3000);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        adapter = new HomeImageAdapter(getActivity());
        homeImagePresenter = new HomeImagePresenter();
        homeImagePresenter.registerView(this);
        homeImagePresenter.getImageData();
        //homeImagePresenter.refreshHomeData();

        initRefreshListener();
        initLoadMoreListener();
        initLayoutListener();
        Log.d("UserFragment", "onCreateView");
        return view;
    }

    /**
     * 解决recyclerview滑动卡顿
     */
    private void initLayoutListener() {
        homeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int headerHeight = nestedLayout.getMeasuredHeight();
                myNestedScrollView.setHeaderHeight(headerHeight);
                int measureHeight = homeLayout.getMeasuredHeight();
                Log.d("HomeFragment", "measureHeight------>" + measureHeight);
                ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
                layoutParams.height = measureHeight;
                recyclerView.setLayoutParams(layoutParams);
                if (measureHeight != 0) {
                    homeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    /**
     * 初始化轮播图
     */
    private void initBannerView() {
        BannerViewAdapter bannerViewAdapter = new BannerViewAdapter(bannerList);
        bannerView.setAdapter(bannerViewAdapter);
        bannerView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(runnable, 3000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bannerView.setCurrentItem(bannerList.size() * 5);
    }

    /**
     * 下拉刷新
     */
    private void initRefreshListener() {
        homeRefresh.setEnableRefresh(true);
        homeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                homeImagePresenter.refreshHomeData();
                refreshLayout.finishRefresh(2000);
            }
        });
    }

    /**
     *上拉加载更多
     */
    private void initLoadMoreListener(){
        homeRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Random random = new Random();
                int index = random.nextInt(10);
                String typeId = Constant.homeLoadMoreImages[index];
                homeImagePresenter.homeLoadMore(typeId);
                refreshLayout.finishLoadMore(2000);
                Log.d("HomeFragment", "onLoadMore------->" + typeId);
            }
        });
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.rv_home_fragment);
        progressBar = view.findViewById(R.id.home_progress);
        bannerView = view.findViewById(R.id.home_view_pager);
        homeRefresh = view.findViewById(R.id.home_refresh);
        homeLayout = view.findViewById(R.id.home_layout);
        myNestedScrollView = view.findViewById(R.id.my_nested_scroll_view);
        nestedLayout = view.findViewById(R.id.nested_layout);
        //top = view.findViewById(R.id.top);
    }

    @Override
    public void onShowImg(ImageBean imageBean) {
        verticalBeanList = imageBean.getRes().getVertical();
        for (int i = 0; i < 5; i++) {
            bannerList.add(verticalBeanList.get(i));
        }
        initBannerView();
        //adapter = new HomeImageAdapter();

        adapter.setImageBeanList(verticalBeanList);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLoad() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadOver() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh(ImageBean imageBean) {
        onShowImg(imageBean);
    }

    @Override
    public void onLoadMore(HomeLoadMoreImageBean imageBean) {
        loadMoreList = imageBean.getRes().getVertical();
        //recyclerView.setNestedScrollingEnabled(false);
        adapter.setLoadMoreList(loadMoreList);
        //Log.d("HomeFragment", "onLoadMore-------->" + loadMoreList.toString());
        //recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homeImagePresenter.unregisterView();
    }
}