package com.livewallpaper.ringtones.callertune.Adapter;

import static com.livewallpaper.ringtones.callertune.SingletonClasses.AppOpenAds.activity;
import static com.livewallpaper.ringtones.callertune.Utils.Constants.DOWNALOD_RINGTONE;
import static com.livewallpaper.ringtones.callertune.Utils.Constants.KEYBOARD_FAV;
import static com.livewallpaper.ringtones.callertune.Utils.Constants.RINGTONE_DOWNLOAD;
import static com.livewallpaper.ringtones.callertune.Utils.Constants.RINGTONE_FAV;
import static com.livewallpaper.ringtones.callertune.Utils.Constants.RINGTONE_PATH;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.adsmodule.api.adsModule.AdUtils;
import com.adsmodule.api.adsModule.utils.Global;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.livewallpaper.ringtones.callertune.Activity.AllCategoriesActivity;
import com.livewallpaper.ringtones.callertune.Activity.RingtoneActivity;
import com.livewallpaper.ringtones.callertune.Activity.WallpaperViewerActivity;
import com.livewallpaper.ringtones.callertune.Model.ExtraCategoryModel;
import com.livewallpaper.ringtones.callertune.Model.ImageModel;
import com.livewallpaper.ringtones.callertune.Model.KeyboardData;
import com.livewallpaper.ringtones.callertune.Model.KeyboardFavouriteRootModel;
import com.livewallpaper.ringtones.callertune.Model.RingtoneData;
import com.livewallpaper.ringtones.callertune.Model.RingtoneDownloadModel;
import com.livewallpaper.ringtones.callertune.Model.RingtoneDownloadRootModel;
import com.livewallpaper.ringtones.callertune.Model.RingtoneFavouriteRootModel;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.SingletonClasses.MyApplication;
import com.livewallpaper.ringtones.callertune.Utils.Constants;
import com.livewallpaper.ringtones.callertune.Utils.Util;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SeeAllItemAdapter extends RecyclerView.Adapter<SeeAllItemAdapter.SomeCategoryViewHolder> {


    Context context;
    List<ExtraCategoryModel> data;
    String type;
    onClickInputMethod onClickInputMethod;
    boolean isKeyboardFav;
    boolean isRingtoneFav;
    boolean isRingtoneDownloaded;
    public SeeAllItemAdapter(Context context, List<ExtraCategoryModel> data, String type, onClickInputMethod onClickInputMethod){
        this.context = context;
        this.data = data;
        this.type = type;
        this.onClickInputMethod = onClickInputMethod;
    }

    @NonNull
    @Override
    public SomeCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (type.equals("wallpaper")){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaper_item, parent, false);
            return new SomeCategoryViewHolder(view);
        } else if (type.equals("keyboard")) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_keybaord_layout, parent, false);
            return new SomeCategoryViewHolder(view);
        } else if (type.equals("ringtone")) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ringtone_layout, parent, false);
            return new SomeCategoryViewHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaper_item, parent, false);
        return new SomeCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SomeCategoryViewHolder holder, int position) {


        if(type.equals("wallpaper")){
            holder.iv_cat_bg.setImageResource(android.R.color.transparent);
            Glide.with(context)
                    .asBitmap()
                    .apply(new RequestOptions().override(920, 600))
                    .load(data.get(holder.getAdapterPosition()).getCatPreivewImageUrl())
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Bitmap> target, boolean isFirstResource) {
                            return true;
                        }

                        @Override
                        public boolean onResourceReady(@NonNull Bitmap resource, @NonNull Object model, Target<Bitmap> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    /*Glide.with(context)
                                            .load(resource)
                                                    .into(holder.iv_cat_bg);*/
                                    holder.iv_cat_bg.setImageBitmap(resource);
                                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            onClickInputMethod.onWallpaperClick(resource, data.get(holder.getAdapterPosition()).getCatPreivewImageUrl());

                                        }
                                    });
                                }
                            });
                            return true;
                        }
                    }).submit();
        }
        else if (type.equals("keyboard")) {
            Log.d("dataisnotshowing", "onBindViewHolder: "+ data.get(holder.getAdapterPosition()).getCatPreivewImageUrl());
            holder.tv_title.setText(data.get(holder.getAdapterPosition()).getCatName()+" Keyboard");
            holder.iv_cat_bg.setImageResource(android.R.color.transparent);

            Glide.with(context)
                    .asBitmap()
                    .apply(new RequestOptions().override(600, 300))
                    .load(data.get(holder.getAdapterPosition()).getCatPreivewImageUrl())
                    .addListener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(@NonNull Bitmap resource, @NonNull Object model, Target<Bitmap> target, @NonNull DataSource dataSource, boolean isFirstResource) {

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    holder.iv_cat_bg.setImageBitmap(resource);

                                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {


                                            String arrayStr = MyApplication.getPreferences().getString(KEYBOARD_FAV, "");
                                            if(!arrayStr.isEmpty()){
                                                if(arrayStr.contains(data.get(holder.getAdapterPosition()).getCatPreivewImageUrl())){
                                                    isKeyboardFav = true;
                                                }else {
                                                    isKeyboardFav = false;
                                                }
                                            }

                                            BottomSheetDialog dialog = new BottomSheetDialog(context);
                                            dialog.setContentView(R.layout.keybaord_bottom_sheet);
                                            dialog.setCancelable(true);
                                            View view = dialog.findViewById(R.id.include);
                                            ImageView iv_bg = view.findViewById(R.id.iv_bg_keyboard);
                                            TextView tv_title = dialog.findViewById(R.id.tv_title);
                                            Glide.with(context)
                                                    .load(resource)
                                                    .into(iv_bg);
                                            ImageView iv_close = dialog.findViewById(R.id.iv_close);
                                            CardView cv_apply = dialog.findViewById(R.id.cv_apply);
                                            ImageView iv_fav = dialog.findViewById(R.id.iv_favourite);
                                            tv_title.setText(data.get(holder.getAdapterPosition()).getCatName()+" Keyboard");
                                            if(isKeyboardFav){
                                                iv_fav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.favourite_ic_filled));
                                            }else{
                                                iv_fav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.favourite_ic));
                                            }
                                            iv_fav.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if(isKeyboardFav){
                                                        iv_fav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.favourite_ic));
                                                        String arrayStr = MyApplication.getPreferences().getString(KEYBOARD_FAV, "");
                                                        KeyboardFavouriteRootModel model = new Gson().fromJson(arrayStr, KeyboardFavouriteRootModel.class);
                                                        ArrayList<KeyboardData> value = new ArrayList<>();
                                                        value.addAll(model.getData());

                                                        KeyboardData keyboardData = new KeyboardData(
                                                                data.get(holder.getAdapterPosition()).getCatName()+" Keyboard",
                                                                data.get(holder.getAdapterPosition()).getCatPreivewImageUrl());
/*                                                        for(KeyboardData keyboardData1 : value){
                                                            if(keyboardData1)
                                                        }*/
                                                        value.removeIf(keyboardData1 -> keyboardData1.getKeyboardBgUrl().equals(keyboardData.getKeyboardBgUrl()));
                                                        model.setData(value);
                                                        String convertedModel = new Gson().toJson(model);
                                                        MyApplication.getPreferences().putString(KEYBOARD_FAV, convertedModel);
                                                        if(activity.getComponentName().getClassName().contains("FavouritesActivity")){
                                                            data.remove(holder.getAdapterPosition());
                                                            notifyItemRemoved(holder.getAdapterPosition());
                                                            dialog.dismiss();
                                                        }
                                                        isKeyboardFav = false;
                                                    }else{
                                                        iv_fav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.favourite_ic_filled));
                                                        String arrayStr = MyApplication.getPreferences().getString(KEYBOARD_FAV, "");
                                                        if(arrayStr.isEmpty()){

                                                            ArrayList<KeyboardData> value = new ArrayList<>();

                                                            KeyboardFavouriteRootModel model = new KeyboardFavouriteRootModel(value);

                                                            String convertedModel = new Gson().toJson(model);
                                                            MyApplication.getPreferences().putString(KEYBOARD_FAV, convertedModel);
                                                            arrayStr = MyApplication.getPreferences().getString(KEYBOARD_FAV, "");
                                                        }
                                                        KeyboardFavouriteRootModel model = new Gson().fromJson(arrayStr, KeyboardFavouriteRootModel.class);
                                                        ArrayList<KeyboardData> value = new ArrayList<>();
                                                        value.addAll(model.getData());
                                                        value.add(new KeyboardData(
                                                                data.get(holder.getAdapterPosition()).getCatName()+" Keyboard",
                                                                data.get(holder.getAdapterPosition()).getCatPreivewImageUrl()
                                                        ));
                                                        model.setData(value);
                                                        String convertedModel = new Gson().toJson(model);
                                                        MyApplication.getPreferences().putString(KEYBOARD_FAV, convertedModel);
/*                                                        if(activity.getComponentName().getClassName().contains("FavouritesActivity")){
                                                            data.add(new ExtraCategoryModel(
                                                                    data.get(holder.getAdapterPosition()).getCatName(),
                                                                    "",
                                                                    "",
                                                                    data.get(holder.getAdapterPosition()).getCatPreivewImageUrl()
                                                            ));
//                                                            notifyDataSetChanged();
                                                        }*/
                                                        isKeyboardFav = true;
                                                    }
                                                }
                                            });

                                            cv_apply.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                        if(!isKeyboardEnabled()){
                                                        openKeyboardSettings();
                                                    }else{
                                                        Global.showAlertProgressDialog(activity);
                                                        String filename = "savedBitmapBackground.png";

                                                        try {
                                                            FileOutputStream stream = context.openFileOutput(filename, Context.MODE_PRIVATE);
                                                            resource.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                                            MyApplication.getPreferences().putString(Constants.KEYBOARD_BG, filename);
                                                            stream.close();
                                                        }catch (Exception e) {
                                                            Global.hideAlertProgressDialog();
                                                            Toast.makeText(context, "Failed To Set Keyboard", Toast.LENGTH_SHORT).show();
                                                            e.printStackTrace();
                                                        }
                                                        if(!isThisKeyboardSetAsDefaultIME(context)){
                                                            Global.hideAlertProgressDialog();
                                                            openKeyboardChooserSettings();
                                                        }else{

                                                            Global.hideAlertProgressDialog();
                                                            if(checkReadPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
                                                                onClickInputMethod.onClickIdentifyKeyboard();
                                                            }else{
                                                                dialog.dismiss();
                                                                Toast.makeText(context, "Keyboard Set Successfully", Toast.LENGTH_SHORT).show();
                                                            }


                                                        }
//                                                        dialog.dismiss();

//                                                        openKeyboardChooserSettings();
//                                                        isKeyboardEnabled();
                                                    }



                                                }
                                            });

/*                                            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                                @Override
                                                public void onDismiss(DialogInterface dialog) {
                                                    notifyDataSetChanged();
                                                }
                                            });*/

                                            iv_close.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.dismiss();
                                                }
                                            });

                                            dialog.show();

                                        }
                                    });
                                    holder.clickable.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            holder.itemView.performClick();
                                        }
                                    });
                                }
                            });


                            return true;
                        }
                    }).submit();
        }
        else if (type.equals("ringtone")) {


            String arrayStr = MyApplication.getPreferences().getString(RINGTONE_FAV, "");
            if(!arrayStr.isEmpty()){
                if(arrayStr.contains(data.get(holder.getAdapterPosition()).getRingtoneUrl())){
                    isRingtoneFav = true;
                    holder.iv_favourite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.favourite_ic_filled));
                }else {
                    isRingtoneFav = false;
                    holder.iv_favourite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.favourite_ic));
                }
            }

            String downloadString = MyApplication.getPreferences().getString(DOWNALOD_RINGTONE, "");
            if(!downloadString.isEmpty() && downloadString.contains(data.get(holder.getAdapterPosition()).getCatName())){
                RingtoneDownloadRootModel downlaodmodel = new Gson().fromJson(downloadString, RingtoneDownloadRootModel.class);


                int downloadIndex2 = 0;
                for(int i = 0 ; i < downlaodmodel.getDownloadModels().size(); i++){
                    if(downlaodmodel.getDownloadModels().get(i).getModel().getCatName().equals(data.get(holder.getAdapterPosition()).getCatName())){
                        downloadIndex2 = i;
                        break;
                    }
                }

                    String path = downlaodmodel.getDownloadModels().get(downloadIndex2).getSavedPath();
                    File downloadFile = new File(path);
                    if(downloadFile.exists()){
                        holder.iv_download.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.downloaded));
                        isRingtoneDownloaded = true;
                    }else{
                        holder.iv_download.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.download));
                        ArrayList<RingtoneDownloadModel> ringtoneDownloadModels = new ArrayList<>();
                        ringtoneDownloadModels.addAll(downlaodmodel.getDownloadModels());
                        ringtoneDownloadModels.remove(holder.getAdapterPosition());
                        downlaodmodel.setDownloadModels(ringtoneDownloadModels);
                        String convertedData = new Gson().toJson(downlaodmodel);
                        MyApplication.getPreferences().putString(DOWNALOD_RINGTONE, convertedData);
                        isRingtoneDownloaded = false;
                    }
                /*else{
                    holder.iv_download.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.download));
                }*/
            }else{
                isRingtoneDownloaded = false;
            }


            holder.tv_song_name.setText(data.get(holder.getAdapterPosition()).getCatName());
            holder.tv_author.setText(data.get(holder.getAdapterPosition()).getCatAuthor());
            holder.tv_sec.setText(data.get(holder.getAdapterPosition()).getCatTime());
            Glide.with(context)
                    .asBitmap()
                    .load(data.get(holder.getAdapterPosition()).getRingtoneImg())
                    .into(holder.tv_img);

            holder.tv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AdUtils.showInterstitialAd(com.adsmodule.api.adsModule.utils.Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                        Intent intent = new Intent(context, RingtoneActivity.class);
                        intent.putExtra("category",data.get(holder.getAdapterPosition()).getCategory());
                        intent.putExtra("pos", holder.getAdapterPosition());
                        context.startActivity(intent);
                    });


                }
            });

            holder.ll_selector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.tv_img.performClick();
                }
            });

            if(!isRingtoneDownloaded){
                holder.iv_download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                int count;
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Util.showDownloadDialog(context);
                                    }
                                });
                                try {
                                    URL url = new URL(data.get(holder.getAdapterPosition()).getRingtoneUrl());
                                    URLConnection connection = url.openConnection();
                                    connection.connect();
                                    InputStream input = new BufferedInputStream(url.openStream(), 8192);

                                    File dir = new File(RINGTONE_DOWNLOAD);
                                    if(!dir.exists()){
                                        dir.mkdir();
                                    }

                                    File file = new File(RINGTONE_DOWNLOAD, "ringtone_" + System.currentTimeMillis() + ".mp3");
                                    OutputStream output = null;
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                        output = Files.newOutputStream(file.toPath());
                                    }else {
                                        output = new FileOutputStream(file);

                                    }

                                    byte data_byte[] = new byte[1024];

                                    while ((count = input.read(data_byte)) != -1) {
                                        output.write(data_byte, 0, count);
                                    }

                                    output.flush();
                                    output.close();
                                    input.close();
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Util.hideDownloadDialog();
                                            String arrayStr = MyApplication.getPreferences().getString(DOWNALOD_RINGTONE, "");
                                            if(arrayStr.isEmpty()){
                                                ArrayList<RingtoneDownloadModel> value = new ArrayList<>();
                                                RingtoneDownloadRootModel model = new RingtoneDownloadRootModel(value);
                                                String convertedModel = new Gson().toJson(model);
                                                MyApplication.getPreferences().putString(DOWNALOD_RINGTONE, convertedModel);
                                                arrayStr = MyApplication.getPreferences().getString(DOWNALOD_RINGTONE, "");
                                            }
                                            RingtoneDownloadRootModel rootModel = new Gson().fromJson(arrayStr, RingtoneDownloadRootModel.class);
                                            ArrayList<RingtoneDownloadModel> value = new ArrayList<>();
                                            value.addAll(rootModel.getDownloadModels());
                                            value.add(new RingtoneDownloadModel(
                                                    data.get(holder.getAdapterPosition()),
                                                    data.get(holder.getAdapterPosition()).getCatName(),
                                                    file.getName(),
                                                    file.getAbsolutePath()
                                            ));
                                            rootModel.setDownloadModels(value);
                                            String convertedModel = new Gson().toJson(rootModel);
                                            MyApplication.getPreferences().putString(DOWNALOD_RINGTONE, convertedModel);
                                            notifyItemChanged(holder.getAdapterPosition());
                                            Toast.makeText(context, "Ringtone Saved", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } catch (Exception e) {
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Util.hideDownloadDialog();
                                            Toast.makeText(context, "Ringtone Failed To Save", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    Log.e("Error: ", e.getMessage());
                                }
                            }
                        });
                    }
                });
            }else{
                holder.iv_download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "Ringtone Already Downloaded", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            String downloadString2 = MyApplication.getPreferences().getString(DOWNALOD_RINGTONE, "");
            if(!downloadString2.isEmpty() && downloadString2.contains(data.get(holder.getAdapterPosition()).getCatName())){
                RingtoneDownloadRootModel downlaodmodel2 = new Gson().fromJson(downloadString2, RingtoneDownloadRootModel.class);
                int downloadIndex = 0;
                for(int i = 0 ; i < downlaodmodel2.getDownloadModels().size(); i++){
                    if(downlaodmodel2.getDownloadModels().get(i).getModel().getCatName().equals(data.get(holder.getAdapterPosition()).getCatName())){
                        downloadIndex = i;
                        break;
                    }
                }

                    String path = downlaodmodel2.getDownloadModels().get(downloadIndex).getSavedPath();
                    File downloadFile = new File(path);
                    if(!downloadFile.exists()){
                        holder.iv_favourite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String arrayStr2 = MyApplication.getPreferences().getString(RINGTONE_FAV, "");
                                if(arrayStr2.contains(data.get(holder.getAdapterPosition()).getRingtoneUrl())){
                                    isRingtoneFav = true;
                                    holder.iv_favourite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.favourite_ic_filled));
                                }else {
                                    isRingtoneFav = false;
                                    holder.iv_favourite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.favourite_ic));
                                }
                                if(isRingtoneFav){
                                    holder.iv_favourite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.favourite_ic));
                                    String arrayStr = MyApplication.getPreferences().getString(RINGTONE_FAV, "");
                                    RingtoneFavouriteRootModel model = new Gson().fromJson(arrayStr, RingtoneFavouriteRootModel.class);
                                    ArrayList<RingtoneData> value = new ArrayList<>();
                                    value.addAll(model.getData());

                                    RingtoneData ringtoneData = new RingtoneData(
                                            data.get(holder.getAdapterPosition()).getCatName(),
                                            data.get(holder.getAdapterPosition()).getCatAuthor(),
                                            data.get(holder.getAdapterPosition()).getCatTime(),
                                            data.get(holder.getAdapterPosition()).getRingtoneImg(),
                                            data.get(holder.getAdapterPosition()).getRingtoneUrl()
                                    );
/*                                                        for(KeyboardData keyboardData1 : value){
                                                            if(keyboardData1)
                                                        }*/
                                    value.removeIf(ringtoneData1 -> ringtoneData1.getRingtoneUrl().equals(ringtoneData.getRingtoneUrl()));
                                    model.setData(value);
                                    String convertedModel = new Gson().toJson(model);
                                    MyApplication.getPreferences().putString(RINGTONE_FAV, convertedModel);
                                    if(activity.getComponentName().getClassName().contains("FavouritesActivity")){
                                        data.remove(holder.getAdapterPosition());
                                        notifyItemRemoved(holder.getAdapterPosition());
                                    }
                                    isRingtoneFav = false;
                                }else{
                                    holder.iv_favourite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.favourite_ic_filled));
                                    String arrayStr = MyApplication.getPreferences().getString(RINGTONE_FAV, "");
                                    if(arrayStr.isEmpty()){

                                        ArrayList<RingtoneData> value = new ArrayList<>();

                                        RingtoneFavouriteRootModel model = new RingtoneFavouriteRootModel(value);

                                        String convertedModel = new Gson().toJson(model);
                                        MyApplication.getPreferences().putString(RINGTONE_FAV, convertedModel);
                                        arrayStr = MyApplication.getPreferences().getString(RINGTONE_FAV, "");
                                    }
                                    RingtoneFavouriteRootModel model = new Gson().fromJson(arrayStr, RingtoneFavouriteRootModel.class);
                                    ArrayList<RingtoneData> value = new ArrayList<>();
                                    value.addAll(model.getData());
                                    value.add(new RingtoneData(
                                            data.get(holder.getAdapterPosition()).getCatName(),
                                            data.get(holder.getAdapterPosition()).getCatAuthor(),
                                            data.get(holder.getAdapterPosition()).getCatTime(),
                                            data.get(holder.getAdapterPosition()).getRingtoneImg(),
                                            data.get(holder.getAdapterPosition()).getRingtoneUrl()
                                    ));
                                    model.setData(value);
                                    String convertedModel = new Gson().toJson(model);
                                    MyApplication.getPreferences().putString(RINGTONE_FAV, convertedModel);

                                    isRingtoneFav = true;
                                }
                            }
                        });
                    }
                /*else{
                    holder.iv_favourite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String arrayStr2 = MyApplication.getPreferences().getString(RINGTONE_FAV, "");
                            if(arrayStr2.contains(data.get(holder.getAdapterPosition()).getRingtoneUrl())){
                                isRingtoneFav = true;
                                holder.iv_favourite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.favourite_ic_filled));
                            }else {
                                isRingtoneFav = false;
                                holder.iv_favourite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.favourite_ic));
                            }
                            if(isRingtoneFav){
                                holder.iv_favourite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.favourite_ic));
                                String arrayStr = MyApplication.getPreferences().getString(RINGTONE_FAV, "");
                                RingtoneFavouriteRootModel model = new Gson().fromJson(arrayStr, RingtoneFavouriteRootModel.class);
                                ArrayList<RingtoneData> value = new ArrayList<>();
                                value.addAll(model.getData());

                                RingtoneData ringtoneData = new RingtoneData(
                                        data.get(holder.getAdapterPosition()).getCatName(),
                                        data.get(holder.getAdapterPosition()).getCatAuthor(),
                                        data.get(holder.getAdapterPosition()).getCatTime(),
                                        data.get(holder.getAdapterPosition()).getRingtoneImg(),
                                        data.get(holder.getAdapterPosition()).getRingtoneUrl()
                                );
*//*                                                        for(KeyboardData keyboardData1 : value){
                                                            if(keyboardData1)
                                                        }*//*
                                value.removeIf(ringtoneData1 -> ringtoneData1.getRingtoneUrl().equals(ringtoneData.getRingtoneUrl()));
                                model.setData(value);
                                String convertedModel = new Gson().toJson(model);
                                MyApplication.getPreferences().putString(RINGTONE_FAV, convertedModel);
                                if(activity.getComponentName().getClassName().contains("FavouritesActivity")){
                                    data.remove(holder.getAdapterPosition());
                                    notifyItemRemoved(holder.getAdapterPosition());
                                }
                                isRingtoneFav = false;
                            }else{
                                holder.iv_favourite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.favourite_ic_filled));
                                String arrayStr = MyApplication.getPreferences().getString(RINGTONE_FAV, "");
                                if(arrayStr.isEmpty()){

                                    ArrayList<RingtoneData> value = new ArrayList<>();

                                    RingtoneFavouriteRootModel model = new RingtoneFavouriteRootModel(value);

                                    String convertedModel = new Gson().toJson(model);
                                    MyApplication.getPreferences().putString(RINGTONE_FAV, convertedModel);
                                    arrayStr = MyApplication.getPreferences().getString(RINGTONE_FAV, "");
                                }
                                RingtoneFavouriteRootModel model = new Gson().fromJson(arrayStr, RingtoneFavouriteRootModel.class);
                                ArrayList<RingtoneData> value = new ArrayList<>();
                                value.addAll(model.getData());
                                value.add(new RingtoneData(
                                        data.get(holder.getAdapterPosition()).getCatName(),
                                        data.get(holder.getAdapterPosition()).getCatAuthor(),
                                        data.get(holder.getAdapterPosition()).getCatTime(),
                                        data.get(holder.getAdapterPosition()).getRingtoneImg(),
                                        data.get(holder.getAdapterPosition()).getRingtoneUrl()
                                ));
                                model.setData(value);
                                String convertedModel = new Gson().toJson(model);
                                MyApplication.getPreferences().putString(RINGTONE_FAV, convertedModel);

                                isRingtoneFav = true;
                            }
                        }
                    });
                }*/

            }
            holder.iv_favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String arrayStr2 = MyApplication.getPreferences().getString(RINGTONE_FAV, "");
                    if(arrayStr2.contains(data.get(holder.getAdapterPosition()).getRingtoneUrl())){
                        isRingtoneFav = true;
                        holder.iv_favourite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.favourite_ic_filled));
                    }else {
                        isRingtoneFav = false;
                        holder.iv_favourite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.favourite_ic));
                    }
                    if(isRingtoneFav){
                        holder.iv_favourite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.favourite_ic));
                        String arrayStr = MyApplication.getPreferences().getString(RINGTONE_FAV, "");
                        RingtoneFavouriteRootModel model = new Gson().fromJson(arrayStr, RingtoneFavouriteRootModel.class);
                        ArrayList<RingtoneData> value = new ArrayList<>();
                        value.addAll(model.getData());

                        RingtoneData ringtoneData = new RingtoneData(
                                data.get(holder.getAdapterPosition()).getCatName(),
                                data.get(holder.getAdapterPosition()).getCatAuthor(),
                                data.get(holder.getAdapterPosition()).getCatTime(),
                                data.get(holder.getAdapterPosition()).getRingtoneImg(),
                                data.get(holder.getAdapterPosition()).getRingtoneUrl()
                        );
/*                                                        for(KeyboardData keyboardData1 : value){
                                                            if(keyboardData1)
                                                        }*/
                        value.removeIf(ringtoneData1 -> ringtoneData1.getRingtoneUrl().equals(ringtoneData.getRingtoneUrl()));
                        model.setData(value);
                        String convertedModel = new Gson().toJson(model);
                        MyApplication.getPreferences().putString(RINGTONE_FAV, convertedModel);
                        if(activity.getComponentName().getClassName().contains("FavouritesActivity")){
                            data.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                        }
                        isRingtoneFav = false;
                    }else{
                        holder.iv_favourite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.favourite_ic_filled));
                        String arrayStr = MyApplication.getPreferences().getString(RINGTONE_FAV, "");
                        if(arrayStr.isEmpty()){

                            ArrayList<RingtoneData> value = new ArrayList<>();

                            RingtoneFavouriteRootModel model = new RingtoneFavouriteRootModel(value);

                            String convertedModel = new Gson().toJson(model);
                            MyApplication.getPreferences().putString(RINGTONE_FAV, convertedModel);
                            arrayStr = MyApplication.getPreferences().getString(RINGTONE_FAV, "");
                        }
                        RingtoneFavouriteRootModel model = new Gson().fromJson(arrayStr, RingtoneFavouriteRootModel.class);
                        ArrayList<RingtoneData> value = new ArrayList<>();
                        value.addAll(model.getData());
                        value.add(new RingtoneData(
                                data.get(holder.getAdapterPosition()).getCatName(),
                                data.get(holder.getAdapterPosition()).getCatAuthor(),
                                data.get(holder.getAdapterPosition()).getCatTime(),
                                data.get(holder.getAdapterPosition()).getRingtoneImg(),
                                data.get(holder.getAdapterPosition()).getRingtoneUrl()
                        ));
                        model.setData(value);
                        String convertedModel = new Gson().toJson(model);
                        MyApplication.getPreferences().putString(RINGTONE_FAV, convertedModel);

                        isRingtoneFav = true;
                    }
                }
            });


            if(activity.getComponentName().getClassName().contains("DownloadActivity")){
                holder.iv_favourite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.favourite_ic));
                holder.iv_favourite.setAlpha(0.3f);
                holder.iv_favourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "Can not Favourite Downloaded File", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }


    }

    private int checkReadPermission(String permission1) {
        return context.checkSelfPermission(permission1);
    }


    private boolean isKeyboardEnabled() {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
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
        context.startActivity(intent);
    }

    private void openKeyboardChooserSettings() {
        InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.showInputMethodPicker();
    }


    @Override
    public int getItemCount() {
        if(activity.getComponentName().getClassName().contains("SeeAllActivity") || activity.getComponentName().getClassName().contains("DownloadActivity") || activity.getComponentName().getClassName().contains("FavouritesActivity")){
            return data.size();
        }
        return Math.min(data.size(), 8);
    }

    static class SomeCategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_cat_bg, tv_img, iv_download, clickable, iv_favourite;
        TextView tv_title, tv_desc, tv_song_name, tv_author, tv_sec;
        LinearLayout ll_selector;

        public SomeCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_cat_bg = itemView.findViewById(R.id.iv_cat);
            tv_desc = itemView.findViewById(R.id.tv_dec_);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_song_name = itemView.findViewById(R.id.tv_song_name);
            tv_author = itemView.findViewById(R.id.tv_author);
            tv_sec = itemView.findViewById(R.id.tv_sec);
            tv_img = itemView.findViewById(R.id.tv_img);
            iv_download = itemView.findViewById(R.id.iv_download);
            clickable = itemView.findViewById(R.id.iv_clickable);
            iv_favourite = itemView.findViewById(R.id.iv_favourite);
            ll_selector = itemView.findViewById(R.id.ll_selector);
        }
    }

    public interface onClickInputMethod{
        void onClickIdentifyKeyboard();
        void onWallpaperClick(Bitmap resource, String url);
    }
}
