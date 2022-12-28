package com.rizorsiumani.workondemanduser.ui.notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

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
    Parcelable recyclerViewState;
    private boolean flag_loading;
    int nextPage = 1;
    private int maxPageLimit;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;

    @Override
    protected ActivityNotificationBinding getActivityBinding() {
        return ActivityNotificationBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        mLayoutManager = new LinearLayoutManager(Notification.this, RecyclerView.VERTICAL, false);

        activityBinding.notificationToolbar.title.setText("Notifications");

        viewModel = new ViewModelProvider(this).get(NotificationViewModel.class);

        getNotifications(nextPage);


        clickListener();

        activityBinding.notificationList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerViewState = activityBinding.notificationList.getLayoutManager().onSaveInstanceState(); // save recycleView state

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {

                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (flag_loading) {

                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            flag_loading = false;
                            nextPage++;

                            if (nextPage > maxPageLimit) {
                                Toast.makeText(Notification.this, "No more data!", Toast.LENGTH_SHORT).show();
                            } else {
                                getNotifications(nextPage);
                                showLoading();
                            }

                        }
                    }
                }
            }
        });

    }

    private void getNotifications(int nextPage) {
        String token = prefRepository.getString("token");
        viewModel.getNotification(token, nextPage);
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
                        flag_loading = true;
                        maxPageLimit = response.getData().getPage();

                        notificationDataItems = new ArrayList<>();
                        notificationDataItems.addAll(response.getData().getData());
                        buildRv(notificationDataItems);
                    }else {
                        showNoDataAnimation(R.raw.bell,"No Notification");
                        flag_loading = false;

                    }
                }

            }
        });
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
        adapter.notifyDataSetChanged();
        activityBinding.notificationList.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        activityBinding.notificationList.setAdapter(adapter);
    }
}
