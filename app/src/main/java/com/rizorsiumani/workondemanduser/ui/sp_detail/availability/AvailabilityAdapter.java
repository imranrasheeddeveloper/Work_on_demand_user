package com.rizorsiumani.workondemanduser.ui.sp_detail.availability;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.AvailabilityDataItem;
import com.rizorsiumani.workondemanduser.ui.booking_date.BookingDateTime;

import java.util.List;

public class AvailabilityAdapter extends RecyclerView.Adapter<AvailabilityAdapter.ViewHolder> {

    private final List<AvailabilityDataItem> list;
    private final Context ctx;

    public AvailabilityAdapter(List<AvailabilityDataItem> data, Context context) {
        this.list = data;
        this.ctx = context;
    }


    @NonNull
    @Override
    public AvailabilityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.availability_slot_design, parent, false);
        return new AvailabilityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailabilityAdapter.ViewHolder holder, int position) {
        AvailabilityDataItem dataItem = list.get(position);
        holder.day.setText(dataItem.getDay());

        if (dataItem.getAvailabilityHours() != null){

            if (dataItem.getAvailabilityHours().size() > 0){
                GridLayoutManager layoutManager = new GridLayoutManager(App.applicationContext, 3);
                holder.timeSlots.setLayoutManager(layoutManager);
//                LinearLayoutManager layoutManager1 = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
//                holder.timeSlots.setLayoutManager(layoutManager1);
                AvailabilityHoursAdapter adapter1 = new AvailabilityHoursAdapter(ctx,dataItem.getAvailabilityHours());
                holder.timeSlots.setAdapter(adapter1);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView day;
        public RecyclerView timeSlots;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            day = itemView.findViewById(R.id.tv_day);
            timeSlots = itemView.findViewById(R.id.timing);
        }
    }
}
