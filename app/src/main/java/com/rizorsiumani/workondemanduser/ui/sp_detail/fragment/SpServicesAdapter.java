package com.rizorsiumani.workondemanduser.ui.sp_detail.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.ui.address.SavedAddresses;
import com.rizorsiumani.workondemanduser.ui.booking.BookService;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

import java.util.List;

public class SpServicesAdapter extends RecyclerView.Adapter<SpServicesAdapter.ViewHolder> {

    private final List<String> list;
    private final Context ctx;

    public SpServicesAdapter(List<String> data, Context context) {
        this.list = data;
        this.ctx = context;
    }

    @NonNull
    @Override
    public SpServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.services_list_design, parent, false);
        return new SpServicesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpServicesAdapter.ViewHolder holder, int position) {
        String name = list.get(position);
        holder.name.setText(name);

        holder.book.setOnClickListener(view -> {
            ActivityUtil.gotoPage(ctx, BookService.class);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,book;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.sName);
            book = itemView.findViewById(R.id.sBook);
        }
    }
}
