package com.livewallpaper.ringtones.callertune.Model;

import java.util.ArrayList;

public class KeyboardFavouriteRootModel {
    ArrayList<KeyboardData> data;

    public KeyboardFavouriteRootModel(ArrayList<KeyboardData> data) {
        this.data = data;
    }

    public ArrayList<KeyboardData> getData() {
        return data;
    }

    public void setData(ArrayList<KeyboardData> data) {
        this.data = data;
    }
}
