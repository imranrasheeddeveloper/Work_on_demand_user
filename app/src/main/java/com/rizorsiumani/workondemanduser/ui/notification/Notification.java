package com.rizorsiumani.workondemanduser.ui.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.NotificationDataItem;
import com.rizorsiumani.workondemanduser.databinding.ActivityNotificationBinding;
import com.rizorsiumani.workondemanduser.ui.sp_detail.DiscountPlansAdapter;

import java.util.ArrayList;
import java.util.List;

public class Notification extends BaseActivity<ActivityNotificationBinding> {

    NotificationViewModel viewModel;
    List<NotificationDataItem> notificationDataItems;


    @Override
    protected ActivityNotificationBinding getActivityBinding() {
        return ActivityNotificationBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        activityBinding.notificationToolbar.title.setText("Notifications");

        viewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        String token = prefRepository.getString("token");
        viewModel.getNotification(token, 1);
        viewModel._notification.observe(this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                    hideLoading();
                    if (response.getData().getData().size() > 0) {
                        hideNoDataAnimation();
                        notificationDataItems = new ArrayList<>();
                        notificationDataItems.addAll(response.getData().getData());
                        buildRv(notificationDataItems);
                    }else {
                        showNoDataAnimation();
                    }
                }

            }
        });

        clickListener();
    }

    private void clickListener() {
        activityBinding.notificationToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

    }

    private void buildRv(List<NotificationDataItem> list) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(Notification.this, RecyclerView.VERTICAL, false);
        activityBinding.notificationList.setLayoutManager(layoutManager);
        NotificationAdapter adapter = new NotificationAdapter(list,Notification.this );
        activityBinding.notificationList.setAdapter(adapter);
    }
}
