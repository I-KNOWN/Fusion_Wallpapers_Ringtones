package com.livewallpaper.ringtones.callertune.Retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BodyModel {

    @SerializedName("package_name")
    private String packageName;
    @SerializedName("file_category")
    private List<String> fileCategory;

    public BodyModel(String packageName, List<String> fileCategory) {
        this.packageName = packageName;
        this.fileCategory = fileCategory;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<String> getFileCategory() {
        return fileCategory;
    }

    public void setFileCategory(List<String> fileCategory) {
        this.fileCategory = fileCategory;
    }
}
