package com.livewallpaper.ringtones.callertune.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.livewallpaper.ringtones.callertune.databinding.ActivityPermissionBinding;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

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
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    if(checkReadPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED){
                        binding.ivExternalArrow.setAlpha(1f);
                        if(!isKeyboardEnabled()){
                            openKeyboardSettings();
                        }else {
                            binding.cvNext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(PermissionActivity.this, PreferencesActivity.class));
                                finish();
                            }
                        });
                            binding.ivKeyboardCheck.setAlpha(1f);
                        }
                    }else{

                        PermissionX.init(PermissionActivity.this)
                                .permissions(Manifest.permission.READ_MEDIA_IMAGES)
                                .request(new RequestCallback() {
                                    @Override
                                    public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                                        if(allGranted){
                                            binding.ivExternalArrow.setAlpha(1f);
                                        }else {
                                            Toast.makeText(PermissionActivity.this, "Permission Needed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }else{
                    if(checkExternalStoragePermission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        binding.ivExternalArrow.setAlpha(1f);
                        if(!isKeyboardEnabled()){
                            openKeyboardSettings();
                        }else {
                            binding.cvNext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(PermissionActivity.this, PreferencesActivity.class));
                                    finish();
                                }
                            });
                            binding.ivKeyboardCheck.setAlpha(1f);
                        }
                    }else{
                        PermissionX.init(PermissionActivity.this)
                                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .request(new RequestCallback() {
                                    @Override
                                    public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                                        if(allGranted){
                                            binding.ivExternalArrow.setAlpha(1f);
                                        }else {
                                            Toast.makeText(PermissionActivity.this, "Permission Needed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }



            }
        });



        binding.cvExternalStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    if(checkReadPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED){
                        binding.ivExternalArrow.setAlpha(1f);
                    }else{

                        PermissionX.init(PermissionActivity.this)
                                .permissions(Manifest.permission.READ_MEDIA_IMAGES)
                                        .request(new RequestCallback() {
                                            @Override
                                            public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                                                if(allGranted){
                                                    binding.ivExternalArrow.setAlpha(1f);
                                                }else {
                                                    Toast.makeText(PermissionActivity.this, "Permission Needed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                    }
                }else{
                    if(checkExternalStoragePermission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        binding.ivExternalArrow.setAlpha(1f);
                    }else{
                        PermissionX.init(PermissionActivity.this)
                                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .request(new RequestCallback() {
                                    @Override
                                    public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                                        if(allGranted){
                                            binding.ivExternalArrow.setAlpha(1f);
                                        }else {
                                            Toast.makeText(PermissionActivity.this, "Permission Needed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }


            }
        });

        binding.btnKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isKeyboardEnabled()){
                    openKeyboardSettings();
                }else {
                    binding.ivKeyboardCheck.setAlpha(1f);

                }
                /*else{
                    openKeyboardChooserSettings();

                }*/
            }
        });

    }


    private int checkExternalStoragePermission(String permission1, String permission2) {
        int res1 = checkSelfPermission(permission1);

        int res2  = checkSelfPermission(permission2);
        if(res1 == res2){
            return 0;
        }
        return -1;
    }
    private int checkReadPermission(String permission1) {
        return checkSelfPermission(permission1);
    }

    private boolean isKeyboardEnabled() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        List<InputMethodInfo> enabledInputMethodIds = inputMethodManager.getEnabledInputMethodList();
        for(InputMethodInfo info: enabledInputMethodIds){
            if(info.getId().contains("com.livewallpaper.ringtones.callertune/.Keyboard.CustomKeyboard")){
                return true;
            }
        }
        return false;
    }

    private void openKeyboardSettings() {
        Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
        startActivity(intent);
    }

    private void openKeyboardChooserSettings() {
        InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        im.showInputMethodPicker();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isKeyboardEnabled()){
            binding.ivKeyboardCheck.setAlpha(1f);
        }
    }
}