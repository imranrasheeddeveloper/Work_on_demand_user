package com.rizorsiumani.workondemanduser.ui.searched_sp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityResultantServiceProvidersBinding;
import com.rizorsiumani.workondemanduser.ui.search.SearchServiceAdapter;
import com.rizorsiumani.workondemanduser.ui.search.SearchServices;
import com.rizorsiumani.workondemanduser.ui.service_providers.ServiceProviderAdapter;
import com.rizorsiumani.workondemanduser.ui.service_providers.Serviceproviders;
import com.rizorsiumani.workondemanduser.ui.sp_detail.SpProfile;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

public class ResultantServiceProviders extends BaseActivity<ActivityResultantServiceProvidersBinding> {


    @Override
    protected ActivityResultantServiceProvidersBinding getActivityBinding() {
        return ActivityResultantServiceProvidersBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void onStart() {
        super.onStart();

        activityBinding.searchedToolbar.title.setText("Sub Services");
        activityBinding.searchedToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        buildRv();
    }

    private void buildRv() {
//        List<String> name = new ArrayList<>();
//        name.add("Michel Jeff");
//        name.add("Michel Jeff");
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
//        activityBinding.searchDataList.setLayoutManager(layoutManager);
//        SearchedProviderAdapter adapter = new SearchedProviderAdapter(name, App.applicationContext);
//        activityBinding.searchDataList.setAdapter(adapter);
//
//        adapter.setOnProviderListener(position -> {
//            ActivityUtil.gotoPage(ResultantServiceProviders.this, Serviceproviders.class);
//            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//        });

        List<String> ser_categories = new ArrayList<>();
        ser_categories.add("Home Cleaning");
        ser_categories.add("Office Cleaning");
        ser_categories.add("Warehouse Cleaning");

        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        activityBinding.searchDataList.setLayoutManager(layoutManager);
        SearchServiceAdapter adapter = new SearchServiceAdapter(ser_categories, App.applicationContext);
        activityBinding.searchDataList.setAdapter(adapter);

        adapter.setOnCategoryClickListener(position -> {
            ActivityUtil.gotoPage(ResultantServiceProviders.this, Serviceproviders.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }
}