package com.rizorsiumani.workondemanduser.ui.sp_detail.service;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.ServicesDataItem;
import com.rizorsiumani.workondemanduser.data.local.TinyDB;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.ui.booking.BookService;
import com.rizorsiumani.workondemanduser.ui.booking.MyCartItems;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;
import com.rizorsiumani.workondemanduser.utils.Constants;

import java.util.List;

public class SpServicesAdapter extends RecyclerView.Adapter<SpServicesAdapter.ViewHolder> {

    private final List<ServicesDataItem> list;
    private final Context ctx;
    OnItemClickListener itemClickListener;

    public void setOnClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public SpServicesAdapter(List<ServicesDataItem> data, Context context) {
        this.list = data;
        this.ctx = context;
    }

    @NonNull
    @Override
    public SpServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.services_list_design, parent, false);
        return new SpServicesAdapter.ViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SpServicesAdapter.ViewHolder holder, int position) {
        ServicesDataItem dataItem = list.get(position);
        holder.name.setText(dataItem.getTitle());
        holder.description.setText(dataItem.getDescription());
        holder.budget.setText(Constants.constant.CURRENCY + dataItem.getPrice());
        holder.budgetUnit.setText("(" + dataItem.getPriceUnit() +")");

        if (TinyDbManager.getCartData().size() > 0){
            for (int i = 0; i < TinyDbManager.getCartData().size(); i++) {
                MyCartItems cartItems = TinyDbManager.getCartData().get(i);
                if (cartItems.getData().getId() == dataItem.getId()){
                    holder.book.setEnabled(false);
                    holder.book.setText("Booked");
                    holder.book.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#d1001f")));
                }else {
                    holder.book.setEnabled(true);
                    holder.book.setText("Book Now");
                    holder.book.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));

                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public interface OnItemClickListener{
        void onBookClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,description, budget,book,budgetUnit;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            name = itemView.findViewById(R.id.sName);
            description = itemView.findViewById(R.id.sDetail);
            budget = itemView.findViewById(R.id.sPerHour);
            budgetUnit = itemView.findViewById(R.id.sHours);
            book = itemView.findViewById(R.id.sBook);

            book.setOnClickListener(view -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION){
                    if (listener != null){
                        listener.onBookClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
