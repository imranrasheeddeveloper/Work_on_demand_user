package com.rizorsiumani.user.ui.fragment.wallet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.View;

import com.google.gson.JsonObject;
import com.rizorsiumani.user.App;
import com.rizorsiumani.user.BaseFragment;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.databinding.FragmentWalletTopupBinding;
import com.rizorsiumani.user.ui.dashboard.Dashboard;
import com.rizorsiumani.user.utils.Constants;


public class WalletTopup extends BaseFragment<FragmentWalletTopupBinding> {

    private WalletViewModel viewModel;

    @Override
    protected FragmentWalletTopupBinding getFragmentBinding() {
        return FragmentWalletTopupBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Constants.constant.isHome = false;

        viewModel = new ViewModelProvider(this).get(WalletViewModel.class);
        hideCartButton();
        Dashboard.hideTabs(true);
        fragmentBinding.topupToolbar.title.setText("Enter top-up amount");
        clickListener();
    }

    private void clickListener() {
        fragmentBinding.topupToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        fragmentBinding.btnTopup.setOnClickListener(view -> {
            String amount = fragmentBinding.edAmount.getText().toString();
            if (TextUtils.isEmpty(amount)){
                showSnackBarShort("Amount Required");
            }else {
                String token = prefRepository.getString("token");
                JsonObject object = new JsonObject();
                object.addProperty("amount", amount);
                viewModel.walletUp(token, object);
                viewModel._up_wallet.observe(getViewLifecycleOwner(), response -> {
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
                        } else if (response.getData().isSuccess()) {
                            hideLoading();
                            showSnackBarShort(response.getData().getMessage());
                            Navigation.findNavController(view).navigateUp();
                        }
                    }
                });
            }
        });

        fragmentBinding.inc200.setOnClickListener(view -> {
            incrementTopup(200);
        });

        fragmentBinding.inc500.setOnClickListener(view -> {
            incrementTopup(500);
        });

        fragmentBinding.inc1000.setOnClickListener(view -> {
            incrementTopup(1000);
        });
    }

    private void incrementTopup(int inc) {
        String amount = fragmentBinding.edAmount.getText().toString();
        if (!amount.isEmpty()) {
            int amountInt = Integer.valueOf(amount);
            amountInt = amountInt + inc;
            fragmentBinding.edAmount.setText(String.valueOf(amountInt));
        }else {
            fragmentBinding.edAmount.setText(String.valueOf(inc));
        }
    }
}