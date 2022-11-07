package com.rizorsiumani.workondemanduser.ui.promo_code;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.PromoDataItem;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.ActivityPromoCodeBinding;
import com.rizorsiumani.workondemanduser.ui.booking_detail.CartServicesAdapter;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.ui.login.Login;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;
import com.rizorsiumani.workondemanduser.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class PromoCode extends BaseActivity<ActivityPromoCodeBinding> {

    PromoCodeAdapter adapter;
    PromotionViewModel viewModel;
    List<PromoDataItem> promoDataItemList;
    String selected_code;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    PromoDataItem selectedPromo;

    @Override
    protected ActivityPromoCodeBinding getActivityBinding() {
        return ActivityPromoCodeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        hideCartButton();

        viewModel = new ViewModelProvider(this).get(PromotionViewModel.class);
        viewModel.getCodes();
        viewModel._promotion.observe(this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                    hideLoading();
                    promoDataItemList = new ArrayList<>();
                    promoDataItemList.addAll(response.getData().getData());
                    buildRv(promoDataItemList);

                }
            }
        });


        activityBinding.promoToolbar.title.setText("Promo Code");
        clickListeners();
    }

    private void buildRv(List<PromoDataItem> promoDataItemList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        activityBinding.promosList.setLayoutManager(layoutManager);
        adapter = new PromoCodeAdapter(promoDataItemList, PromoCode.this);
        activityBinding.promosList.setAdapter(adapter);

        adapter.setOnPromoCodesClickListener(position -> {
            activityBinding.etSelectedCode.setText(String.valueOf(promoDataItemList.get(position).getCode()));
            selectedPromo = promoDataItemList.get(position);
            selected_code = String.valueOf(promoDataItemList.get(position).getCode());
            //Constants.constant.discount = String.valueOf(promoDataItemList.get(position).getDiscount());
        });

    }

    private void clickListeners() {
        activityBinding.promoToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        activityBinding.tvActivateCode.setOnClickListener(view -> {
           if (selectedPromo != null){
               activateCode(activityBinding.etSelectedCode.getText().toString(), selectedPromo);
           }
        });
    }

    private void activateCode(String code, PromoDataItem selectedPromo) {
        showLoading();
//        dialogBuilder = new AlertDialog.Builder(PromoCode.this);
//        View layoutView = getLayoutInflater().inflate(R.layout.activating_code_dialogue, null);
//        ProgressBar progressBar = layoutView.findViewById(R.id.code_activation_spin);
//        Sprite doubleBounce = new FadingCircle();
//        progressBar.setIndeterminateDrawable(doubleBounce);
//        dialogBuilder.setView(layoutView);
//        alertDialog = dialogBuilder.create();
//        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimations;
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        alertDialog.show();

        viewModel.activate(code);
        viewModel._activate.observe(this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    showSnackBarShort(response.getError());
                    hideLoading();
                 //   alertDialog.dismiss();
                } else if (response.getData().isSuccess()) {
                    saveValidCode(selectedPromo);
                    hideLoading();
                    onBackPressed();
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });


    }

    private void saveValidCode(PromoDataItem selectedPromo) {
        TinyDbManager.savePromo(selectedPromo);
    }
}