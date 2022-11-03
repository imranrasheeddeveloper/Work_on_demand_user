package com.rizorsiumani.workondemanduser.ui.all_services;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.CategoryModel;
import com.rizorsiumani.workondemanduser.ui.category.Categories;
import com.rizorsiumani.workondemanduser.ui.fragment.home.ServicesAdapter;
import com.rizorsiumani.workondemanduser.ui.sub_category.SubCategories;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

public class AllServices extends BaseActivity<com.rizorsiumani.workondemanduser.databinding.ActivityAllServicesBinding> {

    @Override
    protected com.rizorsiumani.workondemanduser.databinding.ActivityAllServicesBinding getActivityBinding() {
        return com.rizorsiumani.workondemanduser.databinding.ActivityAllServicesBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void onStart() {
        super.onStart();

        activityBinding.servicesToolbar.title.setText("All Services");
        clickListeners();
        allServices();
    }

    private void allServices() {

        List<CategoryModel> categories = new ArrayList<>();
        categories.add(new CategoryModel("Cleaning", R.drawable.ic_cleaning, "#eb5657"));
        categories.add(new CategoryModel("Appliances", R.drawable.ic_electric_appliance, "#0ebdde"));
        categories.add(new CategoryModel("Electronic", R.drawable.ic_electrician, "#1aa882"));
        categories.add(new CategoryModel("Washing", R.drawable.ic_laundry_machine, "#5824c4"));
        categories.add(new CategoryModel("Painting", R.drawable.ic_paint_roller, "#fda145"));
        categories.add(new CategoryModel("Wood Working", R.drawable.ic_woodworking, "#0ebdde"));
        categories.add(new CategoryModel("Shifting", R.drawable.ic_shiftinng, "#eb5657"));


        LinearLayoutManager glm = new LinearLayoutManager(AllServices.this, RecyclerView.VERTICAL, false);
        activityBinding.allServicesList.setLayoutManager(glm);

        ServicesAdapter adapter = new ServicesAdapter(categories);
        activityBinding.allServicesList.setAdapter(adapter);

        adapter.setOnServiceClickListener(position -> {
//            Intent intent = new Intent(AllServices.this, SubCategories.class);
//            intent.putExtra("category_id", categories.get(position).get());
//            startActivity(intent);
//            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }

    private void clickListeners() {
        activityBinding.servicesToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }
}