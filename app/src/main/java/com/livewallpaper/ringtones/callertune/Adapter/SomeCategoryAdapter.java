package com.livewallpaper.ringtones.callertune.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.livewallpaper.ringtones.callertune.Model.CategoryModel;
import com.livewallpaper.ringtones.callertune.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SomeCategoryAdapter extends RecyclerView.Adapter<SomeCategoryAdapter.SomeCategoryViewHolder> {


    Context context;
    ArrayList<CategoryModel> data;

    public SomeCategoryAdapter(Context context, ArrayList<CategoryModel> data){
        this.context = context;
        this.data = data;
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
        holder.iv_cat_bg.setImageDrawable(ContextCompat.getDrawable(context, model.getCategoryImage()));
        holder.tv_title.setText(model.getCategoryName());
        holder.tv_desc.setText(model.getCategoryDesc());

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
