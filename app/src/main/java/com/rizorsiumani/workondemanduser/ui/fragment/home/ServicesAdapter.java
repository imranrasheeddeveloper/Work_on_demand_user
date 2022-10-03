package com.rizorsiumani.workondemanduser.ui.fragment.home;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.CategoryModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private final List<CategoryModel> services;
    OnItemClickListener itemClickListener;

    public void setOnServiceClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ServicesAdapter(List<CategoryModel> names) {
        this.services = names;
    }

    @NonNull
    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.services_list_item_design, parent, false);
        return new ServicesAdapter.ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesAdapter.ViewHolder holder, int position) {
        CategoryModel item = services.get(position);

        holder.serName.setText(item.getTitle());
        Glide.with(holder.imageView.getContext())
                .load(item.getImageResource())
                .into(holder.imageView);

        try {

            final int[] colors = new int[2];
            colors[0] = Color.parseColor(item.getColorCode());
            colors[1] = Color.parseColor("#fef4ea");

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.RIGHT_LEFT,
                    colors
            );
            holder.cardView.setBackground(gd);
        } catch (Exception e) {
            e.printStackTrace();
        }


//        holder.itemView.setOnClickListener(view -> {
//            Intent intent = new Intent(ctx, Serviceproviders.class);
//            intent.putExtra("ser_name",name);
//            ctx.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public interface OnItemClickListener {
        void onServiceSelect(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View cardView;
        public TextView serName;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            serName = itemView.findViewById(R.id.tv_service_name);
            imageView = itemView.findViewById(R.id.ser_image);
            cardView = itemView.findViewById(R.id.view_bg);

            imageView.setOnClickListener(view -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onServiceSelect(position);
                    }
                }
            });


        }
    }
}
