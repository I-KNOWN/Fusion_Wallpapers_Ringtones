package com.livewallpaper.ringtones.callertune.Model;

public class CategoryModel {

    String categoryName;
    String categoryDesc;
    String categoryImageUrl;

    public CategoryModel(String categoryName, String categoryDesc, String categoryImageUrl) {
        this.categoryName = categoryName;
        this.categoryDesc = categoryDesc;
        this.categoryImageUrl = categoryImageUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public String getCategoryImageUrl() {
        return categoryImageUrl;
    }

    public void setCategoryImageUrl(String categoryImageUrl) {
        this.categoryImageUrl = categoryImageUrl;
    }
}
