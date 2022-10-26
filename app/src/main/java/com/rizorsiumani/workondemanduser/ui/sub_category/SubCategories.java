package com.rizorsiumani.workondemanduser.ui.sub_category;

import android.content.Intent;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.SubCategoryDataItem;
import com.rizorsiumani.workondemanduser.databinding.ActivityResultantServiceProvidersBinding;
import com.rizorsiumani.workondemanduser.ui.search.SearchServiceAdapter;
import com.rizorsiumani.workondemanduser.ui.service_providers.Serviceproviders;
import com.rizorsiumani.workondemanduser.ui.walkthrough.OnBoardingViewModel;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

public class SubCategories extends BaseActivity<ActivityResultantServiceProvidersBinding> {


    private SubCategoryViewModel viewModel;
    List<SubCategoryDataItem> dataItems;
    int catID;

    @Override
    protected ActivityResultantServiceProvidersBinding getActivityBinding() {
        return ActivityResultantServiceProvidersBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void onStart() {
        super.onStart();

        try {
            catID = getIntent().getIntExtra("category_id",0);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        viewModel = new ViewModelProvider(this).get(SubCategoryViewModel.class);

        if (viewModel._subCategory.getValue() == null){
            viewModel.subCategories(catID,1);
        }

        viewModel._subCategory.observe(this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    //we have error to show
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                    hideLoading();

                    dataItems = new ArrayList<>();
                    dataItems.addAll(response.getData().getData());
                    if (dataItems.size() > 0) {
                        buildRv(dataItems);
                    }else {
                        showNoDataAnimation();
                    }

                }
            }
        });

        activityBinding.searchedToolbar.title.setText("Sub Services");
        activityBinding.searchedToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

    }

    private void buildRv(List<SubCategoryDataItem> dataItems) {
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        activityBinding.searchDataList.setLayoutManager(layoutManager);
        SearchServiceAdapter adapter = new SearchServiceAdapter(dataItems, SubCategories.this);
        activityBinding.searchDataList.setAdapter(adapter);

        adapter.setOnCategoryClickListener(position -> {
            Intent intent = new Intent(SubCategories.this, Serviceproviders.class);
            intent.putExtra("sub_cat_id",String.valueOf(dataItems.get(position).getId()));
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        viewModel._subCategory.removeObservers(this);
        viewModel = null;
    }
}