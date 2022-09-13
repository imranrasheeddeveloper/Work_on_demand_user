package com.rizorsiumani.workondemanduser.ui.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.R;

import java.util.List;

public class CategoryFilterAdapter extends RecyclerView.Adapter<CategoryFilterAdapter.ViewHolder>{

    private final List<String> categories;
    private Context context;
    private OnItemClickListener mListener;

    public CategoryFilterAdapter(List<String> category, Context ctx) {
        this.categories = category;
        this.context = ctx;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }//onClick

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View car_list= layoutInflater.inflate(R.layout.filter_category_item_design, parent, false);
        return new ViewHolder(car_list,mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String current_category = categories.get(position);
        holder.cat.setText(current_category);

    }


    @Override
    public int getItemCount() {
        return categories.size();
    }

    //listener interface
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView cat;
        static ImageView selector;

        public ViewHolder(View itemView,OnItemClickListener listener) {
            super(itemView);

            cat = (itemView).findViewById(R.id.serviceName01);
             selector = (itemView).findViewById(R.id.selectCat);

             selector.setOnClickListener(view -> {
                 if (ViewHolder.selector.getTag().toString().equalsIgnoreCase("unselect")){
                     ViewHolder.selector.setImageResource(R.drawable.selected_value);
                     ViewHolder.selector.setTag("selected");
                 }else {
                     ViewHolder.selector.setImageResource(R.drawable.unselected_value);
                     ViewHolder.selector.setTag("unselect");
                 }
             });

            //setListener
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });

        }


    }

}


