package com.livewallpaper.ringtones.callertune.Adapter;

import static com.livewallpaper.ringtones.callertune.SingletonClasses.AppOpenAds.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.adsmodule.api.adsModule.utils.Global;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.livewallpaper.ringtones.callertune.Activity.WallpaperViewerActivity;
import com.livewallpaper.ringtones.callertune.Model.ExtraCategoryModel;
import com.livewallpaper.ringtones.callertune.Model.ImageModel;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.SingletonClasses.MyApplication;
import com.livewallpaper.ringtones.callertune.Utils.Constants;

import java.io.FileOutputStream;
import java.util.List;

public class SeeAllItemAdapter extends RecyclerView.Adapter<SeeAllItemAdapter.SomeCategoryViewHolder> {


    Context context;
    List<ExtraCategoryModel> data;
    String type;
    public SeeAllItemAdapter(Context context, List<ExtraCategoryModel> data, String type){
        this.context = context;
        this.data = data;
        this.type = type;
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

        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaper_item, parent, false);
        return new SomeCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SomeCategoryViewHolder holder, int position) {


        if(type.equals("wallpaper")){
            Glide.with(context)
                    .asBitmap()
                    .load(data.get(holder.getAdapterPosition()).getCatPreivewImageUrl())
                    .addListener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Bitmap> target, boolean isFirstResource) {
                            return true;
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

                                            Global.showAlertProgressDialog(activity);

                                            String filename = "bitmap.png";

                                            try {
                                                FileOutputStream stream = context.openFileOutput(filename, Context.MODE_PRIVATE);
                                                resource.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                                stream.close();
                                            }catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            Intent intent =new Intent(context, WallpaperViewerActivity.class);
                                            intent.putExtra("filename", filename);
                                            context.startActivity(intent);
                                            Global.hideAlertProgressDialog();
                                        }
                                    });
                                }
                            });
                            return true;
                        }
                    }).submit();
        } else if (type.equals("keyboard")) {
            Log.d("dataisnotshowing", "onBindViewHolder: "+ data.get(holder.getAdapterPosition()));
            holder.tv_title.setText(data.get(holder.getAdapterPosition()).getCatName());
            Glide.with(context)
                    .asBitmap()
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
                                            BottomSheetDialog dialog = new BottomSheetDialog(context);
                                            dialog.setContentView(R.layout.keybaord_bottom_sheet);
                                            dialog.setCancelable(true);
                                            View view = dialog.findViewById(R.id.include);
                                            ImageView iv_bg = view.findViewById(R.id.iv_bg_keyboard);
                                            Glide.with(context)
                                                    .load(resource)
                                                    .into(iv_bg);
                                            ImageView iv_close = dialog.findViewById(R.id.iv_close);
                                            CardView cv_apply = dialog.findViewById(R.id.cv_apply);
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
                                                            e.printStackTrace();
                                                        }
                                                        openKeyboardChooserSettings();
                                                        Global.hideAlertProgressDialog();
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

                                }
                            });


                            return true;
                        }
                    }).submit();
        }


    }

    private boolean isKeyboardEnabled() {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        List<InputMethodInfo> enabledInputMethodIds = inputMethodManager.getEnabledInputMethodList();
        for(InputMethodInfo info: enabledInputMethodIds){
            if(info.getId().contains("com.livewallpaper.ringtones.callertune/.Keyboard.CustomKeyboard")){
                return true;
            }
        }
        return false;
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
        return Math.min(data.size(), 8);
    }

    static class SomeCategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_cat_bg;
        TextView tv_title, tv_desc;

        public SomeCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_cat_bg = itemView.findViewById(R.id.iv_cat);
            tv_desc = itemView.findViewById(R.id.tv_dec_);
            tv_title = itemView.findViewById(R.id.tv_title);

        }
    }
}
