package com.rizorsiumani.workondemanduser.ui.searched_sp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.ui.sp_detail.SpProfile;

import java.util.List;

public class SearchedProviderAdapter extends RecyclerView.Adapter<SearchedProviderAdapter.ViewHolder> {

    private final List<String> list;
    private final Context ctx;
    onItemClickListener itemClickListener;

    public void setOnProviderListener(onItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public SearchedProviderAdapter(List<String> data, Context context) {
        this.list = data;
        this.ctx = context;
    }

    @NonNull
    @Override
    public SearchedProviderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_provider_item_design, parent, false);
        return new SearchedProviderAdapter.ViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchedProviderAdapter.ViewHolder holder, int position) {
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

        public ViewHolder(@NonNull View itemView, SearchedProviderAdapter.onItemClickListener itemClickListener) {
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

