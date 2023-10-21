package com.livewallpaper.ringtones.callertune.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.livewallpaper.ringtones.callertune.CustomViews.SpeedyLinearLayoutManager;
import com.livewallpaper.ringtones.callertune.Model.AutoScrollImageModel;
import com.livewallpaper.ringtones.callertune.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingHolder> {


    Context context;
    ArrayList<AutoScrollImageModel> data;

    public OnboardingAdapter(Context context, ArrayList<AutoScrollImageModel> data){
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public OnboardingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_splash, parent , false);
        return new OnboardingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingHolder holder, int position) {
        AutoScrollImageModel value = data.get(holder.getAdapterPosition());


        List<Integer> rv1_data = new ArrayList<>();
        List<Integer> rv2_data = new ArrayList<>();
        List<Integer> rv3_data = new ArrayList<>();

        rv1_data.addAll(value.getImageList());
        rv2_data.addAll(value.getImageList());
        rv3_data.addAll(value.getImageList());

        Collections.shuffle(rv1_data);
        Collections.shuffle(rv2_data);
        Collections.shuffle(rv3_data);

        AutoScrollAdapter autoScrollAdapter1 = new AutoScrollAdapter(context, rv1_data);
        AutoScrollAdapter autoScrollAdapter2 = new AutoScrollAdapter(context, rv2_data);
        AutoScrollAdapter autoScrollAdapter3 = new AutoScrollAdapter(context, rv3_data);

        SpeedyLinearLayoutManager llm1 = new SpeedyLinearLayoutManager(context, SpeedyLinearLayoutManager.VERTICAL, false);
        SpeedyLinearLayoutManager llm2 = new SpeedyLinearLayoutManager(context, SpeedyLinearLayoutManager.VERTICAL, false);
        SpeedyLinearLayoutManager llm3 = new SpeedyLinearLayoutManager(context, SpeedyLinearLayoutManager.VERTICAL, false);

        holder.rv1.setLayoutManager(llm1);
        holder.rv2.setLayoutManager(llm2);
        holder.rv3.setLayoutManager(llm3);
        holder.rv1.setAdapter(autoScrollAdapter1);
        holder.rv2.setAdapter(autoScrollAdapter2);
        holder.rv3.setAdapter(autoScrollAdapter3);

        holder.rv2.scrollToPosition(rv2_data.size() - 1);

        if (llm1.findLastCompletelyVisibleItemPosition() < (rv1_data.size() - 1)){
            llm1.smoothScrollToPosition(holder.rv1, new RecyclerView.State(), (rv1_data.size() - 1));
        }else if(llm1.findLastCompletelyVisibleItemPosition() == (rv1_data.size() - 1)){
            llm1.smoothScrollToPosition(holder.rv1, new RecyclerView.State(), 0);
        }

        if (llm3.findLastCompletelyVisibleItemPosition() < (rv2_data.size() - 1)){
            llm2.smoothScrollToPosition(holder.rv2, new RecyclerView.State(), 0);
        }else if(llm3.findLastCompletelyVisibleItemPosition() == (rv2_data.size() - 1)){
            llm2.smoothScrollToPosition(holder.rv2, new RecyclerView.State(), (rv2_data.size() - 1));
        }

        if (llm3.findLastCompletelyVisibleItemPosition() < (rv3_data.size() - 1)){
            llm3.smoothScrollToPosition(holder.rv3, new RecyclerView.State(), (rv3_data.size() - 1));
        }else if(llm3.findLastCompletelyVisibleItemPosition() == (rv3_data.size() - 1)){
            llm3.smoothScrollToPosition(holder.rv3, new RecyclerView.State(), 0);
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (llm1.findLastCompletelyVisibleItemPosition() < (rv1_data.size() - 1)){
                    llm1.smoothScrollToPosition(holder.rv1, new RecyclerView.State(), (rv1_data.size() - 1));
                }else if(llm1.findLastCompletelyVisibleItemPosition() == (rv1_data.size() - 1)){
                    llm1.smoothScrollToPosition(holder.rv1, new RecyclerView.State(), 0);
                }
            }
        }, 700, 5000);

        Timer time2 = new Timer();
        time2.schedule(new TimerTask() {
            @Override
            public void run() {
                if (llm3.findLastCompletelyVisibleItemPosition() < (rv2_data.size() - 1)){
                    llm2.smoothScrollToPosition(holder.rv2, new RecyclerView.State(), 0);
                }else if(llm3.findLastCompletelyVisibleItemPosition() == (rv2_data.size() - 1)){
                    llm2.smoothScrollToPosition(holder.rv2, new RecyclerView.State(), (rv2_data.size() - 1));
                }
            }
        }, 700, 5000);


        Timer timer3 = new Timer();
        timer3.schedule(new TimerTask() {
            @Override
            public void run() {
                if (llm3.findLastCompletelyVisibleItemPosition() < (rv3_data.size() - 1)){
                    llm3.smoothScrollToPosition(holder.rv3, new RecyclerView.State(), (rv3_data.size() - 1));
                }else if(llm3.findLastCompletelyVisibleItemPosition() == (rv3_data.size() - 1)){
                    llm3.smoothScrollToPosition(holder.rv3, new RecyclerView.State(), 0);
                }
            }
        }, 700, 5000);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class OnboardingHolder extends RecyclerView.ViewHolder{

        RecyclerView rv1, rv2, rv3;

        public OnboardingHolder(@NonNull View itemView) {
            super(itemView);

            rv1 = itemView.findViewById(R.id.rv_auto1);
            rv2 = itemView.findViewById(R.id.rv_auto2);
            rv3 = itemView.findViewById(R.id.rv_auto3);
        }
    }
}
