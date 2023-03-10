package com.rizorsiumani.user.ui.category;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.graphics.Color;

import com.rizorsiumani.user.App;
import com.rizorsiumani.user.BaseActivity;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.CategoriesDataItem;
import com.rizorsiumani.user.data.businessModels.SerCategoryModel;
import com.rizorsiumani.user.data.local.TinyDbManager;
import com.rizorsiumani.user.databinding.ActivityCategoriesBinding;
import com.rizorsiumani.user.ui.fragment.home.CategoriesAdapter;
import com.rizorsiumani.user.ui.fragment.home.HomeViewModel;
import com.rizorsiumani.user.ui.sub_category.SubCategories;
import com.rizorsiumani.user.utils.ActivityUtil;
import com.rizorsiumani.user.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class Categories extends BaseActivity<ActivityCategoriesBinding> {

    List<SerCategoryModel> service_categories;
    private HomeViewModel viewModel;
    List<CategoriesDataItem> categoriesDataItems;


    @Override
    protected ActivityCategoriesBinding getActivityBinding() {
        return ActivityCategoriesBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        activityBinding.categoriesToolbar.title.setText("All Services");

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.categories(1);

        viewModel._category.observe(this, response -> {
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
                    categoriesDataItems = new ArrayList<>();
                    categoriesDataItems.addAll(response.getData().getData());
                    if (categoriesDataItems.size() > 0) {
                        buildCategoryRv(categoriesDataItems);
                    }
                }
            }
        });
//        service_categories = new ArrayList<>();
//        service_categories.add(new SerCategoryModel("Cleaning", R.drawable.ic_cleaning, "#eb5657"));
//        service_categories.add(new SerCategoryModel("Appliances", R.drawable.ic_electric_appliance, "#0ebdde"));
//        service_categories.add(new SerCategoryModel("Electronic", R.drawable.ic_electrician, "#1aa882"));
//        service_categories.add(new SerCategoryModel("Washing", R.drawable.ic_laundry_machine, "#5824c4"));
//        service_categories.add(new SerCategoryModel("Painting", R.drawable.ic_paint_roller, "#fda145"));
//        service_categories.add(new SerCategoryModel("Wood Working", R.drawable.ic_woodworking, "#0ebdde"));
//        service_categories.add(new SerCategoryModel("Shifting", R.drawable.ic_shiftinng, "#eb5657"));
//
//
//        Rv();
        clickListeners();
    }

    private void buildCategoryRv(List<CategoriesDataItem> categoriesDataItems) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        activityBinding.categoriiesList.setLayoutManager(layoutManager);
        CatAdapter adapter = new CatAdapter(categoriesDataItems, Categories.this);
        activityBinding.categoriiesList.setAdapter(adapter);

        adapter.setOnServiceClickListener(position -> {
            TinyDbManager.saveCategory(categoriesDataItems.get(position));
            ActivityUtil.gotoPage(Categories.this, SubCategories.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    private void clickListeners() {
        activityBinding.gridView.setOnClickListener(view -> {

            activityBinding.gridView.setImageTintList(ColorStateList.valueOf(Color.parseColor("#f4841f")));
            activityBinding.linearView.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B8B8BC")));

            GridLayoutManager layoutManager = new GridLayoutManager(App.applicationContext, 2);
            activityBinding.categoriiesList.setLayoutManager(layoutManager);
            CategoriesAdapter adapter = new CategoriesAdapter(Categories.this, categoriesDataItems);
            activityBinding.categoriiesList.setAdapter(adapter);

            adapter.setOnServiceClickListener(position -> {
                TinyDbManager.saveCategory(categoriesDataItems.get(position));
                ActivityUtil.gotoPage(Categories.this, SubCategories.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            });
        });

        activityBinding.linearView.setOnClickListener(view -> {
            activityBinding.gridView.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B8B8BC")));
            activityBinding.linearView.setImageTintList(ColorStateList.valueOf(Color.parseColor("#f4841f")));
            buildCategoryRv(categoriesDataItems);
        });

        activityBinding.categoriesToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }


}