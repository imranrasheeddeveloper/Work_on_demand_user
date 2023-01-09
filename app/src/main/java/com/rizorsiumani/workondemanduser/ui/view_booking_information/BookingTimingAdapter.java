package com.rizorsiumani.workondemanduser.ui.view_booking_information;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.AvailabilityDataItem;
import com.rizorsiumani.workondemanduser.ui.job_timing.AvailabilitiesItem;
import com.rizorsiumani.workondemanduser.ui.sp_detail.availability.AvailabilityAdapter;
import com.rizorsiumani.workondemanduser.ui.sp_detail.availability.AvailabilityHoursAdapter;

import java.util.List;

public class BookingTimingAdapter extends RecyclerView.Adapter<AvailabilityAdapter.ViewHolder> {

    private final List<AvailabilitiesItem> list;
    private final Context ctx;

    public BookingTimingAdapter(List<AvailabilitiesItem> data, Context context) {
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
        AvailabilitiesItem dataItem = list.get(position);
        holder.day.setText(dataItem.getDay());

        if (dataItem.getTime() != null){

            if (dataItem.getTime().size() > 0){

                LinearLayoutManager layoutManager1 = new LinearLayoutManager(App.applicationContext, RecyclerView.HORIZONTAL, false);
                holder.timeSlots.setLayoutManager(layoutManager1);
                BookingTimeSlotsAdapter adapter1 = new BookingTimeSlotsAdapter(ctx,dataItem.getTime());
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

