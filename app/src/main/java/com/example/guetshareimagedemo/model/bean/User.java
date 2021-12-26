package com.example.guetshareimagedemo.model.bean;

import java.util.List;

/**
 * Created by ZhangSilong on 2021/12/24.
 */
public class User {

    private String account;     //账号
    private String password;    //密码
    private String nickname;    //昵称
    private String gender;      //性别
    private String selfInfo;    //个人介绍
    private String userImage;   //头像
    private int awardedCount;   //获赞数
    private int attentionCount; //关注人数
    private int fans;           //粉丝数

    private List<LikeImage> likeImageUrl;  //点赞的照片
    private List<UpLoadImage> upLoadImageUrl; //发布的照片

    public User() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSelfInfo() {
        return selfInfo;
    }

    public void setSelfInfo(String selfInfo) {
        this.selfInfo = selfInfo;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public int getAwardedCount() {
        return awardedCount;
    }

    public void setAwardedCount(int awardedCount) {
        this.awardedCount = awardedCount;
    }

    public int getAttentionCount() {
        return attentionCount;
    }

    public void setAttentionCount(int attentionCount) {
        this.attentionCount = attentionCount;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public List<LikeImage> getLikeImageUrl() {
        return likeImageUrl;
    }

    public void setLikeImageUrl(List<LikeImage> likeImageUrl) {
        this.likeImageUrl = likeImageUrl;
    }

    public List<UpLoadImage> getUpLoadImageUrl() {
        return upLoadImageUrl;
    }

    public void setUpLoadImageUrl(List<UpLoadImage> upLoadImageUrl) {
        this.upLoadImageUrl = upLoadImageUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", gender='" + gender + '\'' +
                ", selfInfo='" + selfInfo + '\'' +
                ", userImage='" + userImage + '\'' +
                ", awardedCount=" + awardedCount +
                ", attentionCount=" + attentionCount +
                ", fans=" + fans +
                ", likeImageUrl=" + likeImageUrl +
                ", upLoadImageUrl=" + upLoadImageUrl +
                '}';
    }
}
