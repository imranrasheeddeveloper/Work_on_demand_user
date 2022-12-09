package com.rizorsiumani.workondemanduser.ui.job_timing;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.R;

import java.util.List;

public class DayAndDateAdapter1 extends RecyclerView.Adapter<DayAndDateAdapter1.ViewHolder> {

    private final List<DayTimeModel> data;
    private final Context ctx;
    OnItemClickListener itemClickListener;
    public static int selectedPosition = -1;
    public static int lastSelectedPos = -1;


    public void setOnDaySelectListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public DayAndDateAdapter1(Context context, List<DayTimeModel> list) {
        this.data = list;
        this.ctx = context;
    }

    @NonNull
    @Override
    public DayAndDateAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.days_list_item, parent, false);
        return new DayAndDateAdapter1.ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DayAndDateAdapter1.ViewHolder holder, int position) {
        DayTimeModel dayTimeModel = data.get(position);

        holder.day.setText(dayTimeModel.getDay());
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
        public TextView day, date;
        public LinearLayout ll;

        public ViewHolder(@NonNull View itemView, DayAndDateAdapter1.OnItemClickListener itemClickListener) {
            super(itemView);

            day = itemView.findViewById(R.id.tv_day);
            // date = itemView.findViewById(R.id.tv_date);

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
//            date.setTextColor(Color.parseColor("#FFFFFFFF"));
            day.setTextColor(Color.parseColor("#FFFFFFFF"));
        }

        public void unSelectedDay() {
            ll.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
           // date.setTextColor(Color.parseColor("#FF000000"));
            day.setTextColor(Color.parseColor("#808080"));
        }
    }
}
