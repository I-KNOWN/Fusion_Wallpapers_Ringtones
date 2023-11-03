package com.livewallpaper.ringtones.callertune.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adsmodule.api.adsModule.retrofit.AdsResponseModel;
import com.adsmodule.api.adsModule.utils.Constants;
import com.google.gson.JsonObject;
import com.livewallpaper.ringtones.callertune.Activity.AllCategoriesActivity;
import com.livewallpaper.ringtones.callertune.Activity.SeeAllActivity;
import com.livewallpaper.ringtones.callertune.Adapter.SeeAllItemAdapter;
import com.livewallpaper.ringtones.callertune.Adapter.SomeCategoryAdapter;
import com.livewallpaper.ringtones.callertune.Model.CategoryModel;
import com.livewallpaper.ringtones.callertune.Model.ExtraCategoryModel;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.databinding.FragmentRingtoneCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class RingtoneCategoryFragment extends Fragment {

    FragmentRingtoneCategoryBinding binding;
    ArrayList<CategoryModel> data;
    SomeCategoryAdapter someCategoryAdapter;
    SeeAllItemAdapter adapter;
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

        binding.tvSeeallPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AdsResponseModel.ExtraDataFieldDTO.RingtoneCategoriesDTO> dto = Constants.adsResponseModel.getExtra_data_field().getRingtone_categories();

                Intent intent = new Intent(requireActivity(), SeeAllActivity.class);
                intent.putExtra("type", "ringtone");
                intent.putExtra("category", dto.get(Constants.adsResponseModel.getExtra_data_field().getPopularringtoneIndex()).getCategory_name());
                startActivity(intent);
            }
        });
    }

    private void initSomeCategoryData() {
        List<AdsResponseModel.ExtraDataFieldDTO.RingtoneCategoriesDTO> dto = Constants.adsResponseModel.getExtra_data_field().getRingtone_categories();
        data = new ArrayList<>();
        if(dto != null){
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

        
    }


    private void initSomeCategoryRecyclerView() {
        someCategoryAdapter = new SomeCategoryAdapter(requireContext(), data, "ringtone");
        binding.rvSomeCat.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvSomeCat.setAdapter(someCategoryAdapter);
    }

    private void initSomePopData() {

        List<ExtraCategoryModel> data = new ArrayList<>();

        List<AdsResponseModel.ExtraDataFieldDTO.RingtoneCategoriesDTO> dto = Constants.adsResponseModel.getExtra_data_field().getRingtone_categories();
        List<String> id = dto.get(Constants.adsResponseModel.getExtra_data_field().getPopularringtoneIndex()).getIds();
        binding.tvRigPop.setText(dto.get(Constants.adsResponseModel.getExtra_data_field().getPopularkeyboardIndex()).getCategory_name());
        int size = Math.min(id.size(), 7);
        for(int i = 0; i < size; i++){
            JsonObject wallpaperData = Constants.adsResponseModel.getExtra_data_field().getRingtone_data();
            JsonObject kpop1 = wallpaperData.getAsJsonObject(id.get(i));
            String urlKpop = kpop1.get("url").getAsString();
            String baseurl = Constants.adsResponseModel.getExtra_data_field().getRingtone_base_url();
            String ringtoneUrl = baseurl+urlKpop;
            ExtraCategoryModel extraCategoryModel = new ExtraCategoryModel(
                    kpop1.get("ringtone_name").getAsString(),
                    kpop1.get("ringtone_author").getAsString(),
                    kpop1.get("ringtone_duration").getAsString(),
                    ""

            );
            extraCategoryModel.setRingtoneImg(kpop1.get("ringtone_img").getAsString());
            extraCategoryModel.setCategory(dto.get(Constants.adsResponseModel.getExtra_data_field().getPopularringtoneIndex()).getCategory_name());
            extraCategoryModel.setRingtoneUrl(ringtoneUrl);
            data.add(extraCategoryModel);
        }

        intRecyclerView(data);
    }

    private void intRecyclerView(List<ExtraCategoryModel> imageModels) {
        adapter = new SeeAllItemAdapter(requireContext(), imageModels, "ringtone", new SeeAllItemAdapter.onClickInputMethod() {
            @Override
            public void onClickIdentifyKeyboard() {
/*                mState = NONE;
                pickInput();*/
            }
        });
        binding.rvPopCat.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.rvPopCat.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        initSomePopData();
    }
}