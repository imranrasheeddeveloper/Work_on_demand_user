package com.rizorsiumani.user.ui.fragment.booking;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.user.R;

import java.util.List;

public class BookingStatusAdapter extends RecyclerView.Adapter<BookingStatusAdapter.ViewHolder> {

    private final List<String> data;
    private final Context ctx;
    OnItemClickListener itemClickListener;
    public static int selectedPosition = -1;
    public static int  lastSelectedPos = -1;


    public void setOnStatusSelectListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public BookingStatusAdapter(Context context, List<String> list) {
        this.data = list;
        this.ctx = context;
    }

    @NonNull
    @Override
    public BookingStatusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_status_design, parent, false);
        return new BookingStatusAdapter.ViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingStatusAdapter.ViewHolder holder, int position) {
        String status = data.get(position);

        holder.stats.setText(status);


        if (position == selectedPosition){
            holder.selectedStatus();
        }else {
            holder.unSelectedStatus();
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickListener {
        void onSelect(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView stats;

        public ViewHolder(@NonNull View itemView, BookingStatusAdapter.OnItemClickListener itemClickListener) {
            super(itemView);

            stats = itemView.findViewById(R.id.status);

            itemView.setOnClickListener(view -> {
                if (itemClickListener != null) {

                    selectedPosition = getAdapterPosition();

                    if (lastSelectedPos != -1){
                        notifyItemChanged(lastSelectedPos);
                    }

                        lastSelectedPos = selectedPosition;
                        notifyItemChanged(selectedPosition);

                    if (selectedPosition != RecyclerView.NO_POSITION) {
                        itemClickListener.onSelect(selectedPosition);
                    }
                }
            });
        }

        public void selectedStatus() {
            stats.setBackgroundColor(Color.parseColor("#00A688"));
            stats.setTextColor(Color.parseColor("#FFFFFFFF"));
        }

        public void unSelectedStatus() {
            stats.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            stats.setTextColor(Color.parseColor("#FF000000"));
        }
    }
}
