package com.rizorsiumani.workondemanduser.ui.booking;

import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.data.businessModels.ServicesDataItem;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.ActivityBookServiceBinding;

public class BookService extends BaseActivity<ActivityBookServiceBinding> {

    ServicesDataItem servicesDataItem;
    String spID;


    @Override
    protected ActivityBookServiceBinding getActivityBinding() {
        return ActivityBookServiceBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();


        try {

            spID = getIntent().getStringExtra("service_provider_id");
            String data = getIntent().getStringExtra("service_data");
            if (data != null) {
                Gson gson = new Gson();
                servicesDataItem = gson.fromJson(data, ServicesDataItem.class);
                setData(servicesDataItem);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        activityBinding.bookToolbar.title.setText("Book Now");
        clickListeners();

    }

    private void setData(ServicesDataItem servicesDataItem) {
        try {

            activityBinding.sName.setText(servicesDataItem.getTitle());
            activityBinding.sDetail.setText(servicesDataItem.getDescription());
            activityBinding.chargePerHour.setText(servicesDataItem.getPrice());
            activityBinding.minimumHour.setText(servicesDataItem.getPriceUnit());
            activityBinding.estimatedCharge.setText(servicesDataItem.getPrice());

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void clickListeners() {

        activityBinding.bookToolbar.back.setOnClickListener(view -> {
            prefRepository.setString("cart", String.valueOf(false));
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        activityBinding.btnAddItem.setOnClickListener(view -> {
            if (servicesDataItem != null) {
                insertDataInCart(servicesDataItem,spID);
            }
        });

    }

    private void insertDataInCart(ServicesDataItem servicesDataItem, String spID) {
        MyCartItems cartItems = new MyCartItems(spID,servicesDataItem);
        TinyDbManager.saveCartData(cartItems);
        onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}