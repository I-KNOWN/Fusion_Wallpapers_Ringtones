package com.livewallpaper.ringtones.callertune.Activity;

import static com.adsmodule.api.adsModule.retrofit.APICallHandler.callAdsApi;
import static com.livewallpaper.ringtones.callertune.SingletonClasses.AppOpenAds.activity;
import static com.livewallpaper.ringtones.callertune.SingletonClasses.MyApplication.getConnectionStatus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.adsmodule.api.adsModule.AdUtils;
import com.adsmodule.api.adsModule.retrofit.AdsDataRequestModel;
import com.adsmodule.api.adsModule.utils.Constants;
import com.google.gson.JsonObject;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.SingletonClasses.MyApplication;
import com.livewallpaper.ringtones.callertune.databinding.ActivitySplashBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        if (getConnectionStatus().isConnectingToInternet()) {
            callAdsApi(activity, Constants.MAIN_BASE_URL, new AdsDataRequestModel(this.getPackageName(), ""), adsResponseModel -> {
                if (adsResponseModel != null) {
                    AdUtils.showAppOpenAds(Constants.adsResponseModel.getApp_open_ads().getAdx(), activity, state_load -> {

/*                        JsonObject wallpaperData = Constants.adsResponseModel.getExtra_data_field().getWallpaper_data();
                            JsonObject kpop1 = wallpaperData.getAsJsonObject("Kpop1");
                            String category = kpop1.get("category").getAsString();
                            String url = kpop1.get("url").getAsString();

                            System.out.println("Category: " + category);
                            System.out.println("URL: " + url);*/

                        nextActivity();


                    });
                } else new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nextActivity();
                    }
                }, 1500);

            });
        } else new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        }, 1500);


    }

    private void nextActivity() {
        startActivity(MyApplication.getPreferences().isFirstRun()? new Intent(SplashActivity.this, PermissionActivity.class): new Intent(SplashActivity.this, MainActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}