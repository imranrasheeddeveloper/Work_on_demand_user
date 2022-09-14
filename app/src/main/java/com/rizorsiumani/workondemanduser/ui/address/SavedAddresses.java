package com.rizorsiumani.workondemanduser.ui.address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityAddAddressBinding;
import com.rizorsiumani.workondemanduser.databinding.ActivitySavedAddressesBinding;
import com.rizorsiumani.workondemanduser.ui.add_location.AddAddress;
import com.rizorsiumani.workondemanduser.ui.fragment.wallet.TransactionsAdapter;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

public class SavedAddresses extends BaseActivity<ActivitySavedAddressesBinding> {

    @Override
    protected ActivitySavedAddressesBinding getActivityBinding() {
        return ActivitySavedAddressesBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        activityBinding.addressToolbar.title.setText("Address");
        addressRv();
        clickListeners();

    }

    private void clickListeners() {

        activityBinding.addAddress.setOnClickListener(view -> {
            ActivityUtil.gotoPage(SavedAddresses.this, AddAddress.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        activityBinding.addressToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

    }

    private void addressRv() {
        List<String> transactions = new ArrayList<>();
        transactions.add("Nawab Town, Lahore");
        transactions.add("Model Town, Lahore");
        transactions.add("Gullberg 3, Lahore");

        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        activityBinding.addressList.setLayoutManager(layoutManager);
        AdressesAdapter adapter = new AdressesAdapter(transactions, App.applicationContext);
        activityBinding.addressList.setAdapter(adapter);
    }

}