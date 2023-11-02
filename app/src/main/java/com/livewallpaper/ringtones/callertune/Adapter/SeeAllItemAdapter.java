package com.livewallpaper.ringtones.callertune.Adapter;

import static com.livewallpaper.ringtones.callertune.SingletonClasses.AppOpenAds.activity;
import static com.livewallpaper.ringtones.callertune.Utils.Constants.RINGTONE_DOWNLOAD;
import static com.livewallpaper.ringtones.callertune.Utils.Constants.RINGTONE_PATH;

import android.content.ComponentName;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

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
import com.livewallpaper.ringtones.callertune.Activity.AllCategoriesActivity;
import com.livewallpaper.ringtones.callertune.Activity.RingtoneActivity;
import com.livewallpaper.ringtones.callertune.Activity.WallpaperViewerActivity;
import com.livewallpaper.ringtones.callertune.Model.ExtraCategoryModel;
import com.livewallpaper.ringtones.callertune.Model.ImageModel;
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
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SeeAllItemAdapter extends RecyclerView.Adapter<SeeAllItemAdapter.SomeCategoryViewHolder> {


    Context context;
    List<ExtraCategoryModel> data;
    String type;
    onClickInputMethod onClickInputMethod;

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
        }
        else if (type.equals("keyboard")) {
            Log.d("dataisnotshowing", "onBindViewHolder: "+ data.get(holder.getAdapterPosition()));
            holder.tv_title.setText(data.get(holder.getAdapterPosition()).getCatName()+" Keyboard");
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
                                            TextView tv_title = dialog.findViewById(R.id.tv_title);
                                            Glide.with(context)
                                                    .load(resource)
                                                    .into(iv_bg);
                                            ImageView iv_close = dialog.findViewById(R.id.iv_close);
                                            CardView cv_apply = dialog.findViewById(R.id.cv_apply);
                                            tv_title.setText(data.get(holder.getAdapterPosition()).getCatName()+" Keyboard");

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
                                                            if(checkReadPermission(android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
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
        else if (type.equals("ringtone")) {
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
                    Intent intent = new Intent(context, RingtoneActivity.class);
                    intent.putExtra("category",data.get(holder.getAdapterPosition()).getCategory());
                    intent.putExtra("pos", holder.getAdapterPosition());
                    context.startActivity(intent);
                }
            });

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

                                byte data[] = new byte[1024];

                                while ((count = input.read(data)) != -1) {
                                    output.write(data, 0, count);
                                }

                                output.flush();
                                output.close();
                                input.close();
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Util.hideDownloadDialog();
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
        return Math.min(data.size(), 8);
    }

    static class SomeCategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_cat_bg, tv_img, iv_download;
        TextView tv_title, tv_desc, tv_song_name, tv_author, tv_sec;

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
        }
    }

    public interface onClickInputMethod{
        void onClickIdentifyKeyboard();
    }
}
