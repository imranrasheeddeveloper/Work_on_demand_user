
package com.rizorsiumani.workondemanduser.ui.all_posted_jobs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.data.businessModels.PostedJobsDataItem;
import com.rizorsiumani.workondemanduser.databinding.ActivityAllPostedJobsBinding;
import com.rizorsiumani.workondemanduser.ui.address.AdressesAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllPostedJobs extends BaseActivity<ActivityAllPostedJobsBinding> {

    private PostedJobsViewModel viewModel;
    JobsListAdapter adapter;
    List<PostedJobsDataItem> dataItems;

    @Override
    protected ActivityAllPostedJobsBinding getActivityBinding() {
        return ActivityAllPostedJobsBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewModel = new ViewModelProvider(AllPostedJobs.this).get(PostedJobsViewModel.class);
        if (viewModel._posted_jobs.getValue() == null) {
            String token = prefRepository.getString("token");
            viewModel.getJobs(token);
        }

        viewModel._posted_jobs.observe(AllPostedJobs.this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                    hideLoading();

                    if (response.getData().getData().size() > 0) {
                        dataItems = new ArrayList<>();
                        dataItems.addAll(response.getData().getData());
                        setRv(dataItems);
                    }

                }
            }
        });


        activityBinding.allJobsToolbar.title.setText("All Posted Jobs");
        clickListeners();
    }

    private void setRv(List<PostedJobsDataItem> dataItems) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        activityBinding.jobsList.setLayoutManager(layoutManager);
        adapter = new JobsListAdapter(AllPostedJobs.this, dataItems);
        activityBinding.jobsList.setAdapter(adapter);
    }

    private void clickListeners() {

        activityBinding.allJobsToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        viewModel._posted_jobs.removeObservers(AllPostedJobs.this);
        viewModel = null;
    }
}