package com.rizorsiumani.workondemanduser.ui.fragment.wallet;

import static android.icu.number.Precision.increment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.FragmentWalletTopupBinding;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;


public class WalletTopup extends BaseFragment<FragmentWalletTopupBinding> {

    private WalletViewModel viewModel;

    @Override
    protected FragmentWalletTopupBinding getFragmentBinding() {
        return FragmentWalletTopupBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(WalletViewModel.class);
        hideCartButton();
        Dashboard.hideTabs();
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
                        } else if (!response.getError().isEmpty()) {
                            hideLoading();
                            showSnackBarShort(response.getError());
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