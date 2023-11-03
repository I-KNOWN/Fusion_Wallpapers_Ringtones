package com.livewallpaper.ringtones.callertune.Activity;

import static com.livewallpaper.ringtones.callertune.SingletonClasses.AppOpenAds.activity;
import static com.livewallpaper.ringtones.callertune.Utils.Constants.WALLPAPER_FAV;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.Animator;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import com.adsmodule.api.adsModule.utils.Global;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.livewallpaper.ringtones.callertune.Model.WallpaperFavouriteModel;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.SingletonClasses.MyApplication;
import com.livewallpaper.ringtones.callertune.databinding.ActivityWallpaperBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WallpaperActivity extends AppCompatActivity {

    ActivityWallpaperBinding binding;

    BottomSheetBehavior behaviorSetter;
    BottomSheetBehavior behaviorSave;
    String url;

    Bitmap loadedBitmap;
    boolean isFavourite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWallpaperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initWallpaper();
        initBehaviour();
        initBtn();
        setUpWalpperBtn();
    }

    private void setUpWalpperBtn() {
        WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        binding.llLockWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.showAlertProgressDialog(activity);
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            manager.setBitmap(loadedBitmap, null, true, WallpaperManager.FLAG_LOCK);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Global.hideAlertProgressDialog();
                                    behaviorSave.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                    Toast.makeText(WallpaperActivity.this, "Wallpaper Set Successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Global.hideAlertProgressDialog();
                                    Toast.makeText(WallpaperActivity.this, "Failed to set Wallpaper", Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        binding.llHomeWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.showAlertProgressDialog(activity);
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            manager.setBitmap(loadedBitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Global.hideAlertProgressDialog();
                                    behaviorSave.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                    Toast.makeText(WallpaperActivity.this, "Wallpaper Set Successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Global.hideAlertProgressDialog();
                                    Toast.makeText(WallpaperActivity.this, "Failed to set Wallpaper", Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();

                        }
                    }
                });
            }
        });

        binding.llBothWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.showAlertProgressDialog(activity);
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            manager.setBitmap(loadedBitmap, null, true, WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Global.hideAlertProgressDialog();
                                    behaviorSave.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                    Toast.makeText(WallpaperActivity.this, "Wallpaper Set Successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Global.hideAlertProgressDialog();
                                    Toast.makeText(WallpaperActivity.this, "Failed to set Wallpaper", Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void initBtn() {
        binding.tvMaybe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behaviorSave.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.ivFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavourite){
                    binding.ivFavourite.setImageDrawable(ContextCompat.getDrawable(WallpaperActivity.this, R.drawable.favourite_ic));
                    isFavourite = false;
                    String arryString =  MyApplication.getPreferences().getString(WALLPAPER_FAV, "");
                    WallpaperFavouriteModel favouriteModel = new Gson().fromJson(arryString, WallpaperFavouriteModel.class);
                    ArrayList<String> data = new ArrayList<>();
                    data.addAll(favouriteModel.getData());
                    data.remove(url);
                    favouriteModel.setData(data);
                    String convertedArray = new Gson().toJson(favouriteModel);
                    MyApplication.getPreferences().putString(WALLPAPER_FAV, convertedArray);
                }else{
                    binding.ivFavourite.setImageDrawable(ContextCompat.getDrawable(WallpaperActivity.this, R.drawable.favourite_ic_filled));
                    isFavourite = true;
                    String arryString =  MyApplication.getPreferences().getString(WALLPAPER_FAV, "");
                    if (arryString.equals("")){
                        WallpaperFavouriteModel datamodel = new WallpaperFavouriteModel(new ArrayList<>());
                        MyApplication.getPreferences().putString(WALLPAPER_FAV, new Gson().toJson(datamodel));
                        arryString =  MyApplication.getPreferences().getString(WALLPAPER_FAV, "");
                    }

                    WallpaperFavouriteModel favouriteModel = new Gson().fromJson(arryString, WallpaperFavouriteModel.class);
                    ArrayList<String> data = new ArrayList<>();
                    data.addAll(favouriteModel.getData());
                    data.add(url);
                    favouriteModel.setData(data);
                    String convertedArray = new Gson().toJson(favouriteModel);
                    MyApplication.getPreferences().putString(WALLPAPER_FAV, convertedArray);
                }
            }
        });
    }

    private void initWallpaper() {

        Bitmap bmp = null;
        String filename = getIntent().getStringExtra("image");
        try {
            FileInputStream is = this.openFileInput(filename);
            bmp = BitmapFactory.decodeStream(is);
            loadedBitmap = bmp;
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.ivWallpaper.setImageBitmap(loadedBitmap);
        binding.cvWatchAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnSavePng();
            }
        });

/*        Glide.with(WallpaperActivity.this)
                .asBitmap()
                .load(url)
                .addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(@NonNull Bitmap resource, @NonNull Object model, Target<Bitmap> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                        loadedBitmap = resource;
                        binding.ivWallpaper.setImageBitmap(resource);
                        setUpWalpperBtn();
                        binding.cvWatchAd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                onBtnSavePng();
                            }
                        });
                        return true;
                    }
                })
                .into(binding.ivWallpaper);*/
    }



    private void initBehaviour() {
        behaviorSetter = BottomSheetBehavior.from(binding.llSetter);
        behaviorSetter.setPeekHeight(0, true);
        behaviorSetter.setDraggable(true);
        behaviorSetter.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState == BottomSheetBehavior.STATE_COLLAPSED){
                    binding.llSetter.animate()
                            .alpha(0)
                            .setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(@NonNull Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(@NonNull Animator animation) {
                                    binding.llSetter.setVisibility(View.GONE);
                                }

                                @Override
                                public void onAnimationCancel(@NonNull Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(@NonNull Animator animation) {

                                }
                            })
                            .setDuration(200);

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        behaviorSetter.setState(BottomSheetBehavior.STATE_COLLAPSED);


        behaviorSave = BottomSheetBehavior.from(binding.cvSaveWithAd);
        behaviorSave.setDraggable(true);
        behaviorSave.setPeekHeight(0, true);
        behaviorSave.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState == BottomSheetBehavior.STATE_COLLAPSED){
                    binding.cvSaveWithAd.animate()
                            .alpha(0)
                            .setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(@NonNull Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(@NonNull Animator animation) {
                                    binding.cvSaveWithAd.setVisibility(View.GONE);
                                }

                                @Override
                                public void onAnimationCancel(@NonNull Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(@NonNull Animator animation) {

                                }
                            })
                            .setDuration(200);

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        behaviorSave.setState(BottomSheetBehavior.STATE_COLLAPSED);

        binding.ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.showAlertProgressDialog(activity);

                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            String filename = "bitmap.png";

                            try {
                                FileOutputStream stream = openFileOutput(filename, Context.MODE_PRIVATE);
                                loadedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                stream.close();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Global.hideAlertProgressDialog();
                                    Intent in1 = new Intent(WallpaperActivity.this, EditorActivity.class);
                                    in1.putExtra("image", filename);
                                    startActivity(in1);
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                                }
                            });

                        }
                    });




/*
                Intent intent = new Intent(WallpaperActivity.this, EditorActivity.class);
                intent.putExtra()
                startActivity();*/
            }
        });
        binding.ivDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llSetter.animate()
                        .alpha(0)
                        .setDuration(1);
                binding.llSetter.setVisibility(View.VISIBLE);
                binding.llSetter.animate().alpha(1).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animation) {
                        behaviorSetter.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) {

                    }
                }).setDuration(0);
            }
        });

        binding.llSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behaviorSetter.setState(BottomSheetBehavior.STATE_COLLAPSED);
                binding.cvSaveWithAd.animate()
                        .alpha(0)
                        .setDuration(1);
                binding.cvSaveWithAd.setVisibility(View.VISIBLE);
                binding.cvSaveWithAd.animate().alpha(1).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animation) {
                        behaviorSave.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) {

                    }
                }).setDuration(0);
            }
        });


    }

    public void onBtnSavePng() {
        try {
            String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File myDir = new File(root + "/Fusion_Wallpaper");
            if (!myDir.exists())
                myDir.mkdirs();
            String fname = System.currentTimeMillis()+".jpg"; // Note the correction here
            File file = new File(myDir, fname);
            Bitmap bm = loadedBitmap;
            FileOutputStream fos = new FileOutputStream(file); // Use FileOutputStream directl
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            // MediaScannerConnection to notify the system about the new file
            MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null, null);
            Toast.makeText(WallpaperActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
            behaviorSave.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } catch (Exception e) {
            Toast.makeText(WallpaperActivity.this, "Failed To Save Image", Toast.LENGTH_SHORT).show();
            Log.d("onBtnSavePng", e.toString());
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        url = getIntent().getStringExtra("url");
        String arryString =  MyApplication.getPreferences().getString(WALLPAPER_FAV, "");
        if(arryString.contains(url)){
            isFavourite = true;
            binding.ivFavourite.setImageDrawable(ContextCompat.getDrawable(WallpaperActivity.this, R.drawable.favourite_ic_filled));
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}