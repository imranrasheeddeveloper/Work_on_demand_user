package com.rizorsiumani.workondemanduser.ui.fragment.home;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.RecommendedServicesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProviderCategoriesItem;
import com.rizorsiumani.workondemanduser.utils.Constants;
import com.wang.avi.AVLoadingIndicatorView;

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

        holder.sp_name.setText(item.getServiceProvider().getFirstName());

        Glide.with(holder.sp_image.getContext())
                .load(Constants.IMG_PATH + item.getServiceProvider().getProfilePhoto())
                .placeholder(R.color.placeholder_bg)
                .into(holder.sp_image);

        holder.ratingBar.setRating(Float.valueOf(item.getServiceProvider().getServiceProviderReviews().getRating()));


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
            ratingBar = itemView.findViewById(R.id.rating);
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
