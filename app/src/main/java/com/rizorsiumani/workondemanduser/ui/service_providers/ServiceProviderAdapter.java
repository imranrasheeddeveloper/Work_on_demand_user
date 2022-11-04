package com.rizorsiumani.workondemanduser.ui.service_providers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProviderDataItem;
import com.rizorsiumani.workondemanduser.utils.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ServiceProviderAdapter extends RecyclerView.Adapter<ServiceProviderAdapter.ViewHolder> {

    private final List<ServiceProviderDataItem> list;
    private final Context ctx;
    onItemClickListener itemClickListener;

    public void setOnProviderSelectListener(onItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ServiceProviderAdapter(List<ServiceProviderDataItem> data, Context context) {
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
        ServiceProviderDataItem dataItem = list.get(position);
        holder.name.setText(dataItem.getFirstName() +" "+ dataItem.getLastName());
        Glide.with(ctx).load(Constants.IMG_PATH + dataItem.getProfilePhoto()).into(holder.imageView);
        if (dataItem.getServiceProviderServices() != null) {
            holder.service.setText(dataItem.getServiceProviderServices().get(0).getTitle());
            holder.price.setText(Constants.CURRENCY + dataItem.getServiceProviderServices().get(0).getPrice());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface onItemClickListener{
        void onProviderSelect(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, service, price;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView, onItemClickListener itemClickListener) {
            super(itemView);

            name = itemView.findViewById(R.id.sp_name11);
            service = itemView.findViewById(R.id.sp_service);
            price = itemView.findViewById(R.id.sp_rate);
            imageView = itemView.findViewById(R.id.sp_image);

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

