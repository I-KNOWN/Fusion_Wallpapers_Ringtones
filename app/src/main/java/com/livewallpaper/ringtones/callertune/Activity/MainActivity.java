package com.livewallpaper.ringtones.callertune.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.livewallpaper.ringtones.callertune.CustomViews.ConstraintWithBoolean;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.Utils.Util;
import com.livewallpaper.ringtones.callertune.databinding.ActivityMainBinding;
import com.livewallpaper.ringtones.callertune.databinding.ItemKeyboardBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Animation slideUp, slideDown, initialUp;
    String currentFrag ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        currentFrag = "wallpaper";
        initTabAnimation();
        initStartTabAnimation();
        initTabButton();
        initBg();
    }

    private void initBg() {

        Glide.with(MainActivity.this)
                .asBitmap()
                .load("https://www.api.idecloudstoragepanel.com/media/Applications_Data/com.livewallpaper.ringtones.callertune/Image_Files/car/car_1.jpg")
                .addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(@NonNull Bitmap resource, @NonNull Object model, Target<Bitmap> target, @NonNull DataSource dataSource, boolean isFirstResource) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.ivBg.setImageBitmap(resource);
                                binding.item1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
                                        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                                        ItemKeyboardBinding keyboardBinding = ItemKeyboardBinding.inflate(layoutInflater);
                                        bottomSheetDialog.setContentView(keyboardBinding.getRoot());
                                        bottomSheetDialog.setCancelable(true);
                                        ImageView iv_back = bottomSheetDialog.findViewById(R.id.iv_bg_keyboard);
                                        iv_back.setImageBitmap(resource);
                                        bottomSheetDialog.show();
                                    }
                                });

                            }
                        });


                        return true;
                    }
                }).submit();

    }

    private void intiTrashClicks() {

    }

    private void initStartTabAnimation() {
        binding.clParentTvWallpaper.animate()
                .translationY(Util.pxFromDp(MainActivity.this, 60))
                .setDuration(1);
        binding.clParentTvKeyboard.animate()
                .translationY(Util.pxFromDp(MainActivity.this, 60))
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
                .translationY(Util.pxFromDp(MainActivity.this, 60))
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
        slideUp = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_up_tab);
        slideDown = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_down_tab);
        initialUp = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_up_tab_fast);
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
                    ConstraintWithBoolean constraintWithBoolean = (ConstraintWithBoolean) view;
                    animateTabButton(constraintWithBoolean);
                }
            }
        });
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




}