package com.rizorsiumani.workondemanduser.ui.booking_detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.ui.booking.MyCartItems;

import java.util.List;

public class CartServicesAdapter extends RecyclerView.Adapter<CartServicesAdapter.ViewHolder> {

    private final List<MyCartItems> list;
    private final Context ctx;


    public CartServicesAdapter(List<MyCartItems> data, Context context) {
        this.list = data;
        this.ctx = context;
    }

    @NonNull
    @Override
    public CartServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_service_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartServicesAdapter.ViewHolder holder, int position) {
        MyCartItems cartItems = list.get(position);
        holder.name.setText(cartItems.getData().getTitle());
        holder.price.setText(cartItems.getData().getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.sName);
            price = itemView.findViewById(R.id.sRate);

        }
    }
}
