package com.rizorsiumani.user.ui.service_providers.sp_map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.ServiceProviderDataItem;
import com.rizorsiumani.user.utils.Constants;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SpMapAdapter extends RecyclerView.Adapter<SpMapAdapter.ViewHolder> {

    private final List<ServiceProviderDataItem> list;
    private final Context ctx;
    ItemClickListener listener;

    public void setOnProviderClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public SpMapAdapter(List<ServiceProviderDataItem> data, Context context) {
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
      try {
        ServiceProviderDataItem dataItem = list.get(position);
        holder.name.setText(dataItem.getFirstName() + " " + dataItem.getLastName());
        Glide.with(ctx)
                .load(Constants.IMG_PATH + dataItem.getProfilePhoto())
                .placeholder(R.color.placeholder_bg)
                .into(holder.imageView);
        if (dataItem.getServiceProviderServices() != null && dataItem.getServiceProviderServices().size() > 0) {
            holder.service.setText(dataItem.getServiceProviderServices().get(0).getTitle());
            holder.budget.setText(Constants.constant.CURRENCY + dataItem.getServiceProviderServices().get(0).getPrice());
        }

          if (dataItem.getServiceProviderReviews() != null && dataItem.getServiceProviderReviews().size() > 0){
              int ratings = 0;
              for (int i = 0; i < dataItem.getServiceProviderReviews().size(); i++) {
                  int currentRating = dataItem.getServiceProviderReviews().get(i).getRaiting();
                  ratings  = ratings + currentRating;
              }
              int average = ratings / dataItem.getServiceProviderReviews().size();
              if (average > 0){
                  holder.reviews.setRating((float) average);
              }
          }

//          if (dataItem.getServiceProviderReviews() != null){
//              holder.reviews.setRating(Float.parseFloat(dataItem.getServiceProviderReviews().getRating()));
//          }

    }catch (NullPointerException |IndexOutOfBoundsException| IllegalStateException | IllegalArgumentException e){
        e.printStackTrace();
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
        RatingBar reviews;
        public Button profile , message;
        public CircleImageView imageView;

        public ViewHolder(@NonNull View itemView, ItemClickListener listener) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_sp_name);
            profile = itemView.findViewById(R.id.profile);
            service = itemView.findViewById(R.id.sp_service);
            budget = itemView.findViewById(R.id.sp_rate);
            imageView = itemView.findViewById(R.id.iv_sp);
            message = itemView.findViewById(R.id.message_btn);
            reviews = itemView.findViewById(R.id.rating_sp);

            profile.setOnClickListener(view -> {
                if (listener != null){
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.onProfileClick(getAdapterPosition());
                    }
                }
            });

            message.setOnClickListener(view -> {
                if (listener != null){
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.onMessageClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
