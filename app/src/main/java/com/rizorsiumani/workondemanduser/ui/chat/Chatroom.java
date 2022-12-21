package com.rizorsiumani.workondemanduser.ui.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.ReceiverModel;
import com.rizorsiumani.workondemanduser.data.businessModels.SenderModel;
import com.rizorsiumani.workondemanduser.data.businessModels.chat.MessagesItem;
import com.rizorsiumani.workondemanduser.data.businessModels.inbox.ServiceProvider;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.ActivityChatroomBinding;
import com.rizorsiumani.workondemanduser.utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Chatroom extends BaseActivity<ActivityChatroomBinding> {

    String inboxID;
    ServiceProvider provider;
    ChatViewModel viewModel;
    String token;
    private ArrayList<Object> msgs;
    ChatAdapter adapter;


    @Override
    protected ActivityChatroomBinding getActivityBinding() {
        return ActivityChatroomBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        token = prefRepository.getString("token");
        try {
            inboxID = getIntent().getStringExtra("inbox_id");
            String data = getIntent().getStringExtra("provider_detail");
            Gson gson = new Gson();
            provider = gson.fromJson(data, ServiceProvider.class);

            Glide.with(Chatroom.this).load(Constants.IMG_PATH + provider.getProfilePhoto())
                    .placeholder(R.drawable.ic_profile)
                    .into(activityBinding.chatToolbar.chatImage);

            activityBinding.chatToolbar.title.setText(provider.getFirstName() + " " + provider.getLastName());

        }catch (NullPointerException e){
            e.printStackTrace();
            activityBinding.chatToolbar.title.setText("Chat");
        }

        getAllMessages();
        clickListener();

        activityBinding.chat.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (bottom <= oldBottom) {

                activityBinding.chat.smoothScrollToPosition(bottom);
            }
        });

    }

    private void getAllMessages() {
        viewModel.getMessages(token, Integer.parseInt(inboxID));
        viewModel._get_messages.observe(this, response -> {
            if (response != null){
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                    hideLoading();
                    if (response.getData().getData().getMessages().size() > 0){
                        msgs = new ArrayList<>();
                        LinearLayoutManager layoutManager = new LinearLayoutManager(Chatroom.this, RecyclerView.VERTICAL, false);
                        activityBinding.chat.setLayoutManager(layoutManager);
                        List<MessagesItem> messagesItems = response.getData().getData().getMessages();
                        for (int i = 0; i <= messagesItems.size() - 1; i++) {

                            if (messagesItems.get(i).getSentBy() == TinyDbManager.getUserInformation().getId()) {
                                msgs.add(new SenderModel(messagesItems.get(i).getBody(),
                                        messagesItems.get(i).getCreatedAt()));
                            } else {
                                msgs.add(new ReceiverModel(messagesItems.get(i).getBody(),
                                        messagesItems.get(i).getCreatedAt()));
                            }
                        }
                        buildChatRv(msgs);
                    }

                }
            }
        });

    }

    private void buildChatRv(ArrayList<Object> msgs) {
        adapter = new ChatAdapter(Chatroom.this, msgs);
        activityBinding.chat.setAdapter(adapter);
        activityBinding.chat.scrollToPosition(msgs.size() - 1);
    }

    private void clickListener() {
        activityBinding.chatToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        activityBinding.sendMessage.setOnClickListener(v -> {
            String message = activityBinding.messgae.getText().toString();
            if (TextUtils.isEmpty(message)){
                showSnackBarShort("Enter message");
                activityBinding.sendMessage.requestFocus();
            }else {
                JsonObject object = new JsonObject();
                object.addProperty("body",message);
                object.addProperty("service_provider_id",provider.getId());
                object.addProperty("inbox_id",inboxID);

                viewModel.sendMessage(token,object);
                viewModel._send_message.observe(this, response -> {
                    if (response != null) {
                        if (response.isLoading()) {
                            showLoading();
                        } else if (!response.getError().isEmpty()) {
                            hideLoading();
                            showSnackBarShort(response.getError());
                        } else if (response.getData().isSuccess()) {
                            hideLoading();
                            msgs.add(new SenderModel(message, Calendar.getInstance().getTime().toString()));

                            if (msgs.size() == 1){
                                adapter = new ChatAdapter(Chatroom.this, msgs);
                                activityBinding.chat.setAdapter(adapter);
                                activityBinding.chat.scrollToPosition(msgs.size() - 1);
                                activityBinding.messgae.setText("");
                                activityBinding.sendMessage.setEnabled(true);
                            }else {
                                activityBinding.chat.scrollToPosition(msgs.size() - 1);
                                adapter.notifyItemInserted(msgs.size() - 1);
                                adapter.notifyDataSetChanged();
                                activityBinding.messgae.setText("");
                                activityBinding.sendMessage.setEnabled(true);
                            }
                        }
                    }
                });
            }
        });
    }
}