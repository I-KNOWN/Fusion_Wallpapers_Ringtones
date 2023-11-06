package com.livewallpaper.ringtones.callertune.Adapter;

import static com.livewallpaper.ringtones.callertune.SingletonClasses.AppOpenAds.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.adsmodule.api.adsModule.AdUtils;
import com.adsmodule.api.adsModule.utils.Constants;
import com.adsmodule.api.adsModule.utils.Global;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.livewallpaper.ringtones.callertune.Activity.WallpaperViewerActivity;
import com.livewallpaper.ringtones.callertune.Model.CategoryModel;
import com.livewallpaper.ringtones.callertune.Model.ImageModel;
import com.livewallpaper.ringtones.callertune.R;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class WallpaperItemAdapter extends RecyclerView.Adapter<WallpaperItemAdapter.SomeCategoryViewHolder> {


    Context context;
    List<String> data;

    public WallpaperItemAdapter(Context context, List<String> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public SomeCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaper_item, parent, false);
        return new SomeCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SomeCategoryViewHolder holder, int position) {
        Glide.with(context)
                .asBitmap()
                        .load(data.get(holder.getAdapterPosition()))
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
                                                        Global.hideAlertProgressDialog();
                                                        AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                                                            Intent intent =new Intent(context, WallpaperViewerActivity.class);
                                                            intent.putExtra("filename", filename);
                                                            intent.putExtra("url", data.get(holder.getAdapterPosition()));
                                                            intent.putExtra("activity", "");
                                                            if(activity.getComponentName().getClassName().contains("DownloadActivity")){
                                                                intent.putExtra("activity", "download");
                                                            }
                                                            context.startActivity(intent);
                                                        });



                                                    }
                                                });
                                             }
                                         });
                                        return true;
                                    }
                                }).submit();




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
