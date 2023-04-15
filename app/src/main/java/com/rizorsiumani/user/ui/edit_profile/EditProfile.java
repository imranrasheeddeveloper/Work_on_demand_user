package com.rizorsiumani.user.ui.edit_profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rizorsiumani.user.App;
import com.rizorsiumani.user.BaseActivity;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.common.ImageUploadViewModel;
import com.rizorsiumani.user.data.businessModels.CompanyInfoModel;
import com.rizorsiumani.user.data.businessModels.UserData;
import com.rizorsiumani.user.data.local.TinyDbManager;
import com.rizorsiumani.user.databinding.ActivityEditProfileBinding;
import com.rizorsiumani.user.utils.Constants;
import com.rizorsiumani.user.utils.GetRealPathFromUri;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfile extends BaseActivity<ActivityEditProfileBinding> {

    private ImageUploadViewModel viewModel;
    Uri uri;
    String imagePath;

    @Override
    protected ActivityEditProfileBinding getActivityBinding() {
        return ActivityEditProfileBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        try {

            if (TinyDbManager.getUserInformation() != null) {
                UserData userData = TinyDbManager.getUserInformation();
                if (TinyDbManager.getUserType().equalsIgnoreCase("Commercial")) {
                    activityBinding.companyDetails.setVisibility(View.VISIBLE);
                } else {
                    activityBinding.companyDetails.setVisibility(View.GONE);
                }
                setData(userData);

            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        viewModel = new ViewModelProvider(this).get(ImageUploadViewModel.class);
        activityBinding.editProfileToolbar.title.setText("Edit Profile");

        clickListeners();
    }

    private void setData(UserData userData) {
        activityBinding.companyDetails.setVisibility(View.GONE);

        if (userData.getImage() != null) {
            if (uri == null) {
//                Glide.with(EditProfile.this)
//                        .load(Constants.IMG_PATH + userData.getImage())
//                        .placeholder(R.color.placeholder_bg)
//                        .into(activityBinding.userImage);

                activityBinding.editUserImage.setImageResource(R.drawable.ic_edit_blue);
            }
        } else {
            activityBinding.editUserImage.setImageResource(R.drawable.ic_add);
        }

        activityBinding.edFirstname.setText(userData.getFirstName());
        activityBinding.edLastname.setText(userData.getLastName());
        activityBinding.edEmail.setText(userData.getEmail());
        activityBinding.edNumber.setText(userData.getPhoneNumber());
        if (userData.getCompany() != null) {
            activityBinding.companyDetails.setVisibility(View.VISIBLE);
            activityBinding.edCompanyName.setText(userData.getCompany().getCompanyName());
            activityBinding.edCompanyBuilding.setText(userData.getLastName());
            activityBinding.edCompanyOwner.setText(userData.getEmail());
            activityBinding.edCompanyVat.setText(userData.getPhoneNumber());
            activityBinding.edCompanyReg.setText(userData.getPhoneNumber());
            activityBinding.edCompanyStreet.setText(userData.getPhoneNumber());
            activityBinding.edCompanyPosition.setText(userData.getPhoneNumber());
        }
    }

    private void clickListeners() {
        activityBinding.editProfileToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        activityBinding.editUserImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
        });

        activityBinding.btnUpdate.setOnClickListener(view -> {

            String first_name = activityBinding.edFirstname.getText().toString().trim();
            String last_name = activityBinding.edLastname.getText().toString().trim();
            String email = activityBinding.edEmail.getText().toString().trim();
            String number = activityBinding.edNumber.getText().toString().trim();
            String com_name = activityBinding.edCompanyName.getText().toString().trim();
            String com_building = activityBinding.edCompanyBuilding.getText().toString().trim();
            String com_owner = activityBinding.edCompanyOwner.getText().toString().trim();
            String com_position = activityBinding.edCompanyPosition.getText().toString().trim();
            String com_street = activityBinding.edCompanyStreet.getText().toString();
            String com_reg = activityBinding.edCompanyReg.getText().toString();
            String com_vat = activityBinding.edCompanyVat.getText().toString();

            if (TinyDbManager.getUserType().equalsIgnoreCase("Commercial")) {


                if (TextUtils.isEmpty(first_name)) {
                    showSnackBarShort("First Name Required");
                } else if (TextUtils.isEmpty(last_name)) {
                    showSnackBarShort("Last Name Required");
                } else if (TextUtils.isEmpty(email)) {
                    showSnackBarShort("Email Required");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    showSnackBarShort("Valid Email Required");
                } else if (TextUtils.isEmpty(number)) {
                    showSnackBarShort("Phone Number Required");
                } else if (!Patterns.PHONE.matcher(number).matches()) {
                    showSnackBarShort("Valid Number Required");
                } else if (TextUtils.isEmpty(com_name)) {
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
                    if (uri != null) {
                        uploadCommercialImage(first_name, last_name, email, number,com_name,com_building,com_owner,com_position,com_street,com_reg,com_vat);
                    } else {
                        updateCommercialUser(first_name, last_name, email, number,com_name,com_building,com_owner,com_position,com_street,com_reg,com_vat);
                    }
                }
            }else {

                if (TextUtils.isEmpty(first_name)) {
                    showSnackBarShort("First Name Required");
                } else if (TextUtils.isEmpty(last_name)) {
                    showSnackBarShort("Last Name Required");
                } else if (TextUtils.isEmpty(email)) {
                    showSnackBarShort("Email Required");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    showSnackBarShort("Valid Email Required");
                } else if (TextUtils.isEmpty(number)) {
                    showSnackBarShort("Phone Number Required");
                } else if (!Patterns.PHONE.matcher(number).matches()) {
                    showSnackBarShort("Valid Number Required");
                } else {
                    if (uri != null) {
                        uploadImage(first_name, last_name, email, number);
                    } else {
                        updateData(first_name, last_name, email, number);
                    }
                }
            }

        });
    }

    private void updateCommercialUser(String first_name, String last_name, String email, String number, String com_name, String com_building, String com_owner, String com_position, String com_street, String com_reg, String com_vat) {
        JsonObject object = new JsonObject();
        Gson gson = new Gson();
        object.addProperty("first_name", first_name);
        object.addProperty("last_name", last_name);
        object.addProperty("email", email);
        object.addProperty("phone_number", number);
        object.addProperty("type", "Commercial");
        CompanyInfoModel model = new CompanyInfoModel(com_building,com_owner,com_reg,com_name,com_vat,com_position,com_street);
        object.add("company",gson.toJsonTree(model));
        if (imagePath == null) {
            if (TinyDbManager.getUserInformation().getImage() != null) {
                object.addProperty("image", TinyDbManager.getUserInformation().getImage());
            } else {
                object.addProperty("image", "");
            }
        } else {
            object.addProperty("image", Constants.IMG_PATH + imagePath);
        }
        String token = prefRepository.getString("token");

        viewModel.update(token, object);

        viewModel._update.observe(this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (response.getError() != null) {
                    hideLoading();
                    if (response.getError() == null){
                        showSnackBarShort("Something went wrong!!");
                    }else {
                        Constants.constant.getApiError(com.rizorsiumani.user.App.applicationContext,response.getError());
                    }
                } else if (response.getData().getData() != null) {
                    hideLoading();
                    showSnackBarShort(response.getData().getMessage());
                    TinyDbManager.saveUserData(response.getData().getData());
                    setData(TinyDbManager.getUserInformation());
                }
            }
        });
    }

    private void uploadCommercialImage(String first_name, String last_name, String email, String number, String com_name, String com_building, String com_owner, String com_position, String com_street, String com_reg, String com_vat) {
        File file1 = new File(GetRealPathFromUri.getRealPathFromURI(uri, EditProfile.this));

        MultipartBody.Part multiPartProfile = MultipartBody.Part.createFormData("image",
                file1.getName(),
                RequestBody.create(
                        file1,
                        MediaType.parse("*/*")
                )
        );

        showLoading();
        viewModel.upload(multiPartProfile);

        viewModel._response.observe(this, response -> {
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
                    if (!response.getData().getFilePATH().isEmpty()) {
                        imagePath = response.getData().getFilePATH();
                        updateCommercialUser(first_name, last_name, email, number,com_name,com_building,com_owner,com_position,com_street,com_reg,com_vat);
                    }
                }
            }
        });

    }


    private void uploadImage(String first_name, String last_name, String email, String number) {
        File file1 = new File(GetRealPathFromUri.getRealPathFromURI(uri, EditProfile.this));

        MultipartBody.Part multiPartProfile = MultipartBody.Part.createFormData("image",
                file1.getName(),
                RequestBody.create(
                        file1,
                        MediaType.parse("*/*")
                )
        );

        showLoading();
        viewModel.upload(multiPartProfile);

        viewModel._response.observe(this, response -> {
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
                    if (!response.getData().getFilePATH().isEmpty()) {
                        imagePath = response.getData().getFilePATH();
                        updateData(first_name, last_name, email, number);
                    }
                }
            }
        });


    }

    private void updateData(String first_name, String last_name, String email, String number) {
        JsonObject object = new JsonObject();
        object.addProperty("first_name", first_name);
        object.addProperty("last_name", last_name);
        object.addProperty("email", email);
        object.addProperty("type", "Residential");
        object.addProperty("phone_number", number);
        if (imagePath == null) {
            if (TinyDbManager.getUserInformation().getImage() != null) {
                object.addProperty("image", TinyDbManager.getUserInformation().getImage());
            } else {
                object.addProperty("image", "");
            }
        } else {
            object.addProperty("image", Constants.IMG_PATH + imagePath);
        }
        String token = prefRepository.getString("token");

        viewModel.update(token, object);

        viewModel._update.observe(this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                 } else if (response.getError() != null) {
                    hideLoading();
                    if (response.getError() == null){
                        showSnackBarShort("Something went wrong!!");
                    }else {
                        Constants.constant.getApiError(com.rizorsiumani.user.App.applicationContext,response.getError());
                    }
                } else if (response.getData().getData() != null) {
                    hideLoading();
                    showSnackBarShort(response.getData().getMessage());
                    TinyDbManager.saveUserData(response.getData().getData());
                    setData(TinyDbManager.getUserInformation());
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                uri = data.getData();
                activityBinding.userImage.setImageURI(uri);
                activityBinding.userImage.setTag("uri");
                activityBinding.editUserImage.setImageResource(R.drawable.ic_edit_blue);
//                String path = Constants.constant.getRealPathFromURI(uri, EditProfile.this);
//                Glide.with(EditProfile.this).load(path).into(activityBinding.userImage);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        viewModel._update.removeObservers(this);
        viewModel._response.removeObservers(this);
        viewModel = null;
    }

}