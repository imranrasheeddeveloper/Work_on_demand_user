package com.rizorsiumani.workondemanduser.ui.chat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.ReceiverModel;
import com.rizorsiumani.workondemanduser.data.businessModels.SenderModel;
import com.rizorsiumani.workondemanduser.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> data;
    private Context context;
    OnItemClickListener itemClickListener;
    private static final int OUTGOING_VIEW_TYPE = 1;
    private static final int INCOMING_VIEW_TYPE = 0;

    public void setOnChatClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public ChatAdapter(Context appContext, ArrayList<Object> list) {
        this.context = appContext;
        this.data = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receiver_layout, parent, false);
            return new RecieverVH(view);
        } else {
            View viewSender = LayoutInflater.from(parent.getContext()).inflate(R.layout.sender_msg_layout, parent, false);
            return new SenderVH(viewSender);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 0:

                try {

                    RecieverVH rvh = (RecieverVH) holder;
                    ReceiverModel data1 = (ReceiverModel) data.get(position);
                    rvh.message.setText(data1.getMessage());

                    String rStime;
                    String[] rSentTime = data1.getTime().split("T");
                    rStime = rSentTime[1].substring(0, Math.min(rSentTime[1].length(), 5));
                    rvh.time.setText(Constants.constant.getTime(data1.getTime()));

//                    if (data.getReceiver() != null){
//                        ((RecieverVH) holder).imageView.setImageResource(Constants.getAvatarIcon(context, Integer.parseInt(data.getReceiver().getAvatar())));
//
////                    Glide.with(context).load(Constants.IMG_PATH + data.getReceiver().getAvatar()).placeholder(R.color.images_placeholder).into(((RecieverVH) holder).imageView);
//                        ((RecieverVH) holder).name.setText(data.getReceiver().getName());
//                        ((RecieverVH) holder).name.setVisibility(View.VISIBLE);
//                        ((RecieverVH) holder).imageView.setVisibility(View.VISIBLE);
//                    }
                } catch (NullPointerException e) {
                    Log.e("chat", e.getMessage());
                }

                break;
            case 1:

                try {

                    SenderVH svh = (SenderVH) holder;
                    SenderModel data2 = (SenderModel) data.get(position);
                    Log.e("TAG", "onBindViewHolder: " + data2.getMessage());
                    svh.senderMessage.setText(data2.getMessage());
                    String sSTime;
                    String[] sSentTime = data2.getTime().split("T");
                    sSTime = sSentTime[1].substring(0, Math.min(sSentTime[1].length(), 5));
                    // svh.time.setText(sSTime);
                } catch (NullPointerException e) {
                    Log.e("chat", e.getMessage());
                }
                break;

        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object obj = data.get(position);

        if (obj instanceof SenderModel) {
            return OUTGOING_VIEW_TYPE;
        } else {
            return INCOMING_VIEW_TYPE;
        }
    }

    public interface OnItemClickListener {
        void onChatSelect(int position);
    }


    public static class RecieverVH extends RecyclerView.ViewHolder {
        private final TextView message, time;

        public RecieverVH(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.tv_receiver_message);
            time = itemView.findViewById(R.id.receiver_time);
        }
    }


    public static class SenderVH extends RecyclerView.ViewHolder {
        private final TextView senderMessage;

        public SenderVH(@NonNull View itemView) {
            super(itemView);
            senderMessage = itemView.findViewById(R.id.tv_sender_message);
        }
    }
}

