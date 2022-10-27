package com.rizorsiumani.workondemanduser.ui.inbox;

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
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.utils.Constants;

import java.util.List;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> {

    private final List<String> data;
    private final Context context;
    InboxAdapter.OnItemClickListener itemClickListener;


    public void setOnChatClickListener(InboxAdapter.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public InboxAdapter(List<String> list, Context appContext) {
        this.context = appContext;
        this.data = list;
    }

    @NonNull
    @Override
    public InboxAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_design, parent, false);
        return new InboxAdapter.ViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull InboxAdapter.ViewHolder holder, int position) {

        String model = data.get(position);
        holder.name.setText(model);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickListener {
        void onChatSelect(int position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;


        public ViewHolder(@NonNull View itemView, InboxAdapter.OnItemClickListener itemClickListener) {
            super(itemView);
            //find views
            name = itemView.findViewById(R.id.i_name);


            itemView.setOnClickListener(view -> {
                if (itemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickListener.onChatSelect(position);
                    }
                }
            });

        }
    }
}
