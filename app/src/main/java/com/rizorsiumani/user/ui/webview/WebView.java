package com.rizorsiumani.user.ui.webview;

import android.view.View;

import com.rizorsiumani.user.BaseActivity;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.databinding.ActivityWebViewBinding;

public class WebView extends BaseActivity<ActivityWebViewBinding> {

    String title, url;

    @Override
    protected ActivityWebViewBinding getActivityBinding() {
        return ActivityWebViewBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        hideCartButton();
        showLoading();

        try {
            title = getIntent().getStringExtra("web_title");
            url = getIntent().getStringExtra("web_url");
            activityBinding.webToolbar.title.setText(title);

        }catch (NullPointerException e){
            e.printStackTrace();
        }
        clickEvents();

        activityBinding.webview.getSettings().setLoadsImagesAutomatically(true);
        activityBinding.webview.getSettings().setJavaScriptEnabled(true);
        activityBinding.webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        activityBinding.webview.loadUrl(url);


        hideLoading();

    }


    private void clickEvents() {
        activityBinding.webToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });


    }
}