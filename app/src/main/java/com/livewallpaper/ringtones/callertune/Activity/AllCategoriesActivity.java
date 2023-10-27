package com.livewallpaper.ringtones.callertune.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.adsmodule.api.adsModule.retrofit.AdsResponseModel;
import com.adsmodule.api.adsModule.utils.Constants;
import com.google.gson.JsonObject;
import com.livewallpaper.ringtones.callertune.Adapter.AllCategoryAdapter;
import com.livewallpaper.ringtones.callertune.Adapter.CircularCategoryAdapter;
import com.livewallpaper.ringtones.callertune.Model.CategoryModel;
import com.livewallpaper.ringtones.callertune.Model.ExtraCategoryModel;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.databinding.ActivityAllCategoriesBinding;

import java.util.ArrayList;
import java.util.List;

public class AllCategoriesActivity extends AppCompatActivity {

    ActivityAllCategoriesBinding binding;
    ArrayList<ExtraCategoryModel> data;
    ArrayList<CategoryModel> data2;
    CircularCategoryAdapter circularCategoryAdapter;
    AllCategoryAdapter allCategoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        initColorCatergory();
        initAllCategory();
        initBtn();
    }

    private void initAllCategory() {
        List<AdsResponseModel.ExtraDataFieldDTO.WallpaperCategoriesDTO> dto = Constants.adsResponseModel.getExtra_data_field().getWallpaper_categories();
        data2 = new ArrayList<>();
        for(int i = 0; i < dto.size(); i++){
            JsonObject wallpaperData = Constants.adsResponseModel.getExtra_data_field().getWallpaper_data();
            JsonObject kpop1 = wallpaperData.getAsJsonObject(dto.get(i).getIds().get(i));
            String urlKpop = kpop1.get("url").getAsString();
            String baseUrl = Constants.adsResponseModel.getExtra_data_field().getWallpaper_base_url();
            data2.add(new CategoryModel(
                    dto.get(i).getCategory_name(),
                    dto.get(i).getCategory_desc(),
                    baseUrl+urlKpop
            ));
        }

        initRecyclerViewCategory();
    }

    private void initRecyclerViewCategory() {
        allCategoryAdapter = new AllCategoryAdapter(AllCategoriesActivity.this, data2);
        binding.rvCat.setLayoutManager(new GridLayoutManager(AllCategoriesActivity.this, 3));
        binding.rvCat.setAdapter(allCategoryAdapter);
    }

    private void initColorCatergory() {
        List<AdsResponseModel.ExtraDataFieldDTO.WallpaperColorsDTO> dto = Constants.adsResponseModel.getExtra_data_field().getWallpaper_colors();
        data = new ArrayList<>();
        for(int i = 0; i < dto.size(); i++){
            String colorName = dto.get(i).getColor_name();
            String id = dto.get(i).getIds().get(0);
            JsonObject wallpaperData = Constants.adsResponseModel.getExtra_data_field().getWallpaper_data();
            JsonObject kpop1 = wallpaperData.getAsJsonObject(id);
            String urlKpop = kpop1.get("url").getAsString();
            String baseUrl = Constants.adsResponseModel.getExtra_data_field().getWallpaper_base_url();

            data.add(new ExtraCategoryModel(
                colorName,
                    baseUrl+urlKpop
            ));
        }

        initReyclerViewcolor();

    }

    private void initReyclerViewcolor() {
        circularCategoryAdapter = new CircularCategoryAdapter(AllCategoriesActivity.this, data);
        binding.rvExtraCat.setLayoutManager(new LinearLayoutManager(AllCategoriesActivity.this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvExtraCat.setAdapter(circularCategoryAdapter);
    }

    private void initBtn() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}