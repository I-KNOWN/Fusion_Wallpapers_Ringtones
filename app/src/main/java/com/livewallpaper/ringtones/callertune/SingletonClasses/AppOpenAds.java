package com.livewallpaper.ringtones.callertune.SingletonClasses;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.adsmodule.api.adsModule.AdUtils;
import com.adsmodule.api.adsModule.interfaces.AppInterfaces;
import com.adsmodule.api.adsModule.retrofit.APICallHandler;
import com.adsmodule.api.adsModule.retrofit.AdsDataRequestModel;
import com.adsmodule.api.adsModule.retrofit.AdsResponseModel;
import com.adsmodule.api.adsModule.utils.Constants;
import com.livewallpaper.ringtones.callertune.Activity.SplashActivity;


public class AppOpenAds implements LifecycleObserver, Application.ActivityLifecycleCallbacks {


    @SuppressLint("StaticFieldLeak")
    public static Activity activity;
    MyApplication application;
    boolean isAdShowing;
    Bundle bundle;


    public AppOpenAds(MyApplication application) {
        this.application = application;
        application.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        isAdShowing = false;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
        if (activity.getComponentName().getClassName().contains("com.livewallpaper.ringtones.callertune")){
            MyApplication.makeStatusBarTransparentLight(activity);
        }
        AppOpenAds.activity = activity;
        bundle = savedInstanceState;

        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                if (!activity.getComponentName().getClassName().equals("com.google.android.gms.ads.AdActivity")) {

                    Log.e("isConnected", "true");
                    if (Constants.adsResponseModel.getApp_name().isEmpty()) {
                        APICallHandler.callAdsApi(activity, Constants.MAIN_BASE_URL, new AdsDataRequestModel(activity.getPackageName(), ""), new AppInterfaces.AdDataInterface() {
                            @Override
                            public void getAdData(AdsResponseModel adsResponseModel) {
                                if (adsResponseModel != null) {
                                    Constants.adsResponseModel = adsResponseModel;
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onLost(@NonNull Network network) {
                // network unavailable
                Log.e("isConnected", "false");
            }
        };

        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else {
            NetworkRequest request = new NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build();
            connectivityManager.registerNetworkCallback(request, networkCallback);
        }
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        if (!activity.getComponentName().getClassName().equals("com.google.android.gms.ads.AdActivity"))
            AppOpenAds.activity = activity;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        if (Constants.adsResponseModel != null && Constants.adsResponseModel.isShow_ads()) {
            if (!isAdShowing && AppOpenAds.activity != null && (!AppOpenAds.activity.getClass().getName().equals(SplashActivity.class.getName()))) {
                isAdShowing = true;

                AdUtils.showAppOpenAds(Constants.adsResponseModel.getApp_open_ads().getAdx(), AppOpenAds.activity, state_load -> {
                    isAdShowing = false;
                });

            } else {
                isAdShowing = false;
            }
        }

    }

    @Override
    public void onActivityResumed(Activity activity) {

        AppOpenAds.activity = activity;

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
    }

}