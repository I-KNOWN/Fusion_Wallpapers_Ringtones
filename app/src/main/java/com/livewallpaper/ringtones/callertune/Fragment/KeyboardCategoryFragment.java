package com.livewallpaper.ringtones.callertune.Fragment;

import static com.livewallpaper.ringtones.callertune.SingletonClasses.AppOpenAds.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.livewallpaper.ringtones.callertune.Activity.DownloadActivity;
import com.livewallpaper.ringtones.callertune.Activity.SeeAllActivity;
import com.livewallpaper.ringtones.callertune.Activity.WallpaperViewerActivity;
import com.livewallpaper.ringtones.callertune.Adapter.SeeAllItemAdapter;
import com.livewallpaper.ringtones.callertune.Adapter.SomeCategoryAdapter;
import com.livewallpaper.ringtones.callertune.Model.CategoryModel;
import com.livewallpaper.ringtones.callertune.Model.ExtraCategoryModel;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.Utils.Util;
import com.livewallpaper.ringtones.callertune.databinding.FragmentKeyboardCategoryBinding;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class KeyboardCategoryFragment extends Fragment {

    FragmentKeyboardCategoryBinding binding;
    ArrayList<CategoryModel> data;
    SomeCategoryAdapter someCategoryAdapter;
    SeeAllItemAdapter adapter;
    public KeyboardCategoryFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentKeyboardCategoryBinding.inflate(inflater, container, false);
        View view= binding.getRoot();

        initSomeCategoryData();
        initSomeCategoryRecyclerView();
        initSomePopData();
        initBtn();
//

        return view;
    }

    private void initBtn() {

        binding.tvSeeallCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AdsResponseModel.ExtraDataFieldDTO.WallpaperCategoriesDTO> dto = Constants.adsResponseModel.getExtra_data_field().getWallpaper_categories();
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    Intent intent = new Intent(requireActivity(), AllCategoriesActivity.class);
                    intent.putExtra("type", "keyboard");
                    startActivity(intent);
                });

            }
        });
        binding.tvSeeallPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AdsResponseModel.ExtraDataFieldDTO.WallpaperCategoriesDTO> dto = Constants.adsResponseModel.getExtra_data_field().getWallpaper_categories();
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    Intent intent = new Intent(requireActivity(), SeeAllActivity.class);
                    intent.putExtra("type", "keyboard");
                    intent.putExtra("category", dto.get(Constants.adsResponseModel.getExtra_data_field().getPopularWallpaperIndex()).getCategory_name());
                    startActivity(intent);
                });

            }
        });
    }

    private void initSomePopData() {
        List<AdsResponseModel.ExtraDataFieldDTO.WallpaperCategoriesDTO> dto = Constants.adsResponseModel.getExtra_data_field().getWallpaper_categories();
        List<ExtraCategoryModel> data = new ArrayList<>();
        List<String> id = dto.get(Constants.adsResponseModel.getExtra_data_field().getPopularkeyboardIndex()).getIds();
        binding.tvPopCat.setText(dto.get(Constants.adsResponseModel.getExtra_data_field().getPopularkeyboardIndex()).getCategory_name());
        String baseUrl = Constants.adsResponseModel.getExtra_data_field().getKeyboard_base_url();
        int size = Math.min(id.size(), 7);
        for(int i = 0; i < size; i++){
            JsonObject wallpaperData = Constants.adsResponseModel.getExtra_data_field().getWallpaper_data();
            JsonObject kpop1 = wallpaperData.getAsJsonObject(id.get(i));
            String urlKpop = kpop1.get("url").getAsString();
            String name = kpop1.get("name").getAsString();

            data.add(new ExtraCategoryModel(
                    name,
                    "",
                    "",
                    baseUrl+urlKpop
            ));
        }

        intRecyclerView(data);
    }

    private void intRecyclerView(List<ExtraCategoryModel> imageModels) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                adapter = new SeeAllItemAdapter(requireContext(), imageModels, "keyboard", new SeeAllItemAdapter.onClickInputMethod() {
                    @Override
                    public void onClickIdentifyKeyboard() {
/*                mState = NONE;
                pickInput();*/

                        PermissionX.init(requireActivity())
                                .permissions(android.Manifest.permission.RECORD_AUDIO)
                                .request(new RequestCallback() {
                                    @Override
                                    public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                                        if(allGranted){
                                        }else {
                                            Toast.makeText(requireContext(), "Permission Needed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onWallpaperClick(Bitmap resource, String url) {
                        AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                            String filename = "bitmap.png";

                            try {
                                FileOutputStream stream = requireActivity().openFileOutput(filename, Context.MODE_PRIVATE);
                                resource.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                stream.close();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }

                            Intent intent =new Intent(requireActivity(), WallpaperViewerActivity.class);
                            intent.putExtra("filename", filename);
                            intent.putExtra("activity", filename);
                            intent.putExtra("url", url);
                            startActivity(intent);
                        });

                    }

                });
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.rvSomePop.setLayoutManager(new GridLayoutManager(requireContext(), 2));
                        binding.rvSomePop.setAdapter(adapter);
                    }
                });

            }
        });

    }


    private void initSomeCategoryData() {
        List<AdsResponseModel.ExtraDataFieldDTO.WallpaperCategoriesDTO> dto = Constants.adsResponseModel.getExtra_data_field().getWallpaper_categories();
        data = new ArrayList<>();
        int size = Math.min(dto.size(), 4);
        for(int i = 0; i < size; i++){
            JsonObject keyboardData = Constants.adsResponseModel.getExtra_data_field().getWallpaper_data();
            JsonObject kpop1 = keyboardData.getAsJsonObject(dto.get(i).getIds().get(i));
            String urlKpop = kpop1.get("url").getAsString();
            String baseUrl = Constants.adsResponseModel.getExtra_data_field().getKeyboard_base_url();
            data.add(new CategoryModel(
                    dto.get(i).getCategory_name(),
                    dto.get(i).getCategory_desc(),
                    baseUrl+urlKpop
            ));
        }
    }

    private void initSomeCategoryRecyclerView() {
        someCategoryAdapter = new SomeCategoryAdapter(requireContext(), data, "keyboard");
        binding.rvSomeCat.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvSomeCat.setAdapter(someCategoryAdapter);
    }

}