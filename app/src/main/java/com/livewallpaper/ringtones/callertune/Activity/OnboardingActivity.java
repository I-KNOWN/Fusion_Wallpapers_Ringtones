package com.livewallpaper.ringtones.callertune.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.livewallpaper.ringtones.callertune.Adapter.OnboardingAdapter;
import com.livewallpaper.ringtones.callertune.Model.AutoScrollImageModel;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.databinding.ActivityOnboardingBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class OnboardingActivity extends AppCompatActivity {


    ActivityOnboardingBinding binding;
    OnboardingAdapter onboardingAdapter;
    private static int currentPage = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnboardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViewPager();
    }

    private void initViewPager() {

        ArrayList<AutoScrollImageModel> data = new ArrayList<>();
        ArrayList<Integer> mode1 = new ArrayList<>();
        mode1.add(R.drawable.board1_01);
        mode1.add(R.drawable.board1_02);
        mode1.add(R.drawable.board1_03);
        mode1.add(R.drawable.board1_04);
        mode1.add(R.drawable.board1_05);
        mode1.add(R.drawable.board1_06);
        mode1.add(R.drawable.board1_07);
        data.add(new AutoScrollImageModel(
                mode1
        ));

        ArrayList<Integer> mode2 = new ArrayList<>();
        mode2.add(R.drawable.board2_01);
        mode2.add(R.drawable.board2_02);
        mode2.add(R.drawable.board2_03);
        mode2.add(R.drawable.board2_04);
        mode2.add(R.drawable.board2_05);
        mode2.add(R.drawable.board2_06);
        mode2.add(R.drawable.board2_07);
        data.add(new AutoScrollImageModel(
                mode2
        ));

        ArrayList<Integer> mode3 = new ArrayList<>();
        mode3.add(R.drawable.board3_01);
        mode3.add(R.drawable.board3_02);
        mode3.add(R.drawable.board3_03);
        mode3.add(R.drawable.board3_04);
        mode3.add(R.drawable.board3_05);
        mode3.add(R.drawable.board3_06);
        mode3.add(R.drawable.board3_07);
        data.add(new AutoScrollImageModel(
                mode3
        ));

        onboardingAdapter = new OnboardingAdapter(OnboardingActivity.this, data);
        binding.vp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(position == 0){
                    binding.ivDot1.setBackground(ContextCompat.getDrawable(OnboardingActivity.this, R.drawable.dot_fill));
                    binding.ivDot2.setBackground(ContextCompat.getDrawable(OnboardingActivity.this, R.drawable.dot_unfill));
                    binding.ivDot3.setBackground(ContextCompat.getDrawable(OnboardingActivity.this, R.drawable.dot_unfill));

                    binding.tvDesc.setText(R.string.boarding_desc_1);
                    binding.tvHeader.setText(R.string.boarding_title_1);
                } else if (position == 1) {
                    binding.ivDot1.setBackground(ContextCompat.getDrawable(OnboardingActivity.this, R.drawable.dot_unfill));
                    binding.ivDot2.setBackground(ContextCompat.getDrawable(OnboardingActivity.this, R.drawable.dot_fill));
                    binding.ivDot3.setBackground(ContextCompat.getDrawable(OnboardingActivity.this, R.drawable.dot_unfill));
                    binding.tvDesc.setText(R.string.boarding_desc_2);
                    binding.tvHeader.setText(R.string.boarding_title_2);
                } else if (position == 2) {
                    binding.ivDot1.setBackground(ContextCompat.getDrawable(OnboardingActivity.this, R.drawable.dot_unfill));
                    binding.ivDot2.setBackground(ContextCompat.getDrawable(OnboardingActivity.this, R.drawable.dot_unfill));
                    binding.ivDot3.setBackground(ContextCompat.getDrawable(OnboardingActivity.this, R.drawable.dot_fill));
                    binding.tvDesc.setText(R.string.boarding_desc_3);
                    binding.tvHeader.setText(R.string.boarding_title_3);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        binding.vp2.setAdapter(onboardingAdapter);



    }
}