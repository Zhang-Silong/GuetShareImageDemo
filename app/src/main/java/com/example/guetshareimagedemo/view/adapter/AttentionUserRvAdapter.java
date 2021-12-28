package com.example.guetshareimagedemo.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.model.bean.UserAttention;
import com.example.guetshareimagedemo.utils.Constants;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

/**
 * Created by ZhangSilong on 2021/12/27.
 */
public class AttentionUserRvAdapter extends RecyclerView.Adapter<AttentionUserRvAdapter.UserViewHolder> {

    private List<UserAttention> attentionList;

    public AttentionUserRvAdapter() {
    }

    public void setAttentionList(List<UserAttention> attentionList) {
        this.attentionList = attentionList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attention_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        String imageUrl = attentionList.get(position).getUserImage();
        String base64 = attentionList.get(position).getUserImageBase64();
        String nickName = attentionList.get(position).getNickName();
        Glide.with(holder.topUserImage.getContext()).load(Constants.HEAD_BASE_64 + base64).into(holder.topUserImage);
        holder.topNickName.setText(nickName);
    }

    @Override
    public int getItemCount() {
        return attentionList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView topUserImage;
        TextView topNickName;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            topUserImage = itemView.findViewById(R.id.attention_top_user_image);
            topNickName = itemView.findViewById(R.id.attention_top_user_nickname);
        }
    }

}
