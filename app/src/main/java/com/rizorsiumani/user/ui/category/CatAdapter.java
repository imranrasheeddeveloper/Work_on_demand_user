package com.rizorsiumani.user.ui.category;

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

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> {

    private final List<CategoriesDataItem> data;
    private final Context context;
    OnItemClickListener itemClickListener;


    public void setOnServiceClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public CatAdapter(List<CategoriesDataItem> list, Context appContext) {
        this.context = appContext;
        this.data = list;
    }

    @NonNull
    @Override
    public CatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_list_design, parent, false);
        return new CatAdapter.ViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CatAdapter.ViewHolder holder, int position) {

        CategoriesDataItem model = data.get(position);
        Glide.with(context).load(Constants.IMG_PATH + model.getImage()).into(holder.icon);
        holder.query.setText(model.getTitle());

        try {
            String color = model.getColor();
            if (!color.startsWith("#")) {
                color = "#" + color;
                final int[] colors = new int[2];
                colors[0] = Color.parseColor("#fef4ea");
                colors[1] = Color.parseColor(color);
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
        return data.size();
    }

    public interface OnItemClickListener {
        void onServiceSelect(int position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout layout;
        public TextView query;
        public ImageView icon;

        public ViewHolder(@NonNull View itemView, OnItemClickListener itemClickListener) {
            super(itemView);
            //find views
            query = itemView.findViewById(R.id.query);
            icon = itemView.findViewById(R.id.icon);
            layout = itemView.findViewById(R.id.catView);

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
