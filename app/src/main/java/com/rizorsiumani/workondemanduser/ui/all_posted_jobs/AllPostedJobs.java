
package com.rizorsiumani.workondemanduser.ui.all_posted_jobs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.databinding.ActivityAllPostedJobsBinding;
import com.rizorsiumani.workondemanduser.ui.address.AdressesAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllPostedJobs extends BaseActivity<ActivityAllPostedJobsBinding> {


    JobsListAdapter adapter;

    @Override
    protected ActivityAllPostedJobsBinding getActivityBinding() {
        return ActivityAllPostedJobsBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        activityBinding.allJobsToolbar.title.setText("All Posted Jobs");
        getAllPostedJobs();
        clickListeners();
    }

    private void clickListeners() {

        activityBinding.allJobsToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
    }

    private void getAllPostedJobs() {
        List<String> service = new ArrayList<>();
        service.add("Home Cleaning");
        service.add("Home Cleaning");
        service.add("Home Cleaning");

        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        activityBinding.jobsList.setLayoutManager(layoutManager);
        adapter = new JobsListAdapter(App.applicationContext,service);
        activityBinding.jobsList.setAdapter(adapter);
    }
}