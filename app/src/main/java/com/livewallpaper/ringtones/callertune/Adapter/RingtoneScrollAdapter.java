package com.livewallpaper.ringtones.callertune.Adapter;

import static com.livewallpaper.ringtones.callertune.SingletonClasses.AppOpenAds.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.livewallpaper.ringtones.callertune.R;

import java.util.ArrayList;

public class RingtoneScrollAdapter extends RecyclerView.Adapter<RingtoneScrollAdapter.RingtoneViewHolder> {

    Context context;
    ArrayList<String> data;
    public RingtoneScrollAdapter(Context context, ArrayList<String> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RingtoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ringtone_display, parent, false);
        return new RingtoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RingtoneViewHolder holder, int position) {
        Glide.with(context)
                .asBitmap()
                .apply(new RequestOptions().override(920, 600))
                .load(data.get(holder.getAdapterPosition()))
                .addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Bitmap> target, boolean isFirstResource) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(@NonNull Bitmap resource, @NonNull Object model, Target<Bitmap> target, @NonNull DataSource dataSource, boolean isFirstResource) {

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                holder.iv_ring_bg.setImageBitmap(resource);
                            }
                        });

                        return true;
                    }
                }).submit();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void initDataAgain(ArrayList<String> data){
//        this.data.clear();
        this.data = data ;
        notifyDataSetChanged();
    }

    static class RingtoneViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_ring_bg;

        public RingtoneViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_ring_bg = itemView.findViewById(R.id.iv_ring_img);
        }
    }
}
