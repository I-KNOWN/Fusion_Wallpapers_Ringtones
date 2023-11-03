package com.livewallpaper.ringtones.callertune.Utils;

import android.os.Environment;

import java.io.File;

public class Constants {
    public static final String ADJUST = " @adjust brightness 0 @adjust contrast 1 @adjust saturation 1 @adjust sharpen 0 @adjust exposure 0 @adjust hue 0 ";
    public static final String KEYBOARD_BG = "BACKGROUND_FILE";
    public static final String RINGTONE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES) + File.separator + ".FusionRingtone/";
    public static final String RINGTONE_DOWNLOAD = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "FusionRingtone/";

    public static final String WALLPAPER_FAV = "FAV_WALLPAPER";
    public static final String KEYBOARD_FAV = "FAV_KEYBOARD";
    public static final String RINGTONE_FAV = "FAV_RINGTONE";

    public static final String DOWNALOD_RINGTONE = "DOWN_RING";

}
