package com.rizorsiumani.workondemanduser.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.databinding.ActivityInboxBinding;

public class Inbox extends BaseActivity<ActivityInboxBinding> {

    @Override
    protected ActivityInboxBinding getActivityBinding() {
        return ActivityInboxBinding.inflate(getLayoutInflater());
    }


}