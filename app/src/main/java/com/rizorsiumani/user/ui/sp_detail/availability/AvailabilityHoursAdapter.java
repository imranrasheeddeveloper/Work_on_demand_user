package com.rizorsiumani.user.ui.sp_detail.availability;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.AvailabilityHoursItem;

import java.util.List;

public class AvailabilityHoursAdapter extends RecyclerView.Adapter<AvailabilityHoursAdapter.ViewHolder> {

    private final List<AvailabilityHoursItem> list;
    private final Context ctx;

    public AvailabilityHoursAdapter(Context context, List<AvailabilityHoursItem> data) {
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
        AvailabilityHoursItem dataItem = list.get(position);
        holder.from.setText(dataItem.getFromTime() + " to " + dataItem.getToTime());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView from;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            from = itemView.findViewById(R.id.time_value);
        }
    }
}
