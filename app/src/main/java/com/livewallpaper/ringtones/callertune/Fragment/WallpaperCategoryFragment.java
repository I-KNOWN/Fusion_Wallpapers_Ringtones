package com.livewallpaper.ringtones.callertune.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

        binding.tvSeeallPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), SeeAllActivity.class);
                intent.putExtra("type", "wallpaper");
                intent.putExtra("category", "kpop");
                startActivity(intent);
            }
        });

    }

    private void getData() {
        ImageCall imageCall = ImageRetrofit.getClient().create(ImageCall.class);
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
        });
    }

    private void intRecyclerView(List<ImageModel> imageModels) {
        wallpaperItemAdapter = new WallpaperItemAdapter(requireContext(), imageModels);
        binding.rvPopular.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
        binding.rvPopular.setAdapter(wallpaperItemAdapter);
    }


    private void initSomeCategoryData() {

        data = new ArrayList<>();
/*        data.add(new CategoryModel(
                "Abstract",
                "Abstracts, Dark Wallpaper",
                R.drawable.abstract_sample
        ));

        data.add(new CategoryModel(
                "Cars",
                "Cool, Attractive, Expensive",
                R.drawable.cars
        ));

        data.add(new CategoryModel(
                "Cartoon",
                "Cute, Charming, innocent",
                R.drawable.cartoon
        ));*/

    }

    private void initSomeCategoryRecyclerView() {
        someCategoryAdapter = new SomeCategoryAdapter(requireContext(), data);
        binding.rvSomeCat.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvSomeCat.setAdapter(someCategoryAdapter);
    }
}