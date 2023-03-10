package com.rizorsiumani.user.ui.post_job;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.user.R;
import com.rizorsiumani.user.ui.job_timing.TimeItem;

import java.util.List;

public class SelectedTimeAdapter extends RecyclerView.Adapter<SelectedTimeAdapter.ViewHolder> {

    private final List<TimeItem> data;
    private final Context ctx;
    OnItemClickListener mListener;


    public void setOnSlotClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public SelectedTimeAdapter(Context context, List<TimeItem> list) {
        this.data = list;
        this.ctx = context;
    }

    @NonNull
    @Override
    public SelectedTimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_time_design, parent, false);
        return new SelectedTimeAdapter.ViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedTimeAdapter.ViewHolder holder, int position) {
        try {

        TimeItem itemModel = data.get(position);
        holder.label.setText(itemModel.getFromTime() + " to " + itemModel.getToTime());

        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    public interface OnItemClickListener {
        void onTimeDelete(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView label;
        public ImageView delete_icon;

        public ViewHolder(@NonNull View itemView, OnItemClickListener mListener) {
            super(itemView);

            label = itemView.findViewById(R.id.time_label);
            delete_icon = itemView.findViewById(R.id.delete_time);

            delete_icon.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION){
                    if (mListener != null){
                        mListener.onTimeDelete(getAdapterPosition());
                    }
                }
            });

        }

    }


}
