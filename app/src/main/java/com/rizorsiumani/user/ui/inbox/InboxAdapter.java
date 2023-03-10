package com.rizorsiumani.user.ui.inbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.inbox.InboxDataItem;
import com.rizorsiumani.user.utils.Constants;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> {

    private final List<InboxDataItem> data;
    private final Context context;
    OnItemClickListener itemClickListener;


    public void setOnChatClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public InboxAdapter(List<InboxDataItem> list, Context appContext) {
        this.context = appContext;
        this.data = list;
    }

    @NonNull
    @Override
    public InboxAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_design, parent, false);
        return new InboxAdapter.ViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull InboxAdapter.ViewHolder holder, int position) {

        try {

            InboxDataItem model = data.get(position);
            holder.name.setText(model.getServiceProvider().getFirstName() + " " +model.getServiceProvider().getLastName());
            holder.message.setText(model.getLastMsg());
            Glide.with(context)
                    .load(Constants.IMG_PATH + model.getServiceProvider().getProfilePhoto())
                    .placeholder(R.drawable.ic_profile)
                    .into(holder.image);

        }catch (NullPointerException | IllegalStateException e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickListener {
        void onChatSelect(int position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,message;
        CircleImageView image;


        public ViewHolder(@NonNull View itemView, InboxAdapter.OnItemClickListener itemClickListener) {
            super(itemView);
            //find views
            name = itemView.findViewById(R.id.inbox_name);
            image = itemView.findViewById(R.id.iv_inbox);
            message = itemView.findViewById(R.id.last_message);


            itemView.setOnClickListener(view -> {
                if (itemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickListener.onChatSelect(position);
                    }
                }
            });

        }
    }
}
