package com.rizorsiumani.user.ui.requested_sevices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.user.R;

import java.util.List;

public class RequestedServiceAdapter extends RecyclerView.Adapter<RequestedServiceAdapter.ViewHolder> {

    private final List<String> list;
    private final Context ctx;


    public RequestedServiceAdapter(List<String> data, Context context) {
        this.list = data;
        this.ctx = context;
    }

    @NonNull
    @Override
    public RequestedServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.requested_service_list_design, parent, false);
        return new RequestedServiceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestedServiceAdapter.ViewHolder holder, int position) {
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

            name = itemView.findViewById(R.id.service_name);

        }
    }
}
