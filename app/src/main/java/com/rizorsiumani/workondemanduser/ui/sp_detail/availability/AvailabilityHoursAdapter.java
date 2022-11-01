package com.rizorsiumani.workondemanduser.ui.sp_detail.availability;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.AvailabilityHoursItem;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProviderAvailabilitiesHoursItem;

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
        holder.hour.setText(dataItem.getTime());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView hour;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            hour = itemView.findViewById(R.id.tv_hour);
        }
    }
}
