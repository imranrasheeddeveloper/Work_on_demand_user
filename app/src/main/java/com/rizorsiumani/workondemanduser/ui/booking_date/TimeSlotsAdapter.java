package com.rizorsiumani.workondemanduser.ui.booking_date;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.AvailabilityHoursItem;

import java.util.List;

public class TimeSlotsAdapter extends RecyclerView.Adapter<TimeSlotsAdapter.ViewHolder> {

    private final List<AvailabilityHoursItem> data;
    private final Context ctx;
    OnItemClickListener itemClickListener;
    public static int selectedPosition = -1;
    public static int lastSelectedPos = -1;


    public void setOnSlotClickListener(TimeSlotsAdapter.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public TimeSlotsAdapter(Context context, List<AvailabilityHoursItem> list) {
        this.data = list;
        this.ctx = context;
    }

    @NonNull
    @Override
    public TimeSlotsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.availability_hours_design, parent, false);
        return new TimeSlotsAdapter.ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotsAdapter.ViewHolder holder, int position) {
        AvailabilityHoursItem hoursItem = data.get(position);

        holder.fromTime.setText(hoursItem.getFromTime() + " to " + hoursItem.getToTime());

        if (position == selectedPosition) {
            holder.selectedTimeSlot();
        } else {
            holder.unSelectedTimeSlot();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickListener {
        void onTimeSelect(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView fromTime;
        CardView card;
        ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView, TimeSlotsAdapter.OnItemClickListener itemClickListener) {
            super(itemView);

            fromTime = itemView.findViewById(R.id.fromTime);
            card = itemView.findViewById(R.id.slotCard);
            layout = itemView.findViewById(R.id.timeView);

            itemView.setOnClickListener(view -> {
                if (itemClickListener != null) {

                    selectedPosition = getAdapterPosition();

                    if (lastSelectedPos != -1) {
                        notifyItemChanged(lastSelectedPos);
                    }

                    lastSelectedPos = selectedPosition;
                    notifyItemChanged(selectedPosition);

                    if (selectedPosition != RecyclerView.NO_POSITION) {
                        itemClickListener.onTimeSelect(selectedPosition);
                    }

                }
            });


        }

        public void selectedTimeSlot() {
            //card.setUseCompatPadding(false);
            layout.setBackgroundColor(Color.parseColor("#00A688"));
            fromTime.setTextColor(Color.parseColor("#FFFFFFFF"));

            card.setEnabled(false);
        }

        public void unSelectedTimeSlot() {
           // card.setUseCompatPadding(true);
            layout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            fromTime.setTextColor(Color.parseColor("#FF000000"));
            card.setEnabled(true);

        }
    }

}