package com.livewallpaper.ringtones.callertune.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.adsmodule.api.adsModule.utils.Global;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.livewallpaper.ringtones.callertune.Adapter.WallpaperPreviewAdapter;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.databinding.ActivityWallpaperViewerBinding;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class WallpaperViewerActivity extends AppCompatActivity {

    ActivityWallpaperViewerBinding binding;
    WallpaperPreviewAdapter adapter;
    String url = "https://raw.githubusercontent.com/brijesh-ide/AppLock_Hide_Photos_Videos/master/themes/img_animals.png";
    Bitmap bitmap = null;
    public static CardView cv_setter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWallpaperViewerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        intiRecyclerView();
        initBtn();
    }

    private void initBtn() {
        binding.ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(WallpaperViewerActivity.this, "Wait For Image To Load", Toast.LENGTH_SHORT).show();
            }
        });

        binding.ivDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(WallpaperViewerActivity.this, "Wait For Image To Load", Toast.LENGTH_SHORT).show();
            }
        });

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }
    
    

    private void intiRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WallpaperViewerActivity.this, LinearLayoutManager.HORIZONTAL, false);
        binding.rvWalpaperPreview.setLayoutManager(linearLayoutManager);
        adapter = new WallpaperPreviewAdapter(WallpaperViewerActivity.this, null, new WallpaperPreviewAdapter.onPreviewClick() {
            @Override
            public void onNext(int pos) {
                        if (pos == 0){
                            binding.tvTitle.setText("Lock Screen");
                            linearLayoutManager.smoothScrollToPosition(binding.rvWalpaperPreview, new RecyclerView.State(), (pos+1));
                        }else{
                            binding.tvTitle.setText("Home Screen");
                            linearLayoutManager.smoothScrollToPosition(binding.rvWalpaperPreview, new RecyclerView.State(), (pos-1));
                        }
            }
        });
        binding.rvWalpaperPreview.setAdapter(adapter);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.rvWalpaperPreview);

        Bitmap bmp = null;
        String filename = getIntent().getStringExtra("filename");
        try {
            FileInputStream is = this.openFileInput(filename);
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Glide.with(WallpaperViewerActivity.this)
                .asBitmap()
                .load(bmp)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Bitmap> target, boolean isFirstResource) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(WallpaperViewerActivity.this, "Failed To Get Image", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(@NonNull Bitmap resource, @NonNull Object model, Target<Bitmap> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                        bitmap = resource;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setBitmap(resource);
                                initBtnAfterReady();
                            }
                        });

                        return true;
                    }
                }).submit()
        ;

    }

    private void initBtnAfterReady() {
        binding.ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in1 = new Intent(WallpaperViewerActivity.this, EditorActivity.class);
                in1.putExtra("image", getIntent().getStringExtra("filename"));
                startActivity(in1);
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                /*String filename = "bitmap.png";

                try {
                    FileOutputStream stream = openFileOutput(filename, Context.MODE_PRIVATE);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    stream.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }*/

            }
        });

        binding.ivDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                String filename = "bitmap.png";

                try {
                    FileOutputStream stream = openFileOutput(filename, Context.MODE_PRIVATE);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    stream.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }*/
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Global.hideAlertProgressDialog();
                        Intent in1 = new Intent(WallpaperViewerActivity.this, WallpaperActivity.class);
                        in1.putExtra("image", getIntent().getStringExtra("filename"));
                        in1.putExtra("url", url);
                        startActivity(in1);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                    }
                });
            }
        });
    }
}