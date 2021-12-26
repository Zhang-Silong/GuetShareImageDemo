package com.example.guetshareimagedemo.model.bean;

/**
 * Created by ZhangSilong on 2021/12/24.
 */
public class LikeImage {

    private String imageUrl;
    private String account;

    public LikeImage() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "LikeImage{" +
                "imageUrl='" + imageUrl + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}
