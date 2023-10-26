package com.livewallpaper.ringtones.callertune.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.livewallpaper.ringtones.callertune.Adapter.SeeAllItemAdapter;
import com.livewallpaper.ringtones.callertune.Model.ImageModel;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.Retrofit.BodyModel;
import com.livewallpaper.ringtones.callertune.Retrofit.ImageCall;
import com.livewallpaper.ringtones.callertune.Retrofit.ImageRetrofit;
import com.livewallpaper.ringtones.callertune.databinding.ActivitySeeAllBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAllActivity extends AppCompatActivity {

    ActivitySeeAllBinding binding;
    String type, category;
    SeeAllItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeeAllBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        type = getIntent().getStringExtra("type");
        category = getIntent().getStringExtra("category");
        initBtn();
        getData();

    }

    private void initBtn() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getData() {
        ImageCall imageCall = ImageRetrofit.getClient().create(ImageCall.class);
        List<String> data = new ArrayList<>();
        data.add(category.toLowerCase());
        Call<List<ImageModel>> call = imageCall.getSpecificImageCategory(new BodyModel(getPackageName(), data));

        call.enqueue(new Callback<List<ImageModel>>() {
            @Override
            public void onResponse(Call<List<ImageModel>> call, Response<List<ImageModel>> response) {
                List<ImageModel> imageModels = response.body();

                intRecyclerView(imageModels);
            }

            @Override
            public void onFailure(Call<List<ImageModel>> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SeeAllActivity.this, "Failed To Get Data", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    private void intRecyclerView(List<ImageModel> imageModels) {
        adapter = new SeeAllItemAdapter(SeeAllActivity.this, imageModels, type);
        binding.rvAll.setLayoutManager(new GridLayoutManager(SeeAllActivity.this, 2));
        binding.rvAll.setAdapter(adapter);
    }

}