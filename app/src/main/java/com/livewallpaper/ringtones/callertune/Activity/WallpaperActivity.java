package com.livewallpaper.ringtones.callertune.Activity;

import static com.livewallpaper.ringtones.callertune.SingletonClasses.AppOpenAds.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
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
        behaviorSetter.setState(BottomSheetBehavior.STATE_COLLAPSED);
        behaviorSetter.setPeekHeight(0, true);
        behaviorSetter.setDraggable(true);

        behaviorSave = BottomSheetBehavior.from(binding.cvSaveWithAd);
        behaviorSave.setState(BottomSheetBehavior.STATE_COLLAPSED);
        behaviorSave.setDraggable(true);
        behaviorSave.setPeekHeight(0, true);

        binding.ivDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behaviorSetter.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        binding.llSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behaviorSetter.setState(BottomSheetBehavior.STATE_COLLAPSED);
                behaviorSave.setState(BottomSheetBehavior.STATE_EXPANDED);
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