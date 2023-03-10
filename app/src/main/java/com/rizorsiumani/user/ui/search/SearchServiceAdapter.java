package com.rizorsiumani.user.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.SubCategoryDataItem;
import com.rizorsiumani.user.utils.Constants;

import java.util.List;

public class SearchServiceAdapter extends RecyclerView.Adapter<SearchServiceAdapter.ViewHolder> {

    private final List<SubCategoryDataItem> list;
    private final Context ctx;
    ItemClickListener listener;

    public void setOnCategoryClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public SearchServiceAdapter(List<SubCategoryDataItem> data, Context context) {
        this.list = data;
        this.ctx = context;
    }

    @NonNull
    @Override
    public SearchServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_service_list_item_design, parent, false);
        return new SearchServiceAdapter.ViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchServiceAdapter.ViewHolder holder, int position) {
        SubCategoryDataItem dataItem = list.get(position);
        holder.name.setText(dataItem.getTitle());
        Glide.with(ctx)
                .load(Constants.IMG_PATH + dataItem.getImage())
                .into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //listener interface
    public interface ItemClickListener {
        void onCategoryClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView icon;

        public ViewHolder(@NonNull View itemView, ItemClickListener listener) {
            super(itemView);

            name = itemView.findViewById(R.id.serName);
            icon = itemView.findViewById(R.id.ser_image);

            itemView.setOnClickListener(view -> {
                if (listener!= null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onCategoryClick(position);
                    }
                }
            });

        }
    }
}
