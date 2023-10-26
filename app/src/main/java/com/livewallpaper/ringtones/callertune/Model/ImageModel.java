package com.livewallpaper.ringtones.callertune.Model;

import com.google.gson.annotations.SerializedName;

public class ImageModel {


    @SerializedName("file_category")
    private String fileCategory;
    @SerializedName("image_title")
    private String imageTitle;
    @SerializedName("app_image")
    private String appImage;

    public String getFileCategory() {
        return fileCategory;
    }

    public void setFileCategory(String fileCategory) {
        this.fileCategory = fileCategory;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getAppImage() {
        return appImage;
    }

    public void setAppImage(String appImage) {
        this.appImage = appImage;
    }
}
