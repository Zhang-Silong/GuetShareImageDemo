package com.example.guetshareimagedemo.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.model.bean.UpLoadImage;
import com.example.guetshareimagedemo.view.adapter.UserSendRvAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserSendFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<UpLoadImage> upLoadImageList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setUpLoadImageList(List<UpLoadImage> upLoadImageList) {
        this.upLoadImageList = upLoadImageList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_send, container, false);
        recyclerView = view.findViewById(R.id.rv_view_pager_send);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        UserSendRvAdapter userSendRvAdapter = new UserSendRvAdapter();
        if (upLoadImageList != null) {
            userSendRvAdapter.setUpLoadImageList(upLoadImageList);
            recyclerView.setAdapter(userSendRvAdapter);
        }
        return view;
    }


}