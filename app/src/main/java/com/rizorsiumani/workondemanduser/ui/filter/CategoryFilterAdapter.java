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
import com.rizorsiumani.workondemanduser.data.businessModels.CategoriesDataItem;

import java.util.List;

public class CategoryFilterAdapter extends RecyclerView.Adapter<CategoryFilterAdapter.ViewHolder>{

    private final List<CategoriesDataItem> categories;
    private Context context;
    private OnItemClickListener mListener;

    public CategoryFilterAdapter(List<CategoriesDataItem> category, Context ctx) {
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

        CategoriesDataItem dataItem = categories.get(position);
        holder.cat.setText(dataItem.getTitle());
//        holder.selector.setOnClickListener(view -> {
//            if (holder.selector.getTag().toString().equalsIgnoreCase("unselect")){
//                holder.selector.setImageResource(R.drawable.selected_value);
//                holder.selector.setTag("selected");
//            }else {
//                holder.selector.setImageResource(R.drawable.unselected_value);
//                holder.selector.setTag("unselect");
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }

    //listener interface
    public interface OnItemClickListener {
        void onSelect(int position);
        void onUnselect(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView cat;
        ImageView selector;

        public ViewHolder(View itemView,OnItemClickListener listener) {
            super(itemView);

            cat = (itemView).findViewById(R.id.serviceName01);
            selector = (itemView).findViewById(R.id.selectCat);

            selector.setOnClickListener(view -> {
                 if (selector.getTag().toString().equalsIgnoreCase("unselect")){
                     selector.setImageResource(R.drawable.selected_value);
                     selector.setTag("selected");
                     listener.onSelect(getAdapterPosition());

                 }else {
                     selector.setImageResource(R.drawable.unselected_value);
                     selector.setTag("unselect");
                     listener.onUnselect(getAdapterPosition());
                 }
            });


//            //setListener
//            itemView.setOnClickListener(v -> {
//                if (listener != null) {
//                        listener.onItemClick(getAdapterPosition());
//                }
//            });

        }


    }

}


