package com.rizorsiumani.user.ui.booking;

import android.content.Intent;

import com.google.gson.Gson;
import com.rizorsiumani.user.BaseActivity;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.ServicesDataItem;
import com.rizorsiumani.user.data.local.TinyDbManager;
import com.rizorsiumani.user.databinding.ActivityBookServiceBinding;
import com.rizorsiumani.user.ui.sp_detail.SpProfile;
import com.rizorsiumani.user.utils.Constants;

public class BookService extends BaseActivity<ActivityBookServiceBinding> {

    ServicesDataItem servicesDataItem;
    String spID;
    int selectedHours;


    @Override
    protected ActivityBookServiceBinding getActivityBinding() {
        return ActivityBookServiceBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();


        try {

            spID = TinyDbManager.getServiceProviderID();
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
            activityBinding.chargePerHour.setText(Constants.constant.CURRENCY + servicesDataItem.getPrice());
            activityBinding.minimumHour.setText(servicesDataItem.getPriceUnit());
            activityBinding.estimatedCharge.setText(Constants.constant.CURRENCY + servicesDataItem.getPrice());

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void clickListeners() {

        activityBinding.bookToolbar.back.setOnClickListener(view -> {
            prefRepository.setString("cart", String.valueOf(false));
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        activityBinding.btnAddItem.setOnClickListener(view -> {
            String description = activityBinding.instructions.getText().toString();
            if (description.isEmpty()){
                showSnackBarShort("Add Detail for Provider");
            }else {
                if (servicesDataItem != null) {
                    insertDataInCart(servicesDataItem,spID,description);
                }
            }
        });

    }

    private void insertDataInCart(ServicesDataItem servicesDataItem, String spID,String description) {
        if (TinyDbManager.getCartData() != null){
            boolean available = false;
            for (int i = 0; i < TinyDbManager.getCartData().size(); i++) {
                MyCartItems cartItems = TinyDbManager.getCartData().get(i);
                if (cartItems.data.getId() == servicesDataItem.getId()){
                    available = true;
                    showSnackBarShort("This item already in Cart");
                    Nav();

                }
            }
            if (!available){
                MyCartItems cartItems = new MyCartItems(spID,servicesDataItem, TinyDbManager.getBookingTiming() , description, Constants.constant.start_date);
                TinyDbManager.saveCartData(cartItems);
                Constants.constant.start_date = "";
//              TinyDbManager.saveServiceProviderID(spID);
                TinyDbManager.clearBookingTiming();
                Nav();

            }
        }


    }

    private void Nav() {
        Intent intent = new Intent(BookService.this, SpProfile.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}