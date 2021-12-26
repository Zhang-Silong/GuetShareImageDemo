package com.example.guetshareimagedemo.view.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.guetshareimagedemo.model.bean.LikeImage;
import com.example.guetshareimagedemo.model.bean.UpLoadImage;
import com.example.guetshareimagedemo.model.bean.User;
import com.example.guetshareimagedemo.view.fragment.UserViewPagerFragment;

import java.util.List;

/**
 * Created by ZhangSilong on 2021/12/23.
 */
public class UserViewPagerAdapter extends FragmentPagerAdapter {

    private List<String> titleList;

    private List<LikeImage> likeImageList;

    private List<UpLoadImage> upLoadImageList;

    public void setTitleList(List<String> titleList){
        this.titleList = titleList;
        notifyDataSetChanged();
    }

    public void setLikeImageList(List<LikeImage> likeImageList) {
        this.likeImageList = likeImageList;
        Log.d("User", "size----->" + likeImageList.size());
        notifyDataSetChanged();
    }

    public void setUpLoadImageList(List<UpLoadImage> upLoadImageList) {
        this.upLoadImageList = upLoadImageList;
        //Log.d("upLoad", "size----->" + likeImageList.size());
        notifyDataSetChanged();
    }

    public void setAllDataList(List<UpLoadImage> upLoadImageList, List<LikeImage> likeImageList){
        this.upLoadImageList = upLoadImageList;
        this.likeImageList = likeImageList;
    }

    public UserViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        UserViewPagerFragment userViewPagerFragment = new UserViewPagerFragment();
        /*if (position == 0) {
            userViewPagerFragment.setFlagPosition(position);
            userViewPagerFragment.setUpLoadImage(upLoadImageList);
        }else {
            userViewPagerFragment.setFlagPosition(position);
            userViewPagerFragment.setImageList(likeImageList);
        }*/
        Log.d("TestTest", String.valueOf(upLoadImageList.size() + likeImageList.size()));
        userViewPagerFragment.setAllData(upLoadImageList, likeImageList, position);
        return userViewPagerFragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    /*@Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }*/

    /*@Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }*/
}
