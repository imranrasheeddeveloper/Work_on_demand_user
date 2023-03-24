package com.rizorsiumani.user.ui.fragment.home;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.CategoriesDataItem;
import com.rizorsiumani.user.utils.Constants;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private final List<CategoriesDataItem> services;
    private final Context ctx;
    OnItemClickListener itemClickListener;


    public void setOnServiceClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CategoriesAdapter(Context context, List<CategoriesDataItem> list) {
        this.ctx = context;
        this.services = list;
    }

    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item_design, parent, false);
        return new CategoriesAdapter.ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, int position) {
        CategoriesDataItem model = services.get(position);

        holder.serName.setText(model.getTitle());
        Glide.with(ctx)
                .load(Constants.IMG_PATH + model.getImage())
                .into(holder.serImage);

        try {
            String color = model.getColor();
            if (!color.startsWith("#")) {
                color = "#" + color;
                final int[] colors = new int[2];
                colors[0] = Color.parseColor(color);
                colors[1] = Color.parseColor("#fef4ea");
                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT,
                        colors
                );
                holder.layout.setBackground(gd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public interface OnItemClickListener {
        void onServiceSelect(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView serName;
        public ImageView serImage;
        public ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView, OnItemClickListener itemClickListener) {
            super(itemView);

            serName = itemView.findViewById(R.id.sName);
            serImage = itemView.findViewById(R.id.sImage);
            layout = itemView.findViewById(R.id.cat_layout);

            layout.setOnClickListener(view -> {
                if (itemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickListener.onServiceSelect(position);
                    }
                }
            });

        }
    }
}
