package com.rizorsiumani.user.ui.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.user.App;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.HomeContentDataItem;
import com.rizorsiumani.user.data.local.TinyDbManager;
import com.rizorsiumani.user.ui.sp_detail.SpProfile;

import java.util.List;

public class AllSerAdapter extends RecyclerView.Adapter<AllSerAdapter.ViewHolder> {

    private final List<HomeContentDataItem> data;
    OnItemClickListener itemClickListener;
    Context ctx;


    public AllSerAdapter(Context context, List<HomeContentDataItem> serviceModels) {
        this.ctx = context;
        this.data = serviceModels;
    }


    public void setOnServiceClickListener(AllSerAdapter.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public AllSerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_categories_design, parent, false);
        return new AllSerAdapter.ViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AllSerAdapter.ViewHolder holder, int position) {

        try {

        HomeContentDataItem model = data.get(position);
        holder.name.setText(model.getTitle());


        LinearLayoutManager layoutManager1 = new LinearLayoutManager(App.applicationContext, RecyclerView.HORIZONTAL, false);
        holder.recyclerView.setLayoutManager(layoutManager1);
        PromotionalAdapter adapter1 = new PromotionalAdapter(model.getServiceProviderCategories());
        holder.recyclerView.setAdapter(adapter1);

        adapter1.setOnCellClickListener(pos -> {
            TinyDbManager.saveServiceProviderID(String.valueOf(model.getServiceProviderCategories().get(pos).getServiceProvider().getId()));
            Intent intent = new Intent(ctx,SpProfile.class);
            ctx.startActivity(intent);
        });


        }catch (NullPointerException | IllegalStateException | IllegalArgumentException e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickListener {
        void onServiceSelect(int position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView recyclerView;
        public TextView name , viewAll;

        public ViewHolder(@NonNull View itemView, AllSerAdapter.OnItemClickListener itemClickListener) {
            super(itemView);
            //find views
            name = itemView.findViewById(R.id.tv_name);
            recyclerView = itemView.findViewById(R.id.list);
            viewAll = itemView.findViewById(R.id.tv_viewAll1);

            viewAll.setOnClickListener(view -> {
                if (itemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickListener.onServiceSelect(position);
                    }
                }
            });

        }
    }
}
