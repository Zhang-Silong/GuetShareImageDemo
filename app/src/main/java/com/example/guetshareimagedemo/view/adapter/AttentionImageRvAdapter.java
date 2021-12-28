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
import com.example.guetshareimagedemo.model.bean.UserAttention;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import cn.leancloud.LCObject;
import cn.leancloud.LCQuery;
import cn.leancloud.LCUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by ZhangSilong on 2021/12/27.
 */
public class AttentionImageRvAdapter extends RecyclerView.Adapter<AttentionImageRvAdapter.ImageViewHolder> {

    private List<UserAttention> attentionList;

    public AttentionImageRvAdapter() {
    }

    public void setAttentionList(List<UserAttention> attentionList) {
        this.attentionList = attentionList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attention_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String attentionImage = attentionList.get(position).getImageUrl();
        String msg = attentionList.get(position).getImageMsg();
        String userImage = attentionList.get(position).getUserImage();
        String nickName = attentionList.get(position).getNickName();
        Glide.with(holder.attentionImage).load(attentionImage).into(holder.attentionImage);
        holder.attentionMsg.setText(msg);
        Glide.with(holder.attentionUserImage.getContext()).load(userImage).into(holder.attentionUserImage);
        holder.attentionUserNickName.setText(nickName);
        holder.attentionLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return attentionList.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder{

        ShapeableImageView attentionImage;
        TextView attentionMsg;
        ShapeableImageView attentionUserImage;
        TextView attentionUserNickName;
        ImageView attentionLike;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            attentionImage = itemView.findViewById(R.id.attention_image);
            attentionMsg = itemView.findViewById(R.id.attention_msg);
            attentionUserImage = itemView.findViewById(R.id.attention_user_image);
            attentionUserNickName = itemView.findViewById(R.id.attention_user_nickname);
            attentionLike = itemView.findViewById(R.id.attention_like);
        }
    }

}
