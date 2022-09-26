package com.rizorsiumani.workondemanduser.ui.service_providers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.ui.filter.CategoryFilterAdapter;
import com.rizorsiumani.workondemanduser.ui.sp_detail.ServiceProviderProfile;
import com.rizorsiumani.workondemanduser.ui.sp_detail.SpProfile;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ServiceProviderAdapter extends RecyclerView.Adapter<ServiceProviderAdapter.ViewHolder> {

    private final List<String> list;
    private final Context ctx;
    onItemClickListener itemClickListener;

    public void setOnProviderSelectListener(onItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ServiceProviderAdapter(List<String> data, Context context) {
        this.list = data;
        this.ctx = context;
    }

    @NonNull
    @Override
    public ServiceProviderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_provider_item_design, parent, false);
        return new ServiceProviderAdapter.ViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceProviderAdapter.ViewHolder holder, int position) {
        String name = list.get(position);
        holder.name.setText(name);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(ctx, SpProfile.class);
            ctx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface onItemClickListener{
        void onProviderSelect(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public ViewHolder(@NonNull View itemView, onItemClickListener itemClickListener) {
            super(itemView);

            name = itemView.findViewById(R.id.sp_name);

            itemView.setOnClickListener(view -> {
                if (itemClickListener!= null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickListener.onProviderSelect(position);
                    }
                }
            });
        }
    }
}

