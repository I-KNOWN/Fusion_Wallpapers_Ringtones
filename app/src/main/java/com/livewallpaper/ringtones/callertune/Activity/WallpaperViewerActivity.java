package com.livewallpaper.ringtones.callertune.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.databinding.ActivityWallpaperViewerBinding;

public class WallpaperViewerActivity extends AppCompatActivity {

    ActivityWallpaperViewerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWallpaperViewerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}