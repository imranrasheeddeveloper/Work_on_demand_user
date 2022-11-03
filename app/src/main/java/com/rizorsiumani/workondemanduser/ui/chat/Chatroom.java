package com.rizorsiumani.workondemanduser.ui.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityChatroomBinding;

public class Chatroom extends BaseActivity<ActivityChatroomBinding> {

    String id , name;

    @Override
    protected ActivityChatroomBinding getActivityBinding() {
        return ActivityChatroomBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        try {
            id = getIntent().getStringExtra("service_provider_id");
            name = getIntent().getStringExtra("service_provider_name");

            activityBinding.chatToolbar.title.setText(name);

        }catch (NullPointerException e){
            e.printStackTrace();
        }

        clickListener();

    }

    private void clickListener() {
        activityBinding.chatToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }
}