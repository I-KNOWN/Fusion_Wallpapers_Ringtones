package com.livewallpaper.ringtones.callertune.Activity;

import static com.livewallpaper.ringtones.callertune.SingletonClasses.AppOpenAds.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adsmodule.api.adsModule.retrofit.AdsResponseModel;
import com.adsmodule.api.adsModule.utils.Constants;
import com.adsmodule.api.adsModule.utils.Global;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.JsonObject;
import com.livewallpaper.ringtones.callertune.Adapter.AllCategoryAdapter;
import com.livewallpaper.ringtones.callertune.Adapter.CircularCategoryAdapter;
import com.livewallpaper.ringtones.callertune.Model.CategoryModel;
import com.livewallpaper.ringtones.callertune.Model.ExtraCategoryModel;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.SingletonClasses.MyApplication;
import com.livewallpaper.ringtones.callertune.databinding.ActivityAllCategoriesBinding;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AllCategoriesActivity extends AppCompatActivity {

    ActivityAllCategoriesBinding binding;
    ArrayList<ExtraCategoryModel> data;
    ArrayList<CategoryModel> data2;
    CircularCategoryAdapter circularCategoryAdapter;
    AllCategoryAdapter allCategoryAdapter;

    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        type = getIntent().getStringExtra("type");
        if(type.equals("keyboard")){
            binding.tvTitle.setText("Keyboard Categories");
        } else if (type.equals("wallpaper")) {
            binding.tvTitle.setText("Wallpaper Categories");   
        } else if (type.equals("ringtone")) {
            binding.tvTitle.setText("Ringtone Categories");
        }

        initColorCatergory();
        initAllCategory();
        initBtn();
    }

    private void initAllCategory() {
        
        if(type.equals("keyboard")){
            binding.tvTitle.setText("Keyboard Categories");

            List<AdsResponseModel.ExtraDataFieldDTO.KeyboardCategoriesDTO> dto = Constants.adsResponseModel.getExtra_data_field().getKeyboard_categories();
            data2 = new ArrayList<>();
            data2.add(new CategoryModel(
                    "customKeybaord",
                    "",
                    ""
            ));
            for(int i = 0; i < dto.size(); i++){
                JsonObject wallpaperData = Constants.adsResponseModel.getExtra_data_field().getKeyboard_data();
                JsonObject kpop1 = wallpaperData.getAsJsonObject(dto.get(i).getIds().get(i));
                String urlKpop = kpop1.get("url").getAsString();
                String baseUrl = Constants.adsResponseModel.getExtra_data_field().getKeyboard_base_url();
                data2.add(new CategoryModel(
                        dto.get(i).getCategory_name(),
                        dto.get(i).getCategory_desc(),
                        baseUrl+urlKpop
                ));
            }

        } else if (type.equals("wallpaper")) {
            binding.tvTitle.setText("Wallpaper Categories");
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
        } else if (type.equals("ringtone")) {

        }


//        initRecyclerViewCategory();
    }


    private void initRecyclerViewCategory() {
        allCategoryAdapter = new AllCategoryAdapter(AllCategoriesActivity.this, data2, type, new AllCategoryAdapter.onClickGalleryIntent() {
            @Override
            public void galleryIntent() {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                onGalleryIntentResult.launch(intent);
            }
        });
        binding.rvCat.setLayoutManager(new GridLayoutManager(AllCategoriesActivity.this, 3));
        binding.rvCat.setAdapter(allCategoryAdapter);
    }


    ActivityResultLauncher<Intent> onGalleryIntentResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        Intent data = result.getData();
                        Glide.with(AllCategoriesActivity.this)
                                .asBitmap()
                                .load(data.getData())
                                .addListener(new RequestListener<Bitmap>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Bitmap> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(@NonNull Bitmap resource, @NonNull Object model, Target<Bitmap> target, @NonNull DataSource dataSource, boolean isFirstResource) {

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                BottomSheetDialog dialog = new BottomSheetDialog(AllCategoriesActivity.this);
                                                dialog.setContentView(R.layout.keybaord_bottom_sheet);
                                                dialog.setCancelable(true);
                                                View view = dialog.findViewById(R.id.include);
                                                ImageView iv_bg = view.findViewById(R.id.iv_bg_keyboard);
                                                TextView tv_title = dialog.findViewById(R.id.tv_title);
                                                Glide.with(AllCategoriesActivity.this)
                                                        .load(resource)
                                                        .into(iv_bg);
                                                ImageView iv_close = dialog.findViewById(R.id.iv_close);
                                                CardView cv_apply = dialog.findViewById(R.id.cv_apply);
                                                tv_title.setText("Custom Keyboard");


                                                cv_apply.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                        if(!isKeyboardEnabled()){
                                                            openKeyboardSettings();
                                                        }else{
                                                            Global.showAlertProgressDialog(activity);
                                                            String filename = "savedBitmapBackground.png";

                                                            try {
                                                                FileOutputStream stream = openFileOutput(filename, Context.MODE_PRIVATE);
                                                                resource.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                                                MyApplication.getPreferences().putString(com.livewallpaper.ringtones.callertune.Utils.Constants.KEYBOARD_BG, filename);
                                                                stream.close();
                                                            }catch (Exception e) {
                                                                Global.hideAlertProgressDialog();
                                                                Toast.makeText(AllCategoriesActivity.this, "Failed To Set Keyboard", Toast.LENGTH_SHORT).show();
                                                                e.printStackTrace();
                                                            }
                                                            if(!isThisKeyboardSetAsDefaultIME(AllCategoriesActivity.this)){
                                                                Global.hideAlertProgressDialog();
                                                                openKeyboardChooserSettings();
                                                            }else{
                                                                Global.hideAlertProgressDialog();
                                                                dialog.dismiss();
                                                                Toast.makeText(AllCategoriesActivity.this, "Keyboard Set Successfully", Toast.LENGTH_SHORT).show();
                                                            }
//                                                        dialog.dismiss();

//                                                        openKeyboardChooserSettings();
//                                                        isKeyboardEnabled();
                                                        }



                                                    }
                                                });

                                                iv_close.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        dialog.dismiss();
                                                    }
                                                });

                                                dialog.show();
                                            }
                                        });

                                        return true;
                                    }
                                }).submit();
                    }
                }
            });
    private boolean isKeyboardEnabled() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        List<InputMethodInfo> enabledInputMethodIds = inputMethodManager.getEnabledInputMethodList();
        for(InputMethodInfo info: enabledInputMethodIds){
            Log.d("whaistheinfo", info.toString());
            if(info.getId().contains("com.livewallpaper.ringtones.callertune/.Keyboard.CustomKeyboard")){
                return true;
            }
        }
        return false;
    }


    public static boolean isThisKeyboardSetAsDefaultIME(Context context) {
        final String defaultIME = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD);
        return isThisKeyboardSetAsDefaultIME(defaultIME, context.getPackageName());
    }


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

    private void openKeyboardSettings() {
        Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
        startActivity(intent);
    }

    private void openKeyboardChooserSettings() {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.showInputMethodPicker();
    }


    private void initColorCatergory() {

        if(type.equals("keyboard")){
            List<AdsResponseModel.ExtraDataFieldDTO.KeyboardColorsDTO> dto = Constants.adsResponseModel.getExtra_data_field().getKeyboard_colors();
            data = new ArrayList<>();
            for(int i = 0; i < dto.size(); i++){
                String colorName = dto.get(i).getColor_name();
                String id = dto.get(i).getIds().get(0);
                JsonObject wallpaperData = Constants.adsResponseModel.getExtra_data_field().getKeyboard_data();
                JsonObject kpop1 = wallpaperData.getAsJsonObject(id);
                String urlKpop = kpop1.get("url").getAsString();
                String baseUrl = Constants.adsResponseModel.getExtra_data_field().getKeyboard_base_url();
                data.add(new ExtraCategoryModel(
                        colorName,
                        "",
                        "",
                        baseUrl+urlKpop
                ));
            }
        } else if (type.equals("wallpaper")) {
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
                        "",
                        "",
                        baseUrl+urlKpop
                ));
            }
        } else if (type.equals("ringtone")) {
            List<AdsResponseModel.ExtraDataFieldDTO.RingtoneMoodsDTO> dto = Constants.adsResponseModel.getExtra_data_field().getRingtone_moods();
            data = new ArrayList<>();
            for(int i = 0; i < dto.size(); i++){
                String colorName = dto.get(i).getRingtone_name();
                String moode_img = dto.get(i).getMoode_img();

                data.add(new ExtraCategoryModel(
                        colorName,
                        "",
                        "",
                        moode_img
                ));
            }
        }


        initReyclerViewcolor();

    }

    private void initReyclerViewcolor() {
        circularCategoryAdapter = new CircularCategoryAdapter(AllCategoriesActivity.this, data, type);
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