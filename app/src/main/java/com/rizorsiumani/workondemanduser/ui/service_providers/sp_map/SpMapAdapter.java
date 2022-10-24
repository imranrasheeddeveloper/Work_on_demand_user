package com.rizorsiumani.workondemanduser.ui.service_providers.sp_map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.DataItem;

import java.util.List;

public class SpMapAdapter extends RecyclerView.Adapter<SpMapAdapter.ViewHolder> {

    private final List<DataItem> list;
    private final Context ctx;
    ItemClickListener listener;

    public void setOnProviderClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public SpMapAdapter(List<DataItem> data, Context context) {
        this.list = data;
        this.ctx = context;
    }

    @NonNull
    @Override
    public SpMapAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_sp_list_design, parent, false);
        return new SpMapAdapter.ViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SpMapAdapter.ViewHolder holder, int position) {
        DataItem dataItem = list.get(position);
        holder.name.setText(dataItem.getFirstName() + " " + dataItem.getLastName());
        if (dataItem.getServiceProviderServices() != null) {
            holder.service.setText(dataItem.getServiceProviderServices().get(0).getTitle());
            holder.budget.setText(dataItem.getServiceProviderServices().get(0).getPrice());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener{
        void onMessageClick(int position);
        void onProfileClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,service,budget;
        public Button profile;

        public ViewHolder(@NonNull View itemView, ItemClickListener listener) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_sp_name);
            profile = itemView.findViewById(R.id.profile);
            service = itemView.findViewById(R.id.sp_service);
            budget = itemView.findViewById(R.id.sp_rate);

            profile.setOnClickListener(view -> {
                if (listener != null){
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.onProfileClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
