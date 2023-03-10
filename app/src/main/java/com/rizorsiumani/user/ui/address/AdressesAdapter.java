package com.rizorsiumani.user.ui.address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rizorsiumani.user.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdressesAdapter extends RecyclerView.Adapter<AdressesAdapter.ViewHolder> {

    private final List<String> list;
    private final Context ctx;

    public AdressesAdapter(List<String> data, Context context) {
        this.list = data;
        this.ctx = context;
    }

    @NonNull
    @Override
    public AdressesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_address_list_design, parent, false);
        return new AdressesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdressesAdapter.ViewHolder holder, int position) {
        String name = list.get(position);
        holder.name.setText(name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_address);
        }
    }
}
