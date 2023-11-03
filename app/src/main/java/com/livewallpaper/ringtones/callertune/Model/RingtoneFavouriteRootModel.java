package com.livewallpaper.ringtones.callertune.Model;

import java.util.ArrayList;

public class RingtoneFavouriteRootModel {
    ArrayList<RingtoneData> data;


    public RingtoneFavouriteRootModel(ArrayList<RingtoneData> data) {
        this.data = data;
    }

    public ArrayList<RingtoneData> getData() {
        return data;
    }

    public void setData(ArrayList<RingtoneData> data) {
        this.data = data;
    }
}
