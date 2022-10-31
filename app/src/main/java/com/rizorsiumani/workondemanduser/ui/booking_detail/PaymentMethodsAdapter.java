package com.rizorsiumani.workondemanduser.ui.booking_detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.PaymentDataItem;
import com.rizorsiumani.workondemanduser.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PaymentMethodsAdapter extends RecyclerView.Adapter<PaymentMethodsAdapter.ViewHolder>{

    private List<PaymentDataItem> list;
    private Context context;
    private PaymentMethodsAdapter.AddressSelectionListener listener;

    public PaymentMethodsAdapter(List<PaymentDataItem> data, Context ctx) {
        this.list = data;
        this.context = ctx;
    }

    public void setonClickListener(PaymentMethodsAdapter.AddressSelectionListener listener){
        this.listener = listener;
    }


    @Override @NonNull
    public PaymentMethodsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View addressesList= layoutInflater.inflate(R.layout.payment_method_design, parent, false);

        return new PaymentMethodsAdapter.ViewHolder(addressesList, listener);
    }

    @Override
    public void onBindViewHolder(PaymentMethodsAdapter.ViewHolder holder, int position) {

        PaymentDataItem dataItem = list.get(position);
        holder.title.setText(dataItem.getTitle());
        Glide.with(context).load(Constants.IMG_PATH + dataItem.getImage()).into(holder.imageView);


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface AddressSelectionListener{
        void onSelect(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView imageView;


        public ViewHolder(View itemView, PaymentMethodsAdapter.AddressSelectionListener listener) {
            super(itemView);

            imageView = (itemView).findViewById(R.id.cashIcon);
            title = (itemView).findViewById(R.id.p_name);

            itemView.setOnClickListener(view -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION){
                    if (listener != null){
                        listener.onSelect(getAdapterPosition());
                    }
                }
            });


        }
    }

}

 