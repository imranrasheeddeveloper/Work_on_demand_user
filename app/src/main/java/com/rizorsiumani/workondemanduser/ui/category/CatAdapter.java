package com.rizorsiumani.workondemanduser.ui.category;

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

import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.SerCategoryModel;
import com.rizorsiumani.workondemanduser.ui.fragment.home.CategoriesAdapter;

import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> {

    private final List<SerCategoryModel> data;
    private final Context context;
    OnItemClickListener itemClickListener;


    public void setOnServiceClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public CatAdapter(List<SerCategoryModel> list, Context appContext) {
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

        SerCategoryModel model = data.get(position);
        holder.icon.setImageResource(model.getIcon());

        try {
            final int[] colors = new int[2];
            colors[0] = Color.parseColor("#fef4ea");
            colors[1] = Color.parseColor(model.getColorCode());
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT,
                    colors
            );
            holder.layout.setBackground(gd);
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
