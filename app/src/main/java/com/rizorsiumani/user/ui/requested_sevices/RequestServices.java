package com.rizorsiumani.user.ui.requested_sevices;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.user.App;
import com.rizorsiumani.user.BaseActivity;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.databinding.ActivityRequestServicesBinding;

import java.util.ArrayList;
import java.util.List;

public class RequestServices extends BaseActivity<ActivityRequestServicesBinding> {


    RequestedServiceAdapter adapter;

    @Override
    protected ActivityRequestServicesBinding getActivityBinding() {
        return ActivityRequestServicesBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        activityBinding.rsToolbar.title.setText("Requested Services");
        activityBinding.rsToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        requestRv();

    }

    private void requestRv() {
        List<String> data = new ArrayList<>();
        data.add("2 Hours Cleaning Service");
        data.add("3 Hours Shifting Service");


        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        activityBinding.requestedServicesList.setLayoutManager(layoutManager);
        adapter = new RequestedServiceAdapter(data,RequestServices.this);
        activityBinding.requestedServicesList.setAdapter(adapter);
    }
}