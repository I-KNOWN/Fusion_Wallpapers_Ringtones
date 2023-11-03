package com.livewallpaper.ringtones.callertune.Model;

import java.util.ArrayList;

public class WallpaperFavouriteModel {
    ArrayList<String> data;

    public WallpaperFavouriteModel(ArrayList<String> data) {
        this.data = data;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }
}
