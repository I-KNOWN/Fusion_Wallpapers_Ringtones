package com.livewallpaper.ringtones.callertune.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;

import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.databinding.ActivityPermissionBinding;

import java.util.List;

public class PermissionActivity extends AppCompatActivity {

    ActivityPermissionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPermissionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initBtn();
    }

    private void initBtn() {
        binding.cvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PermissionActivity.this, PreferencesActivity.class));
                finish();
            }
        });

        binding.btnKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openKeyboardChooserSettings();

/*                if(!isKeyboardEnabled()){
                    openKeyboardSettings();
                }else{
                }*/
            }
        });

    }

    private boolean isKeyboardEnabled() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        List<InputMethodInfo> enabledInputMethodIds = inputMethodManager.getEnabledInputMethodList();
        return enabledInputMethodIds.contains("com.livewallpaper.ringtones.callertune.Keyboard/.CustomKeyboard");
    }

    private void openKeyboardSettings() {
        Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
        startActivity(intent);
    }

    private void openKeyboardChooserSettings() {
        InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        im.showInputMethodPicker();
    }


}