package com.example.guetshareimagedemo.model.bean;

/**
 * Created by ZhangSilong on 2021/12/24.
 */
public class UpLoadImage {

    private String imageUrl;
    private String account;
    private String nickName;
    private String parentId;
    private String imageTitle;
    private String imageMsg;
    private String userImage;
    private String base64;
    private boolean flag;
    private String userImageBase64;

    public String getUserImageBase64() {
        return userImageBase64;
    }

    public void setUserImageBase64(String userImageBase64) {
        this.userImageBase64 = userImageBase64;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getImageMsg() {
        return imageMsg;
    }

    public void setImageMsg(String imageMsg) {
        this.imageMsg = imageMsg;
    }

    public UpLoadImage() {
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
        return "UpLoadImage{" +
                "imageUrl='" + imageUrl + '\'' +
                ", account='" + account + '\'' +
                ", parentId='" + parentId + '\'' +
                ", imageTitle='" + imageTitle + '\'' +
                ", imageMsg='" + imageMsg + '\'' +
                '}';
    }
}
