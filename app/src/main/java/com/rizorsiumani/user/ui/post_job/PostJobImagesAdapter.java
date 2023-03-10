package com.rizorsiumani.user.ui.post_job;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rizorsiumani.user.R;

import java.util.List;

public class PostJobImagesAdapter extends RecyclerView.Adapter<PostJobImagesAdapter.ViewHolder>{

    private final List<String> list;
    private Context context;

    public PostJobImagesAdapter(List<String> imagesPath, Context ctx) {
        this.list = imagesPath;
        this.context = ctx;
    }


    @NonNull
    @Override
    public PostJobImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.add_images_design, parent, false);
        return new PostJobImagesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostJobImagesAdapter.ViewHolder holder, int position) {

        String path = list.get(position);
        Glide.with(context).load(path).into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    //listener interface
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

         ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (itemView).findViewById(R.id.iv_add);

        }


    }

}


