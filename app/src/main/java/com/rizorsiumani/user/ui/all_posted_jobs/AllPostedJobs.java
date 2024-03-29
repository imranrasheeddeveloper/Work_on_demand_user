
package com.rizorsiumani.user.ui.all_posted_jobs;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import com.google.gson.Gson;
import com.rizorsiumani.user.App;
import com.rizorsiumani.user.BaseActivity;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.PostedJobsDataItem;
import com.rizorsiumani.user.databinding.ActivityAllPostedJobsBinding;
import com.rizorsiumani.user.ui.post_job.PostJob;
import com.rizorsiumani.user.utils.Constants;

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
                 } else if (response.getError() != null) {
                    hideLoading();
                    if (response.getError() == null){
                        showSnackBarShort("Something went wrong!!");
                    }else {
                        Constants.constant.getApiError(App.applicationContext,response.getError());
                    }
                } else if (response.getData().getData() != null) {
                    hideLoading();

                    if (response.getData().getData().size() > 0) {
                        hideNoDataAnimation();
                        dataItems = new ArrayList<>();
                        dataItems.addAll(response.getData().getData());
                        setRv(dataItems);
                    }else {
                        showNoDataAnimation(R.raw.no_job,"No Jobs");
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

        adapter.setOnJobClickListener(new JobsListAdapter.OnItemClickListener() {
            @Override
            public void onCancel(int position) {
                String token = prefRepository.getString("token");
                viewModel.deleteJob(token,dataItems.get(position).getId());
                viewModel._delete_job.observe(AllPostedJobs.this, response -> {
                    if (response != null){
                        if (response.isLoading()){
                            showLoading();
                        } else if (response.getError() != null) {
                    hideLoading();
                    if (response.getError() == null){
                        showSnackBarShort("Something went wrong!!");
                    }else {
                        Constants.constant.getApiError(App.applicationContext,response.getError());
                    }
                        } else if (response.getData().isSuccess()) {
                            hideLoading();
                            adapter.remove(position);

                        }
                    }
                });
            }

            @Override
            public void onUpdate(int position) {
                Gson gson = new Gson();
                String job_details = gson.toJson(dataItems.get(position),PostedJobsDataItem.class);
                dataItems.get(position);
                Intent intent = new Intent(AllPostedJobs.this, PostJob.class);
                intent.putExtra("posted_job_detail", job_details);
                intent.putExtra("status", "update");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private void clickListeners() {

        activityBinding.allJobsToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        viewModel._posted_jobs.removeObservers(AllPostedJobs.this);
        viewModel = null;
    }
}