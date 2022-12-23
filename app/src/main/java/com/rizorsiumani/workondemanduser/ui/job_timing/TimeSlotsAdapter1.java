package com.rizorsiumani.workondemanduser.ui.job_timing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.R;

import java.util.List;

public class TimeSlotsAdapter1 extends RecyclerView.Adapter<TimeSlotsAdapter1.ViewHolder> {

    private final List<TimeItem> data;
    private final Context ctx;
    OnItemClickListener itemClickListener;
    public static int selectedPosition = -1;
    public static int lastSelectedPos = -1;


    public void setOnSlotClickListener(TimeSlotsAdapter1.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public TimeSlotsAdapter1(Context context, List<TimeItem> list) {
        this.data = list;
        this.ctx = context;
    }

    @NonNull
    @Override
    public TimeSlotsAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_slot_item, parent, false);
        return new TimeSlotsAdapter1.ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotsAdapter1.ViewHolder holder, int position) {
        TimeItem itemModel = data.get(position);

        if (itemModel != null){
            holder.froTime.setText(itemModel.getFromTime() + " " + itemModel.getFromTime());

        }


    }

    @Override
    public int getItemCount() {
        if(data != null){
            return data.size();
        }
        else {
            return  0;
        }
    }

    public interface OnItemClickListener {
        void onTimeSelect(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView toTime, froTime;
        CardView card;

        public ViewHolder(@NonNull View itemView, TimeSlotsAdapter1.OnItemClickListener itemClickListener) {
            super(itemView);

           // toTime = itemView.findViewById(R.id.toTime);
            froTime = itemView.findViewById(R.id.time_value);
            card = itemView.findViewById(R.id.slotCard);
            

        }
        
    }


}
