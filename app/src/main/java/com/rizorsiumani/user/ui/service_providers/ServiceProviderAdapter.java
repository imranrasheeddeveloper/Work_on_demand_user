package com.rizorsiumani.user.ui.service_providers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.ServiceProviderDataItem;
import com.rizorsiumani.user.utils.Constants;

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
        try {

        ServiceProviderDataItem dataItem = list.get(position);
        holder.name.setText(dataItem.getFirstName() +" "+ dataItem.getLastName());
        Glide.with(ctx)
                .load(Constants.IMG_PATH + dataItem.getProfilePhoto())
                .placeholder(R.drawable.ic_profile)
                .into(holder.imageView);
        if (dataItem.getServiceProviderServices() != null && dataItem.getServiceProviderServices().size() > 0) {
            holder.service.setText(dataItem.getServiceProviderServices().get(0).getTitle());
            holder.price.setText(Constants.constant.CURRENCY + dataItem.getServiceProviderServices().get(0).getPrice());
        }

            if (dataItem.getServiceProviderReviews() != null && dataItem.getServiceProviderReviews().size() > 0){
                int ratings = 0;
                for (int i = 0; i < dataItem.getServiceProviderReviews().size(); i++) {
                    int currentRating = dataItem.getServiceProviderReviews().get(i).getRaiting();
                    ratings  = ratings + currentRating;
                }
                int average = ratings / dataItem.getServiceProviderReviews().size();
                if (average > 0){
                    holder.reviews.setText(average + " reviews");
                }
            }
//        if (dataItem.getServiceProviderReviews() != null){
//            holder.reviews.setText(String.valueOf(dataItem.getServiceProviderReviews().getRating()) + " reviews");
//        }

        }catch (NullPointerException |IndexOutOfBoundsException| IllegalStateException | IllegalArgumentException e){
            e.printStackTrace();
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
        public TextView name, service, price, reviews;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView, onItemClickListener itemClickListener) {
            super(itemView);

            name = itemView.findViewById(R.id.sp_name11);
            service = itemView.findViewById(R.id.sp_service);
            price = itemView.findViewById(R.id.sp_rate);
            imageView = itemView.findViewById(R.id.iv_sp);
            reviews = itemView.findViewById(R.id.sp_reviews);

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

