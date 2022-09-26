package com.rizorsiumani.workondemanduser.ui.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityNotificationBinding;
import com.rizorsiumani.workondemanduser.ui.sp_detail.DiscountPlansAdapter;

import java.util.ArrayList;
import java.util.List;

public class Notification extends BaseActivity<ActivityNotificationBinding> {

    @Override
    protected ActivityNotificationBinding getActivityBinding() {
        return ActivityNotificationBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        activityBinding.notificationToolbar.title.setText("Notifications");
        clickListener();
        buildRv();
    }

    private void clickListener() {
        activityBinding.notificationToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

    }

    private void buildRv() {

        List<String> notifications = new ArrayList<>();
        notifications.add("Your Order Delivered");
        notifications.add("Payment Succeed");
        notifications.add("Your Order Cancelled");

        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        activityBinding.notificationList.setLayoutManager(layoutManager);
        NotificationAdapter adapter = new NotificationAdapter(notifications, App.applicationContext);
        activityBinding.notificationList.setAdapter(adapter);
    }
}