package com.example.guetshareimagedemo.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.databinding.FragmentUserSendBinding;
import com.example.guetshareimagedemo.model.bean.UpLoadImage;
import com.example.guetshareimagedemo.view.adapter.UserSendRvAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserSendFragment extends Fragment {

    private List<UpLoadImage> upLoadImageList = new ArrayList<>();
    private FragmentUserSendBinding sendBinding;

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
        sendBinding = FragmentUserSendBinding.inflate(getLayoutInflater());
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        sendBinding.rvViewPagerSend.setLayoutManager(staggeredGridLayoutManager);
        UserSendRvAdapter userSendRvAdapter = new UserSendRvAdapter();
        if (upLoadImageList != null) {
            userSendRvAdapter.setUpLoadImageList(upLoadImageList);
            sendBinding.rvViewPagerSend.setAdapter(userSendRvAdapter);
        }
        return sendBinding.getRoot();
    }


}