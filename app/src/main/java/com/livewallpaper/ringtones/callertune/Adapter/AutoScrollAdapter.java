package com.livewallpaper.ringtones.callertune.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.livewallpaper.ringtones.callertune.R;

import java.util.ArrayList;
import java.util.List;

public class AutoScrollAdapter extends RecyclerView.Adapter<AutoScrollAdapter.MyViewHolder> {


    Context context;
    List<Integer> data;

    public AutoScrollAdapter(Context context, List<Integer> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.auto_scroll_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView.setBackgroundResource(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_img);
        }
    }
}