package com.livewallpaper.ringtones.callertune.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.databinding.ActivityAllCategoriesBinding;

public class AllCategoriesActivity extends AppCompatActivity {

    ActivityAllCategoriesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}