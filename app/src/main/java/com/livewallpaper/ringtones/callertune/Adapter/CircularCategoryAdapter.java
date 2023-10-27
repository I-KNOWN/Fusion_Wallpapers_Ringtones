package com.livewallpaper.ringtones.callertune.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.livewallpaper.ringtones.callertune.Model.ExtraCategoryModel;
import com.livewallpaper.ringtones.callertune.R;

import java.util.List;

public class CircularCategoryAdapter extends RecyclerView.Adapter<CircularCategoryAdapter.CircularViewHolder> {

    Context context;
    List<ExtraCategoryModel> data;
    public CircularCategoryAdapter(Context context, List<ExtraCategoryModel> data){
        this.context = context;
        this.data = data;
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
                .load(data.get(holder.getAdapterPosition()).getCatPreivewImageUrl())
                .into(holder.iv_preview);
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
