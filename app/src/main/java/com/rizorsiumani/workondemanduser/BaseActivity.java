package com.rizorsiumani.workondemanduser;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.rizorsiumani.workondemanduser.data.local.PreferenceRepository;
import com.wang.avi.AVLoadingIndicatorView;

public abstract class BaseActivity<binding extends ViewBinding> extends AppCompatActivity {

    protected binding activityBinding;
    private AVLoadingIndicatorView progressBar;

    protected abstract binding getActivityBinding();

    protected PreferenceRepository prefRepository = null;
    private LottieAnimationView animationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = getActivityBinding();
        setContentView(activityBinding.getRoot());
        progressBar = findViewById(R.id.progress);
        animationView = findViewById(R.id.no_data_animation);
        prefRepository = new PreferenceRepository();
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
