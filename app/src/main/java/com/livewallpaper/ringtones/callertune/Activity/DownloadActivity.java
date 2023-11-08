package com.livewallpaper.ringtones.callertune.Activity;

import static com.livewallpaper.ringtones.callertune.SingletonClasses.AppOpenAds.activity;
import static com.livewallpaper.ringtones.callertune.Utils.Constants.DOWNALOD_RINGTONE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.adsmodule.api.adsModule.AdUtils;
import com.adsmodule.api.adsModule.utils.Constants;
import com.google.gson.Gson;
import com.livewallpaper.ringtones.callertune.Adapter.SeeAllItemAdapter;
import com.livewallpaper.ringtones.callertune.Adapter.WallpaperItemAdapter;
import com.livewallpaper.ringtones.callertune.CustomViews.ConstraintWithBoolean;
import com.livewallpaper.ringtones.callertune.Model.ExtraCategoryModel;
import com.livewallpaper.ringtones.callertune.Model.RingtoneDownloadModel;
import com.livewallpaper.ringtones.callertune.Model.RingtoneDownloadRootModel;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.SingletonClasses.MyApplication;
import com.livewallpaper.ringtones.callertune.Utils.Util;
import com.livewallpaper.ringtones.callertune.databinding.ActivityDownloadBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class DownloadActivity extends AppCompatActivity {

    ActivityDownloadBinding binding;
    Animation slideUp, slideDown, initialUp;
    WallpaperItemAdapter wallpaperItemAdapter;
    SeeAllItemAdapter seeAllItemAdapter;
    ArrayList<String> imgPath;
    ArrayList<ExtraCategoryModel> downlaodModel;
    String currentFrag ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDownloadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        currentFrag = "wallpaper";
        imgPath = new ArrayList<>();
        downlaodModel = new ArrayList<>();
        initTabAnimation();
        initStartTabAnimation();
        initTabButton();
        initBtn();
    }

    private void initWallpaperData() {
        imgPath.clear();
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File myDir = new File(root + "/Fusion_Wallpaper");
        if(!myDir.exists()){
            myDir.mkdir();
        }
        File[] allImages = myDir.listFiles();
        ArrayList<File> allData = new ArrayList<>();
        allData.addAll(Arrays.asList(allImages));
        for(File file: allData){
            imgPath.add(file.getAbsolutePath());
        }
        if(allData.size() == 0){
            binding.tvFav.setVisibility(View.VISIBLE);
            binding.rvData.setVisibility(View.GONE);
        }else{
            binding.tvFav.setVisibility(View.GONE);
            binding.rvData.setVisibility(View.VISIBLE);
            initRecyclerViewWallpaper();
        }
    }

    private void initRecyclerViewWallpaper() {
        wallpaperItemAdapter = new WallpaperItemAdapter(DownloadActivity.this, imgPath);
        binding.rvData.setLayoutManager(new GridLayoutManager(DownloadActivity.this, 2));
        binding.rvData.setAdapter(wallpaperItemAdapter);
    }
    private void initRingtoneData() {
        downlaodModel.clear();
        String downloadString = MyApplication.getPreferences().getString(DOWNALOD_RINGTONE, "");
        if(!downloadString.isEmpty()){
            RingtoneDownloadRootModel downlaodmodel = new Gson().fromJson(downloadString, RingtoneDownloadRootModel.class);
            for(RingtoneDownloadModel ringtoneDownloadModel: downlaodmodel.getDownloadModels()){
                String path = ringtoneDownloadModel.getSavedPath();
                File downloadFile = new File(path);
                if(downloadFile.exists()){

                    ExtraCategoryModel extraCategoryModel = new ExtraCategoryModel(
                            ringtoneDownloadModel.getModel().getCatName(),
                            ringtoneDownloadModel.getModel().getCatAuthor(),
                            ringtoneDownloadModel.getModel().getCatTime(),
                            ""

                    );
                    extraCategoryModel.setRingtoneImg(ringtoneDownloadModel.getModel().getRingtoneImg());
                    extraCategoryModel.setCategory("download");
                    extraCategoryModel.setRingtoneUrl(ringtoneDownloadModel.getModel().getRingtoneUrl());
                    downlaodModel.add(extraCategoryModel);
                }else{
                    ArrayList<RingtoneDownloadModel> ringtoneDownloadModels = new ArrayList<>();
                    ringtoneDownloadModels.addAll(downlaodmodel.getDownloadModels());
                    ringtoneDownloadModels.remove(ringtoneDownloadModel);
                    downlaodmodel.setDownloadModels(ringtoneDownloadModels);
                    String convertedData = new Gson().toJson(downlaodmodel);
                    MyApplication.getPreferences().putString(DOWNALOD_RINGTONE, convertedData);
                }
            }
            if(downlaodmodel.getDownloadModels().size() == 0){
                binding.tvFav.setVisibility(View.VISIBLE);
                binding.rvData.setVisibility(View.GONE);
            }else{
                binding.tvFav.setVisibility(View.GONE);
                binding.rvData.setVisibility(View.VISIBLE);
                initRingtoneRecyclerView();
            }


        }else{
            binding.tvFav.setVisibility(View.VISIBLE);
            binding.rvData.setVisibility(View.GONE);
        }

    }

    private void initRingtoneRecyclerView() {
        seeAllItemAdapter = new SeeAllItemAdapter(DownloadActivity.this, downlaodModel, "ringtone", new SeeAllItemAdapter.onClickInputMethod() {
            @Override
            public void onClickIdentifyKeyboard() {
/*                mState = NONE;
                pickInput();*/
            }

            @Override
            public void onWallpaperClick(Bitmap resource, String url) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    String filename = "bitmap.png";

                    try {
                        FileOutputStream stream = openFileOutput(filename, Context.MODE_PRIVATE);
                        resource.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        stream.close();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }

                    Intent intent =new Intent(DownloadActivity.this, WallpaperViewerActivity.class);
                    intent.putExtra("filename", filename);
                    intent.putExtra("activity", filename);
                    intent.putExtra("url", url);
                    startActivity(intent);
                });

            }


        });
        binding.rvData.setLayoutManager(new LinearLayoutManager(DownloadActivity.this, LinearLayoutManager.VERTICAL, false));
        binding.rvData.setAdapter(seeAllItemAdapter);
    }

    private void initBtn() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), state_load -> {finish();});
    }

    private void initStartTabAnimation() {
        binding.clParentTvWallpaper.animate()
                .translationY(Util.pxFromDp(DownloadActivity.this, 60))
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        binding.clParentTvRingtone.startAnimation(initialUp);
                        binding.llRingtone.startAnimation(initialUp);
                    }
                })
                .setDuration(0);

        binding.clParentTvRingtone.animate()
                .translationY(Util.pxFromDp(DownloadActivity.this, 60))
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .setDuration(0);
    }

    private void initTabAnimation() {
        slideUp = AnimationUtils.loadAnimation(DownloadActivity.this, R.anim.slide_up_tab);
        slideDown = AnimationUtils.loadAnimation(DownloadActivity.this, R.anim.slide_down_tab);
        initialUp = AnimationUtils.loadAnimation(DownloadActivity.this, R.anim.slide_up_tab_fast);
        slideDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.clWallpaper.setOnClickListener(null);
                binding.clRingtone.setOnClickListener(null);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                initTabButton();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        slideUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.clWallpaper.setOnClickListener(null);
                binding.clRingtone.setOnClickListener(null);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                initTabButton();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void initTabButton() {
        binding.clWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!currentFrag.equals("wallpaper")){
                    undoClick();
                    currentFrag = "wallpaper";
//                    initReyclerView(currentFrag);
                    initWallpaperData();
                    ConstraintWithBoolean constraintWithBoolean = (ConstraintWithBoolean) view;
                    animateTabButton(constraintWithBoolean);
                }
            }
        });

        binding.clRingtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!currentFrag.equals("ringtone")){
                    undoClick();
                    currentFrag = "ringtone";
//                    initReyclerView(currentFrag);
                    initRingtoneData();
                    ConstraintWithBoolean constraintWithBoolean = (ConstraintWithBoolean) view;
                    animateTabButton(constraintWithBoolean);
                }
            }
        });
    }

    private void undoClick() {
        if (currentFrag.equals("wallpaper")) {
            binding.clWallpaper.getChildAt(0).startAnimation(slideUp);
            binding.clWallpaper.getChildAt(1).startAnimation(slideUp);
            binding.clWallpaper.setIsOn(false);
        } else if (currentFrag.equals("ringtone")) {
            binding.clRingtone.getChildAt(0).startAnimation(slideUp);
            binding.clRingtone.getChildAt(1).startAnimation(slideUp);
            binding.clRingtone.setIsOn(false);
        }

    }
    private void animateTabButton(ConstraintWithBoolean constraintWithBoolean) {
        if (constraintWithBoolean.isOn()) {
            constraintWithBoolean.getChildAt(0).startAnimation(slideUp);
            constraintWithBoolean.getChildAt(1).startAnimation(slideUp);
            constraintWithBoolean.setIsOn(false);
        } else {
            constraintWithBoolean.getChildAt(0).startAnimation(slideDown);
            constraintWithBoolean.getChildAt(1).startAnimation(slideDown);
            constraintWithBoolean.setIsOn(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentFrag.equals("wallpaper")){
            initWallpaperData();
        } else if (currentFrag.equals("ringtone")) {
            initRingtoneData();
        }
    }


}