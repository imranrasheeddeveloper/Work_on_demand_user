package com.rizorsiumani.user.ui.support_chat;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.rizorsiumani.user.App;
import com.rizorsiumani.user.BaseActivity;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.SenderModel;
import com.rizorsiumani.user.data.businessModels.chat.SupportActionModel;
import com.rizorsiumani.user.data.businessModels.chatwoot_model.MessagesModel;
import com.rizorsiumani.user.databinding.ActivitySupportChatBinding;
import com.rizorsiumani.user.utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;

public class SupportChat extends BaseActivity<ActivitySupportChatBinding> {

    SupportChatViewModel supportChatViewModel;
    private ArrayList<Object> msgs;
    SupportChatAdapter adapter;

    @Override
    protected ActivitySupportChatBinding getActivityBinding() {
        return ActivitySupportChatBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        supportChatViewModel = new ViewModelProvider(this).get(SupportChatViewModel.class);
        activityBinding.supportChatToolbar.title.setText("Chat Support");
        clickListener();
        msgs = new ArrayList<>();
        getMessages();
    }

    private void getMessages() {

        supportChatViewModel.getChat(Constants.CHATWOOT_API_KEY, Integer.parseInt(Constants.ACCOUNT_ID), prefRepository.getInt("CONVERSATION_ID"));
        supportChatViewModel._messages.observe(this , response -> {
            if (response != null){
                if (response.isLoading()){
                    showLoading();
                } else if (response.getError() != null) {
                    hideLoading();
                    if (response.getError() == null){
                        showSnackBarShort("Something went wrong!!");
                    }else {
                        Constants.constant.getApiError(App.applicationContext,response.getError());
                    }
                } else if (response.getData() != null) {
                    hideLoading();
                    if (response.getData().getPayload() != null){
                            msgs = new ArrayList<>();
                            buildSupportRv(response.getData());
                    }
                }
            }
        });
    }

    private void buildSupportRv(MessagesModel data) {
        for (int i = 0; i <= data.getPayload().size() - 1; i++) {
            if (data.getPayload().get(i).getSender() != null) {
                if (data.getPayload().get(i).getSender().getType().equalsIgnoreCase("user")) {
                    msgs.add(new SupportReceiverModel(data.getPayload().get(i).getContent(),
                            data.getPayload().get(i).getCreatedAt(), data.getMeta().getAssignee()
                    ));
                } else {
                    msgs.add(new SenderModel(data.getPayload().get(i).getContent(),
                            ""
                    ));
                }
            } else {
                if (!data.getPayload().get(i).getContent().isEmpty()) {
                    msgs.add(new SupportActionModel(data.getPayload().get(i).getContent(), ""));
                }
            }
        }
        buildRec(msgs);
    }

    private void buildRec(ArrayList<Object> msgs) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(SupportChat.this, RecyclerView.VERTICAL, false);
        activityBinding.chat.setLayoutManager(layoutManager);
        adapter = new SupportChatAdapter(SupportChat.this, msgs);
        activityBinding.chat.setAdapter(adapter);
        activityBinding.chat.scrollToPosition(msgs.size() - 1);

    }

    private void clickListener() {
        activityBinding.supportChatToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        activityBinding.sendSupportMessage.setOnClickListener(view -> {
            String query = activityBinding.edMessage.getText().toString();
            if (TextUtils.isEmpty(query)){
                activityBinding.edMessage.requestFocus();
            }else {
//                if (msgs.size() == 0) {
                if (prefRepository.getInt("CONVERSATION_ID") != 0) {
//                        String query1 = "My ID is " + TinyDbManager.getUserInformation().getId();
                    JsonObject object = new JsonObject();
                    object.addProperty("content", query);
                    object.addProperty("message_type", "incoming");
                    object.addProperty("private", false);
                    supportChatViewModel.sendMessage(Constants.CHATWOOT_API_KEY, prefRepository.getInt("CONVERSATION_ID"), Integer.parseInt(Constants.ACCOUNT_ID), object);
                    supportChatViewModel._send_query.observe(this, response -> {
                        if (response != null) {
                            if (response.isLoading()) {

                            } else if (response.getError() != null) {
                                hideLoading();
                                if (response.getError() == null) {
                                    showSnackBarShort("Something went wrong!!");
                                } else {
                                    Constants.constant.getApiError(App.applicationContext, response.getError());
                                }
                            } else if (response.getData() != null) {
                                hideLoading();
                                if (msgs == null){
                                    msgs = new ArrayList<>();
                                }
                                String message1 = activityBinding.edMessage.getText().toString();
                                if (!message1.isEmpty()) {
                                    msgs.add(new SenderModel(message1, Calendar.getInstance().getTime().toString()));
                                }
                                if (msgs.size() == 1){
                                    adapter = new SupportChatAdapter(SupportChat.this, msgs);
                                    activityBinding.chat.setAdapter(adapter);
                                    activityBinding.chat.scrollToPosition(msgs.size() - 1);
                                    activityBinding.edMessage.setText("");
                                    activityBinding.sendSupportMessage.setEnabled(true);
                                }else {
                                    activityBinding.chat.scrollToPosition(msgs.size() - 1);
                                    adapter.notifyItemInserted(msgs.size() - 1);
                                    adapter.notifyDataSetChanged();
                                    activityBinding.edMessage.setText("");
                                    activityBinding.sendSupportMessage.setEnabled(true);
                                }
                            }
                        }
                    });
                }
            }

        });




    }
}