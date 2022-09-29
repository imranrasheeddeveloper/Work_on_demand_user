package com.rizorsiumani.workondemanduser.ui.fragment.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.SerCategoryModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private final List<SerCategoryModel> services;
    private final Context ctx;




    public CategoriesAdapter(Context context, List<SerCategoryModel> list) {
        this.ctx = context;
        this.services = list;
    }

    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item_design, parent, false);
        return new CategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, int position) {
        SerCategoryModel model = services.get(position);

        holder.serName.setText(model.getName());
        holder.layout.setImageResource(model.getBackground());
        holder.serImage.setImageResource(model.getIcon());

    }

    @Override
    public int getItemCount() {
        return services.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView serName;
        public ImageView serImage;
        public ImageView layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            serName = itemView.findViewById(R.id.sName);
            serImage = itemView.findViewById(R.id.sImage);
            layout = itemView.findViewById(R.id.cat_layout);

        }
    }
}
