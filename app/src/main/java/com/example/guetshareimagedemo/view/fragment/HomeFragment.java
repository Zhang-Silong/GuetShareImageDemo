package com.example.guetshareimagedemo.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
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

import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.databinding.FragmentHomeBinding;
import com.example.guetshareimagedemo.model.bean.HomeLoadMoreImageBean;
import com.example.guetshareimagedemo.model.bean.ImageBean;
import com.example.guetshareimagedemo.presenter.HomeImagePresenter;
import com.example.guetshareimagedemo.utils.Constants;
import com.example.guetshareimagedemo.view.MyNestedScrollView;
import com.example.guetshareimagedemo.view.activity.SearchActivity;
import com.example.guetshareimagedemo.view.adapter.BannerViewAdapter;
import com.example.guetshareimagedemo.view.adapter.HomeImageAdapter;
import com.example.guetshareimagedemo.view.callback.IHomeView;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class HomeFragment extends Fragment implements IHomeView {

    public static final String HOME_SEARCH = "home_search";
    public static final String SEARCH_TITLE = "search_title";
    private FragmentHomeBinding homeBinding;

    private HomeImagePresenter homeImagePresenter;
    private HomeImageAdapter adapter;



    private final Handler handler = new Handler();



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
     * 搜索返回的数据
     */
    private List<HomeLoadMoreImageBean.ResBean.VerticalBean> searchList = new ArrayList<>();

    /**
     *创建一个线程，控制Viewpager的自动轮播
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            homeBinding.homeViewPager.setCurrentItem(homeBinding.homeViewPager.getCurrentItem() + 1);
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
        homeBinding = FragmentHomeBinding.inflate(getLayoutInflater());
        adapter = new HomeImageAdapter(getActivity());
        homeImagePresenter = new HomeImagePresenter();
        homeImagePresenter.registerView(this);
        homeImagePresenter.getImageData();
        //homeImagePresenter.refreshHomeData();

        initRefreshListener();
        initLoadMoreListener();
        initLayoutListener();
        homeBinding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String typeId = "";
                Intent intent = null;
                if (query.equals("动漫")) {
                    typeId = Constants.homeLoadMoreImages[0];
                    homeImagePresenter.homeSearch(typeId);
                    intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra(HOME_SEARCH, (Serializable) searchList);
                    intent.putExtra(SEARCH_TITLE, query);
                    getActivity().startActivity(intent);
                }else if (query.equals("风景")){
                    typeId = Constants.homeLoadMoreImages[1];
                    homeImagePresenter.homeSearch(typeId);
                    intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra(HOME_SEARCH, (Serializable) searchList);
                    intent.putExtra(SEARCH_TITLE, query);
                    getActivity().startActivity(intent);
                }else if (query.equals("游戏")){
                    typeId = Constants.homeLoadMoreImages[2];
                    homeImagePresenter.homeSearch(typeId);
                    intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra(HOME_SEARCH, (Serializable) searchList);
                    intent.putExtra(SEARCH_TITLE, query);
                    getActivity().startActivity(intent);
                }else if (query.equals("文字")){
                    typeId = Constants.homeLoadMoreImages[3];
                    homeImagePresenter.homeSearch(typeId);
                    intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra(HOME_SEARCH, (Serializable) searchList);
                    intent.putExtra(SEARCH_TITLE, query);
                    getActivity().startActivity(intent);
                }else if (query.equals("视觉")){
                    typeId = Constants.homeLoadMoreImages[4];
                    homeImagePresenter.homeSearch(typeId);
                    intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra(HOME_SEARCH, (Serializable) searchList);
                    intent.putExtra(SEARCH_TITLE, query);
                    getActivity().startActivity(intent);
                }else if (query.equals("情感")){
                    typeId = Constants.homeLoadMoreImages[5];
                    homeImagePresenter.homeSearch(typeId);
                    intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra(HOME_SEARCH, (Serializable) searchList);
                    intent.putExtra(SEARCH_TITLE, query);
                    getActivity().startActivity(intent);
                }else if (query.equals("设计")){
                    typeId = Constants.homeLoadMoreImages[6];
                    homeImagePresenter.homeSearch(typeId);
                    intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra(HOME_SEARCH, (Serializable) searchList);
                    intent.putExtra(SEARCH_TITLE, query);
                    getActivity().startActivity(intent);
                }else if (query.equals("明星")){
                    typeId = Constants.homeLoadMoreImages[7];
                    homeImagePresenter.homeSearch(typeId);
                    intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra(HOME_SEARCH, (Serializable) searchList);
                    intent.putExtra(SEARCH_TITLE, query);
                    getActivity().startActivity(intent);
                }else if (query.equals("物语")){
                    typeId = Constants.homeLoadMoreImages[8];
                    homeImagePresenter.homeSearch(typeId);
                    intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra(HOME_SEARCH, (Serializable) searchList);
                    intent.putExtra(SEARCH_TITLE, query);
                    getActivity().startActivity(intent);
                }else if (query.equals("艺术")){
                    typeId = Constants.homeLoadMoreImages[9];
                    homeImagePresenter.homeSearch(typeId);
                    intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra(HOME_SEARCH, (Serializable) searchList);
                    intent.putExtra(SEARCH_TITLE, query);
                    getActivity().startActivity(intent);
                }else if (query.equals("卡通")){
                    typeId = Constants.homeLoadMoreImages[10];
                    homeImagePresenter.homeSearch(typeId);
                    intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra(HOME_SEARCH, (Serializable) searchList);
                    intent.putExtra(SEARCH_TITLE, query);
                    getActivity().startActivity(intent);
                }else if (query.equals("城市")){
                    typeId = Constants.homeLoadMoreImages[11];
                    homeImagePresenter.homeSearch(typeId);
                    intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra(HOME_SEARCH, (Serializable) searchList);
                    intent.putExtra(SEARCH_TITLE, query);
                    getActivity().startActivity(intent);
                }else if (query.equals("动物")){
                    typeId = Constants.homeLoadMoreImages[12];
                    homeImagePresenter.homeSearch(typeId);
                    intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra(HOME_SEARCH, (Serializable) searchList);
                    intent.putExtra(SEARCH_TITLE, query);
                    getActivity().startActivity(intent);
                }else if (query.equals("运动")){
                    typeId = Constants.homeLoadMoreImages[13];
                    homeImagePresenter.homeSearch(typeId);
                    intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra(HOME_SEARCH, (Serializable) searchList);
                    intent.putExtra(SEARCH_TITLE, query);
                    getActivity().startActivity(intent);
                }else if (query.equals("影视")){
                    typeId = Constants.homeLoadMoreImages[14];
                    homeImagePresenter.homeSearch(typeId);
                    intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra(HOME_SEARCH, (Serializable) searchList);
                    intent.putExtra(SEARCH_TITLE, query);
                    getActivity().startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra(HOME_SEARCH, (Serializable) searchList);
                    intent.putExtra(SEARCH_TITLE, query);
                    getActivity().startActivity(intent);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        Log.d("UserFragment", "onCreateView");
        return homeBinding.getRoot();
    }

    /**
     * 解决recyclerview滑动卡顿
     */
    private void initLayoutListener() {
        homeBinding.homeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int headerHeight = homeBinding.nestedLayout.getMeasuredHeight();
                homeBinding.myNestedScrollView.setHeaderHeight(headerHeight);
                int measureHeight = homeBinding.homeLayout.getMeasuredHeight();
                Log.d("HomeFragment", "measureHeight------>" + measureHeight);
                ViewGroup.LayoutParams layoutParams = homeBinding.rvHomeFragment.getLayoutParams();
                layoutParams.height = measureHeight;
                homeBinding.rvHomeFragment.setLayoutParams(layoutParams);
                if (measureHeight != 0) {
                    homeBinding.homeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    /**
     * 初始化轮播图
     */
    private void initBannerView() {
        BannerViewAdapter bannerViewAdapter = new BannerViewAdapter(bannerList);
        homeBinding.homeViewPager.setAdapter(bannerViewAdapter);
        homeBinding.homeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        homeBinding.homeViewPager.setCurrentItem(bannerList.size() * 5);
    }

    /**
     * 下拉刷新
     */
    private void initRefreshListener() {
        homeBinding.homeRefresh.setEnableRefresh(true);
        homeBinding.homeRefresh.setOnRefreshListener(new OnRefreshListener() {
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
        homeBinding.homeRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Random random = new Random();
                int index = random.nextInt(10);
                String typeId = Constants.homeLoadMoreImages[index];
                homeImagePresenter.homeLoadMore(typeId);
                refreshLayout.finishLoadMore(2000);
                Log.d("HomeFragment", "onLoadMore------->" + typeId);
            }
        });
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
        homeBinding.rvHomeFragment.setLayoutManager(staggeredGridLayoutManager);
        homeBinding.rvHomeFragment.setAdapter(adapter);
    }

    @Override
    public void onLoad() {
        homeBinding.homeProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSearch(HomeLoadMoreImageBean imageBean) {
        searchList = imageBean.getRes().getVertical();
        Log.d("SearchView", "search---->" + searchList.toString());
    }

    @Override
    public void onLoadOver() {
        homeBinding.homeProgress.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh(ImageBean imageBean) {
        onShowImg(imageBean);
    }

    @Override
    public void onLoadMore(HomeLoadMoreImageBean imageBean) {
        loadMoreList = imageBean.getRes().getVertical();
        adapter.setLoadMoreList(loadMoreList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homeImagePresenter.unregisterView();
    }
}