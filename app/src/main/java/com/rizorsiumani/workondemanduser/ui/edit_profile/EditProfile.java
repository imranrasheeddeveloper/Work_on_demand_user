package com.rizorsiumani.workondemanduser.ui.edit_profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.common.ImageUploadViewModel;
import com.rizorsiumani.workondemanduser.data.businessModels.UpdateUserModel;
import com.rizorsiumani.workondemanduser.databinding.ActivityEditProfileBinding;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.ui.register.CreatePassword;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;
import com.rizorsiumani.workondemanduser.utils.Constants;

public class EditProfile extends BaseActivity<ActivityEditProfileBinding> {

    private ImageUploadViewModel viewModel;
    String imageUrl;

    @Override
    protected ActivityEditProfileBinding getActivityBinding() {
        return ActivityEditProfileBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel = new ViewModelProvider(this).get(ImageUploadViewModel.class);
        activityBinding.editProfileToolbar.title.setText("Edit Profile");
        clickListeners();
    }

    private void clickListeners() {
        activityBinding.editProfileToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
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
            String pass = activityBinding.edPass.getText().toString().trim();

            if (TextUtils.isEmpty(first_name)){
                showSnackBarShort("First Name Required");
            }else if (TextUtils.isEmpty(last_name)){
                showSnackBarShort("Last Name Required");
            }else if (TextUtils.isEmpty(email)){
                showSnackBarShort("Email Required");
            }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                showSnackBarShort("Valid Email Required");
            }else if (TextUtils.isEmpty(number)){
                showSnackBarShort("Phone Number Required");
            } else if (!Patterns.PHONE.matcher(number).matches()) {
                showSnackBarShort("Valid Number Required");
            }else if (TextUtils.isEmpty(pass)){
                showSnackBarShort("Password Required");
            }else {
                updateData(first_name,last_name,email,number,pass);
            }
        });
    }

    private void updateData(String first_name, String last_name, String email, String number, String pass) {
        JsonObject object = new JsonObject();
        object.addProperty("firstName",first_name);
        object.addProperty("lastName",last_name);
        object.addProperty("email",email);
        object.addProperty("phoneNumber",number);
        object.addProperty("password",pass);
        object.addProperty("image", "");
        String token = prefRepository.getString("token");

        viewModel.update(token,object);

        viewModel._update.observe(this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                    hideLoading();
                    Toast.makeText(EditProfile.this, "Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                String path = Constants.constant.getRealPathFromURI(uri, EditProfile.this);
                Glide.with(EditProfile.this).load(path).into(activityBinding.userImage);

                JsonObject object = new JsonObject();
                object.addProperty("image", path);
                viewModel.upload(object);

                viewModel._response.observe(this, response -> {
                    if (response != null) {
                        if (response.isLoading()) {
                            showLoading();
                        } else if (!response.getError().isEmpty()) {
                            //we have error to show
                            hideLoading();
                            showSnackBarShort(response.getError());
                        } else if (response.getData().getMessage() != null) {
                            showSnackBarShort(response.getData().getMessage());
                        }
                    }
                });
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