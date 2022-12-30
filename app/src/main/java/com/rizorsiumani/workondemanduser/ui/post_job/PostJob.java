package com.rizorsiumani.workondemanduser.ui.post_job;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
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
import com.rizorsiumani.workondemanduser.data.businessModels.CategoriesDataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.PostedJobsDataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.SubCategoryDataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.job_timing.JobDaysItem;
import com.rizorsiumani.workondemanduser.data.businessModels.job_timing.JobTimingDataItem;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.ActivityPostJobBinding;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.ui.fragment.home.HomeViewModel;
import com.rizorsiumani.workondemanduser.ui.job_timing.JobTiming;
import com.rizorsiumani.workondemanduser.ui.job_timing.TimeItem;
import com.rizorsiumani.workondemanduser.ui.sub_category.SubCategoryViewModel;
import com.rizorsiumani.workondemanduser.ui.view_booking_information.BookingInformation;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;
import com.rizorsiumani.workondemanduser.utils.Constants;
import com.rizorsiumani.workondemanduser.utils.GetRealPathFromUri;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PostJob extends BaseActivity<ActivityPostJobBinding> implements DatePickerDialog.OnDateSetListener {

    String imagesPath;

    ArrayList<String> budgetUnitList;
    private HomeViewModel homeViewModel;
    private SubCategoryViewModel subCategoryViewModel;
    List<CategoriesDataItem> categoriesDataItems;
    List<SubCategoryDataItem> subCategoryDataItems;
    private int READ_STORAGE_PERMISSION_REQUEST_CODE = 23;

    int selectedCatID = 0;
    int selectedSubCatID;
    String selectedBudgetUnit;

    private PostJobViewModel postJobViewModel;
    Uri imageUri;
    AlertDialog alertDialog1 = null;
    PostedJobsDataItem postedJobsDataItem;
    String status;



    @Override
    protected ActivityPostJobBinding getActivityBinding() {
        return ActivityPostJobBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        status = getIntent().getStringExtra("status");



        homeViewModel = new ViewModelProvider(PostJob.this).get(HomeViewModel.class);
        subCategoryViewModel = new ViewModelProvider(PostJob.this).get(SubCategoryViewModel.class);
        postJobViewModel = new ViewModelProvider(PostJob.this).get(PostJobViewModel.class);

        if (homeViewModel._ddCategory.getValue() == null) {
            homeViewModel.dropDownCategories();
        }


        homeViewModel._ddCategory.observe(PostJob.this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                    hideLoading();
                    categoriesDataItems = new ArrayList<>();
                    categoriesDataItems.addAll(response.getData().getData());
                    if (status.equalsIgnoreCase("update")) {
                        for (int i = 0; i < categoriesDataItems.size(); i++) {
                            if (categoriesDataItems.get(i).getId() == postedJobsDataItem.getCategoryId()) {
                                activityBinding.selectedCategory.setText(categoriesDataItems.get(i).getTitle());
                                subCategoryViewModel.dropDownSubCategories(categoriesDataItems.get(i).getId());
                                subCategoryViewModel._subCategory.observe(PostJob.this, response1 -> {
                                    if (response1 != null) {
                                        if (response1.isLoading()) {
                                            showLoading();
                                        } else if (!response1.getError().isEmpty()) {
                                            hideLoading();
                                            showSnackBarShort(response1.getError());
                                        } else if (response1.getData().getData() != null) {
                                            hideLoading();

                                            subCategoryDataItems = new ArrayList<>();
                                            subCategoryDataItems.addAll(response1.getData().getData());
                                            for (int k = 0; k < subCategoryDataItems.size(); k++) {
                                                if (subCategoryDataItems.get(k).getId() == postedJobsDataItem.getSubCategoryId()) {
                                                    activityBinding.selectedSubcategory.setText(subCategoryDataItems.get(k).getTitle());
                                                }
                                            }
                                        }
                                    }
                                });
                                break;
                            }
                        }
                    }
                }
            }
        });

        if (status.equalsIgnoreCase("update")) {
            Gson gson = new Gson();
            String data = getIntent().getStringExtra("posted_job_detail");
            postedJobsDataItem = gson.fromJson(data, PostedJobsDataItem.class);
            if (postedJobsDataItem != null){
                setData(postedJobsDataItem);
            }
        }

        clickListeners();
        if (TinyDbManager.getTiming() != null) {
            List<TimeItem> data = new ArrayList<>();
            for (int i = 0; i < TinyDbManager.getTiming().size(); i++) {
                try {
                    if (TinyDbManager.getTiming().get(i).getTime() != null) {
                        if (TinyDbManager.getTiming().get(i).getTime().size() > 0) {
                            data.addAll(TinyDbManager.getTiming().get(i).getTime());
                        }
                        buildTimeSlotRv(data);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    private void setData(PostedJobsDataItem postedJobsDataItem) {
        try {

            activityBinding.edTitle.setText(postedJobsDataItem.getTitle());
            activityBinding.edBudget.setText(postedJobsDataItem.getBudget());
            activityBinding.edDescribe.setText(postedJobsDataItem.getDescription());
            Glide.with(PostJob.this).load(Constants.IMG_PATH + postedJobsDataItem.getAttachment()).into(activityBinding.ivAddImage);
            activityBinding.ivAddImage.setBackgroundResource(R.drawable.rect_bg);
            activityBinding.deleteImage.setVisibility(View.VISIBLE);


            activityBinding.selectedBudgetUnit.setText(postedJobsDataItem.getPriceUnit());
            postJobViewModel.getJobTiming(postedJobsDataItem.getId());
            postJobViewModel._job_timing.observe(this , response -> {
                if (response != null){
                    if (response.isLoading()) {
                        showLoading();
                    } else if (!response.getError().isEmpty()) {
                        hideLoading();
                        showSnackBarShort(response.getError());
                    } else if (response.getData().getData() != null) {
                        hideLoading();
                        if (response.getData().getData().size() > 0){
                            List<TimeItem> timeItems = new ArrayList<>();
                            for (int i = 0; i < response.getData().getData().size(); i++) {
                                for (int j = 0; j < response.getData().getData().get(i).getJobDays().size(); j++) {
                                    JobDaysItem daysItem = response.getData().getData().get(i).getJobDays().get(j);
                                    timeItems.add(new TimeItem(Integer.valueOf(daysItem.getTotalHours()), daysItem.getFromTime(), daysItem.getToTime()));

                                }
                            }
                            buildTimeSlotRv(timeItems);

                        }
                    }
                }
            });




        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void buildTimeSlotRv(List<TimeItem> timeItems) {
        if (timeItems.size() > 0) {
            GridLayoutManager layoutManager = new GridLayoutManager(PostJob.this, 3);
            activityBinding.timeData.setLayoutManager(layoutManager);
            SelectedTimeAdapter adapter = new SelectedTimeAdapter(PostJob.this, timeItems);
            activityBinding.timeData.setAdapter(adapter);
            activityBinding.deadlineDate.setVisibility(View.GONE);
            activityBinding.tvDeadline.setVisibility(View.GONE);
            activityBinding.timeData.setVisibility(View.VISIBLE);

            adapter.setOnSlotClickListener(position -> {

            });

        } else {
            activityBinding.deadlineDate.setVisibility(View.VISIBLE);
            activityBinding.tvDeadline.setVisibility(View.VISIBLE);
            activityBinding.timeData.setVisibility(View.GONE);
        }
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
            if (checkPermission()) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 13);
            } else {
                requestPermission();
            }
        });

        activityBinding.postJobToolbar.back.setOnClickListener(view -> {
            Intent intent = new Intent(PostJob.this, Dashboard.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.stationary, R.anim.slide_down);
        });

        activityBinding.selectedCategory.setOnClickListener(view -> {
            if (categoriesDataItems.size() > 0) {
                showCategoriesDialogue(0, activityBinding.selectedCategory);
            }
        });

        activityBinding.selectedSubcategory.setOnClickListener(view -> {
            if (selectedCatID != 0) {
                getSubCategories(selectedCatID);


            } else {
                showSnackBarShort("Category Required");
            }
        });

        activityBinding.budgetUnitLayout.setOnClickListener(view -> {
            showCategoriesDialogue(2, activityBinding.selectedBudgetUnit);
        });

        activityBinding.deleteImage.setOnClickListener(view -> {
            activityBinding.ivAddImage.setImageResource(R.drawable.ic_addd);
            activityBinding.ivAddImage.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#c7e9e6")));
            activityBinding.deleteImage.setVisibility(View.GONE);
        });

        activityBinding.deadlineDate.setOnClickListener(view -> {
            ActivityUtil.gotoPage(PostJob.this, JobTiming.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        });

        activityBinding.btnPost.setOnClickListener(view -> {
            try {

                if (imageUri != null) {
                    String title = activityBinding.edTitle.getText().toString();
                    String description = activityBinding.edDescribe.getText().toString();
                    String budget = activityBinding.edBudget.getText().toString();
                    String date = activityBinding.deadlineDate.getText().toString();

                    if (TextUtils.isEmpty(title)) {
                        showSnackBarShort("Title required");
                    }else if (TextUtils.isEmpty(description)){
                        showSnackBarShort("Description required");
                    } else if (TextUtils.isEmpty(budget)) {
                        showSnackBarShort("Budget required");
                    } else if (selectedBudgetUnit == null) {
                        showSnackBarShort("Budget Unit required");
                    } else if (selectedCatID == 0) {
                        showSnackBarShort("Select Category");
                    } else if (selectedSubCatID == 0) {
                        showSnackBarShort("Select Sub Category");
                    } else if (selectedBudgetUnit == null) {
                        showSnackBarShort("Select Budget unit");
                    }else if (TinyDbManager.getTiming().size() == 0) {
                        showSnackBarShort("Add Your Timing");
                    } else {
                        uploadImage(title, description, budget, selectedBudgetUnit, selectedCatID, selectedSubCatID, date);
                    }
                } else {
                    showSnackBarShort("Image Required");
                }

            } catch (NullPointerException | IllegalArgumentException | IllegalStateException e) {
                e.printStackTrace();
            }
        });


    }

    private void getSubCategories(int selectedCatID) {
        subCategoryViewModel.dropDownSubCategories(selectedCatID);
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
                    for (int i = 0; i < subCategoryDataItems.size(); i++) {
                        if (subCategoryDataItems.get(i).getId() == postedJobsDataItem.getSubCategoryId()){
                            activityBinding.selectedSubcategory.setText(subCategoryDataItems.get(i).getTitle());
                        }
                    }
                    if (subCategoryDataItems.size() > 0) {
                        showSubCategoriesDialogue(activityBinding.selectedSubcategory);
//                                if (id != 0) {
//                                    selectedSubCatID = id;
//                                }
                    } else {
                        showSnackBarShort("No Sub Category, Select Other Category");
                    }

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PostJob.this, Dashboard.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.stationary, R.anim.slide_down);
    }

    private void postJob(String title, String description, String budget, String selectedBudgetUnit,
                         int selectedCatID, int selectedSubCatID, String imagesPath, String date) {

        try {

        showLoading();
        String token = prefRepository.getString("token");

        Gson gson = new Gson();
        JsonObject object = new JsonObject();
        object.addProperty("title", title);
        object.addProperty("description", description);
        object.addProperty("category_id", String.valueOf(selectedCatID));
        object.addProperty("sub_category_id", String.valueOf(selectedSubCatID));
        object.addProperty("budget", budget);
        object.addProperty("attachment", imagesPath);
        object.addProperty("price_unit", selectedBudgetUnit);
        object.add("timings", gson.toJsonTree(TinyDbManager.getTiming()));
        object.addProperty("latitude", String.valueOf(Constants.constant.latitude));
        object.addProperty("longitude", String.valueOf(Constants.constant.longitude));
        object.addProperty("address", TinyDbManager.getCurrentAddress());


        postJobViewModel.post(token, object);
        postJobViewModel._job.observe(PostJob.this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().isSuccess()) {
                    hideLoading();
                    showSnackBarShort(response.getData().getMessage());
                    TinyDbManager.clearTiming();
                    Intent intent = new Intent(PostJob.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.stationary, R.anim.slide_down);

                }
            }
        });


        }catch (NullPointerException | IllegalArgumentException |IllegalStateException e){
            e.printStackTrace();
        }

    }


    private void showCategoriesDialogue(int value, TextView textView) {
        AlertDialog.Builder dialogBuilder;
        AlertDialog alertDialog;
        dialogBuilder = new AlertDialog.Builder(PostJob.this);
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
        } else if (value == 1) {
            pickerItems = getSubCatPickerItems();
            valuePickerView.setItems(getSubCatPickerItems());
        } else if (value == 2) {
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
        });

        select.setOnClickListener(view1 -> {
            if (value == 2) {
                selectedBudgetUnit = valuePickerView.getSelectedItem().getTitle();
            } else if (value == 0) {
                selectedCatID = valuePickerView.getSelectedItem().getId();
            } else if (value == 1) {
                selectedSubCatID = valuePickerView.getSelectedItem().getId();
            }
            textView.setText(valuePickerView.getSelectedItem().getTitle());
            alertDialog.dismiss();

        });

    }

    private void showSubCategoriesDialogue(TextView textView) {
        AlertDialog.Builder dialogBuilder1;
        if (alertDialog1 == null) {
            dialogBuilder1 = new AlertDialog.Builder(PostJob.this);
            View layoutView1 = getLayoutInflater().inflate(R.layout.pick_category_dialogue, null);
            TextView cancel = (TextView) layoutView1.findViewById(R.id.tv_cancel);
            TextView select = (TextView) layoutView1.findViewById(R.id.tv_done);
            ValuePickerView valuePickerView = (ValuePickerView) layoutView1.findViewById(R.id.teamPicker);
            valuePickerView.setOnItemSelectedListener((item) -> {
                textView.setTag(item.getTitle());
            });

            List<Item> pickerItems = null;

            pickerItems = getSubCatPickerItems();
            valuePickerView.setItems(getSubCatPickerItems());

            //valuePickerView.setSelectedItem(pickerItems.get(2));


            dialogBuilder1.setView(layoutView1);
            alertDialog1 = dialogBuilder1.create();
            alertDialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimations;
            alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog1.show();
            cancel.setOnClickListener(view1 -> {
                alertDialog1.dismiss();
            });

            select.setOnClickListener(view1 -> {
                selectedSubCatID = valuePickerView.getSelectedItem().getId();
                textView.setText(valuePickerView.getSelectedItem().getTitle());
                alertDialog1.dismiss();

            });
        } else {
            alertDialog1.show();
        }
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

    private void uploadImage(String title, String description, String budget, String selectedBudgetUnit, int selectedCatID, int selectedSubCatID, String date) {
        File file1 = new File(GetRealPathFromUri.getPathFromUri(PostJob.this, imageUri));

        MultipartBody.Part multiPartProfile = MultipartBody.Part.createFormData("image",
                file1.getName(),
                RequestBody.create(
                        file1,
                        MediaType.parse("*/*")
                )
        );

        showLoading();
        postJobViewModel.postImage(multiPartProfile);

        postJobViewModel._job_image.observe(this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getMessage() != null) {
                   // showSnackBarShort(response.getData().getMessage());
                    imagesPath = response.getData().getFilePATH();
                    postJob(title, description, budget, selectedBudgetUnit, selectedCatID, selectedSubCatID, imagesPath, date);
                }
            }
        });
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


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, 112);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 112:
                if (grantResults.length > 0) {

                    boolean storage = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (storage) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 13);
                    }else{
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
                                    showMessageOKCancel("You need to allow access to both the permissions",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                        requestPermissions(new String[]{READ_EXTERNAL_STORAGE},
                                                                112);
                                                    }
                                                }
                                            });
                                    return;
                                }
                            }

                        }
                    }


                    break;
                }
        }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(PostJob.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

        private void checkPermissions () {

            Dexter.withContext(PostJob.this)
                    .withPermissions(Manifest.permission.CAMERA, READ_EXTERNAL_STORAGE)
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
        public void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 13) {
                if (resultCode == Activity.RESULT_OK) {
                    imageUri = data.getData();
                    activityBinding.ivAddImage.setImageResource(0);
                    activityBinding.ivAddImage.setImageURI(imageUri);
                    activityBinding.ivAddImage.setBackgroundResource(R.drawable.rect_bg);
                    activityBinding.deleteImage.setVisibility(View.VISIBLE);
                }
            }
        }


        @Override
        public void onDateSet (DatePicker datePicker,int year, int month, int dayOfMonth){
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
            activityBinding.deadlineDate.setText(selectedDate);

        }

        @Override
        protected void onDestroy () {
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