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
import com.example.guetshareimagedemo.model.bean.HomeLoadMoreImageBean;
import com.example.guetshareimagedemo.utils.StatusBarUtil;
import com.example.guetshareimagedemo.view.adapter.SearchAdapter;
import com.example.guetshareimagedemo.view.fragment.HomeFragment;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView searchTitle;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.rv_search);
        searchTitle = findViewById(R.id.search_text);
        progressBar = findViewById(R.id.search_progress);
        StatusBarUtil.transparencyBar(this);
        StatusBarUtil.StatusBarLightMode(this);
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        List<HomeLoadMoreImageBean.ResBean.VerticalBean> list = (List<HomeLoadMoreImageBean.ResBean.VerticalBean>) intent.getSerializableExtra(HomeFragment.HOME_SEARCH);
        if (list.size() == 0) {
            Toast.makeText(this, "未搜索到相关内容", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }else {
            SearchAdapter adapter = new SearchAdapter();
            adapter.setSearchList(list);
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            recyclerView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }
        String title = intent.getStringExtra(HomeFragment.SEARCH_TITLE);
        searchTitle.setText(title);

        Log.d("SearchActivity", "list----->" + list.toString());
    }
}