package com.livewallpaper.ringtones.callertune.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.databinding.ActivityTmcactivityBinding;

public class TMCActivity extends AppCompatActivity {

    ActivityTmcactivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTmcactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(getIntent().getStringExtra("type") != null){
            binding.tvNext.setText("Close");
            binding.cvNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }else{
            initBtn();
        }


    }

    private void initBtn() {
        binding.cvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TMCActivity.this, PermissionActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}