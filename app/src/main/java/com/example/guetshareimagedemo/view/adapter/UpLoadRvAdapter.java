package com.example.guetshareimagedemo.view.adapter;

import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.model.bean.UpLoadImage;
import com.example.guetshareimagedemo.utils.SizeUtils;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

/**
 * Created by ZhangSilong on 2021/12/26.
 */
public class UpLoadRvAdapter extends RecyclerView.Adapter<UpLoadRvAdapter.UpLoadViewHolder> {

    private List<UpLoadImage> upLoadImageList;
    private int horizontalCount = 0;

    public UpLoadRvAdapter() {
    }

    public void setUpLoadImageList(List<UpLoadImage> upLoadImageList) {
        this.upLoadImageList = upLoadImageList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UpLoadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upload_iamge, parent, false);
        return new UpLoadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpLoadViewHolder holder, int position) {
        if (upLoadImageList.size() < 3) {
            horizontalCount  = upLoadImageList.size();
        }else {
            horizontalCount = 3;
        }
        String imagePath = upLoadImageList.get(position).getImageUrl();
        Point point = SizeUtils.getScreenSize(holder.upLoadImage.getContext());
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(point.x / 4, point.x / 4);
        holder.itemView.setLayoutParams(layoutParams);
        Glide.with(holder.upLoadImage.getContext()).load(imagePath).into(holder.upLoadImage);
    }

    @Override
    public int getItemCount() {
        return upLoadImageList.size();
    }

    static class UpLoadViewHolder extends RecyclerView.ViewHolder{

        ShapeableImageView upLoadImage;

        public UpLoadViewHolder(@NonNull View itemView) {
            super(itemView);
            upLoadImage = itemView.findViewById(R.id.user_upload_image);
        }
    }

}
