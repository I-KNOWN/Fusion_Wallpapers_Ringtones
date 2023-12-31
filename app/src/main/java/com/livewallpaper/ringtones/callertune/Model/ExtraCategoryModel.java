package com.livewallpaper.ringtones.callertune.Model;

public class ExtraCategoryModel {
    String catName;
    String category;
    String catAuthor;
    String catTime;
    String ringtoneImg;
    String catPreivewImageUrl;
    String ringtoneUrl;

    public ExtraCategoryModel(String catName, String catAuthor, String catTime, String catPreivewImageUrl) {
        this.catName = catName;
        this.catAuthor = catAuthor;
        this.catTime = catTime;
        this.catPreivewImageUrl = catPreivewImageUrl;
    }


    public String getRingtoneUrl() {
        return ringtoneUrl;
    }

    public void setRingtoneUrl(String ringtoneUrl) {
        this.ringtoneUrl = ringtoneUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRingtoneImg() {
        return ringtoneImg;
    }

    public void setRingtoneImg(String ringtoneImg) {
        this.ringtoneImg = ringtoneImg;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatAuthor() {
        return catAuthor;
    }

    public void setCatAuthor(String catAuthor) {
        this.catAuthor = catAuthor;
    }

    public String getCatTime() {
        return catTime;
    }

    public void setCatTime(String catTime) {
        this.catTime = catTime;
    }

    public String getCatPreivewImageUrl() {
        return catPreivewImageUrl;
    }

    public void setCatPreivewImageUrl(String catPreivewImageUrl) {
        this.catPreivewImageUrl = catPreivewImageUrl;
    }
}
