package com.livewallpaper.ringtones.callertune.Activity;

import static com.livewallpaper.ringtones.callertune.SingletonClasses.AppOpenAds.activity;
import static com.livewallpaper.ringtones.callertune.Utils.Constants.KEYBOARD_FAV;
import static com.livewallpaper.ringtones.callertune.Utils.Constants.RINGTONE_FAV;
import static com.livewallpaper.ringtones.callertune.Utils.Constants.WALLPAPER_FAV;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.adsmodule.api.adsModule.AdUtils;
import com.adsmodule.api.adsModule.utils.Constants;
import com.google.gson.Gson;
import com.livewallpaper.ringtones.callertune.Adapter.SeeAllItemAdapter;
import com.livewallpaper.ringtones.callertune.Adapter.WallpaperItemAdapter;
import com.livewallpaper.ringtones.callertune.CustomViews.ConstraintWithBoolean;
import com.livewallpaper.ringtones.callertune.Fragment.KeyboardCategoryFragment;
import com.livewallpaper.ringtones.callertune.Fragment.RingtoneCategoryFragment;
import com.livewallpaper.ringtones.callertune.Fragment.WallpaperCategoryFragment;
import com.livewallpaper.ringtones.callertune.Model.ExtraCategoryModel;
import com.livewallpaper.ringtones.callertune.Model.KeyboardData;
import com.livewallpaper.ringtones.callertune.Model.KeyboardFavouriteRootModel;
import com.livewallpaper.ringtones.callertune.Model.RingtoneData;
import com.livewallpaper.ringtones.callertune.Model.RingtoneFavouriteRootModel;
import com.livewallpaper.ringtones.callertune.Model.WallpaperFavouriteModel;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.SingletonClasses.MyApplication;
import com.livewallpaper.ringtones.callertune.Utils.Util;
import com.livewallpaper.ringtones.callertune.databinding.ActivityFavouritesBinding;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {

    ActivityFavouritesBinding binding;
    Animation slideUp, slideDown, initialUp;
    String currentFrag ;
    WallpaperItemAdapter wallpaperItemAdapter;
    SeeAllItemAdapter seeAllItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavouritesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        currentFrag = "wallpaper";
        initTabAnimation();
        initStartTabAnimation();
        initTabButton();
        initReyclerView(currentFrag);
        initBtn();

    }

    private void initBtn() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initStartTabAnimation() {
        binding.clParentTvWallpaper.animate()
                .translationY(Util.pxFromDp(FavouritesActivity.this, 60))
                .setDuration(1);
        binding.clParentTvKeyboard.animate()
                .translationY(Util.pxFromDp(FavouritesActivity.this, 60))
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        binding.clParentTvRingtone.startAnimation(initialUp);
                        binding.llRingtone.startAnimation(initialUp);
                    }
                })
                .setDuration(1);
        binding.clParentTvRingtone.animate()
                .translationY(Util.pxFromDp(FavouritesActivity.this, 60))
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        binding.clParentTvKeyboard.startAnimation(initialUp);
                        binding.llKeyboard.startAnimation(initialUp);
                    }
                })
                .setDuration(1);
    }

    private void initTabAnimation() {
        slideUp = AnimationUtils.loadAnimation(FavouritesActivity.this, R.anim.slide_up_tab);
        slideDown = AnimationUtils.loadAnimation(FavouritesActivity.this, R.anim.slide_down_tab);
        initialUp = AnimationUtils.loadAnimation(FavouritesActivity.this, R.anim.slide_up_tab_fast);
        slideDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.clWallpaper.setOnClickListener(null);
                binding.clKeyboard.setOnClickListener(null);
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
                binding.clKeyboard.setOnClickListener(null);
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
                    initReyclerView(currentFrag);
                    ConstraintWithBoolean constraintWithBoolean = (ConstraintWithBoolean) view;
                    animateTabButton(constraintWithBoolean);
                }
            }
        });
        binding.clKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!currentFrag.equals("keyboard")){
                    undoClick();
                    currentFrag = "keyboard";
                    initReyclerView(currentFrag);
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
                    initReyclerView(currentFrag);
                    ConstraintWithBoolean constraintWithBoolean = (ConstraintWithBoolean) view;
                    animateTabButton(constraintWithBoolean);
                }
            }
        });
    }

    private void initReyclerView(String currentFrag) {
        if(currentFrag.equals("wallpaper")){
            String arryString =  MyApplication.getPreferences().getString(WALLPAPER_FAV, "");
            if (!arryString.equals("")){
                WallpaperFavouriteModel favouriteModel = new Gson().fromJson(arryString, WallpaperFavouriteModel.class);
                if(favouriteModel.getData().size() == 0){
                    binding.tvFav.setVisibility(View.VISIBLE);
                    binding.rvData.setVisibility(View.GONE);
                }else {
                    binding.tvFav.setVisibility(View.GONE);
                    binding.rvData.setVisibility(View.VISIBLE);
                    wallpaperItemAdapter = new WallpaperItemAdapter(FavouritesActivity.this, favouriteModel.getData());
                    binding.rvData.setLayoutManager(new GridLayoutManager(FavouritesActivity.this, 2));
                    binding.rvData.setAdapter(wallpaperItemAdapter);
                }
            }else{
                binding.tvFav.setVisibility(View.VISIBLE);
                binding.rvData.setVisibility(View.GONE);
            }
        }else if (currentFrag.equals("keyboard")){
            String arrayStr = MyApplication.getPreferences().getString(KEYBOARD_FAV, "");
            if(!arrayStr.isEmpty()){
                KeyboardFavouriteRootModel model = new Gson().fromJson(arrayStr, KeyboardFavouriteRootModel.class);
                ArrayList<ExtraCategoryModel> arrayList = new ArrayList<>();
                for (KeyboardData data : model.getData()){
                    arrayList.add(new ExtraCategoryModel(
                            data.getKeyboardName().split(" ")[0],
                            "",
                            "",
                            data.getKeyboardBgUrl()
                    ));
                }

                if(arrayList.size() == 0){
                    binding.tvFav.setVisibility(View.VISIBLE);
                    binding.rvData.setVisibility(View.GONE);
                }else{
                    binding.tvFav.setVisibility(View.GONE);
                    binding.rvData.setVisibility(View.VISIBLE);
                    seeAllItemAdapter = new SeeAllItemAdapter(FavouritesActivity.this, arrayList, "keyboard", new SeeAllItemAdapter.onClickInputMethod() {
                        @Override
                        public void onClickIdentifyKeyboard() {

                        }

                        @Override
                        public void onWallpaperClick(Bitmap resource, String url) {

                        }
                    });
                    binding.rvData.setLayoutManager(new GridLayoutManager(FavouritesActivity.this, 2));
                    binding.rvData.setAdapter(seeAllItemAdapter);
                }



            }else{
                binding.tvFav.setVisibility(View.VISIBLE);
                binding.rvData.setVisibility(View.GONE);
            }
        }else if (currentFrag.equals("ringtone")){
            String arrayStr = MyApplication.getPreferences().getString(RINGTONE_FAV, "");
            if(!arrayStr.isEmpty()){
                RingtoneFavouriteRootModel model = new Gson().fromJson(arrayStr, RingtoneFavouriteRootModel.class);
                ArrayList<ExtraCategoryModel> arrayList = new ArrayList<>();

                for (RingtoneData data : model.getData()){
                    ExtraCategoryModel extraCategoryModel1 = new ExtraCategoryModel(
                            data.getRingtoneName(),
                            data.getRingtoneAuthor(),
                            data.getRingtoneTime(),
                            ""
                    );
                    extraCategoryModel1.setRingtoneImg(data.getRingtonePreviewImage());
                    extraCategoryModel1.setCategory("favourite");
                    extraCategoryModel1.setRingtoneUrl(data.getRingtoneUrl());
                    arrayList.add(extraCategoryModel1);
                }

                if(arrayList.size() == 0){
                    binding.tvFav.setVisibility(View.VISIBLE);
                    binding.rvData.setVisibility(View.GONE);
                }else {
                    binding.tvFav.setVisibility(View.GONE);
                    binding.rvData.setVisibility(View.VISIBLE);

                    seeAllItemAdapter = new SeeAllItemAdapter(FavouritesActivity.this, arrayList, "ringtone", new SeeAllItemAdapter.onClickInputMethod() {
                        @Override
                        public void onClickIdentifyKeyboard() {

                        }

                        @Override
                        public void onWallpaperClick(Bitmap resource, String url) {

                        }
                    });
                    binding.rvData.setLayoutManager(new LinearLayoutManager(FavouritesActivity.this, LinearLayoutManager.VERTICAL, false));
                    binding.rvData.setAdapter(seeAllItemAdapter);
                }
            }else{
                binding.tvFav.setVisibility(View.VISIBLE);
                binding.rvData.setVisibility(View.GONE);
            }
        }
    }

    private void undoClick() {
        if(currentFrag.equals("keyboard")){
            binding.clKeyboard.getChildAt(0).startAnimation(slideUp);
            binding.clKeyboard.getChildAt(1).startAnimation(slideUp);
            binding.clKeyboard.setIsOn(false);
        } else if (currentFrag.equals("wallpaper")) {
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
    public void onBackPressed() {
        AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), state_load -> {finish();});
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wallpaperItemAdapter!= null && currentFrag.equals("wallpaper")){
            initReyclerView(currentFrag);
        }else if(seeAllItemAdapter!= null && currentFrag.equals("ringtone")){
            initReyclerView(currentFrag);
        }
    }
}