package com.example.guetshareimagedemo.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.model.bean.HomeLoadMoreImageBean;
import com.example.guetshareimagedemo.model.bean.ImageBean;
import com.example.guetshareimagedemo.model.bean.User;
import com.example.guetshareimagedemo.utils.Constant;
import com.example.guetshareimagedemo.view.activity.ShowImageDetailsActivity;
import com.google.android.material.imageview.ShapeableImageView;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

import cn.leancloud.LCObject;
import cn.leancloud.LCUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by ZhangSilong on 2021/12/17.
 */
public class HomeImageAdapter extends RecyclerView.Adapter<HomeImageAdapter.MyViewHolder> {

    private List<ImageBean.ResBean.VerticalBean> imageBeanList;
    private List<HomeLoadMoreImageBean.ResBean.VerticalBean> loadMoreList;

    private boolean isClick = false;
    private int countFlag = 1;
    private Context context;

    public void setImageBeanList(List<ImageBean.ResBean.VerticalBean> imageBeanList) {
        if (this.imageBeanList != null) {
            this.imageBeanList.clear();
        }
        this.imageBeanList = imageBeanList;
        notifyDataSetChanged();
        initHeightList();
    }

    public void setLoadMoreList(List<HomeLoadMoreImageBean.ResBean.VerticalBean> loadMoreList) {
        int lastSize;
        if (this.loadMoreList == null){
            lastSize = this.imageBeanList.size();
            this.loadMoreList = loadMoreList;
        }else {
            lastSize = this.loadMoreList.size() + this.imageBeanList.size();
            this.loadMoreList.addAll(loadMoreList);
            int currentSize = this.loadMoreList.size() + this.imageBeanList.size();
            notifyItemRangeChanged(lastSize, currentSize);
        }
        Log.d("HomeImageAdapter", "lastSize------->" + lastSize);
    }

    private List<Integer> heightList = new ArrayList<>();

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
        //heightList.add(600);
    }

    public HomeImageAdapter(Context context) {
        this.context = context;
    }

    public HomeImageAdapter(List<ImageBean.ResBean.VerticalBean> imageBeanList){
        this.imageBeanList = imageBeanList;
        initHeightList();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_img, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
        params.height = heightList.get(position);
        holder.imageView.setLayoutParams(params);
        String imgUrl;
        if (position >= imageBeanList.size()){
            imgUrl = loadMoreList.get(imageBeanList.size() + loadMoreList.size() - position - 1).getImg();
        }else {
            imgUrl = imageBeanList.get(position).getImg();
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.imageView.getContext(), ShowImageDetailsActivity.class);
                intent.putExtra(Constant.KEY_SHOW_IMG_DETAILS, imgUrl);
                holder.imageView.getContext().startActivity(intent);
            }
        });

        holder.imageFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClick = !isClick;
                if (isClick) {
                    holder.imageFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                    LCObject likeImage = new LCObject("LikeImageUrl");
                    LCUser currentUser = LCUser.getCurrentUser();
                    likeImage.put("account", currentUser.getUsername());
                    likeImage.put("parentId", currentUser.getObjectId());
                    likeImage.put("imageUrl", imgUrl);
                    likeImage.saveInBackground().subscribe(new Observer<LCObject>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull LCObject lcObject) {
                            Toast.makeText(context, "收藏成功！", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                    isClick = false;
                }else {
                    holder.imageFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                    isClick = true;
                }
                isClick = !isClick;

            }
        });
        Log.d("HomeImageAdapter", "ImageList--------->" + imgUrl);
        Glide.with(holder.imageView.getContext()).load(imgUrl).into(holder.imageView);
    }



    @Override
    public int getItemCount() {
        if (loadMoreList != null){
            return imageBeanList.size() + loadMoreList.size();
        }
        return imageBeanList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        ShapeableImageView imageView;
        TextView imageText;
        ImageButton imageFavorite;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            imageText = itemView.findViewById(R.id.image_text);
            imageFavorite = itemView.findViewById(R.id.image_favorite);
        }
    }
}
