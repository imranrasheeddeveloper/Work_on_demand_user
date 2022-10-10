package com.rizorsiumani.workondemanduser.ui.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityChatroomBinding;

public class Chatroom extends BaseActivity<ActivityChatroomBinding> {

    @Override
    protected ActivityChatroomBinding getActivityBinding() {
        return ActivityChatroomBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();



    }
}