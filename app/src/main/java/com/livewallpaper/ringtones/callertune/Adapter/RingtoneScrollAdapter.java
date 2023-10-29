package com.livewallpaper.ringtones.callertune.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.livewallpaper.ringtones.callertune.R;

import java.util.ArrayList;

public class RingtoneScrollAdapter extends RecyclerView.Adapter<RingtoneScrollAdapter.RingtoneViewHolder> {

    Context context;
    ArrayList<Integer> data;
    public RingtoneScrollAdapter(Context context, ArrayList<Integer> data){
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
        holder.iv_ring_bg.setImageDrawable(ContextCompat.getDrawable(context, data.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class RingtoneViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_ring_bg;

        public RingtoneViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_ring_bg = itemView.findViewById(R.id.iv_ring_img);
        }
    }
}
