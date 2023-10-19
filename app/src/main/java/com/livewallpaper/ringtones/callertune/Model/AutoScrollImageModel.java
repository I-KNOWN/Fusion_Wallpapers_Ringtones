package com.livewallpaper.ringtones.callertune.Model;

import java.util.ArrayList;

public class AutoScrollImageModel {
    private ArrayList<Integer> imageList;

    public AutoScrollImageModel(ArrayList<Integer> imageList) {
        this.imageList = imageList;
    }

    public ArrayList<Integer> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<Integer> imageList) {
        this.imageList = imageList;
    }
}
