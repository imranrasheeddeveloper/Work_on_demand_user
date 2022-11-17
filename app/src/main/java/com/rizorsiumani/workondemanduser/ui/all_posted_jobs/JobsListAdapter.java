package com.rizorsiumani.workondemanduser.ui.all_posted_jobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.PostedJobsDataItem;
import com.rizorsiumani.workondemanduser.utils.Constants;

import java.util.List;

public class JobsListAdapter extends RecyclerView.Adapter<JobsListAdapter.ViewHolder> {

    private final List<PostedJobsDataItem> list;
    private final Context ctx;


    public JobsListAdapter(Context context, List<PostedJobsDataItem> data) {
        this.ctx = context;
        this.list = data;
    }

    @NonNull
    @Override
    public JobsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobs_list_item_design, parent, false);
        return new JobsListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobsListAdapter.ViewHolder holder, int position) {
        PostedJobsDataItem dataItem = list.get(position);
        holder.name.setText(dataItem.getTitle());
        holder.description.setText(dataItem.getDescription());
        holder.name.setText(dataItem.getBudget());
        holder.budget_unit.setText("("+ dataItem.getPriceUnit()+")");
        holder.budget.setText(Constants.constant.CURRENCY + dataItem.getBudget());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,description,budget,budget_unit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.service_title);
            description = itemView.findViewById(R.id.ser_detail);
            budget = itemView.findViewById(R.id.service_rate);
            budget_unit = itemView.findViewById(R.id.service_budget_unit);

        }
    }
}
