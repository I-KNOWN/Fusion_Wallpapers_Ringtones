package com.livewallpaper.ringtones.callertune.Utils;

import android.content.Context;

public class Util {
    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }


}
