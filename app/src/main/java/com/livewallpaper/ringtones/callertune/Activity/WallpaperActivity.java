package com.livewallpaper.ringtones.callertune.Activity;

import static com.livewallpaper.ringtones.callertune.SingletonClasses.AppOpenAds.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.databinding.ActivityWallpaperBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WallpaperActivity extends AppCompatActivity {

    ActivityWallpaperBinding binding;

    BottomSheetBehavior behaviorSetter;
    BottomSheetBehavior behaviorSave;
    String url;

    Bitmap loadedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWallpaperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        url = "https://raw.githubusercontent.com/brijesh-ide/AppLock_Hide_Photos_Videos/master/themes/img_animals.png";
        initBehaviour();
        initWallpaper();
        initBtn();
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
    }

    private void initWallpaper() {
        Glide.with(WallpaperActivity.this)
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
                .into(binding.ivWallpaper);
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

        binding.cvWatchAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(WallpaperActivity.this, "Wait For Image To Load", Toast.LENGTH_SHORT).show();
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
}