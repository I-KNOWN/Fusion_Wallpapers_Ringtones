package com.livewallpaper.ringtones.callertune.Adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.livewallpaper.ringtones.callertune.Activity.SeeAllActivity;
import com.livewallpaper.ringtones.callertune.Model.CategoryModel;
import com.livewallpaper.ringtones.callertune.R;

import java.util.ArrayList;

public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.SomeCategoryViewHolder> {


    Context context;
    ArrayList<CategoryModel> data;
    String type;
    onClickGalleryIntent onClickGalleryIntent;
    public AllCategoryAdapter(Context context, ArrayList<CategoryModel> data, String type, onClickGalleryIntent onClickGalleryIntent){
        this.context = context;
        this.data = data;
        this.type =type;
        this.onClickGalleryIntent = onClickGalleryIntent;
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
        if(!model.getCategoryName().equals("customKeybaord")){
            Glide.with(context)
                    .load(model.getCategoryImageUrl())
                    .into(holder.iv_cat_bg);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SeeAllActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("category", model.getCategoryName());
                    context.startActivity(intent);
                }
            });
//        holder.iv_cat_bg.setImageDrawable(ContextCompat.getDrawable(context, model.getCategoryImage()));
            holder.tv_title.setText(model.getCategoryName());
            holder.tv_desc.setText(model.getCategoryDesc());
        }else{
            holder.tv_title.setVisibility(View.GONE);
            holder.tv_desc.setVisibility(View.GONE);
            holder.iv_cat_bg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.custom_linear_gradient));
            holder.llcont.setVisibility(View.VISIBLE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickGalleryIntent.galleryIntent();
                }
            });
        }


    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    static class SomeCategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_cat_bg;
        TextView tv_title, tv_desc;
        LinearLayout llcont;

        public SomeCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_cat_bg = itemView.findViewById(R.id.iv_cat);
            tv_desc = itemView.findViewById(R.id.tv_dec_);
            tv_title = itemView.findViewById(R.id.tv_title);
            llcont = itemView.findViewById(R.id.ll_cont);
        }
    }

    public interface onClickGalleryIntent{
        void galleryIntent();
    }

}
