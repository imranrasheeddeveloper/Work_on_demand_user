package com.rizorsiumani.workondemanduser.ui.filter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.slider.RangeSlider;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityFilterSearchBinding;

import java.util.ArrayList;
import java.util.List;

public class FilterSearch extends BaseActivity<ActivityFilterSearchBinding> {


    String updatedValue;

    @Override
    protected ActivityFilterSearchBinding getActivityBinding() {
        return ActivityFilterSearchBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        activityBinding.filterToolbar.title.setText("Filter");

        getCategories();
        setClickListeners();


    }

    private void setClickListeners() {

        activityBinding.filterToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        activityBinding.filterByPrice.setOnClickListener(view -> {
            activityBinding.filterByPrice.setBackgroundColor(getResources().getColor(R.color.off_white));
            activityBinding.category.setBackgroundColor(getResources().getColor(R.color.white));
            activityBinding.categoryList.setVisibility(View.GONE);
            activityBinding.priceLayout.setVisibility(View.VISIBLE);
        });

        activityBinding.category.setOnClickListener(view -> {
            activityBinding.filterByPrice.setBackgroundColor(getResources().getColor(R.color.white));
            activityBinding.category.setBackgroundColor(getResources().getColor(R.color.off_white));
            activityBinding.categoryList.setVisibility(View.VISIBLE);
            activityBinding.priceLayout.setVisibility(View.GONE);
        });

        activityBinding.rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                int accurate_startingValue = Math.round(slider.getValues().get(0));
                int accurate_endingValue = Math.round(slider.getValues().get(1));

                activityBinding.startingRange.setText("[ " + accurate_startingValue);
                activityBinding.endingRange.setText(" - " + accurate_endingValue + " ]");

            }
        });

        activityBinding.rangeSlider.setLabelFormatter(value -> {
            int accurate_value = Math.round(value);
            updatedValue = String.valueOf(accurate_value);
            return updatedValue;
        });
    }

    private void getCategories() {
//        List<String> service_categories = new ArrayList<>();
//        service_categories.add("Cleaning");
//        service_categories.add("Shifting");
//        service_categories.add("Appliances");
//        service_categories.add("Painting");
//        service_categories.add("Electronic");
//        service_categories.add("Repairing");
//        service_categories.add("Cleaning");
//        service_categories.add("More");
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(FilterSearch.this, RecyclerView.VERTICAL, false);
//        activityBinding.categoryList.setLayoutManager(layoutManager);
//        CategoryFilterAdapter adapter = new CategoryFilterAdapter(service_categories, FilterSearch.this);
//        activityBinding.categoryList.setAdapter(adapter);

    }


}