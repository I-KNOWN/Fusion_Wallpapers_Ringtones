package com.livewallpaper.ringtones.callertune.Model;

public class RingtoneData {
    String ringtoneName;
    String ringtoneAuthor;
    String ringtoneTime;
    String ringtonePreviewImage;
    String ringtoneUrl;

    public RingtoneData(String ringtoneName, String ringtoneAuthor, String ringtoneTime, String ringtonePreviewImage, String ringtoneUrl) {
        this.ringtoneName = ringtoneName;
        this.ringtoneAuthor = ringtoneAuthor;
        this.ringtoneTime = ringtoneTime;
        this.ringtonePreviewImage = ringtonePreviewImage;
        this.ringtoneUrl = ringtoneUrl;
    }

    public String getRingtoneName() {
        return ringtoneName;
    }

    public void setRingtoneName(String ringtoneName) {
        this.ringtoneName = ringtoneName;
    }

    public String getRingtoneAuthor() {
        return ringtoneAuthor;
    }

    public void setRingtoneAuthor(String ringtoneAuthor) {
        this.ringtoneAuthor = ringtoneAuthor;
    }

    public String getRingtoneTime() {
        return ringtoneTime;
    }

    public void setRingtoneTime(String ringtoneTime) {
        this.ringtoneTime = ringtoneTime;
    }

    public String getRingtonePreviewImage() {
        return ringtonePreviewImage;
    }

    public void setRingtonePreviewImage(String ringtonePreviewImage) {
        this.ringtonePreviewImage = ringtonePreviewImage;
    }

    public String getRingtoneUrl() {
        return ringtoneUrl;
    }

    public void setRingtoneUrl(String ringtoneUrl) {
        this.ringtoneUrl = ringtoneUrl;
    }
}
