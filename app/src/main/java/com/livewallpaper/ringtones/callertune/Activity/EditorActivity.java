package com.livewallpaper.ringtones.callertune.Activity;

import static com.livewallpaper.ringtones.callertune.SingletonClasses.AppOpenAds.activity;
import static com.livewallpaper.ringtones.callertune.Utils.Constants.ADJUST;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.adsmodule.api.adsModule.AdUtils;
import com.adsmodule.api.adsModule.interfaces.AppInterfaces;
import com.adsmodule.api.adsModule.utils.Constants;
import com.adsmodule.api.adsModule.utils.Global;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.slider.Slider;
import com.isseiaoki.simplecropview.CropImageView;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.Utils.FilterType;
import com.livewallpaper.ringtones.callertune.databinding.ActivityEditorBinding;
import com.livewallpaper.ringtones.callertune.editor.OnPhotoEditorListener;
import com.livewallpaper.ringtones.callertune.editor.PhotoEditor;
import com.livewallpaper.ringtones.callertune.editor.ViewType;
import com.steelkiwi.cropiwa.AspectRatio;
import com.steelkiwi.cropiwa.CropIwaView;
import com.steelkiwi.cropiwa.config.CropIwaSaveConfig;

import org.wysaid.myUtils.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eightbitlab.com.blurview.RenderEffectBlur;
import eightbitlab.com.blurview.RenderScriptBlur;

public class EditorActivity extends AppCompatActivity {

    ActivityEditorBinding binding;
    int current_index;
    Bitmap bitmap;
    BottomSheetBehavior behaviorSetter;
    BottomSheetBehavior behaviorSave;
    float currentContrast = 1, currentSaturation = 1, currentWarmth = 1, currentBrightness = 1;
    FilterType selectedFilter;
    boolean isActivited;
    CropImageView cropIwaView;
    BottomSheetBehavior bottomSheetCrop, bottomSheetOption;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        current_index = 1;
        isActivited = false;

        Bitmap bmp = null;
        String filename = getIntent().getStringExtra("image");
        try {
            FileInputStream is = this.openFileInput(filename);
            bmp = BitmapFactory.decodeStream(is);
            bitmap = bmp;
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.ivEditor.setDrawingCacheEnabled(true);

//        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.board1_01);
        binding.ivEditor.setImageBitmap(bitmap);
        initCrop();
        intiSlider();
        initBtn();
        unSelectAll();
        initBehaviour();
        initBtnExtra();
        binding.ivContrast.performClick();
    }

    private void initBtnExtra() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.ivVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetOption.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetOption.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    binding.cvBottomOption.animate()
                            .alpha(0)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                }
                            })
                            .setDuration(200);
                } else if(bottomSheetOption.getState() == BottomSheetBehavior.STATE_COLLAPSED){
                    binding.cvBottomOption.animate()
                            .alpha(1)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    bottomSheetOption.setState(BottomSheetBehavior.STATE_EXPANDED);
                                }
                            })
                            .setDuration(200);


                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), state_load -> {finish();});
    }

    /*   @NonNull
        @Override
        public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
            finish();
            return super.getOnBackInvokedDispatcher();
        }*/
    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void initCrop() {
        cropIwaView = findViewById(R.id.crop_view);
        cropIwaView.setCropMode(CropImageView.CropMode.FIT_IMAGE);
        cropIwaView.setImageBitmap(bitmap);
        cropIwaView.setInitialFrameScale(0.5f);
        cropIwaView.setCustomRatio(binding.ivEditor.getWidth(), binding.ivEditor.getHeight());
        bottomSheetCrop = BottomSheetBehavior.from(binding.blurView);
        bottomSheetCrop.setPeekHeight(0);
        bottomSheetCrop.setDraggable(false);
        bottomSheetCrop.setState(BottomSheetBehavior.STATE_COLLAPSED);
        initBlur();
        binding.ivCloseCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.ivContrast.performClick();
                bottomSheetCrop.setState(BottomSheetBehavior.STATE_COLLAPSED);
                binding.cvBottomOption.animate()
                        .alpha(1)
                        .setDuration(0);
                bottomSheetOption.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        binding.ivCheckCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap cropped = binding.cropView.getCroppedBitmap();
                bitmap = cropped;
                binding.ivEditor.setImageBitmap(bitmap);
                currentContrast = 1;
                currentSaturation = 1;
                currentWarmth = 1;
                currentBrightness = 1;
                binding.ivContrast.performClick();
                bottomSheetCrop.setState(BottomSheetBehavior.STATE_COLLAPSED);
                binding.cvBottomOption.animate()
                        .alpha(1)
                        .setDuration(0);
                bottomSheetOption.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

    }
    private void initBtnMaybe() {
        binding.tvMaybe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behaviorSave.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });
    }

    private void setUpWalpperBtn() {
        initBtnMaybe();
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
                            binding.ivEditor.setDrawingCacheEnabled(true);

                            manager.setBitmap(binding.ivEditor.getDrawingCache(), null, true, WallpaperManager.FLAG_LOCK);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Global.hideAlertProgressDialog();
                                    behaviorSave.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                    Toast.makeText(EditorActivity.this, "Wallpaper Set Successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Global.hideAlertProgressDialog();
                                    Toast.makeText(EditorActivity.this, "Failed to set Wallpaper", Toast.LENGTH_SHORT).show();
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
                            binding.ivEditor.setDrawingCacheEnabled(true);

                            manager.setBitmap(binding.ivEditor.getDrawingCache(), null, true, WallpaperManager.FLAG_SYSTEM);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Global.hideAlertProgressDialog();
                                    behaviorSave.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                    Toast.makeText(EditorActivity.this, "Wallpaper Set Successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Global.hideAlertProgressDialog();
                                    Toast.makeText(EditorActivity.this, "Failed to set Wallpaper", Toast.LENGTH_SHORT).show();
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
                            binding.ivEditor.setDrawingCacheEnabled(true);

                            manager.setBitmap(binding.ivEditor.getDrawingCache(), null, true, WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Global.hideAlertProgressDialog();
                                    behaviorSave.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                    Toast.makeText(EditorActivity.this, "Wallpaper Set Successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Global.hideAlertProgressDialog();
                                    Toast.makeText(EditorActivity.this, "Failed to set Wallpaper", Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void initBehaviour() {
        setUpWalpperBtn();

        behaviorSetter = BottomSheetBehavior.from(binding.llSetter);
        behaviorSetter.setState(BottomSheetBehavior.STATE_COLLAPSED);
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
                            .setDuration(100);

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        behaviorSave = BottomSheetBehavior.from(binding.cvSaveWithAd);
        behaviorSave.setState(BottomSheetBehavior.STATE_COLLAPSED);
        behaviorSave.setDraggable(false);
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
                            .setDuration(100);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

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
                AdUtils.showRewardAd(Constants.adsResponseModel.getRewarded_ads().getAdx(), activity, new AppInterfaces.RewardedAd() {
                    @Override
                    public void rewardState(boolean adState) {
                        if(adState){
                            onBtnSavePng();
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(EditorActivity.this, "Failed To Load Ad", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

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
            binding.ivEditor.setDrawingCacheEnabled(true);
            Bitmap bm = binding.ivEditor.getDrawingCache();
            FileOutputStream fos = new FileOutputStream(file); // Use FileOutputStream directl
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            // MediaScannerConnection to notify the system about the new file
            MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null, null);
            Toast.makeText(EditorActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
            behaviorSave.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } catch (Exception e) {
            Toast.makeText(EditorActivity.this, "Failed To Save Image", Toast.LENGTH_SHORT).show();
            Log.d("onBtnSavePng", e.toString());
        }
    }

    private void unSelectAll(){
        binding.ivCrop.setAlpha(0.5f);
        binding.ivBrightness.setAlpha(0.5f);
        binding.ivContrast.setAlpha(0.5f);
        binding.ivClip.setAlpha(0.5f);
        binding.ivPallet.setAlpha(0.5f);
    }

    private void intiSlider() {
        binding.filterSlider.setValueFrom(0);
        binding.filterSlider.setValueTo(3);
        binding.filterSlider.setStepSize(0.1f);

        binding.filterSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                if(isActivited){
                    if(selectedFilter == FilterType.Brightness){
                        currentBrightness = value;
                        binding.ivEditor.setBrightness(value);
                    } else if (selectedFilter == FilterType.Saturation) {
                        currentSaturation = value;
                        binding.ivEditor.setSaturation(value);
                    } else if (selectedFilter == FilterType.Warmth) {
                    currentWarmth = value;
                    binding.ivEditor.setWarmth(value);
                    } else if (selectedFilter == FilterType.Contrast) {
                        currentContrast = value;
                        binding.ivEditor.setContrast(value);
                    }
                }

            }
        });
        binding.ivCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

/*                Bitmap ret = Bitmap.createBitmap(binding.ivTest.getWidth(), binding.ivTest.getHeight(), Bitmap.Config.ARGB_8888);

                Canvas canvas = new Canvas(ret);

                binding.ivTest.draw(canvas);*/

            }
        });

//        binding.filterSlider.setValue((float) 0.5 * 100);
    }
    private void initBlur(){
        float radius = 20f;

        View decorView = getWindow().getDecorView();
        Drawable windowBackground = decorView.getBackground();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            binding.blurView.setupWith(binding.clEditor,new RenderEffectBlur()) // or RenderEffectBlur
                    .setFrameClearDrawable(windowBackground) // Optional
                    .setBlurRadius(radius);
        }else{
            binding.blurView.setupWith(binding.clEditor,new RenderScriptBlur(this)) // or RenderEffectBlur
                    .setFrameClearDrawable(windowBackground) // Optional
                    .setBlurRadius(radius);
        }
    }
    
    private void initBtn() {


        bottomSheetOption = BottomSheetBehavior.from(binding.cvBottomOption);
        bottomSheetOption.setPeekHeight(0);
        bottomSheetOption.setDraggable(false);
        bottomSheetOption.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetOption.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState == BottomSheetBehavior.STATE_COLLAPSED){

                } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        binding.llSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        binding.ivBrightness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unSelectAll();
                view.setAlpha(1f);
                selectedFilter = FilterType.Brightness;
                setUpFilterRange(currentBrightness);
            }
        });

        binding.ivPallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unSelectAll();
                view.setAlpha(1f);
                selectedFilter = FilterType.Saturation;
                setUpFilterRange(currentSaturation);
            }
        });

        binding.ivClip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unSelectAll();
                view.setAlpha(1f);
                selectedFilter = FilterType.Warmth;
                setUpFilterRange(currentWarmth);
            }
        });

        binding.ivContrast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unSelectAll();
                view.setAlpha(1f);
                selectedFilter = FilterType.Contrast;
                setUpFilterRange(currentContrast);
            }
        });

        binding.ivCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unSelectAll();
                view.setAlpha(1f);
//                binding.cvBottomOption.animate()
//                        .alpha(1)
//                        .setDuration(50);

                Bitmap ret = Bitmap.createBitmap(binding.ivEditor.getWidth(), binding.ivEditor.getHeight(), Bitmap.Config.ARGB_8888);

                Canvas canvas = new Canvas(ret);

                binding.ivEditor.draw(canvas);
                binding.cropView.setImageBitmap(ret);
                binding.cvBottomOption.animate()
                        .alpha(0)
                        .setDuration(50);

                bottomSheetOption.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomSheetCrop.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });



    }

    private void setUpFilterRange(float current) {
        isActivited = false;
        binding.filterSlider.setValue(current);
        binding.filterSlider.setValueFrom(0);
        binding.filterSlider.setValueTo(2);
        binding.filterSlider.setStepSize(0.1f);
        isActivited = true;
    }
}