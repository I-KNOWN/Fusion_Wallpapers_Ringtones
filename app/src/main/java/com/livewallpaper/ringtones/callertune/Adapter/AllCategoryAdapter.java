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
import com.livewallpaper.ringtones.callertune.Model.CategoryModel;
import com.livewallpaper.ringtones.callertune.R;

import java.util.ArrayList;

public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.SomeCategoryViewHolder> {


    Context context;
    ArrayList<CategoryModel> data;

    public AllCategoryAdapter(Context context, ArrayList<CategoryModel> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public SomeCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_all, parent, false);
        return new SomeCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SomeCategoryViewHolder holder, int position) {
        CategoryModel model = data.get(holder.getAdapterPosition());
        Glide.with(context)
                .load(model.getCategoryImageUrl())
                        .into(holder.iv_cat_bg);
//        holder.iv_cat_bg.setImageDrawable(ContextCompat.getDrawable(context, model.getCategoryImage()));
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
