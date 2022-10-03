package com.rizorsiumani.workondemanduser.ui.fragment.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.RecommendedServicesModel;

import java.util.List;

public class PromotionalAdapter extends RecyclerView.Adapter<PromotionalAdapter.ViewHolder> {

    private final List<RecommendedServicesModel> data;
    private OnPromotionAdapterClick mListener;

    public PromotionalAdapter(List<RecommendedServicesModel> list) {
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
        RecommendedServicesModel item = data.get(position);

        String rate = item.getServicePrice() + "$";
        holder.textView.setText(rate);

        holder.sp_name.setText(item.getServiceProviderName());

        Glide.with(holder.sp_image.getContext())
                .load(item.getImgSource())
                .into(holder.sp_image);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView sp_name;
        public ImageView sp_image;
        public ConstraintLayout item_view;

        public ViewHolder(@NonNull View itemView, OnPromotionAdapterClick listener) {
            super(itemView);

            textView = itemView.findViewById(R.id.startFrom);
            sp_name = itemView.findViewById(R.id.sp_name);
            sp_image = itemView.findViewById(R.id.sp_image);
          //  item_view = itemView.findViewById(R.id.item_view);

//            item_view.setOnClickListener(v -> {
//                if (listener != null) {
//                    listener.onCellClick(getAdapterPosition());
//                }
//            });

        }
    }

    interface OnPromotionAdapterClick {
        void onCellClick(int pos);
    }
}
