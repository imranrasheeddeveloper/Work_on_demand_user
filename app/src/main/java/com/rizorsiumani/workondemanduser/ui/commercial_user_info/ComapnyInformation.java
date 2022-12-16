package com.rizorsiumani.workondemanduser.ui.commercial_user_info;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.CompanyInfoModel;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.ActivityComapnyInformationBinding;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.ui.login.Login;
import com.rizorsiumani.workondemanduser.ui.register.Register;
import com.rizorsiumani.workondemanduser.ui.register.RegisterUserViewModel;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;
import com.rizorsiumani.workondemanduser.utils.Constants;

public class ComapnyInformation extends BaseActivity<ActivityComapnyInformationBinding> {

    private RegisterUserViewModel viewModel;


    @Override
    protected ActivityComapnyInformationBinding getActivityBinding() {
        return ActivityComapnyInformationBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();
        hideCartButton();
        viewModel = new ViewModelProvider(this).get(RegisterUserViewModel.class);

        activityBinding.btnSaveCompanyDetail.setOnClickListener(view -> {
            String com_name = activityBinding.edCompanyName.getText().toString().trim();
            String com_building = activityBinding.edCompanyBuilding.getText().toString().trim();
            String com_owner = activityBinding.edCompanyOwner.getText().toString().trim();
            String com_position = activityBinding.edCompanyPosition.getText().toString().trim();
            String com_street = activityBinding.edCompanyStreet.getText().toString();
            String com_reg = activityBinding.edCompanyReg.getText().toString();
            String com_vat = activityBinding.edCompanyVat.getText().toString();


            if (TextUtils.isEmpty(com_name)) {
                showSnackBarShort("Company Name Required");
            } else if (TextUtils.isEmpty(com_building)) {
                showSnackBarShort("Building Name Required");
            } else if (TextUtils.isEmpty(com_owner)) {
                showSnackBarShort("Owner Name Required");
            } else if (TextUtils.isEmpty(com_position)) {
                showSnackBarShort("Position Required");
            } else if (TextUtils.isEmpty(com_street)) {
                showSnackBarShort("Company Address Required");
            } else if (TextUtils.isEmpty(com_reg)) {
                showSnackBarShort("Registration Required");
            } else if (TextUtils.isEmpty(com_vat)) {
                showSnackBarShort("VAT Number Required");
            } else {
                try {
                    String first_name = getIntent().getStringExtra("first_name");
                    String last_name = getIntent().getStringExtra("last_name");
                    String email = getIntent().getStringExtra("email");
                    String number = getIntent().getStringExtra("phone_number");
                    String password = getIntent().getStringExtra("password");
                    regCompany(first_name,last_name,email,number,password,com_name,com_building,com_owner,com_position,com_street,com_reg,com_vat);

                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void regCompany(String first_name, String last_name, String email, String number, String password, String com_name, String com_building, String com_owner, String com_position, String com_street, String com_reg, String com_vat) {
        try {
            Gson gson = new Gson();
            CompanyInfoModel model = new CompanyInfoModel(com_building,com_owner,com_reg,com_name,com_vat,com_position,com_street);
            JsonObject object = new JsonObject();
            object.addProperty("first_name",first_name);
            object.addProperty("last_name",last_name);
            object.addProperty("email",email);
            object.addProperty("phone_number",number);
            object.addProperty("password",password);
            object.addProperty("type","Commercial");
            object.add("company",gson.toJsonTree(model));
            object.addProperty("fcm_token", Constants.constant.FCM_TOKEN);

            viewModel.registerUser(object);

            viewModel._regData.observe(this, response -> {
                if (response != null) {
                    if (response.isLoading()) {
                        showLoading();
                    } else if (!response.getError().isEmpty()) {
                        hideLoading();
                        showSnackBarShort(response.getError());
                    } else if (response.getData().getData() != null) {
                        hideLoading();
                        prefRepository.setString("token" , "Bearer "+response.getData().getToken());
                        TinyDbManager.saveUserData(response.getData().getData());
                        ActivityUtil.gotoPage(ComapnyInformation.this, Login.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }
            });

        }catch (NullPointerException |IllegalStateException | IllegalArgumentException e){
            e.printStackTrace();
        }
    }
}