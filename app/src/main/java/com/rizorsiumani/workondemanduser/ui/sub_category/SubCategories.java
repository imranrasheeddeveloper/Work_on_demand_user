package com.rizorsiumani.workondemanduser.ui.sub_category;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Intent;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.CategoriesDataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProviderDataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProviderModel;
import com.rizorsiumani.workondemanduser.data.businessModels.SubCategoryDataItem;
import com.rizorsiumani.workondemanduser.data.local.TinyDB;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.ActivityResultantServiceProvidersBinding;
import com.rizorsiumani.workondemanduser.ui.search.SearchServiceAdapter;
import com.rizorsiumani.workondemanduser.ui.service_providers.ServiceProviderViewModel;
import com.rizorsiumani.workondemanduser.ui.service_providers.Serviceproviders;
import com.rizorsiumani.workondemanduser.ui.walkthrough.OnBoardingViewModel;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

public class SubCategories extends BaseActivity<ActivityResultantServiceProvidersBinding> {


    private SubCategoryViewModel viewModel;
    List<SubCategoryDataItem> dataItems;
    int catID;
    String title;
    private ServiceProviderViewModel providerViewModel;


    @Override
    protected ActivityResultantServiceProvidersBinding getActivityBinding() {
        return ActivityResultantServiceProvidersBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void onStart() {
        super.onStart();

        try {
            CategoriesDataItem categoriesDataItem = TinyDbManager.getCategory();
            title  = categoriesDataItem.getTitle();
            catID = categoriesDataItem.getId();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        viewModel = new ViewModelProvider(this).get(SubCategoryViewModel.class);
        providerViewModel = new ViewModelProvider(this).get(ServiceProviderViewModel.class);

        viewModel.subCategories(catID,1);


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
                        hideNoDataAnimation();
                        buildRv(dataItems);
                    }else {
                        showNoDataAnimation(R.raw.no_job,"No Category Available");
                    }

                }
            }
        });

        if (!title.isEmpty()) {
            activityBinding.searchedToolbar.title.setText(title);
        }else {
            activityBinding.searchedToolbar.title.setText("Sub Categories");
        }
        activityBinding.searchedToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
            try {

            JsonObject object = new JsonObject();
            object.addProperty("latitude", "31.510376");
            object.addProperty("longitude", "74.339676");
            object.addProperty("category_id", String.valueOf(dataItems.get(position).getId()));
            String token = prefRepository.getString("token");
            providerViewModel.catServiceProviders(1, token, object);
            providerViewModel._by_cat_provider.observe(this, response -> {
                if (response != null) {
                    if (response.isLoading()) {
                        showLoading();
                    } else if (!response.getError().isEmpty()) {
                        hideLoading();
                        showSnackBarShort(response.getError());
                    } else if (response.getData().isSuccess()) {
                        hideLoading();
                        if (response.getData().getData().size() > 0) {
//                            Gson gson = new Gson();
//                            String providers = gson.toJson(response.getData(), ServiceProviderModel.class);
                            TinyDbManager.saveProviderForMapScreen(response.getData());
                            Intent intent = new Intent(SubCategories.this, Serviceproviders.class);
//                            intent.putExtra("providers",providers);
                            intent.putExtra("path","subcategory");
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        } else {
                            showSnackBarShort("Service Providers not Available");
                        }
                    }

                }
            });

            }catch (NullPointerException | IllegalArgumentException e){
                e.printStackTrace();
            }

        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        viewModel._subCategory.removeObservers(this);
        viewModel = null;
    }


}