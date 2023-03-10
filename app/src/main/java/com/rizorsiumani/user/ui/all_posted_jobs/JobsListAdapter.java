package com.rizorsiumani.user.ui.all_posted_jobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.PostedJobsDataItem;
import com.rizorsiumani.user.utils.Constants;

import java.util.List;

public class JobsListAdapter extends RecyclerView.Adapter<JobsListAdapter.ViewHolder> {

    private final List<PostedJobsDataItem> list;
    private final Context ctx;
    OnItemClickListener listener;

    public void setOnJobClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public JobsListAdapter(Context context, List<PostedJobsDataItem> data) {
        this.ctx = context;
        this.list = data;
    }

    @NonNull
    @Override
    public JobsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobs_list_item_design, parent, false);
        return new JobsListAdapter.ViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull JobsListAdapter.ViewHolder holder, int position) {
        try {

        PostedJobsDataItem dataItem = list.get(position);
        holder.name.setText(dataItem.getTitle());
        holder.description.setText(dataItem.getDescription());
        holder.budget_unit.setText("("+ dataItem.getPriceUnit()+")");
        holder.budget.setText(Constants.constant.CURRENCY + dataItem.getBudget());
        Glide.with(ctx)
                .load(Constants.IMG_PATH + dataItem.getAttachment())
                .placeholder(R.color.teal_200)
                .into(holder.shapeableImageView);

        }catch (NullPointerException | IllegalStateException | IllegalArgumentException e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener{
        void onCancel(int position);
        void onUpdate(int position);
    }

    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,description,budget,budget_unit, btnCancel, btnAlter;
        public ShapeableImageView shapeableImageView;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            name = itemView.findViewById(R.id.service_title);
            description = itemView.findViewById(R.id.ser_detail);
            budget = itemView.findViewById(R.id.service_rate);
            budget_unit = itemView.findViewById(R.id.service_budget_unit);
            shapeableImageView = itemView.findViewById(R.id.service_image);
            btnAlter = itemView.findViewById(R.id.btn_alter);
            btnCancel = itemView.findViewById(R.id.btn_cancel);

            btnCancel.setOnClickListener(v -> {
               if (listener != null){
                   if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                       listener.onCancel(getAdapterPosition());
                   }
               }
            });

            btnAlter.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onUpdate(getAdapterPosition());
                }
            });

        }
    }
}
