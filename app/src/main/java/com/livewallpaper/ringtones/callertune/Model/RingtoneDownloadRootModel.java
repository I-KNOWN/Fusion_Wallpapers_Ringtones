package com.livewallpaper.ringtones.callertune.Model;

import java.util.ArrayList;

public class RingtoneDownloadRootModel {
    ArrayList<RingtoneDownloadModel> downloadModels;

    public RingtoneDownloadRootModel(ArrayList<RingtoneDownloadModel> downloadModels) {
        this.downloadModels = downloadModels;
    }

    public ArrayList<RingtoneDownloadModel> getDownloadModels() {
        return downloadModels;
    }

    public void setDownloadModels(ArrayList<RingtoneDownloadModel> downloadModels) {
        this.downloadModels = downloadModels;
    }
}
