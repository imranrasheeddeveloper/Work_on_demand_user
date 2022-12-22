package com.rizorsiumani.workondemanduser;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.ui.booking.MyCartItems;
import com.rizorsiumani.workondemanduser.ui.booking_date.BookingDateTime;
import com.rizorsiumani.workondemanduser.ui.booking_detail.BookingDetail;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.utils.Constants;
import com.wang.avi.AVLoadingIndicatorView;

public abstract class BaseActivity<binding extends ViewBinding> extends AppCompatActivity {

    protected binding activityBinding;
    private AVLoadingIndicatorView progressBar;

    protected abstract binding getActivityBinding();

    ConstraintLayout cartView;
    protected PreferenceRepository prefRepository = null;
    private LottieAnimationView animationView;
    TextView cartItem;
    String cartTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = getActivityBinding();
        setContentView(activityBinding.getRoot());
        progressBar = findViewById(R.id.progressBar);
        cartView = findViewById(R.id.cartButton);
        animationView = findViewById(R.id.no_data_animation);
        cartItem = findViewById(R.id.cartCount);
        prefRepository = new PreferenceRepository();

        setupUI(activityBinding.getRoot());


        if (TinyDbManager.getCartData().size() > 0) {
            cartView.setVisibility(View.VISIBLE);

            int total = 0;
            for (int i = 0; i < TinyDbManager.getCartData().size(); i++) {
                MyCartItems cartItems = TinyDbManager.getCartData().get(i);
                int price = Integer.parseInt(cartItems.getData().getPrice());
                total = total + price;
            }
            cartTotal = String.valueOf(total);
            cartItem.setText(String.valueOf(TinyDbManager.getCartData().size()));
        }else {
            cartView.setVisibility(View.GONE);
        }


        cartView.setOnClickListener(view -> {
            Intent intent = new Intent(BaseActivity.this, BookingDetail.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });


    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    Constants.constant.hideSoftKeyboard(BaseActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
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



    protected String getCartTotal() {
        return cartTotal;
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
