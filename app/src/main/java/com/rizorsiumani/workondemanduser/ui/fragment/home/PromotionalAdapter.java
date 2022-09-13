package com.rizorsiumani.workondemanduser.ui.fragment.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.R;

import java.util.List;

public class PromotionalAdapter extends RecyclerView.Adapter<PromotionalAdapter.ViewHolder> {

    private final List<Integer> imagesList;
    private final Context ctx;


    public PromotionalAdapter(Context context,List<Integer> images) {
        this.ctx = context;
        this.imagesList = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promotion_images_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Integer image = imagesList.get(position);
        holder.imageView.setImageResource(image);
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.promotion_image);
        }
    }
}
