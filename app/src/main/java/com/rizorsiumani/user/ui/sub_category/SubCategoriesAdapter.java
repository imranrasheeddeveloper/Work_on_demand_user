package com.rizorsiumani.user.ui.sub_category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.user.R;

import java.util.List;

public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.ViewHolder> {

    private final List<String> list;
    private final Context ctx;
    onItemClickListener itemClickListener;

    public void setOnProviderListener(onItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public SubCategoriesAdapter(List<String> data, Context context) {
        this.list = data;
        this.ctx = context;
    }

    @NonNull
    @Override
    public SubCategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_provider_item_design, parent, false);
        return new SubCategoriesAdapter.ViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoriesAdapter.ViewHolder holder, int position) {
        String name = list.get(position);
        holder.name.setText(name);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface onItemClickListener{
        void onProviderSelect(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public ViewHolder(@NonNull View itemView, SubCategoriesAdapter.onItemClickListener itemClickListener) {
            super(itemView);

            name = itemView.findViewById(R.id.sp_name);

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

