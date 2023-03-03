package com.rizorsiumani.workondemanduser.ui.fragment.booking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.GetBookingDataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProvider;
import com.rizorsiumani.workondemanduser.utils.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookingAdopter extends RecyclerView.Adapter<BookingAdopter.ViewHolder> {

    final List<GetBookingDataItem> list;
    Context context;
    ItemClickListener mListener;
    String current_status;

    public void setOnBookingClickListener(ItemClickListener listener) {
        mListener = listener;
    }

    // RecyclerView recyclerView;
    public BookingAdopter(List<GetBookingDataItem> data, String status, Context ctx) {
        this.list = data;
        this.current_status = status;
        this.context = ctx;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View car_list = layoutInflater.inflate(R.layout.booking_list_item_design, parent, false);
        return new ViewHolder(car_list, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try {

            if (current_status.equalsIgnoreCase("Pending")) {
                holder.requested.setVisibility(View.VISIBLE);
                holder.cancel.setVisibility(View.VISIBLE);

            } else if (current_status.equalsIgnoreCase("In Progress")){
                holder.cancel.setText("Cancel");
                holder.requested.setVisibility(View.GONE);
                holder.cancel.setVisibility(View.VISIBLE);
            }else if (current_status.equalsIgnoreCase("Declined")){
                holder.requested.setVisibility(View.GONE);
                holder.cancel.setVisibility(View.GONE);
            }else if (current_status.equalsIgnoreCase("Approval")){
                holder.requested.setText("Approve");
                holder.cancel.setText("Cancel");
                holder.cancel.setVisibility(View.VISIBLE);
                holder.requested.setVisibility(View.VISIBLE);
            }else if (current_status.equalsIgnoreCase("Cancelled")){
                holder.requested.setVisibility(View.GONE);
                holder.cancel.setVisibility(View.GONE);
            }else if (current_status.equalsIgnoreCase("Completed")){
                holder.requested.setVisibility(View.GONE);
                holder.cancel.setVisibility(View.GONE);
                holder.rate.setVisibility(View.VISIBLE);
            }else {

            }
            GetBookingDataItem item = list.get(position);

            holder.status.setText(item.getStatus());
            holder.token.setText("# "+ item.getId());

            holder.date.setText(Constants.constant.getDate(item.getStart_date()));
            String cc = Constants.constant.getBookingDate(item.getCreatedAt());
            String[] date_value = cc.split(" ");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDateTime now = LocalDateTime.now();
                if (now.getYear() == Integer.parseInt(date_value[2]) && now.getMonth().getValue() == Integer.parseInt(date_value[1])){
                    if (now.getDayOfMonth() == Integer.parseInt(date_value[0])){
                    holder.cancel.setVisibility(View.VISIBLE);
                    }else {
                        holder.cancel.setVisibility(View.VISIBLE);
                    }
                }else {
                    holder.cancel.setVisibility(View.GONE);
                }
            }


            holder.service.setHorizontallyScrolling(true);
            holder.service.setFocusable(true);
            holder.service.setSelected(true);
            holder.service.setText(item.getService().getTitle());
            holder.description.setText(item.getDescription());
            holder.total.setText(Constants.constant.CURRENCY + item.getTotal());
            if (item.getServiceProvider() != null) {
                ServiceProvider provider = item.getServiceProvider();
                holder.name.setText(provider.getFirstName() + " " + provider.getLastName());
                Glide.with(context).
                        load(Constants.IMG_PATH + provider.getProfilePhoto())
                        .placeholder(R.color.teal_700)
                        .into(holder.circleImageView);
            }

            if (item.getServiceProvider().getServiceProviderReviews() != null && item.getServiceProvider().getServiceProviderReviews().size() > 0){
                int ratings = 0;
                for (int i = 0; i < item.getServiceProvider().getServiceProviderReviews().size(); i++) {
                    int currentRating = item.getServiceProvider().getServiceProviderReviews().get(i).getRaiting();
                    ratings  = ratings + currentRating;
                }
                int average = ratings / item.getServiceProvider().getServiceProviderReviews().size();
                if (average > 0){
                    holder.ratingBar.setRating((float) average);
                }
            }


        } catch (NullPointerException | IllegalStateException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener {
        void onReschedule(int position);

        void cancelBooking(int position);

        void bookingInformation(int position);

        void rateBooking(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView status, service, name, description, token, total, date, time, rate;
        Button requested, cancel;
        CircleImageView circleImageView;
        RatingBar ratingBar;



        public ViewHolder(View itemView, ItemClickListener mListener) {
            super(itemView);


            ratingBar = itemView.findViewById(R.id.arting);
            status = itemView.findViewById(R.id.booking_status);
            service = itemView.findViewById(R.id.booking_service_title);
            token = itemView.findViewById(R.id.booking_token);
            name = itemView.findViewById(R.id.tv_sp_name);
            circleImageView = itemView.findViewById(R.id.booking_iv_sp);
            token = itemView.findViewById(R.id.booking_token);
            cancel = itemView.findViewById(R.id.cancel_booking);
            description = itemView.findViewById(R.id.description);
            total = itemView.findViewById(R.id.booking_total);
            date = itemView.findViewById(R.id.booking_date);
            time = itemView.findViewById(R.id.booking_time);
            rate = itemView.findViewById(R.id.rate_provider);

            requested = itemView.findViewById(R.id.requested_booking);

            cancel.setOnClickListener(view -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.cancelBooking(position);
                    }
                }
            });

            requested.setOnClickListener(view -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onReschedule(position);
                    }
                }
            });

            itemView.setOnClickListener(view -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.bookingInformation(position);
                    }
                }
            });

            rate.setOnClickListener(view -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.rateBooking(position);
                    }
                }
            });
        }
    }
}


