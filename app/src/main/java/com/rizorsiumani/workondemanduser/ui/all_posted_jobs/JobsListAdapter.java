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

import java.util.List;

public class JobsListAdapter extends RecyclerView.Adapter<JobsListAdapter.ViewHolder> {

    private final List<String> list;
    private final Context ctx;


    public JobsListAdapter(Context context,List<String> data) {
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
        String user_name = list.get(position);
        holder.name.setText(user_name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.service_title);
        }
    }
}
