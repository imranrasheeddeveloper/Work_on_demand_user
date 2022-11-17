package com.rizorsiumani.workondemanduser.ui.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.ui.booking_detail.model.DataItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {

    private final List<DataItem> list;
    private final Context ctx;
    ItemClickListener itemClickListener;

    public void setOnCardClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CardsAdapter(List<DataItem> data, Context context) {
        this.list = data;
        this.ctx = context;
    }

    @NonNull
    @Override
    public CardsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_design, parent, false);
        return new CardsAdapter.ViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CardsAdapter.ViewHolder holder, int position) {
        DataItem dataItem = list.get(position);
        holder.number.setText("**********" + dataItem.getLast4());
        if (dataItem.getBrand() != null){
            String brand = dataItem.getBrand();
            if (brand.equalsIgnoreCase("Visa")){
                holder.icon.setImageResource(R.drawable.ic_visa);
            }else if (brand.equalsIgnoreCase("American Express")){
                holder.icon.setImageResource(R.drawable.ic_amex);
            }else if (brand.equalsIgnoreCase("Master Card")){
                holder.icon.setImageResource(R.drawable.ic_mastercard);
            }else {
                holder.icon.setImageResource(R.drawable.ic_mastercard);
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener {
        void onCardClick(int position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView number;
        ImageView icon;
        ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView, ItemClickListener mListener) {
            super(itemView);

            number = itemView.findViewById(R.id.card_number);
            icon = itemView.findViewById(R.id.card_icon);
            layout = itemView.findViewById(R.id.card_layout);

            layout.setOnClickListener(view -> {
                if (mListener!= null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onCardClick(position);
                    }
                }
            });


        }
    }
}
