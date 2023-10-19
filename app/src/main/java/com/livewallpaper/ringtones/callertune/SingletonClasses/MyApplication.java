package com.livewallpaper.ringtones.callertune.SingletonClasses;


import static com.adsmodule.api.adsModule.retrofit.APICallHandler.callAppCountApi;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.adsmodule.api.adsModule.preferencesManager.AppPreferences;
import com.adsmodule.api.adsModule.retrofit.AdsDataRequestModel;
import com.adsmodule.api.adsModule.utils.ConnectionDetector;
import com.adsmodule.api.adsModule.utils.Constants;
import com.adsmodule.api.adsModule.utils.Global;


public class MyApplication extends Application {

    static AppPreferences preferences;
    private static MyApplication app;
    private static ConnectionDetector cd;

    public static AppPreferences getPreferences() {
        if (preferences == null)
            preferences = new AppPreferences(app);
        return preferences;
    }

    public static void makeStatusBarTransparentDark(Activity context) {
        context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        context.getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    public static void makeStatusBarTransparentLight(Activity context) {
        context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        context.getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    public static ConnectionDetector getConnectionStatus() {
        if (cd == null) {
            cd = new ConnectionDetector(app);
        }
        return cd;
    }


    public static synchronized MyApplication getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        AppPreferences preferences = new AppPreferences(app);
        if (preferences.isFirstRun()) {
            callAppCountApi(Constants.MAIN_BASE_URL, new AdsDataRequestModel(app.getPackageName(), Global.getDeviceId(app)), () -> {
                preferences.setFirstRun(false);
            });
        }

        new AppOpenAds(app);

    }


}
