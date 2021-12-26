package com.example.guetshareimagedemo.view.adapter;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.guetshareimagedemo.model.bean.ImageBean;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

/**
 * Created by ZhangSilong on 2021/12/19.
 */
public class BannerViewAdapter extends PagerAdapter {

    private List<ImageBean.ResBean.VerticalBean> verticalBeanList;

    public BannerViewAdapter(List<ImageBean.ResBean.VerticalBean> verticalBeanList) {
        this.verticalBeanList = verticalBeanList;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition = position % verticalBeanList.size();
        String imgUrl = verticalBeanList.get(realPosition).getImg();
        ImageView bannerImg = new ShapeableImageView(container.getContext());
        Glide.with(container.getContext()).load(imgUrl).into(bannerImg);
        bannerImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(bannerImg);
        return bannerImg;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
