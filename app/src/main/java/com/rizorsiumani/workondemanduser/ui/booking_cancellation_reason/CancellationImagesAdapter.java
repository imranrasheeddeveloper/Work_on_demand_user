package com.rizorsiumani.workondemanduser.ui.booking_cancellation_reason;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.R;

import java.util.ArrayList;
import java.util.List;

public class CancellationImagesAdapter extends RecyclerView.Adapter<CancellationImagesAdapter.ViewHolder> {

    private final ArrayList<Uri> list;
    private final Context ctx;

    public CancellationImagesAdapter(ArrayList<Uri> data, Context context) {
        this.list = data;
        this.ctx = context;
    }

    @NonNull
    @Override
    public CancellationImagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.images_holder_design, parent, false);
        return new CancellationImagesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CancellationImagesAdapter.ViewHolder holder, int position) {
        Uri uri = list.get(position);
        holder.imageView.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.r_image);
        }
    }
}
