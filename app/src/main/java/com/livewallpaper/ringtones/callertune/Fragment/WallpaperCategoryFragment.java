package com.livewallpaper.ringtones.callertune.Fragment;

import static com.livewallpaper.ringtones.callertune.SingletonClasses.AppOpenAds.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.adsmodule.api.adsModule.AdUtils;
import com.adsmodule.api.adsModule.retrofit.AdsResponseModel;
import com.adsmodule.api.adsModule.utils.Constants;
import com.google.gson.JsonObject;
import com.livewallpaper.ringtones.callertune.Activity.AllCategoriesActivity;
import com.livewallpaper.ringtones.callertune.Activity.SeeAllActivity;
import com.livewallpaper.ringtones.callertune.Adapter.SomeCategoryAdapter;
import com.livewallpaper.ringtones.callertune.Adapter.WallpaperItemAdapter;
import com.livewallpaper.ringtones.callertune.Model.CategoryModel;
import com.livewallpaper.ringtones.callertune.Model.ImageModel;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.Retrofit.BodyModel;
import com.livewallpaper.ringtones.callertune.Retrofit.ImageCall;
import com.livewallpaper.ringtones.callertune.Retrofit.ImageRetrofit;
import com.livewallpaper.ringtones.callertune.databinding.FragmentWallpaperCategoryBinding;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WallpaperCategoryFragment extends Fragment {

    FragmentWallpaperCategoryBinding binding;
    ArrayList<CategoryModel> data;
    SomeCategoryAdapter someCategoryAdapter;
    WallpaperItemAdapter wallpaperItemAdapter;
    public WallpaperCategoryFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentWallpaperCategoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initSomeCategoryData();
        initSomeCategoryRecyclerView();
        getData();
        initBtn();
        return view;
    }

    private void initBtn() {

        binding.tvSeeallCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AdsResponseModel.ExtraDataFieldDTO.WallpaperCategoriesDTO> dto = Constants.adsResponseModel.getExtra_data_field().getWallpaper_categories();
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    Intent intent = new Intent(requireActivity(), AllCategoriesActivity.class);
                    intent.putExtra("type", "wallpaper");
                    startActivity(intent);
                });

            }
        });

        binding.tvSeeallPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AdsResponseModel.ExtraDataFieldDTO.WallpaperCategoriesDTO> dto = Constants.adsResponseModel.getExtra_data_field().getWallpaper_categories();
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    Intent intent = new Intent(requireActivity(), SeeAllActivity.class);
                    intent.putExtra("type", "wallpaper");
                    intent.putExtra("category", dto.get(Constants.adsResponseModel.getExtra_data_field().getPopularWallpaperIndex()).getCategory_name());
                    startActivity(intent);
                });

            }
        });

    }

    private void getData() {

        List<AdsResponseModel.ExtraDataFieldDTO.WallpaperCategoriesDTO> dto = Constants.adsResponseModel.getExtra_data_field().getWallpaper_categories();
        List<String> data = new ArrayList<>();
        List<String> id = dto.get(Constants.adsResponseModel.getExtra_data_field().getPopularWallpaperIndex()).getIds();
        binding.tvCatPop.setText(dto.get(Constants.adsResponseModel.getExtra_data_field().getPopularWallpaperIndex()).getCategory_name());
        String baseUrl = Constants.adsResponseModel.getExtra_data_field().getWallpaper_base_url();

        for(int i = 0; i < id.size(); i++){
            JsonObject wallpaperData = Constants.adsResponseModel.getExtra_data_field().getWallpaper_data();
            JsonObject kpop1 = wallpaperData.getAsJsonObject(id.get(i));
            String urlKpop = kpop1.get("url").getAsString();

            data.add(baseUrl+urlKpop);
        }

        intRecyclerView(data);


/*        ImageCall imageCall = ImageRetrofit.getClient().create(ImageCall.class);
        List<String> data = new ArrayList<>();
        data.add("kpop");
        Call<List<ImageModel>> call = imageCall.getSpecificImageCategory(new BodyModel(getActivity().getPackageName(), data));

        call.enqueue(new Callback<List<ImageModel>>() {
            @Override
            public void onResponse(Call<List<ImageModel>> call, Response<List<ImageModel>> response) {
                List<ImageModel> imageModels = response.body();
                intRecyclerView(imageModels);
            }

            @Override
            public void onFailure(Call<List<ImageModel>> call, Throwable t) {
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(requireActivity(), "Failed To Get Data", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });*/
    }

    private void intRecyclerView(List<String> imageModels) {
        wallpaperItemAdapter = new WallpaperItemAdapter(requireContext(), imageModels);
        binding.rvPopular.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
        binding.rvPopular.setAdapter(wallpaperItemAdapter);
    }


    private void initSomeCategoryData() {
        List<AdsResponseModel.ExtraDataFieldDTO.WallpaperCategoriesDTO> dto = Constants.adsResponseModel.getExtra_data_field().getWallpaper_categories();
        data = new ArrayList<>();
        if(dto != null){
            int size = Math.min(dto.size(), 4);
            for(int i = 0; i < size; i++){
                JsonObject wallpaperData = Constants.adsResponseModel.getExtra_data_field().getWallpaper_data();
                JsonObject kpop1 = wallpaperData.getAsJsonObject(dto.get(i).getIds().get(i));
                String urlKpop = kpop1.get("url").getAsString();
                String baseUrl = Constants.adsResponseModel.getExtra_data_field().getWallpaper_base_url();
                data.add(new CategoryModel(
                        dto.get(i).getCategory_name(),
                        dto.get(i).getCategory_desc(),
                        baseUrl+urlKpop
                ));
            }
        }
    }

    private void initSomeCategoryRecyclerView() {
        someCategoryAdapter = new SomeCategoryAdapter(requireContext(), data, "wallpaper");
        binding.rvSomeCat.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvSomeCat.setAdapter(someCategoryAdapter);
    }
}