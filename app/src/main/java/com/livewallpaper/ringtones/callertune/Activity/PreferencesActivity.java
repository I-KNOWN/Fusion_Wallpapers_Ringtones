package com.livewallpaper.ringtones.callertune.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.databinding.ActivityPreferencesBinding;

import java.util.ArrayList;

public class PreferencesActivity extends AppCompatActivity {

    ActivityPreferencesBinding binding;
    int count = 0;
    ArrayList<Integer> preferenceId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreferencesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceId = new ArrayList<>();
        binding.tvProgressIncrement.setText(Integer.toString(count));
        initPrefereneSelector();

        initBtn();
    }

    private void initBtn() {

        binding.cvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(count != 0){
                    Toast.makeText(PreferencesActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PreferencesActivity.this, MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(PreferencesActivity.this, "Select Atleast 1 Interest", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }


    private void initPrefereneSelector() {

        binding.cvBtn1.setOnClickListener(v -> setUpClick((CardView) v));
        binding.cvBtn2.setOnClickListener(v -> setUpClick((CardView) v));
        binding.cvBtn3.setOnClickListener(v -> setUpClick((CardView) v));
        binding.cvBtn4.setOnClickListener(v -> setUpClick((CardView) v));
        binding.cvBtn5.setOnClickListener(v -> setUpClick((CardView) v));
        binding.cvBtn6.setOnClickListener(v -> setUpClick((CardView) v));
        binding.cvBtn7.setOnClickListener(v -> setUpClick((CardView) v));
        binding.cvBtn8.setOnClickListener(v -> setUpClick((CardView) v));
        binding.cvBtn9.setOnClickListener(v -> setUpClick((CardView) v));
        binding.cvBtn10.setOnClickListener(v -> setUpClick((CardView) v));
        binding.cvBtn11.setOnClickListener(v -> setUpClick((CardView) v));
        binding.cvBtn12.setOnClickListener(v -> setUpClick((CardView) v));
    }

    private void setUpClick(CardView view) {
        ImageView iv = (ImageView) view.getChildAt(0);
        boolean isContained = false;
            for (int id: preferenceId){
                if (view.getId() == id){
                    isContained = true;
                }
            }
            if(isContained){
                    preferenceId.remove((Integer) view.getId());
                    Animation fadeOut = AnimationUtils.loadAnimation(PreferencesActivity.this, R.anim.fade_out_image);
                    view.startAnimation(fadeOut);
                    fadeOut.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            Animation fadeIn = AnimationUtils.loadAnimation(PreferencesActivity.this, R.anim.fade_in_image);
                            fadeIn.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    iv.setImageDrawable(ContextCompat.getDrawable(PreferencesActivity.this, R.drawable.preference_btn_outline_unselected));
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            view.startAnimation(fadeIn);
                        }
                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    --count;
                    changeProgress();
            } else{
                if(count != 5){
                    preferenceId.add((Integer) view.getId());
                    Animation fadeOut = AnimationUtils.loadAnimation(PreferencesActivity.this, R.anim.fade_out_image);
                    view.startAnimation(fadeOut);
                    fadeOut.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            Animation fadeIn = AnimationUtils.loadAnimation(PreferencesActivity.this, R.anim.fade_in_image);
                            fadeIn.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    iv.setImageDrawable(ContextCompat.getDrawable(PreferencesActivity.this, R.drawable.preference_btn_outline_selected));
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            view.startAnimation(fadeIn);
                        }
                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });

                    ++count;
                    changeProgress();
                }else{
                    Toast.makeText(PreferencesActivity.this, "Select Only Up to 5 Interest", Toast.LENGTH_SHORT).show();
                }
            }
        }

    private void changeProgress() {
        binding.linearProgress.setProgress(count, true);
        binding.tvProgressIncrement.setText(Integer.toString(count));
    }


}