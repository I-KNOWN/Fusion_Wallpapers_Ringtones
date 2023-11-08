package com.livewallpaper.ringtones.callertune.Adapter;

import static com.livewallpaper.ringtones.callertune.SingletonClasses.AppOpenAds.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.adsmodule.api.adsModule.AdUtils;
import com.adsmodule.api.adsModule.utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.livewallpaper.ringtones.callertune.Activity.SeeAllActivity;
import com.livewallpaper.ringtones.callertune.Model.CategoryModel;
import com.livewallpaper.ringtones.callertune.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SomeCategoryAdapter extends RecyclerView.Adapter<SomeCategoryAdapter.SomeCategoryViewHolder> {


    Context context;
    ArrayList<CategoryModel> data;
    String type;
    public SomeCategoryAdapter(Context context, ArrayList<CategoryModel> data, String type){
        this.context = context;
        this.data = data;
        this.type = type;
    }

    @NonNull
    @Override
    public SomeCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new SomeCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SomeCategoryViewHolder holder, int position) {
        CategoryModel model = data.get(holder.getAdapterPosition());
        Glide.with(context)
                .load(model.getCategoryImageUrl())
                .apply(new RequestOptions().override(920, 600))
                .into(holder.iv_cat_bg);
//        holder.iv_cat_bg.setImageDrawable(ContextCompat.getDrawable(context, model.getCategoryImage()));
        holder.tv_title.setText(model.getCategoryName());
        holder.tv_desc.setText(model.getCategoryDesc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    Intent intent = new Intent(context, SeeAllActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("category", model.getCategoryName());
                    context.startActivity(intent);
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
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
