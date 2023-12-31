package com.livewallpaper.ringtones.callertune.Activity;

import static com.livewallpaper.ringtones.callertune.SingletonClasses.AppOpenAds.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.adsmodule.api.adsModule.AdUtils;
import com.adsmodule.api.adsModule.utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.livewallpaper.ringtones.callertune.CustomViews.ConstraintWithBoolean;
import com.livewallpaper.ringtones.callertune.Fragment.KeyboardCategoryFragment;
import com.livewallpaper.ringtones.callertune.Fragment.RingtoneCategoryFragment;
import com.livewallpaper.ringtones.callertune.Fragment.WallpaperCategoryFragment;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.SingletonClasses.MyApplication;
import com.livewallpaper.ringtones.callertune.Utils.Util;
import com.livewallpaper.ringtones.callertune.databinding.ActivityMainBinding;
import com.livewallpaper.ringtones.callertune.databinding.ItemKeyboardBinding;

import java.io.FileOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        initDrawer();

        chageFragment(new WallpaperCategoryFragment());
    }

    private void initDrawer() {
        binding.ivDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawer.open();

            }
        });


        View headerLayout = binding.navview.getHeaderView(0);
        LinearLayout llShare = headerLayout.findViewById(R.id.ll_share);
        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.shareApp(activity);
                binding.drawer.closeDrawers();
            }
        });

        LinearLayout llSetting = headerLayout.findViewById(R.id.ll_setting);
        llSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    startActivity(new Intent(MainActivity.this, SettingActivity.class));
                    binding.drawer.closeDrawers();
                });

            }
        });
        LinearLayout llFavourite = headerLayout.findViewById(R.id.ll_fav);
        llFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    startActivity(new Intent(MainActivity.this, FavouritesActivity.class));
                    binding.drawer.closeDrawers();
                });

            }
        });

        LinearLayout llDownload = headerLayout.findViewById(R.id.ll_down);
        llDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, DownloadActivity.class));
                    binding.drawer.closeDrawers();
            }
        });

        LinearLayout llWallpaper = headerLayout.findViewById(R.id.ll_wall);
        LinearLayout llKeyboard = headerLayout.findViewById(R.id.ll_key);
        LinearLayout llRingtone = headerLayout.findViewById(R.id.ll_ring);

        llWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    binding.clWallpaper.performClick();
                    binding.drawer.closeDrawers();
                });
            }
        });

        llKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    binding.clKeyboard.performClick();
                    binding.drawer.closeDrawers();
                });
            }
        });

        llRingtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    binding.clRingtone.performClick();
                    binding.drawer.closeDrawers();
                });
            }
        });


    }


    private void chageFragment(Fragment fragment) {

        if(Constants.adsResponseModel.getExtra_data_field().getWallpaper_categories() != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.fragcontainer, fragment);
            fragmentTransaction.commit();
        }else{

            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.item_no_internet);
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }



    }

/*
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


                                        String filename = "savedBitmapBackground.png";

                                        try {
                                            FileOutputStream stream = openFileOutput(filename, Context.MODE_PRIVATE);
                                            resource.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                            MyApplication.getPreferences().putString(Constants.KEYBOARD_BG, filename);
                                            stream.close();
                                        }catch (Exception e) {
                                            e.printStackTrace();
                                        }


                                        startActivity(new Intent(MainActivity.this, PermissionActivity.class));

*/
/*                                        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
                                        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                                        ItemKeyboardBinding keyboardBinding = ItemKeyboardBinding.inflate(layoutInflater);
                                        bottomSheetDialog.setContentView(keyboardBinding.getRoot());
                                        bottomSheetDialog.setCancelable(true);
                                        initButtonclickEffect(keyboardBinding.btnW);
                                        initButtonclickEffect(keyboardBinding.btnQ);
                                        initButtonclickEffect(keyboardBinding.btnE);
                                        initButtonclickEffect(keyboardBinding.btnR);
                                        initButtonclickEffect(keyboardBinding.btnT);
                                        initButtonclickEffect(keyboardBinding.btnY);
                                        initButtonclickEffect(keyboardBinding.btnU);
                                        initButtonclickEffect(keyboardBinding.btnI);
                                        initButtonclickEffect(keyboardBinding.btnO);
                                        initButtonclickEffect(keyboardBinding.btnP);
//                                        ImageView iv_back = bottomSheetDialog.findViewById(R.id.iv_bg_keyboard);
                                        keyboardBinding.ivBgKeyboard.setImageBitmap(resource);
                                        bottomSheetDialog.show();*//*

                                    }
                                });

                            }
                        });


                        return true;
                    }
                }).submit();

    }
*/

    private void intiTrashClicks() {

    }


    private void initButtonclickEffect(View view) {

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    v.setPressed(true);
                    v.animate()
                            .scaleX(0.8f)
                            .scaleY(0.8f)
                            .alpha(0.7f)
                            .setDuration(50);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    v.setPressed(false);
                    v.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .alpha(1f)
                            .setDuration(50);
                }
                return true;
            }
        });

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

                            chageFragment(new WallpaperCategoryFragment());

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
                    chageFragment(new KeyboardCategoryFragment());
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
                    chageFragment(new RingtoneCategoryFragment());
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

    @Override
    public void onBackPressed() {
        if(!currentFrag.equals("wallpaper")){
            binding.clWallpaper.performClick();
        }else{
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.exit_dialog);
            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ImageView iv_close = dialog.findViewById(R.id.iv_close);
            AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), dialog.findViewById(R.id.ll_ads), "full", null);
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            CardView cv_exit = dialog.findViewById(R.id.cv_exit);
            cv_exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishAffinity();
                }
            });

            LinearLayout ll_rate = dialog.findViewById(R.id.ll_rate);
            ll_rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.reviewDialog(activity);
                }
            });
            dialog.show();
        }
    }
}