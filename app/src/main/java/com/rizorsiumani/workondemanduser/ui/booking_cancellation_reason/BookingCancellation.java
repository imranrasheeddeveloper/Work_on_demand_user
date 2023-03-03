package com.rizorsiumani.workondemanduser.ui.booking_cancellation_reason;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityBookingCancellationBinding;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.ui.sp_detail.service.SpServicesAdapter;

import java.util.ArrayList;

public class BookingCancellation extends BaseActivity<ActivityBookingCancellationBinding> {

    int bookingID;

    @Override
    protected ActivityBookingCancellationBinding getActivityBinding() {
        return ActivityBookingCancellationBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        activityBinding.cancellationToolbar.title.setText("Cancel Booking");
        bookingID = getIntent().getIntExtra("bookingID",0);
        clickListeners();

    }

    private void clickListeners() {

        activityBinding.cancellationToolbar.back.setOnClickListener(v -> {
            Dashboard.goToBooking();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        activityBinding.addImages.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 13);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 13) {
            if (resultCode == RESULT_OK && data != null) {
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        mArrayUri.add(uri);
                    }
                    Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    if (mArrayUri.size() > 0){
                        buildRv(mArrayUri);
                    }
                }
            }
        }
    }

    private void buildRv(ArrayList<Uri> mArrayUri) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.HORIZONTAL, false);
        activityBinding.reasonImages.setLayoutManager(layoutManager);
        CancellationImagesAdapter adapter = new CancellationImagesAdapter(mArrayUri, BookingCancellation.this);
        activityBinding.reasonImages.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Dashboard.goToBooking();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}