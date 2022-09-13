package com.rizorsiumani.workondemanduser.ui.fragment.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private final List<String> services;
    private final Context ctx;
    private final List<Integer> icons;


    public ServicesAdapter(Context context, List<String> names, List<Integer> service_icons) {
        this.ctx = context;
        this.services = names;
        this.icons = service_icons;
    }

    @NonNull
    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.services_list_item_design, parent, false);
        return new ServicesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesAdapter.ViewHolder holder, int position) {
        String name = services.get(position);
        Integer icon = icons.get(position);
        holder.imageView.setImageResource(icon);
        holder.serName.setText(name);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView serName;
        public CircleImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            serName = itemView.findViewById(R.id.tv_service_name);
            imageView = itemView.findViewById(R.id.ser_image);
        }
    }
}
