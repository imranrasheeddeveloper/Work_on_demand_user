package com.rizorsiumani.workondemanduser.ui.sp_detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.R;

import java.util.List;

public class DiscountPlansAdapter extends RecyclerView.Adapter<DiscountPlansAdapter.ViewHolder> {

    private final List<String> list;
    private final Context ctx;

    public DiscountPlansAdapter(List<String> data, Context context) {
        this.list = data;
        this.ctx = context;
    }

    @NonNull
    @Override
    public DiscountPlansAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.monthly_plans_item_design, parent, false);
        return new DiscountPlansAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountPlansAdapter.ViewHolder holder, int position) {
        String name = list.get(position);
        holder.plans.setText(name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView plans;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            plans = itemView.findViewById(R.id.tv_month);
        }
    }
}
