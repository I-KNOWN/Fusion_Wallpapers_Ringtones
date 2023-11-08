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
import androidx.recyclerview.widget.RecyclerView;

import com.adsmodule.api.adsModule.AdUtils;
import com.adsmodule.api.adsModule.utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.livewallpaper.ringtones.callertune.Activity.SeeAllActivity;
import com.livewallpaper.ringtones.callertune.Model.ExtraCategoryModel;
import com.livewallpaper.ringtones.callertune.R;

import java.util.List;

public class CircularCategoryAdapter extends RecyclerView.Adapter<CircularCategoryAdapter.CircularViewHolder> {

    Context context;
    List<ExtraCategoryModel> data;
    String type;
    public CircularCategoryAdapter(Context context, List<ExtraCategoryModel> data, String type){
        this.context = context;
        this.data = data;
        this.type = type;
    }


    @NonNull
    @Override
    public CircularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_extra_category_color, parent, false);
        return new CircularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CircularViewHolder holder, int position) {
        holder.tv_name.setText(data.get(holder.getAdapterPosition()).getCatName());
        Glide.with(context)
                .asBitmap()
                .apply(new RequestOptions().override(920, 600))
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
                                holder.iv_preview.setImageBitmap(resource);
                            }
                        });
                        return true;
                    }
                }).submit();



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    Intent intent = new Intent(context, SeeAllActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("category", data.get(holder.getAdapterPosition()).getCatName());
                    intent.putExtra("circular", true);
                    context.startActivity(intent);
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class CircularViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_preview;
        TextView tv_name;

        public CircularViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_preview = itemView.findViewById(R.id.iv_bg_preview);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }
}
