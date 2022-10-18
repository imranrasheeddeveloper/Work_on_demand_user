package com.rizorsiumani.workondemanduser.ui.post_job;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.paulrybitskyi.valuepicker.ValuePickerView;
import com.paulrybitskyi.valuepicker.model.Item;
import com.paulrybitskyi.valuepicker.model.PickerItem;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.common.ImageUploadViewModel;
import com.rizorsiumani.workondemanduser.data.businessModels.CategoriesDataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.SubCategoryDataItem;
import com.rizorsiumani.workondemanduser.databinding.ActivityPostJobBinding;
import com.rizorsiumani.workondemanduser.ui.edit_profile.EditProfile;
import com.rizorsiumani.workondemanduser.ui.fragment.home.HomeViewModel;
import com.rizorsiumani.workondemanduser.ui.sub_category.SubCategoryViewModel;
import com.rizorsiumani.workondemanduser.utils.Constants;
import com.rizorsiumani.workondemanduser.utils.GetRealPathFromUri;
import com.skydoves.elasticviews.ElasticImageView;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PostJob extends BaseActivity<ActivityPostJobBinding> implements DatePickerDialog.OnDateSetListener {

    String imagesPath;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    ArrayList<String> budgetUnitList;
    private HomeViewModel homeViewModel;
    private SubCategoryViewModel subCategoryViewModel;
    List<CategoriesDataItem> categoriesDataItems;
    List<SubCategoryDataItem> subCategoryDataItems;

    int selectedCatID = 0;
    int selectedSubCatID;
    String selectedBudgetUnit;
    int tempID;

    private PostJobViewModel postJobViewModel;



    @Override
    protected ActivityPostJobBinding getActivityBinding() {
        return ActivityPostJobBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        homeViewModel = new ViewModelProvider(PostJob.this).get(HomeViewModel.class);
        subCategoryViewModel = new ViewModelProvider(PostJob.this).get(SubCategoryViewModel.class);
        postJobViewModel = new ViewModelProvider(PostJob.this).get(PostJobViewModel.class);

        if (homeViewModel._category.getValue() == null){
            homeViewModel.categories(1);
        }



        homeViewModel._category.observe(PostJob.this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                } else if (!response.getError().isEmpty()) {
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                    categoriesDataItems = new ArrayList<>();
                    categoriesDataItems.addAll(response.getData().getData());
                }
            }
        });


        clickListeners();

    }

    private ArrayList<String> getList() {
        ArrayList<String> budget_unit = new ArrayList<>();
        budget_unit.add("Fixed");
        budget_unit.add("Hourly");
        budget_unit.add("Weekly");
        budget_unit.add("Monthly");
        return budget_unit;
    }

    private void clickListeners() {

        activityBinding.ivAddImage.setOnClickListener(view -> {
            checkPermissions();
        });

        activityBinding.postJobToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.stationary, R.anim.slide_down);
        });

        activityBinding.tvCategory.setOnClickListener(view -> {
            if (categoriesDataItems.size() > 0) {
                int id = showCategoriesDialogue(0, activityBinding.selectedCategory);
                if (id != 0){
                    selectedCatID = id;
                }
            }
        });

        activityBinding.tvSubcategory.setOnClickListener(view -> {
            if (selectedCatID != 0) {
                if (subCategoryViewModel._subCategory.getValue() == null){
                    subCategoryViewModel.subCategories(selectedCatID,1);
                }

                subCategoryViewModel._subCategory.observe(PostJob.this, response -> {
                    if (response != null) {
                        if (response.isLoading()) {
                            showLoading();
                        } else if (!response.getError().isEmpty()) {
                            hideLoading();
                            showSnackBarShort(response.getError());
                        } else if (response.getData().getData() != null) {
                            hideLoading();

                            subCategoryDataItems = new ArrayList<>();
                            subCategoryDataItems.addAll(response.getData().getData());
                            if (subCategoryDataItems.size() > 0) {
                                int id = showCategoriesDialogue(1, activityBinding.selectedSubcategory);
                                if (id != 0){
                                    selectedSubCatID = id;
                                }
                            }

                        }
                    }
                });

            }else {
                showSnackBarShort("Category Required");
            }
        });

        activityBinding.budgetUnitLayout.setOnClickListener(view -> {


            showCategoriesDialogue(2, activityBinding.selectedBudgetUnit);
        });

        activityBinding.deadlineDate.setOnClickListener(view -> {
            com.rizorsiumani.workondemanduser.utils.DatePicker mDatePickerDialogFragment;
            mDatePickerDialogFragment = new com.rizorsiumani.workondemanduser.utils.DatePicker();
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "Select Date");
        });

        activityBinding.btnPost.setOnClickListener(view -> {
            String title = activityBinding.edTitle.getText().toString();
            String description = activityBinding.edDescribe.getText().toString();
            String budget = activityBinding.edBudget.getText().toString();
            String date = activityBinding.deadlineDate.getText().toString();

            if (TextUtils.isEmpty(title)){
                showSnackBarShort("Title required");
            }else if (TextUtils.isEmpty(budget)){
                showSnackBarShort("Budget required");
            }else if (selectedBudgetUnit.isEmpty()){
                showSnackBarShort("Budget Unit required");
            }else if (TextUtils.isEmpty(date)){
                showSnackBarShort("Date required");
            }else {
                postJob(title,description,budget,selectedBudgetUnit,selectedCatID,selectedSubCatID,imagesPath,date);
            }

        });


    }

    private void postJob(String title, String description, String budget, String selectedBudgetUnit,
                         int selectedCatID, int selectedSubCatID, String imagesPath, String date) {

        String token = prefRepository.getString("token");

        JsonObject object = new JsonObject();
        object.addProperty("title",title);
        object.addProperty("description",description);
        object.addProperty("category_id",selectedCatID);
        object.addProperty("sub_category_id",selectedSubCatID);
        object.addProperty("budget",budget);
        object.addProperty("attachment",imagesPath);
        object.addProperty("price_unit",selectedBudgetUnit);
        object.addProperty("date",date);

        postJobViewModel.post(token,object);
        postJobViewModel._job.observe(PostJob.this,response -> {
            if (response != null) {
                if (response.isLoading()) {
                } else if (!response.getError().isEmpty()) {
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {

                    showSnackBarShort(response.getData().getMessage());

                }
            }
        });

    }


    private int showCategoriesDialogue(int value, TextView textView) {

        dialogBuilder = new AlertDialog.Builder(PostJob.this);
        dialogBuilder.setCancelable(false);
        View layoutView = getLayoutInflater().inflate(R.layout.pick_category_dialogue, null);
        TextView cancel = (TextView) layoutView.findViewById(R.id.tv_cancel);
        TextView select = (TextView) layoutView.findViewById(R.id.tv_done);
        ValuePickerView valuePickerView = (ValuePickerView) layoutView.findViewById(R.id.teamPicker);
        valuePickerView.setOnItemSelectedListener((item) -> {
            textView.setTag(item.getTitle());
        });

        List<Item> pickerItems = null;
        if (value == 0) {
            pickerItems = getCatPickerItems();
            valuePickerView.setItems(getCatPickerItems());
        }else if (value == 1){
            pickerItems = getSubCatPickerItems();
            valuePickerView.setItems(getSubCatPickerItems());
        }else if (value == 2){
            pickerItems = getBudgetPickerItems();
            valuePickerView.setItems(getBudgetPickerItems());
        }

        //valuePickerView.setSelectedItem(pickerItems.get(2));


        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimations;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        cancel.setOnClickListener(view1 -> {
            alertDialog.dismiss();
            tempID = 0;
        });

        select.setOnClickListener(view1 -> {
            if (value == 2){
                selectedBudgetUnit = valuePickerView.getSelectedItem().getTitle();
            }
            textView.setText(valuePickerView.getSelectedItem().getTitle());
            tempID = valuePickerView.getSelectedItem().getId();
            alertDialog.dismiss();

        });

        return tempID;
    }

    private List<Item> getCatPickerItems() {
        final List<Item> pickerItems = new ArrayList<>();

        for (int i = 0; i <= categoriesDataItems.size() - 1; i++) {
            pickerItems.add(
                    new PickerItem(
                            categoriesDataItems.get(i).getId(),
                            categoriesDataItems.get(i).getTitle()
                    )
            );
        }

        return pickerItems;
    }

    private List<Item> getSubCatPickerItems() {
        final List<Item> pickerItems = new ArrayList<>();

        for (int i = 0; i <= subCategoryDataItems.size() - 1; i++) {
            pickerItems.add(
                    new PickerItem(
                            subCategoryDataItems.get(i).getId(),
                            subCategoryDataItems.get(i).getTitle()
                    )
            );
        }

        return pickerItems;
    }

    private List<Item> getBudgetPickerItems() {

        List<String> budgetUnit = new ArrayList<>();
        budgetUnit.add("Fixed");
        budgetUnit.add("Hourly");
        budgetUnit.add("Weekly");
        budgetUnit.add("Yearly");
        final List<Item> pickerItems = new ArrayList<>();

        for (int i = 0; i <= budgetUnit.size() - 1; i++) {
            pickerItems.add(
                    new PickerItem(
                            i,
                            budgetUnit.get(i)
                    )
            );
        }

        return pickerItems;
    }


    private void checkPermissions() {
        Dexter.withContext(PostJob.this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 13);

                        } else if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            //open app settings dialog
                            Toast.makeText(PostJob.this, "Denied Permanently !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 13) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                File file1 = new File(GetRealPathFromUri.getPathFromUri(PostJob.this, uri));

                MultipartBody.Part multiPartProfile = MultipartBody.Part.createFormData("image",
                        file1.getName(),
                        RequestBody.create(
                                file1,
                                MediaType.parse("*/*")
                        )
                );

                postJobViewModel.postImage(multiPartProfile);

                postJobViewModel._job_image.observe(this, response -> {
                    if (response != null) {
                        if (response.isLoading()) {
                            showLoading();
                        } else if (!response.getError().isEmpty()) {
                            hideLoading();
                            showSnackBarShort(response.getError());
                        } else if (response.getData().getMessage() != null) {
                            showSnackBarShort(response.getData().getMessage());
                            imagesPath = response.getData().getFilePATH();
                            Glide.with(PostJob.this).load(Constants.IMG_PATH + imagesPath).into(activityBinding.ivAddImage);
                        }
                    }
                });
            }
        }
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        activityBinding.deadlineDate.setText(selectedDate);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        homeViewModel._category.removeObservers(this);
        postJobViewModel._job.removeObservers(this);
        subCategoryViewModel._subCategory.removeObservers(this);
        postJobViewModel._job_image.removeObservers(this);

        homeViewModel = null;
        postJobViewModel = null;
        subCategoryViewModel = null;
    }
}