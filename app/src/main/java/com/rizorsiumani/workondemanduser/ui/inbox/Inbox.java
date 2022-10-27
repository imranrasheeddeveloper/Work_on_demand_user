package com.rizorsiumani.workondemanduser.ui.inbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityInboxBinding;
import com.rizorsiumani.workondemanduser.ui.chat.Chatroom;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

public class Inbox extends BaseActivity<ActivityInboxBinding> {

    @Override
    protected ActivityInboxBinding getActivityBinding() {
        return ActivityInboxBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void onStart() {
        super.onStart();

        setInboxRv();
    }

    private void setInboxRv() {

        List<String> transactions = new ArrayList<>();
        transactions.add("");
        transactions.add("Model Town, Lahore");
        transactions.add("Gullberg 3, Lahore");

        LinearLayoutManager layoutManager = new LinearLayoutManager(Inbox.this, RecyclerView.VERTICAL, false);
        activityBinding.chatList.setLayoutManager(layoutManager);
        InboxAdapter adapter = new InboxAdapter(transactions, Inbox.this);
        activityBinding.chatList.setAdapter(adapter);

        adapter.setOnChatClickListener(position -> {
            ActivityUtil.gotoPage(Inbox.this, Chatroom.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
}