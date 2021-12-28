package com.example.guetshareimagedemo.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.model.bean.UpLoadImage;
import com.example.guetshareimagedemo.utils.Constants;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangSilong on 2021/12/28.
 */
public class UserSendRvAdapter extends RecyclerView.Adapter<UserSendRvAdapter.SendViewHolder> {

    private List<UpLoadImage> upLoadImageList;
    private List<Integer> heightList = new ArrayList<>();

    public void setUpLoadImageList(List<UpLoadImage> upLoadImageList) {
        this.upLoadImageList = upLoadImageList;
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
    public SendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_send_fragment, parent, false);
        return new SendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SendViewHolder holder, int position) {
        ViewGroup.LayoutParams params = holder.sendImage.getLayoutParams();
        params.height = heightList.get(position);
        holder.sendImage.setLayoutParams(params);
        String base64 = upLoadImageList.get(position).getBase64();
        Glide.with(holder.sendImage.getContext()).load(Constants.HEAD_BASE_64 + base64).into(holder.sendImage);
    }

    @Override
    public int getItemCount() {
        return upLoadImageList.size();
    }

    static class SendViewHolder extends RecyclerView.ViewHolder{

        private ShapeableImageView sendImage;

        public SendViewHolder(@NonNull View itemView) {
            super(itemView);
            sendImage = itemView.findViewById(R.id.send_image);
        }
    }
}
