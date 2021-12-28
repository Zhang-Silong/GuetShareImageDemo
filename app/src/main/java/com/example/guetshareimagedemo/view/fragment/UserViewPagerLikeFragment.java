package com.example.guetshareimagedemo.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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
import com.example.guetshareimagedemo.view.adapter.UserViewRvAdapter;
import com.example.guetshareimagedemo.view.callback.IUserView;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class UserViewPagerLikeFragment extends Fragment {

    private RecyclerView rvLikeImage;

    private List<LikeImage> imageList = new ArrayList<>();

    public void setImageList(List<LikeImage> imageList) {
        this.imageList = imageList;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_view_pager, container, false);
        rvLikeImage = view.findViewById(R.id.rv_view_pager);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvLikeImage.setLayoutManager(staggeredGridLayoutManager);
        UserViewRvAdapter adapter = new UserViewRvAdapter();
        adapter.setImageList(imageList);
        Log.d("UserLikeFragment", "list--------->" + imageList.size());
        rvLikeImage.setAdapter(adapter);
        return view;
    }

}