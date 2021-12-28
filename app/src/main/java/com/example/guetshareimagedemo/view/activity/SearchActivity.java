package com.example.guetshareimagedemo.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.databinding.ActivitySearchBinding;
import com.example.guetshareimagedemo.model.bean.HomeLoadMoreImageBean;
import com.example.guetshareimagedemo.utils.StatusBarUtil;
import com.example.guetshareimagedemo.view.adapter.SearchAdapter;
import com.example.guetshareimagedemo.view.fragment.HomeFragment;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding searchBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(searchBinding.getRoot());
        StatusBarUtil.transparencyBar(this);
        StatusBarUtil.StatusBarLightMode(this);
        searchBinding.searchProgress.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        List<HomeLoadMoreImageBean.ResBean.VerticalBean> list = (List<HomeLoadMoreImageBean.ResBean.VerticalBean>) intent.getSerializableExtra(HomeFragment.HOME_SEARCH);
        if (list.size() == 0) {
            Toast.makeText(this, "未搜索到相关内容", Toast.LENGTH_SHORT).show();
            searchBinding.searchProgress.setVisibility(View.GONE);
        }else {
            SearchAdapter adapter = new SearchAdapter();
            adapter.setSearchList(list);
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            searchBinding.rvSearch.setLayoutManager(staggeredGridLayoutManager);
            searchBinding.rvSearch.setAdapter(adapter);
            searchBinding.searchProgress.setVisibility(View.GONE);
        }
        String title = intent.getStringExtra(HomeFragment.SEARCH_TITLE);
        searchBinding.searchText.setText(title);

        Log.d("SearchActivity", "list----->" + list.toString());
    }
}