package com.livewallpaper.ringtones.callertune.Activity;

import static com.livewallpaper.ringtones.callertune.Utils.Constants.ADJUST;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.slider.Slider;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.databinding.ActivityEditorBinding;
import com.livewallpaper.ringtones.callertune.editor.OnPhotoEditorListener;
import com.livewallpaper.ringtones.callertune.editor.PhotoEditor;
import com.livewallpaper.ringtones.callertune.editor.ViewType;

public class EditorActivity extends AppCompatActivity {

    ActivityEditorBinding binding;
    int current_index;
    public float seekbarIntensity = 0.5f;
    PhotoEditor photoEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        current_index = 0;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.board1_01);
        binding.photoEditor.setImageSource(bitmap);
        initPhotoEditor();
        intiSlider();
        initBtn();
    }

    private void initPhotoEditor() {
        photoEditor = new PhotoEditor.Builder(this, binding.photoEditor).setPinchTextScalable(true).build();
        photoEditor.setAdjustFilter(ADJUST);
        photoEditor.setOnPhotoEditorListener(new OnPhotoEditorListener() {
            @Override
            public void onAddViewListener(ViewType viewType, int i) {

            }

            @Override
            public void onRemoveViewListener(int i) {

            }

            @Override
            public void onRemoveViewListener(ViewType viewType, int i) {

            }

            @Override
            public void onStartViewChangeListener(ViewType viewType) {

            }

            @Override
            public void onStopViewChangeListener(ViewType viewType) {

            }
        });

    }

    private void intiSlider() {
        binding.filterSlider.setValueFrom(0);
        binding.filterSlider.setValueTo(100);
        binding.filterSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                photoEditor.setFilterIntensityForIndex(((float) value) / ((float) slider.getValueTo()), current_index, true);
            }
        });
        binding.filterSlider.setValue((float) 0.5 * 100);
    }

    private void initBtn() {
        binding.ivBrightness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current_index = 0;
                binding.filterSlider.setValue(seekbarIntensity * binding.filterSlider.getValueTo());
            }
        });

    }
}