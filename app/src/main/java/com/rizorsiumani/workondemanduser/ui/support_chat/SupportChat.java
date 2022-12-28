package com.rizorsiumani.workondemanduser.ui.support_chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.databinding.ActivitySupportChatBinding;

public class SupportChat extends BaseActivity<ActivitySupportChatBinding> {

    @Override
    protected ActivitySupportChatBinding getActivityBinding() {
        return ActivitySupportChatBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}