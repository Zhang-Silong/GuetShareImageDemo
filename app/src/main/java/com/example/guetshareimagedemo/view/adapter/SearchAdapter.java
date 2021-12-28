package com.example.guetshareimagedemo.view.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.model.bean.HomeLoadMoreImageBean;
import com.example.guetshareimagedemo.view.activity.ShowImageDetailsActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangSilong on 2021/12/28.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<HomeLoadMoreImageBean.ResBean.VerticalBean> searchList;

    private List<Integer> heightList = new ArrayList<>();

    public void setSearchList(List<HomeLoadMoreImageBean.ResBean.VerticalBean> searchList) {
        this.searchList = searchList;
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
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        ViewGroup.LayoutParams params = holder.searchImage.getLayoutParams();
        params.height = heightList.get(position);
        holder.searchImage.setLayoutParams(params);
        String url = searchList.get(position).getImg();
        Glide.with(holder.searchImage.getContext()).load(searchList.get(position).getImg()).into(holder.searchImage);
        holder.searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.searchImage.getContext(), ShowImageDetailsActivity.class);
                intent.putExtra("search", url);
                holder.searchImage.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder{

        ShapeableImageView searchImage;
        TextView imageText;
        ImageButton favorite;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            searchImage = itemView.findViewById(R.id.search_image);
            /*imageText = itemView.findViewById(R.id.search_image_text);
            favorite = itemView.findViewById(R.id.search_image_favorite);*/
        }
    }
}
