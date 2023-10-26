package com.livewallpaper.ringtones.callertune.Model;

public class CategoryModel {

    String categoryName;
    String categoryDesc;
    int categoryImage;

    public CategoryModel(String categoryName, String categoryDesc, int categoryImage) {
        this.categoryName = categoryName;
        this.categoryDesc = categoryDesc;
        this.categoryImage = categoryImage;
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

    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }
}
