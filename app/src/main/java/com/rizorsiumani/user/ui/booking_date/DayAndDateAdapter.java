package com.rizorsiumani.user.ui.booking_date;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.AvailabilityDataItem;

import java.util.List;

public class DayAndDateAdapter extends RecyclerView.Adapter<DayAndDateAdapter.ViewHolder> {

    private final List<AvailabilityDataItem> data;
    private final Context ctx;
    OnItemClickListener itemClickListener;
    public static int selectedPosition = -1;
    public static int  lastSelectedPos = -1;


    public void setOnDaySelectListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public DayAndDateAdapter(Context context, List<AvailabilityDataItem> list) {
        this.data = list;
        this.ctx = context;
    }

    @NonNull
    @Override
    public DayAndDateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.days_list_item, parent, false);
        return new DayAndDateAdapter.ViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DayAndDateAdapter.ViewHolder holder, int position) {
        AvailabilityDataItem dataItem = data.get(position);

        holder.day.setText(dataItem.getDay());
        if (position == selectedPosition){
            holder.selectedDay();
        }else {
            holder.unSelectedDay();
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
        public TextView day;
        public LinearLayout ll;

        public ViewHolder(@NonNull View itemView, DayAndDateAdapter.OnItemClickListener itemClickListener) {
            super(itemView);

            day = itemView.findViewById(R.id.tv_day);
            ll = itemView.findViewById(R.id.ll);

            itemView.setOnClickListener(view -> {
                if (itemClickListener != null) {

                    selectedPosition = getAdapterPosition();

                    if (lastSelectedPos != -1){
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

        public void selectedDay() {
            ll.setBackgroundColor(Color.parseColor("#00A688"));
            //date.setTextColor(Color.parseColor("#FFFFFFFF"));
            day.setTextColor(Color.parseColor("#FFFFFFFF"));
        }

        public void unSelectedDay() {
            ll.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
           // date.setTextColor(Color.parseColor("#FF000000"));
            day.setTextColor(Color.parseColor("#808080"));
        }
    }
}
