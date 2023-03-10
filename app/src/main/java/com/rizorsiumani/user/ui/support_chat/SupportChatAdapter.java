package com.rizorsiumani.user.ui.support_chat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.ReceiverModel;
import com.rizorsiumani.user.data.businessModels.SenderModel;
import com.rizorsiumani.user.data.businessModels.chat.SupportActionModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SupportChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int OUTGOING_VIEW_TYPE = 1;
    private static final int INCOMING_VIEW_TYPE = 0;
    private static final int ACTION_VIEW_TYPE = 2;

    private final Context context;
    private final ArrayList<Object> mMessageList;


    public SupportChatAdapter(Context appContext, ArrayList<Object> messagesItems) {
        this.context = appContext;
        this.mMessageList = messagesItems;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receiver_layout, parent, false);
            return new RecieverVH(view);

        }else if (viewType ==2){
            View viewSender = LayoutInflater.from(parent.getContext()).inflate(R.layout.support_message_layout, parent, false);
            return new SupportActionVH(viewSender);
        }else {
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
                    ReceiverModel data = (ReceiverModel) mMessageList.get(position);
                    rvh.message.setText(data.getMessage());

                }catch (NullPointerException e){
                    Log.e("support_chat", e.getMessage());
                }

                break;
            case 1:

                try {

                    SenderVH svh = (SenderVH) holder;
                    SenderModel data2 = (SenderModel) mMessageList.get(position);
                    Log.e("TAG", "onBindViewHolder: " + data2.getMessage() );
                    svh.senderMessage.setText(data2.getMessage());

                }catch (NullPointerException e){
                    Log.e("support_chat", e.getMessage());
                }
                break;

            case 2:

                try {

                    SupportActionVH Avh = (SupportActionVH) holder;
                    SupportActionModel actionModel = (SupportActionModel) mMessageList.get(position);
                    Log.e("TAG", "onBindViewHolder: " + actionModel.getMessage() );
                    Avh.action_msg.setText(actionModel.getMessage());

                }catch (NullPointerException e){
                    Log.e("support_chat", e.getMessage());
                }

                break;

        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object obj = mMessageList.get(position);

        if (obj instanceof ReceiverModel) {
            return INCOMING_VIEW_TYPE;
        }else if (obj instanceof SupportActionModel){
            return ACTION_VIEW_TYPE;
        }else {
            return OUTGOING_VIEW_TYPE;
        }
    }

    public static class RecieverVH extends RecyclerView.ViewHolder {
        private final TextView message;

        public RecieverVH(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.tv_receiver_message);

        }
    }



    public static class SenderVH extends RecyclerView.ViewHolder {
        private final TextView senderMessage;

        public SenderVH(@NonNull View itemView) {
            super(itemView);
            senderMessage = itemView.findViewById(R.id.tv_sender_message);
        }
    }

    public static class SupportActionVH extends RecyclerView.ViewHolder {
        private final TextView action_msg;

        public SupportActionVH(@NonNull View itemView) {
            super(itemView);
            action_msg = itemView.findViewById(R.id.support_message);
        }
    }


}

