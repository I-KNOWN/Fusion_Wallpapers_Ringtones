package com.livewallpaper.ringtones.callertune.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.adsmodule.api.adsModule.retrofit.AdsResponseModel;
import com.adsmodule.api.adsModule.utils.Constants;
import com.google.gson.JsonObject;
import com.livewallpaper.ringtones.callertune.Adapter.SeeAllItemAdapter;
import com.livewallpaper.ringtones.callertune.Model.CategoryModel;
import com.livewallpaper.ringtones.callertune.Model.ExtraCategoryModel;
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
        binding.tvTitle.setText(category);
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

        List<ExtraCategoryModel> data = new ArrayList<>();


        if(type.equals("wallpaper")){

            List<AdsResponseModel.ExtraDataFieldDTO.WallpaperCategoriesDTO> dto = Constants.adsResponseModel.getExtra_data_field().getWallpaper_categories();
            int currentIndex = 0;
            for(int i = 0; i < dto.size(); i++){
                if(dto.get(i).getCategory_name().equals(category)){
                    currentIndex = i;
                }
            }

            List<String> id = dto.get(currentIndex).getIds();
            String baseUrl = Constants.adsResponseModel.getExtra_data_field().getWallpaper_base_url();

            for(int i = 0; i < id.size(); i++){
                JsonObject wallpaperData = Constants.adsResponseModel.getExtra_data_field().getWallpaper_data();
                JsonObject kpop1 = wallpaperData.getAsJsonObject(id.get(i));
                String urlKpop = kpop1.get("url").getAsString();

                data.add(new ExtraCategoryModel(
                        "",
                        baseUrl+urlKpop
                ));
            }
        } else if (type.equals("keyboard")) {

            List<AdsResponseModel.ExtraDataFieldDTO.KeyboardCategoriesDTO> dto = Constants.adsResponseModel.getExtra_data_field().getKeyboard_categories();
            int currentIndex = 0;
            for(int i = 0; i < dto.size(); i++){
                if(dto.get(i).getCategory_name().equals(category)){
                    currentIndex = i;
                }
            }

            List<String> id = dto.get(currentIndex).getIds();
            String baseUrl = Constants.adsResponseModel.getExtra_data_field().getKeyboard_base_url();

            for(int i = 0; i < id.size(); i++){
                JsonObject wallpaperData = Constants.adsResponseModel.getExtra_data_field().getKeyboard_data();
                JsonObject kpop1 = wallpaperData.getAsJsonObject(id.get(i));
                String urlKpop = kpop1.get("url").getAsString();

                data.add(new ExtraCategoryModel(
                        kpop1.get("category").getAsString(),
                        baseUrl+urlKpop
                ));
            }
        }


        intRecyclerView(data);


/*        ImageCall imageCall = ImageRetrofit.getClient().create(ImageCall.class);
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
        });*/
    }

    private void intRecyclerView(List<ExtraCategoryModel> imageModels) {
        adapter = new SeeAllItemAdapter(SeeAllActivity.this, imageModels, type);
        binding.rvAll.setLayoutManager(new GridLayoutManager(SeeAllActivity.this, 2));
        binding.rvAll.setAdapter(adapter);
    }

}