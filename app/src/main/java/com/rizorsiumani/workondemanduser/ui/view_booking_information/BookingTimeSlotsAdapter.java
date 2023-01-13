package com.rizorsiumani.workondemanduser.ui.view_booking_information;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.AvailabilityHoursItem;
import com.rizorsiumani.workondemanduser.ui.job_timing.TimeItem;
import com.rizorsiumani.workondemanduser.ui.sp_detail.availability.AvailabilityHoursAdapter;

import java.util.List;

public class BookingTimeSlotsAdapter extends RecyclerView.Adapter<AvailabilityHoursAdapter.ViewHolder> {

    private final List<TimeItem> list;
    private final Context ctx;

    public BookingTimeSlotsAdapter(Context context, List<TimeItem> data) {
        this.ctx = context;
        this.list = data;
    }


    @NonNull
    @Override
    public AvailabilityHoursAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.availability_hours_design, parent, false);
        return new AvailabilityHoursAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailabilityHoursAdapter.ViewHolder holder, int position) {
        TimeItem dataItem = list.get(position);
        holder.from.setText(dataItem.getFromTime() + " to " + dataItem.getToTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView from;
        ImageView delete_icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            from = itemView.findViewById(R.id.time_value);
            delete_icon = itemView.findViewById(R.id.delete_time);

        }
    }
}
