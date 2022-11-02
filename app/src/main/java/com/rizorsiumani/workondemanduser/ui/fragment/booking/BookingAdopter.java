package com.rizorsiumani.workondemanduser.ui.fragment.booking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.GetBookingDataItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookingAdopter extends RecyclerView.Adapter<BookingAdopter.ViewHolder>{

    private final List<GetBookingDataItem>  list;
    private Context context;
    private ItemClickListener mListener;

    public void setOnBookingClickListener(ItemClickListener listener) {
        mListener = listener;
    }

    // RecyclerView recyclerView;
    public BookingAdopter(List<GetBookingDataItem> status, Context ctx) {
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

        GetBookingDataItem item = list.get(position);

        holder.status.setText(item.getStatus());
        holder.service.setHorizontallyScrolling(true);
        holder.service.setFocusable(true);
        holder.service.setSelected(true);
        holder.service.setText(item.getService().getTitle());
        holder.name.setText(item.getServiceProvider().getFirstName() + " " + item.getServiceProvider().getLastName());



    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener{
        void allRequestedBookings(int position);
        void cancelBooking(int position);
        void bookingInformation(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView status, service, name,date,token;
        Button requested,cancel;
        CircleImageView circleImageView;


        public ViewHolder(View itemView, ItemClickListener mListener) {
            super(itemView);


            status = itemView.findViewById(R.id.booking_status);
            service = itemView.findViewById(R.id.booking_service_title);
            token = itemView.findViewById(R.id.booking_token);
            name = itemView.findViewById(R.id.tv_sp_name);
            circleImageView = itemView.findViewById(R.id.iv_sp);
            token = itemView.findViewById(R.id.booking_token);
            cancel = itemView.findViewById(R.id.cancel_booking);

            requested = itemView.findViewById(R.id.requested_booking);

            cancel.setOnClickListener(view -> {
                if (mListener!= null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.cancelBooking(position);
                    }
                }
            });

            requested.setOnClickListener(view -> {
                if (mListener!= null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.allRequestedBookings(position);
                    }
                }
            });

            itemView.setOnClickListener(view -> {
                if (mListener!= null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.bookingInformation(position);
                    }
                }
            });
        }
    }
}


