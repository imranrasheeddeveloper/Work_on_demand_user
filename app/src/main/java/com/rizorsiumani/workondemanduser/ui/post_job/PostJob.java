package com.rizorsiumani.workondemanduser.ui.post_job;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
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
import com.rizorsiumani.workondemanduser.data.businessModels.SerCategoryModel;
import com.rizorsiumani.workondemanduser.databinding.ActivityPostJobBinding;
import com.rizorsiumani.workondemanduser.utils.Constants;
import com.skydoves.elasticviews.ElasticImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PostJob extends BaseActivity<ActivityPostJobBinding> {

    List<String> imagesPath;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    @Override
    protected ActivityPostJobBinding getActivityBinding() {
        return ActivityPostJobBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();


        imagesPath = new ArrayList<>();

        clickListeners();
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
            showCategoriesDialogue(activityBinding.selectedCategory);
        });

        activityBinding.tvSubcategory.setOnClickListener(view -> {
            showCategoriesDialogue(activityBinding.selectedSubcategory);
        });


    }

    private void showCategoriesDialogue(TextView textView) {
        dialogBuilder = new AlertDialog.Builder(PostJob.this);
        dialogBuilder.setCancelable(false);
        View layoutView = getLayoutInflater().inflate(R.layout.pick_category_dialogue, null);
        TextView cancel = (TextView) layoutView.findViewById(R.id.tv_cancel);
        TextView select = (TextView) layoutView.findViewById(R.id.tv_done);
        ValuePickerView valuePickerView = (ValuePickerView) layoutView.findViewById(R.id.teamPicker);
        valuePickerView.setOnItemSelectedListener((item) -> {
            textView.setTag(item.getTitle());
        });

        final List<Item> pickerItems = getPickerItems();

        valuePickerView.setItems(getPickerItems());
        valuePickerView.setSelectedItem(pickerItems.get(2));

        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimations;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        cancel.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });

        select.setOnClickListener(view1 -> {
            textView.setText(valuePickerView.getSelectedItem().getTitle());
            alertDialog.dismiss();

        });
    }

    private List<Item> getPickerItems() {
        final List<Item> pickerItems = new ArrayList<>();

        List<String> categories = new ArrayList<>();
        categories.add("Cleaning");
        categories.add("Appliances");
        categories.add("Electronic");
        categories.add("Washing");
        categories.add("Painting");
        categories.add("Wood Working");
        categories.add("Shifting");
        
        for(int i = 0; i <= categories.size()-1; i++) {
            pickerItems.add(
                    new PickerItem(
                            i,
                            categories.get(i)
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
                            final BottomSheetDialog bt = new BottomSheetDialog(PostJob.this, R.style.BottomSheetDialogTheme);
                            View items = LayoutInflater.from(PostJob.this).inflate(R.layout.layout_item_chooser, null, false);
                            ElasticImageView camera = items.findViewById(R.id.cameraIcon);
                            ElasticImageView gallery = items.findViewById(R.id.galleryIcon);
                            camera.setOnClickListener(view1 -> {
                                Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (pictureIntent.resolveActivity(getPackageManager()) != null) {
//
                                    File file = new File(getExternalCacheDir(),
                                            String.valueOf(System.currentTimeMillis()) + ".jpg");

                                    Uri fileProvider = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                                            "com.rizorsiumani.workondemanduser.provider", file);
                                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
                                    startActivityForResult(pictureIntent, 11);
                                }
                                bt.cancel();
                            });
                            gallery.setOnClickListener(view -> {
                                Intent galleryIntent = new Intent(
                                        Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(galleryIntent, 13);
//                                Intent intent = new Intent();
//                                intent.setType("image/*");
//                                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                                intent.setAction(Intent.ACTION_GET_CONTENT);
//                                startActivityForResult(Intent.createChooser(intent,"Select images"), 13);

                                bt.cancel();
                            });
                            bt.setContentView(items);
                            bt.show();
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
        if (requestCode == 11) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null && data.getExtras() != null) {

                    try {

                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, filePathColumn, null, null, null);
                        if (cursor == null)
                            return;
                        // find the file in the media area
                        cursor.moveToLast();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String filePath = "file:///" + cursor.getString(columnIndex);
                        cursor.close();
                        imagesPath.add(filePath);


                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
            }
        } else if (requestCode == 13) {

//            int count = data.getClipData().getItemCount();
//
//            for(int i = 0; i < count; i++){
//                Uri imageUri = data.getClipData().getItemAt(i).getUri();
//            }
            Uri imageUri = data.getData();
            String path = Constants.constant.getRealPathFromURI(imageUri, PostJob.this);

            imagesPath.add(path);
            buildPhotosList(imagesPath);

        }
    }

    private void buildPhotosList(List<String> imagesPath) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(PostJob.this, RecyclerView.HORIZONTAL, false);
        activityBinding.attachImagesList.setLayoutManager(layoutManager);
        PostJobImagesAdapter adapter = new PostJobImagesAdapter(imagesPath, PostJob.this);
        activityBinding.attachImagesList.setAdapter(adapter);
        activityBinding.attachImagesList.setVisibility(View.VISIBLE);
    }

}