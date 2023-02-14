package com.rizorsiumani.workondemanduser.ui.chat;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.ReceiverModel;
import com.rizorsiumani.workondemanduser.data.businessModels.SenderModel;
import com.rizorsiumani.workondemanduser.data.businessModels.chat.MessagesItem;
import com.rizorsiumani.workondemanduser.data.businessModels.inbox.ServiceProvider;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.ActivityChatroomBinding;
import com.rizorsiumani.workondemanduser.ui.category.Categories;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.ui.post_job.PostJob;
import com.rizorsiumani.workondemanduser.ui.service_providers.Serviceproviders;
import com.rizorsiumani.workondemanduser.utils.Configration;
import com.rizorsiumani.workondemanduser.utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class Chatroom extends BaseActivity<ActivityChatroomBinding> {

    String inboxID;
    ServiceProvider provider;
    ChatViewModel viewModel;
    String token;
    private ArrayList<Object> msgs;
    ChatAdapter adapter;
    private BroadcastReceiver mReceiver;
    String isFromMapScreen;


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
            isFromMapScreen = getIntent().getStringExtra("maps");
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

        registerChatReceiver();
        activityBinding.chat.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (bottom <= oldBottom) {

                activityBinding.chat.smoothScrollToPosition(bottom);
            }
        });

        activityBinding.sendMessage.setOnClickListener(v -> {
            String message = activityBinding.messgae.getText().toString();
            if (TextUtils.isEmpty(message)){
//                showSnackBarShort("Enter message");
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
                        } else if (response.getError() != null) {
                            hideLoading();
                            if (response.getError() == null){
                                showSnackBarShort("Something went wrong!!");
                            }else {
                                Constants.constant.getApiError(App.applicationContext,response.getError());
                            }
                        } else if (response.getData().isSuccess()) {
                            hideLoading();
                            if (msgs == null){
                                msgs = new ArrayList<>();
                            }
                            String message1 = activityBinding.messgae.getText().toString();
                            if (!message1.isEmpty()) {
                                msgs.add(new SenderModel(message1, Calendar.getInstance().getTime().toString()));
                            }
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

    private void registerChatReceiver() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(Configration.CHAT_MSG_NOTIFICATION)) {
                    Log.e("Group Chat", "onReceive: reloaded");
                    getAllMessages();
                }
            }
        };
    }


    private void getAllMessages() {
        viewModel.getMessages(token, Integer.parseInt(inboxID));
        viewModel._get_messages.observe(this, response -> {
            if (response != null){
                if (response.isLoading()) {
                    showLoading();
                 } else if (response.getError() != null) {
                    hideLoading();
                    if (response.getError() == null){
                        showSnackBarShort("Something went wrong!!");
                    }else {
                        Constants.constant.getApiError(App.applicationContext,response.getError());
                    }
                } else if (response.getData().getData() != null) {
                    hideLoading();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(Chatroom.this, RecyclerView.VERTICAL, false);
                    activityBinding.chat.setLayoutManager(layoutManager);
                    if (response.getData().getData().getMessages().size() > 0){
                        msgs = new ArrayList<>();

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
            if (isFromMapScreen.equalsIgnoreCase("true")) {

                Intent intent = new Intent(this, Serviceproviders.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }else {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(Chatroom.this).registerReceiver(mReceiver,
                new IntentFilter(Configration.CHAT_MSG_NOTIFICATION));
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(Chatroom.this).unregisterReceiver(mReceiver);
        super.onPause();
    }

    @Override
    public void onBackPressed(){
//        ActivityManager m = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> runningTaskInfoList =  m.getRunningTasks(10);
//        Iterator<ActivityManager.RunningTaskInfo> itr = runningTaskInfoList.iterator();
//        while(itr.hasNext()){
//            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo)itr.next();
//            int id = runningTaskInfo.id;
//            CharSequence desc= runningTaskInfo.description;
//            int numOfActivities = runningTaskInfo.numActivities;
//            String topActivity = runningTaskInfo.topActivity.getShortClassName();
//            if (topActivity.equalsIgnoreCase(".ui.chat.Chatroom")){
//
//            }
//        }
        super.onBackPressed();

        if (isFromMapScreen.equalsIgnoreCase("true")) {

            Intent intent = new Intent(this, Serviceproviders.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }else {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        }
    }
}