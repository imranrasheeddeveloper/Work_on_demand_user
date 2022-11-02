package com.rizorsiumani.workondemanduser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.rizorsiumani.workondemanduser.data.local.PreferenceRepository;
import com.rizorsiumani.workondemanduser.ui.booking_date.BookingDateTime;
import com.rizorsiumani.workondemanduser.ui.booking_detail.BookingDetail;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.wang.avi.AVLoadingIndicatorView;

public abstract class BaseActivity<binding extends ViewBinding> extends AppCompatActivity {

    protected binding activityBinding;
    private AVLoadingIndicatorView progressBar;

    protected abstract binding getActivityBinding();

    private ConstraintLayout cartView;
    protected PreferenceRepository prefRepository = null;
    private LottieAnimationView animationView;
    TextView cartTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = getActivityBinding();
        setContentView(activityBinding.getRoot());
        progressBar = findViewById(R.id.progress);
        cartView = findViewById(R.id.cartButton);
        animationView = findViewById(R.id.no_data_animation);
        cartTotal = findViewById(R.id.cartCount);
        prefRepository = new PreferenceRepository();

        cartView.setOnClickListener(view -> {
            Intent intent = new Intent(BaseActivity.this, BookingDetail.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        });

    }

    protected void showNoDataAnimation() {
        animationView.setVisibility(View.VISIBLE);
    }

    protected void hideNoDataAnimation() {
        animationView.setVisibility(View.GONE);
    }
    protected void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    protected void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    protected void showCartButton() {
        cartView.setVisibility(View.VISIBLE);
    }

    protected void setCartTotal(String value) {
        cartTotal.setText(value);
    }

    protected String getCartTotal() {
        return cartTotal.getText().toString();
    }
    protected void hideCartButton() {
        cartView.setVisibility(View.GONE);
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showSnackBarShort(String msg) {
        Snackbar.make(activityBinding.getRoot(), msg, Snackbar.LENGTH_SHORT).show();
    }

    protected void showSnackBarShort(@StringRes int resID) {
        Snackbar.make(activityBinding.getRoot(), resID, Snackbar.LENGTH_SHORT).show();
    }
}
