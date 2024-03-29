package com.rizorsiumani.user.ui.fragment.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.ServiceProviderCategoriesItem;
import com.rizorsiumani.user.utils.Constants;

import java.util.List;

public class PromotionalAdapter extends RecyclerView.Adapter<PromotionalAdapter.ViewHolder> {

    private final List<ServiceProviderCategoriesItem> data;
    private OnPromotionAdapterClick mListener;


    public PromotionalAdapter(List<ServiceProviderCategoriesItem> list) {
        this.data = list;
    }

    public void setOnCellClickListener(OnPromotionAdapterClick listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recomended_sp_design, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {

        ServiceProviderCategoriesItem item = data.get(position);

        String rate = Constants.constant.CURRENCY + item.getPrice();
        holder.textView.setText(rate);
        if (item.getServiceProvider() != null) {

            holder.sp_name.setText(item.getServiceProvider().getFirstName());

            Glide.with(holder.sp_image.getContext())
                    .load(Constants.IMG_PATH + item.getServiceProvider().getProfilePhoto())
                    .placeholder(R.color.placeholder_bg)
                    .into(holder.sp_image);

            if (item.getServiceProvider().getServiceProviderReviews() != null && item.getServiceProvider().getServiceProviderReviews().size() > 0){
                int ratings = 0;
                for (int i = 0; i < item.getServiceProvider().getServiceProviderReviews().size(); i++) {
                    int currentRating = item.getServiceProvider().getServiceProviderReviews().get(i).getRaiting();
                    ratings  = ratings + currentRating;
                }
                int average = ratings / item.getServiceProvider().getServiceProviderReviews().size();
                if (average > 0){
                   holder.ratingBar.setRating((float) average);
                }
            }

        }


        }catch (NullPointerException | IllegalStateException | IllegalArgumentException e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView sp_name;
        public ImageView sp_image;
        public RatingBar ratingBar;
        //AVLoadingIndicatorView loading;

        public ViewHolder(@NonNull View itemView, OnPromotionAdapterClick listener) {
            super(itemView);

            textView = itemView.findViewById(R.id.startFrom);
            sp_name = itemView.findViewById(R.id.sp_name);
            sp_image = itemView.findViewById(R.id.sp_image);
            ratingBar = itemView.findViewById(R.id.sp_average_rating);
          //  loading = itemView.findViewById(R.id.avi);


            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCellClick(getAdapterPosition());
                }
            });

        }
    }

    interface OnPromotionAdapterClick {
        void onCellClick(int pos);
    }
}
