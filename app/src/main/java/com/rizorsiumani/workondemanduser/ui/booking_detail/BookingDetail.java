package com.rizorsiumani.workondemanduser.ui.booking_detail;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityBookingDetailBinding;
import com.rizorsiumani.workondemanduser.ui.booking_date.BookingDateTime;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.ui.promo_code.PromoCode;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class BookingDetail extends BaseActivity<ActivityBookingDetailBinding> {

    CartServicesAdapter adapter;
    List<String> name;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    @Override
    protected ActivityBookingDetailBinding getActivityBinding() {
        return ActivityBookingDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

//        String request_status = prefRepository.getString("Complete");
//        if (request_status.equalsIgnoreCase("true")){
//            showRequestedDialogue();
//        }




        activityBinding.bookingDetailToolbar.title.setText("Booking Details");
        clickListeners();
        getCartServices();
    }

    private void showRequestedDialogue() {
        dialogBuilder = new AlertDialog.Builder(BookingDetail.this);
        View layoutView = getLayoutInflater().inflate(R.layout.booking_request_complete_dialogue, null);
        TextView cancel = (TextView) layoutView.findViewById(R.id.cancel_dialogue);
        Button booking_ = (Button) layoutView.findViewById(R.id.view_booking);

        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimations;
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        cancel.setOnClickListener(view -> alertDialog.dismiss());
        booking_.setOnClickListener(view -> {
            Intent intent = new Intent(BookingDetail.this,Dashboard.class);
            intent.putExtra("Navigation","Booking");
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        });

        cancel.setOnClickListener(view -> {
            ActivityUtil.gotoPage(BookingDetail.this,Dashboard.class);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
    }


    private void getCartServices() {
        name = new ArrayList<>();
        name.add("1 hour Pet Sitting");
        name.add("2 Hour Cleaning service");


        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        activityBinding.cartServicesList.setLayoutManager(layoutManager);
        adapter = new CartServicesAdapter(name, App.applicationContext);
        activityBinding.cartServicesList.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(activityBinding.cartServicesList);
    }

    private void clickListeners() {

        activityBinding.bookingDetailToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        activityBinding.promoCard.setOnClickListener(view -> {
            ActivityUtil.gotoPage(BookingDetail.this, PromoCode.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        activityBinding.btnPayNow.setOnClickListener(view -> {
            ActivityUtil.gotoPage(BookingDetail.this, BookingDateTime.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();

            switch (direction) {
                case ItemTouchHelper.LEFT:

                    name.remove(position);
                    adapter.notifyDataSetChanged();

                    break;
            }

        }


        @Override
        public void onChildDraw(Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            String toShow = "DELETE";
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(BookingDetail.this, R.color.primary))
                    .addSwipeLeftActionIcon(R.drawable._cross)
                    .create()
                    .decorate();


            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }


    };


}