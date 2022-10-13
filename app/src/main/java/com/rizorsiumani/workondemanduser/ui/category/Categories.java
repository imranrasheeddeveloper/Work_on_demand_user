package com.rizorsiumani.workondemanduser.ui.category;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.graphics.Color;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.SerCategoryModel;
import com.rizorsiumani.workondemanduser.databinding.ActivityCategoriesBinding;
import com.rizorsiumani.workondemanduser.ui.sub_category.SubCategories;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

public class Categories extends BaseActivity<ActivityCategoriesBinding> {

    List<SerCategoryModel> service_categories;

    @Override
    protected ActivityCategoriesBinding getActivityBinding() {
        return ActivityCategoriesBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        service_categories = new ArrayList<>();
        service_categories.add(new SerCategoryModel("Cleaning", R.drawable.ic_cleaning, "#eb5657"));
        service_categories.add(new SerCategoryModel("Appliances", R.drawable.ic_electric_appliance, "#0ebdde"));
        service_categories.add(new SerCategoryModel("Electronic", R.drawable.ic_electrician, "#1aa882"));
        service_categories.add(new SerCategoryModel("Washing", R.drawable.ic_laundry_machine, "#5824c4"));
        service_categories.add(new SerCategoryModel("Painting", R.drawable.ic_paint_roller, "#fda145"));
        service_categories.add(new SerCategoryModel("Wood Working", R.drawable.ic_woodworking, "#0ebdde"));
        service_categories.add(new SerCategoryModel("Shifting", R.drawable.ic_shiftinng, "#eb5657"));


        Rv();
        clickListeners();
    }

    private void clickListeners() {
//        activityBinding.gridView.setOnClickListener(view -> {
//
//            activityBinding.gridView.setImageTintList(ColorStateList.valueOf(Color.parseColor("#f4841f")));
//            activityBinding.linearView.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B8B8BC")));
//
//            GridLayoutManager layoutManager = new GridLayoutManager(App.applicationContext, 2);
//            activityBinding.categoriiesList.setLayoutManager(layoutManager);
//            CategoriesAdapter adapter = new CategoriesAdapter(Categories.this, service_categories);
//            activityBinding.categoriiesList.setAdapter(adapter);
//
//            adapter.setOnServiceClickListener(position -> {
//                ActivityUtil.gotoPage(Categories.this, ResultantServiceProviders.class);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            });
//        });

        activityBinding.linearView.setOnClickListener(view -> {
            activityBinding.gridView.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B8B8BC")));
            activityBinding.linearView.setImageTintList(ColorStateList.valueOf(Color.parseColor("#f4841f")));
            Rv();
        });
    }

    private void Rv() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        activityBinding.categoriiesList.setLayoutManager(layoutManager);
        CatAdapter adapter = new CatAdapter(service_categories, App.applicationContext);
        activityBinding.categoriiesList.setAdapter(adapter);

        adapter.setOnServiceClickListener(position -> {
            ActivityUtil.gotoPage(Categories.this, SubCategories.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
}