package com.rizorsiumani.workondemanduser.ui.search;

import android.text.Editable;
import android.text.TextWatcher;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivitySearchServicesBinding;
import com.rizorsiumani.workondemanduser.ui.sub_category.SubCategories;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchServices extends BaseActivity<ActivitySearchServicesBinding> {

    @Override
    protected ActivitySearchServicesBinding getActivityBinding() {
        return ActivitySearchServicesBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        activityBinding.searchToolbar.title.setText("Search");
        clickListeners();
        searchTextWatcher();


    }

    private void searchTextWatcher() {
        activityBinding.searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable data) {

                if (!data.toString().isEmpty()) {
                    //getSearchedData(data.toString());
                }
            }
        });
    }

   /* private void getSearchedData(String toString) {

        List<String> ser_categories = new ArrayList<>();
        ser_categories.add("Home Cleaning");
        ser_categories.add("Office Cleaning");
        ser_categories.add("Warehouse Cleaning");

        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        activityBinding.searchDataList.setLayoutManager(layoutManager);
        SearchServiceAdapter adapter = new SearchServiceAdapter(ser_categories, App.applicationContext);
        activityBinding.searchDataList.setAdapter(adapter);

        adapter.setOnCategoryClickListener(position -> {
            ActivityUtil.gotoPage(SearchServices.this, SubCategories.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }*/

    private void clickListeners() {
        activityBinding.searchToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }
}