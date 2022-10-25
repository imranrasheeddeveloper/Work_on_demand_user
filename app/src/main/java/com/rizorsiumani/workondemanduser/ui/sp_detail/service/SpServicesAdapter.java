package com.rizorsiumani.workondemanduser.ui.sp_detail.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.ServicesDataItem;
import com.rizorsiumani.workondemanduser.ui.booking.BookService;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

import java.util.List;

public class SpServicesAdapter extends RecyclerView.Adapter<SpServicesAdapter.ViewHolder> {

    private final List<ServicesDataItem> list;
    private final Context ctx;

    public SpServicesAdapter(List<ServicesDataItem> data, Context context) {
        this.list = data;
        this.ctx = context;
    }

    @NonNull
    @Override
    public SpServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.services_list_design, parent, false);
        return new SpServicesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpServicesAdapter.ViewHolder holder, int position) {
        ServicesDataItem dataItem = list.get(position);
        holder.name.setText(dataItem.getTitle());
        holder.description.setText(dataItem.getDescription());
        holder.budget.setText(dataItem.getPrice());
        holder.budgetUnit.setText("(" + dataItem.getPriceUnit() +")");

        holder.book.setOnClickListener(view -> {
            ActivityUtil.gotoPage(ctx, BookService.class);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,description, budget,book,budgetUnit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.sName);
            description = itemView.findViewById(R.id.sDetail);
            budget = itemView.findViewById(R.id.sPerHour);
            budgetUnit = itemView.findViewById(R.id.sHours);
            book = itemView.findViewById(R.id.sBook);
        }
    }
}
