package com.livewallpaper.ringtones.callertune.Activity;

import static com.livewallpaper.ringtones.callertune.SingletonClasses.AppOpenAds.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.adsmodule.api.adsModule.AdUtils;
import com.adsmodule.api.adsModule.retrofit.AdsResponseModel;
import com.adsmodule.api.adsModule.utils.Constants;
import com.adsmodule.api.adsModule.utils.Global;
import com.google.gson.JsonObject;
import com.livewallpaper.ringtones.callertune.Adapter.SeeAllItemAdapter;
import com.livewallpaper.ringtones.callertune.Model.CategoryModel;
import com.livewallpaper.ringtones.callertune.Model.ExtraCategoryModel;
import com.livewallpaper.ringtones.callertune.Model.ImageModel;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.Retrofit.BodyModel;
import com.livewallpaper.ringtones.callertune.Retrofit.ImageCall;
import com.livewallpaper.ringtones.callertune.Retrofit.ImageRetrofit;
import com.livewallpaper.ringtones.callertune.Utils.Util;
import com.livewallpaper.ringtones.callertune.databinding.ActivitySeeAllBinding;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAllActivity extends AppCompatActivity {

    ActivitySeeAllBinding binding;
    String type, category;
    SeeAllItemAdapter adapter;
    boolean isCustomEnable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeeAllBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        type = getIntent().getStringExtra("type");
        category = getIntent().getStringExtra("category");

        binding.tvTitle.setText(category);
        initBtn();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    @Override
    public void onBackPressed() {
        AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), state_load -> {super.onBackPressed();});
    }

    private void getData() {

        List<ExtraCategoryModel> data = new ArrayList<>();


        if(type.equals("wallpaper")){


            if(getIntent().getBooleanExtra("circular", false)){
                List<AdsResponseModel.ExtraDataFieldDTO.WallpaperColorsDTO> dto = Constants.adsResponseModel.getExtra_data_field().getWallpaper_colors();
                int currentIndex = 0;
                for(int i = 0; i < dto.size(); i++){
                    if(dto.get(i).getColor_name().equals(category)){
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
                            "",
                            "",
                            baseUrl+urlKpop
                    ));
                }
                intRecyclerView(data);
            }else{
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
                            "",
                            "",
                            baseUrl+urlKpop
                    ));
                }
                intRecyclerView(data);
            }

        } else if (type.equals("keyboard")) {


            if(getIntent().getBooleanExtra("circular", false)){

                List<AdsResponseModel.ExtraDataFieldDTO.WallpaperColorsDTO> dto = Constants.adsResponseModel.getExtra_data_field().getWallpaper_colors();
                int currentIndex = 0;
                for(int i = 0; i < dto.size(); i++){
                    if(dto.get(i).getColor_name().equals(category)){
                        currentIndex = i;
                    }
                }

                List<String> id = dto.get(currentIndex).getIds();
                String baseUrl = Constants.adsResponseModel.getExtra_data_field().getKeyboard_base_url();

                for(int i = 0; i < id.size(); i++){
                    JsonObject wallpaperData = Constants.adsResponseModel.getExtra_data_field().getWallpaper_data();
                    JsonObject kpop1 = wallpaperData.getAsJsonObject(id.get(i));
                    String urlKpop = kpop1.get("url").getAsString();

                    data.add(new ExtraCategoryModel(
                            kpop1.get("name").getAsString(),
                            "",
                            "",
                            baseUrl+urlKpop
                    ));
                }
                intRecyclerView(data);

            }else{
                List<AdsResponseModel.ExtraDataFieldDTO.WallpaperCategoriesDTO> dto = Constants.adsResponseModel.getExtra_data_field().getWallpaper_categories();
                int currentIndex = 0;
                for(int i = 0; i < dto.size(); i++){
                    if(dto.get(i).getCategory_name().equals(category)){
                        currentIndex = i;
                    }
                }

                List<String> id = dto.get(currentIndex).getIds();
                String baseUrl = Constants.adsResponseModel.getExtra_data_field().getKeyboard_base_url();

                for(int i = 0; i < id.size(); i++){
                    JsonObject wallpaperData = Constants.adsResponseModel.getExtra_data_field().getWallpaper_data();
                    JsonObject kpop1 = wallpaperData.getAsJsonObject(id.get(i));
                    String urlKpop = kpop1.get("url").getAsString();

                    data.add(new ExtraCategoryModel(
                            kpop1.get("name").getAsString(),
                            "",
                            "",
                            baseUrl+urlKpop
                    ));
                }
                intRecyclerView(data);
            }



        } else if (type.equals("ringtone")) {

            ;
            if(getIntent().getBooleanExtra("circular", false)){
                List<AdsResponseModel.ExtraDataFieldDTO.RingtoneMoodsDTO> dto = Constants.adsResponseModel.getExtra_data_field().getRingtone_moods();
                int currentIndex = 0;
                for(int i = 0; i < dto.size(); i++){
                    if(dto.get(i).getRingtone_name().equals(category)){
                        currentIndex = i;
                    }
                }
                List<String> id = dto.get(currentIndex).getIds();
//            String baseUrl = Constants.adsResponseModel.getExtra_data_field().getKeyboard_base_url();
                for(int i = 0; i < id.size(); i++){
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
                    extraCategoryModel.setCategory(dto.get(currentIndex).getRingtone_name());
                    extraCategoryModel.setRingtoneUrl(ringtoneUrl);
                    data.add(extraCategoryModel);
                }

                intRecyclerView2(data);
            }else{
                List<AdsResponseModel.ExtraDataFieldDTO.RingtoneCategoriesDTO> dto = Constants.adsResponseModel.getExtra_data_field().getRingtone_categories();
                int currentIndex = 0;
                for(int i = 0; i < dto.size(); i++){
                    if(dto.get(i).getCategory_name().equals(category)){
                        currentIndex = i;
                    }
                }
                List<String> id = dto.get(currentIndex).getIds();
//            String baseUrl = Constants.adsResponseModel.getExtra_data_field().getKeyboard_base_url();
                for(int i = 0; i < id.size(); i++){
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
                    extraCategoryModel.setCategory(dto.get(currentIndex).getCategory_name());
                    extraCategoryModel.setRingtoneUrl(ringtoneUrl);
                    data.add(extraCategoryModel);
                }

                intRecyclerView2(data);
            }





        }




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
        adapter = new SeeAllItemAdapter(SeeAllActivity.this, imageModels, type, new SeeAllItemAdapter.onClickInputMethod() {
            @Override
            public void onClickIdentifyKeyboard() {
                PermissionX.init(SeeAllActivity.this)
                        .permissions(android.Manifest.permission.RECORD_AUDIO)
                        .request(new RequestCallback() {
                            @Override
                            public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                                if(allGranted){
                                }else {
                                    Toast.makeText(SeeAllActivity.this, "Permission Needed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

            @Override
            public void onWallpaperClick(Bitmap resource, String url) {
                String filename = "bitmap.png";

                try {
                    FileOutputStream stream = openFileOutput(filename, Context.MODE_PRIVATE);
                    resource.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    stream.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    Intent intent =new Intent(SeeAllActivity.this, WallpaperViewerActivity.class);
                    intent.putExtra("filename", filename);
                    intent.putExtra("activity", filename);
                    intent.putExtra("url", url);
                    startActivity(intent);
                });


            }
        });
        binding.rvAll.setLayoutManager(new GridLayoutManager(SeeAllActivity.this, 2));
        binding.rvAll.setAdapter(adapter);
        if(type.equals("keyboard")){
            binding.rvAll.setHasFixedSize(true);
        }

    }

    private void intRecyclerView2(List<ExtraCategoryModel> imageModels) {
        adapter = new SeeAllItemAdapter(SeeAllActivity.this, imageModels, type, new SeeAllItemAdapter.onClickInputMethod() {
            @Override
            public void onClickIdentifyKeyboard() {
                PermissionX.init(SeeAllActivity.this)
                        .permissions(android.Manifest.permission.RECORD_AUDIO)
                        .request(new RequestCallback() {
                            @Override
                            public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                                if(allGranted){
                                }else {
                                    Toast.makeText(SeeAllActivity.this, "Permission Needed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

            @Override
            public void onWallpaperClick(Bitmap resource, String url) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    String filename = "bitmap.png";

                    try {
                        FileOutputStream stream = openFileOutput(filename, Context.MODE_PRIVATE);
                        resource.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        stream.close();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }

                    Intent intent =new Intent(SeeAllActivity.this, WallpaperViewerActivity.class);
                    intent.putExtra("filename", filename);
                    intent.putExtra("activity", filename);
                    intent.putExtra("url", url);
                    startActivity(intent);
                });


            }
        });
        binding.rvAll.setLayoutManager(new LinearLayoutManager(SeeAllActivity.this));
        binding.rvAll.setAdapter(adapter);
    }


/*    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d("focusChaged", "focus: "+hasFocus);
        if(mState == PICKING) {
            mState = CHOSEN;
        }
        else if(mState == CHOSEN) {

            Log.d("Whatisthebool", "Activated"+ isThisKeyboardSetAsDefaultIME(SeeAllActivity.this));

        }
    }*/

/*
    private static final int NONE = 0;
    private static final int PICKING = 1;
    private static final int CHOSEN = 2;
    private int mState;
    protected final void pickInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showInputMethodPicker();
        mState = PICKING;
//        Log.d("Whatisthebool", "Activated"+ isThisKeyboardSetAsDefaultIME(SeeAllActivity.this));
    }

    public boolean isCustomKeyboardSet(Context context, String customKeyboardPackageName) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        String defaultInputMethod = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD);

        // The defaultInputMethod is the package name of the currently selected keyboard
        if (defaultInputMethod != null && defaultInputMethod.contains(customKeyboardPackageName)) {
            return true;
        }

        return false;
    }

    public static boolean isThisKeyboardSetAsDefaultIME(Context context) {
        final String defaultIME = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD);
        return isThisKeyboardSetAsDefaultIME(defaultIME, context.getPackageName());
    }
*/


    public static boolean isThisKeyboardSetAsDefaultIME(String defaultIME, String myPackageName) {
        if (TextUtils.isEmpty(defaultIME))
            return false;

        ComponentName defaultInputMethod = ComponentName.unflattenFromString(defaultIME);
        if (defaultInputMethod.getPackageName().equals(myPackageName)) {
            return true;
        } else {
            return false;
        }
    }

}