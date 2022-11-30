package com.rizorsiumani.workondemanduser.ui.sp_detail;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityServiceProviderProfileBinding;
import com.rizorsiumani.workondemanduser.ui.sp_detail.reviews.ReviewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ServiceProviderProfile extends BaseActivity<ActivityServiceProviderProfileBinding> {

    @Override
    protected ActivityServiceProviderProfileBinding getActivityBinding() {
        return ActivityServiceProviderProfileBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        getImages();
        getReviews();
        clickListeners();
    }

    private void clickListeners() {
        activityBinding.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        activityBinding.btnBookNow.setOnClickListener(view -> {
            showOrderOption();
        });
    }

    private void showOrderOption() {
        final BottomSheetDialog bt = new BottomSheetDialog(ServiceProviderProfile.this,  R.style.BottomSheetDialogTheme);
        View view_ = LayoutInflater.from(ServiceProviderProfile.this).inflate(R.layout.order_type_design_bottom_sheet, null, false);

        RecyclerView chatLayout = view_.findViewById(R.id.plansList);

        List<String> plans = new ArrayList<>();
        plans.add("3 Month Plan");
        plans.add("6 Month Plan");

        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        chatLayout.setLayoutManager(layoutManager);
        DiscountPlansAdapter adapter = new DiscountPlansAdapter(plans, App.applicationContext);
        chatLayout.setAdapter(adapter);

        bt.setContentView(view_);
        bt.show();
    }

    private void getReviews() {
        List<String> reviews = new ArrayList<>();
        reviews.add("5");
        reviews.add("4");
        reviews.add("3");
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
//        activityBinding.reviewsList.setLayoutManager(layoutManager);
//        ReviewsAdapter adapter = new ReviewsAdapter(reviews, App.applicationContext);
//        activityBinding.reviewsList.setAdapter(adapter);
    }

    private void getImages() {
//        activityBinding.spGallery.fourPlusImagesLayout.iv4Plus.setVisibility(View.VISIBLE);
//        activityBinding.spGallery.fourPlusImagesLayout.L4PText.setText("+6");
    }
}