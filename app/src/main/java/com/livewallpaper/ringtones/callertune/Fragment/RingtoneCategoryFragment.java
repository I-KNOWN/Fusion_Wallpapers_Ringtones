package com.livewallpaper.ringtones.callertune.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adsmodule.api.adsModule.retrofit.AdsResponseModel;
import com.adsmodule.api.adsModule.utils.Constants;
import com.google.gson.JsonObject;
import com.livewallpaper.ringtones.callertune.Activity.AllCategoriesActivity;
import com.livewallpaper.ringtones.callertune.Activity.SeeAllActivity;
import com.livewallpaper.ringtones.callertune.Adapter.SomeCategoryAdapter;
import com.livewallpaper.ringtones.callertune.Model.CategoryModel;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.databinding.FragmentRingtoneCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class RingtoneCategoryFragment extends Fragment {

    FragmentRingtoneCategoryBinding binding;
    ArrayList<CategoryModel> data;
    SomeCategoryAdapter someCategoryAdapter;

    public RingtoneCategoryFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRingtoneCategoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        initBtn();
        initSomeCategoryData();
        
        return view;
    }

    private void initBtn() {
        binding.tvSeeallCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), AllCategoriesActivity.class);
                intent.putExtra("type", "ringtone");
                startActivity(intent);
            }
        });

/*        binding.tvSeeallPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AdsResponseModel.ExtraDataFieldDTO.WallpaperCategoriesDTO> dto = Constants.adsResponseModel.getExtra_data_field().getWallpaper_categories();

                Intent intent = new Intent(requireActivity(), SeeAllActivity.class);
                intent.putExtra("type", "wallpaper");
                intent.putExtra("category", dto.get(Constants.adsResponseModel.getExtra_data_field().getPopularWallpaperIndex()).getCategory_name());
                startActivity(intent);
            }
        });*/
    }

    private void initSomeCategoryData() {
        List<AdsResponseModel.ExtraDataFieldDTO.RingtoneCategoriesDTO> dto = Constants.adsResponseModel.getExtra_data_field().getRingtone_categories();
        data = new ArrayList<>();
        int size = Math.min(dto.size(), 4);
        for(int i = 0; i < size; i++){
/*            JsonObject wallpaperData = Constants.adsResponseModel.getExtra_data_field().getRingtone_data();
            JsonObject kpop1 = wallpaperData.getAsJsonObject(dto.get(i).getIds().get(i));
            String urlKpop = kpop1.get("url").getAsString();
            String baseUrl = Constants.adsResponseModel.getExtra_data_field().getRingtone_base_url();*/
            data.add(new CategoryModel(
                    dto.get(i).getCategory_name(),
                    dto.get(i).getCategory_desc(),
                    dto.get(i).getCategory_img()
            ));
        }
        
        initSomeCategoryRecyclerView();
        
    }

    private void initSomeCategoryRecyclerView() {
        someCategoryAdapter = new SomeCategoryAdapter(requireContext(), data, "ringtone");
        binding.rvSomeCat.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvSomeCat.setAdapter(someCategoryAdapter);
    }
}