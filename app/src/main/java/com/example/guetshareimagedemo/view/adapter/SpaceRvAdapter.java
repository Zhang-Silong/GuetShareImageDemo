package com.example.guetshareimagedemo.view.adapter;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.model.bean.UpLoadImage;
import com.example.guetshareimagedemo.utils.BitmapUtils;
import com.example.guetshareimagedemo.utils.Constants;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import cn.leancloud.LCObject;
import cn.leancloud.LCQuery;
import cn.leancloud.LCUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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
    public void onBindViewHolder(@NonNull SpaceViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String nickName = upLoadImageList.get(position).getNickName();
        String account = upLoadImageList.get(position).getAccount();
        String userImage = upLoadImageList.get(position).getUserImage();
        String imageUrl = upLoadImageList.get(position).getImageUrl();
        String userImageBase64 = upLoadImageList.get(position).getUserImageBase64();
        String title = upLoadImageList.get(position).getImageTitle();
        String msg = upLoadImageList.get(position).getImageMsg();
        String base64 = upLoadImageList.get(position).getBase64();
        Glide.with(holder.spaceUserImage.getContext()).load(Constants.HEAD_BASE_64 + userImageBase64).into(holder.spaceUserImage);
        Glide.with(holder.spaceImage.getContext()).load(Constants.HEAD_BASE_64 + base64).into(holder.spaceImage);
        holder.userNickName.setText(nickName);
        holder.spaceTitle.setText(title);
        holder.spaceMsg.setText(msg);

        LCUser currentUser = LCUser.getCurrentUser();
        if (currentUser != null) {
            LCQuery<LCObject> query = new LCQuery<>("UserAttention");
            query.whereEqualTo("currentAccount", currentUser.getUsername());
            query.findInBackground().subscribe(new Observer<List<LCObject>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @SuppressLint("ResourceAsColor")
                @Override
                public void onNext(@NonNull List<LCObject> lcObjects) {
                    for (LCObject object : lcObjects) {
                        if (upLoadImageList.get(position).getAccount().equals((String) object.get("currentAccount"))) {
                            holder.spaceAttention.setText("已关注");
                            holder.spaceAttention.setTextColor(R.color.black);
                            holder.spaceAttention.setBackgroundResource(R.drawable.shape_text_bg);
                        }
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });

        }

        holder.spaceAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LCUser currentUser = LCUser.getCurrentUser();
                if (currentUser != null) {
                    LCQuery<LCObject> query = new LCQuery<>("UserAttention");
                    query.whereEqualTo("currentAccount", currentUser.getUsername());
                    query.findInBackground().subscribe(new Observer<List<LCObject>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onNext(@NonNull List<LCObject> lcObjects) {
                            if (account == currentUser.getUsername()){
                                Toast.makeText(holder.spaceAttention.getContext(), "关注失败！", Toast.LENGTH_SHORT).show();
                            } else {
                                LCObject lcObject = new LCObject("UserAttention");
                                lcObject.put("currentAccount", currentUser.getUsername());
                                lcObject.put("imageUrl", imageUrl);
                                lcObject.put("account", account);
                                lcObject.put("imageMsg", title);
                                lcObject.put("userImage", userImage);
                                String base64 = BitmapUtils.bitmapToBase64(BitmapFactory.decodeFile(imageUrl));
                                lcObject.put("base64", base64);
                                lcObject.put("userImageBase64", userImageBase64);
                                lcObject.saveInBackground().subscribe(new Observer<LCObject>() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onNext(@NonNull LCObject lcObject) {

                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                                //holder.spaceAttention.setEnabled(false);
                                holder.spaceAttention.setText("已关注");
                                holder.spaceAttention.setTextColor(R.color.black);
                                holder.spaceAttention.setBackgroundResource(R.drawable.shape_text_bg);
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return upLoadImageList.size();
    }

    static class SpaceViewHolder extends RecyclerView.ViewHolder{

        ShapeableImageView spaceUserImage;
        TextView userNickName;
        TextView spaceAttention;
        ImageView spaceImage;
        TextView spaceTitle;
        TextView spaceMsg;
        ImageView spaceLike;

        public SpaceViewHolder(@NonNull View itemView) {
            super(itemView);
            spaceUserImage = itemView.findViewById(R.id.space_user_image);
            userNickName = itemView.findViewById(R.id.user_nickname);
            spaceAttention = itemView.findViewById(R.id.space_attention);
            spaceImage = itemView.findViewById(R.id.space_image);
            spaceTitle = itemView.findViewById(R.id.space_title);
            spaceMsg = itemView.findViewById(R.id.space_msg);
            spaceLike = itemView.findViewById(R.id.space_like);
        }
    }
}
