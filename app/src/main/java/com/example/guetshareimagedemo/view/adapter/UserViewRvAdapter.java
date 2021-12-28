package com.example.guetshareimagedemo.view.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.model.bean.LikeImage;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangSilong on 2021/12/25.
 */
public class UserViewRvAdapter extends RecyclerView.Adapter<UserViewRvAdapter.LikeViewHolder> {

    private List<LikeImage> imageList;
    private List<Integer> heightList = new ArrayList<>();


    @SuppressLint("NotifyDataSetChanged")
    public void setImageList(List<LikeImage> imageList) {
        this.imageList = imageList;
        notifyDataSetChanged();
        initHeightList();
    }


    public void initHeightList() {
        heightList.add(1000);
        heightList.add(1000);
        for (int i = 2; i < 1000; i++) {
            if (i % 2 == 0) {
                heightList.add(1200);
            }else {
                heightList.add(1000);
            }
        }
    }

    @NonNull
    @Override
    public LikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_like_image, parent, false);
        return new LikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LikeViewHolder holder, int position) {
        Log.d("RVTest", String.valueOf(position + imageList.size()));
        ViewGroup.LayoutParams params = holder.likeImage.getLayoutParams();
        params.height = heightList.get(position);
        holder.likeImage.setLayoutParams(params);
        String base64 = imageList.get(position).getBase64();
        Glide.with(holder.likeImage.getContext()).load(imageList.get(position).getImageUrl()).into(holder.likeImage);

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    static class LikeViewHolder extends RecyclerView.ViewHolder{

        ShapeableImageView likeImage;

        public LikeViewHolder(@NonNull View itemView) {
            super(itemView);
            likeImage = itemView.findViewById(R.id.like_image);
        }
    }
}
