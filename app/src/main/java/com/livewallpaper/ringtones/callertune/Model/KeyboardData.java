package com.livewallpaper.ringtones.callertune.Model;

public class KeyboardData {
    String keyboardName;
    String keyboardBgUrl;

    public KeyboardData(String keyboardName, String keyboardBgUrl) {
        this.keyboardName = keyboardName;
        this.keyboardBgUrl = keyboardBgUrl;
    }

    public String getKeyboardName() {
        return keyboardName;
    }

    public void setKeyboardName(String keyboardName) {
        this.keyboardName = keyboardName;
    }

    public String getKeyboardBgUrl() {
        return keyboardBgUrl;
    }

    public void setKeyboardBgUrl(String keyboardBgUrl) {
        this.keyboardBgUrl = keyboardBgUrl;
    }
}
