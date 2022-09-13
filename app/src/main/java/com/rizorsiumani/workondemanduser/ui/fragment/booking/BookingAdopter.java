package com.rizorsiumani.workondemanduser.ui.fragment.booking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.rizorsiumani.workondemanduser.R;
import com.skydoves.elasticviews.ElasticButton;

import java.util.List;

public class BookingAdopter extends RecyclerView.Adapter<BookingAdopter.ViewHolder>{

    private final List<String>  list;
    private Context context;
    private ItemClickListener mListener;

    public void setOnBookingClickListener(ItemClickListener listener) {
        mListener = listener;
    }

    // RecyclerView recyclerView;
    public BookingAdopter(List<String> status, Context ctx) {
        this.list = status;
        this.context = ctx;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View car_list= layoutInflater.inflate(R.layout.booking_list_item_design, parent, false);
        return new ViewHolder(car_list,mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String item = list.get(position);

        holder.status.setText(item);


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener{
        void onBookingClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView status, pickup_address, dropof_address, time,date,token;
        ElasticButton cancel,reshedule;


        public ViewHolder(View itemView, ItemClickListener mListener) {
            super(itemView);


            status = itemView.findViewById(R.id.booking_status);


        }
    }
}


