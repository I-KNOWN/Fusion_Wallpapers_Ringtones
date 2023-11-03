package com.livewallpaper.ringtones.callertune.Model;

public class RingtoneDownloadModel {
    ExtraCategoryModel model;
    String ringtoneName;
    String savedName;

    String savedPath;

    public RingtoneDownloadModel(ExtraCategoryModel model, String ringtoneName, String savedName, String savedPath) {
        this.model = model;
        this.ringtoneName = ringtoneName;
        this.savedName = savedName;
        this.savedPath = savedPath;
    }

    public ExtraCategoryModel getModel() {
        return model;
    }

    public void setModel(ExtraCategoryModel model) {
        this.model = model;
    }

    public String getRingtoneName() {
        return ringtoneName;
    }

    public void setRingtoneName(String ringtoneName) {
        this.ringtoneName = ringtoneName;
    }

    public String getSavedName() {
        return savedName;
    }

    public void setSavedName(String savedName) {
        this.savedName = savedName;
    }

    public String getSavedPath() {
        return savedPath;
    }

    public void setSavedPath(String savedPath) {
        this.savedPath = savedPath;
    }
}
