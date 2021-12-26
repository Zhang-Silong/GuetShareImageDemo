package com.example.guetshareimagedemo.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.model.bean.UpLoadImage;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

/**
 * Created by ZhangSilong on 2021/12/26.
 */
public class SpaceRvAdapter extends RecyclerView.Adapter<SpaceRvAdapter.SpaceViewHolder> {

    private List<UpLoadImage> upLoadImageList;

    public SpaceRvAdapter() {
    }

    public void setUpLoadImageList(List<UpLoadImage> upLoadImageList) {
        this.upLoadImageList = upLoadImageList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SpaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_space, parent, false);
        return new SpaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpaceViewHolder holder, int position) {
        String nickName = upLoadImageList.get(position).getNickName();
        String userImage = upLoadImageList.get(position).getUserImage();
        String imageUrl = upLoadImageList.get(position).getImageUrl();
        String title = upLoadImageList.get(position).getImageTitle();
        String msg = upLoadImageList.get(position).getImageMsg();
        Glide.with(holder.spaceUserImage.getContext()).load(userImage).into(holder.spaceUserImage);
        Glide.with(holder.spaceImage.getContext()).load(imageUrl).into(holder.spaceImage);
        holder.userNickName.setText(nickName);
        holder.spaceTitle.setText(title);
        holder.spaceMsg.setText(msg);
    }

    @Override
    public int getItemCount() {
        return upLoadImageList.size();
    }

    static class SpaceViewHolder extends RecyclerView.ViewHolder{

        ShapeableImageView spaceUserImage;
        TextView userNickName;
        TextView userAttention;
        ImageView spaceImage;
        TextView spaceTitle;
        TextView spaceMsg;
        ImageView spaceLike;

        public SpaceViewHolder(@NonNull View itemView) {
            super(itemView);
            spaceUserImage = itemView.findViewById(R.id.space_user_image);
            userNickName = itemView.findViewById(R.id.user_nickname);
            userAttention = itemView.findViewById(R.id.user_attention);
            spaceImage = itemView.findViewById(R.id.space_image);
            spaceTitle = itemView.findViewById(R.id.space_title);
            spaceMsg = itemView.findViewById(R.id.space_msg);
            spaceLike = itemView.findViewById(R.id.space_like);
        }
    }
}
