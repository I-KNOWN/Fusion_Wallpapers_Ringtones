package com.livewallpaper.ringtones.callertune.Adapter;

import static com.livewallpaper.ringtones.callertune.SingletonClasses.AppOpenAds.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.livewallpaper.ringtones.callertune.Activity.WallpaperActivity;
import com.livewallpaper.ringtones.callertune.Activity.WallpaperViewerActivity;
import com.livewallpaper.ringtones.callertune.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class WallpaperPreviewAdapter extends RecyclerView.Adapter<WallpaperPreviewAdapter.PreviewViewHolder> {


    Context context;
    Bitmap bg;
    onPreviewClick onPreviewClick;
    RoundedImageView iv_bg;
    public WallpaperPreviewAdapter(Context context, Bitmap bg, onPreviewClick onPreviewClick){
        this.context = context;
        this.bg = bg;
        this.onPreviewClick = onPreviewClick;
    }

    @NonNull
    @Override
    public PreviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaper_preview_item, parent, false);
        return new PreviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewViewHolder holder, int position) {
        iv_bg = holder.iv_bg;
        if(bg != null){
            Glide.with(context)
                    .asBitmap()
                    .load(bg)
                    .into(holder.iv_bg);
        }
        if(holder.getAdapterPosition() == 0){
            holder.iv_top.setBackgroundResource(R.drawable.mockup_home);
            holder.iv_left.setVisibility(View.GONE);
            holder.iv_right.setVisibility(View.VISIBLE);
            holder.iv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPreviewClick.onNext(holder.getAdapterPosition());
                }
            });
        }else {
            holder.iv_top.setBackgroundResource(R.drawable.mockup_lock);
            holder.iv_left.setVisibility(View.VISIBLE);
            holder.iv_right.setVisibility(View.GONE);
            holder.iv_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPreviewClick.onNext(holder.getAdapterPosition());
                }
            });
        }

    }
    public void setBitmap(Bitmap bg){
        this.bg = bg;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    static class PreviewViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_top, iv_right, iv_left;
        RoundedImageView iv_bg;
        public PreviewViewHolder(@NonNull View itemView) {
            super(itemView);
            setIsRecyclable(false);
            iv_bg = itemView.findViewById(R.id.iv_bg);
            iv_top = itemView.findViewById(R.id.iv_home_top);
            iv_right = itemView.findViewById(R.id.iv_right);
            iv_left = itemView.findViewById(R.id.iv_left);
        }
    }

    public interface onPreviewClick{
        void onNext(int pos);
    }

}
