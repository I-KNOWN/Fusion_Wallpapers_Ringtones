package com.livewallpaper.ringtones.callertune.Model;

public class ExtraCategoryModel {
    String catName;
    String catPreivewImageUrl;

    public ExtraCategoryModel(String catName, String catPreivewImageUrl) {
        this.catName = catName;
        this.catPreivewImageUrl = catPreivewImageUrl;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatPreivewImageUrl() {
        return catPreivewImageUrl;
    }

    public void setCatPreivewImageUrl(String catPreivewImageUrl) {
        this.catPreivewImageUrl = catPreivewImageUrl;
    }
}
