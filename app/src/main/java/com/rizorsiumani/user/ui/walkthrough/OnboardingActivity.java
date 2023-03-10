package com.rizorsiumani.user.ui.walkthrough;

import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.rizorsiumani.user.App;
import com.rizorsiumani.user.BaseActivity;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.OnBoardDataItem;
import com.rizorsiumani.user.data.local.TinyDbManager;
import com.rizorsiumani.user.databinding.ActivityOnboardingBinding;
import com.rizorsiumani.user.ui.welcome_user.WelcomeUser;
import com.rizorsiumani.user.utils.ActivityUtil;
import com.rizorsiumani.user.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends BaseActivity<ActivityOnboardingBinding> implements View.OnClickListener {


    SliderAdapter adapter;

    private int mCurrentPage;
    private TextView[] mDots;
    private OnBoardingViewModel viewModel;
    List<OnBoardDataItem> dataItems;


    @Override
    protected ActivityOnboardingBinding getActivityBinding() {
        return ActivityOnboardingBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void onStart() {
        super.onStart();

        TinyDbManager.saveVisit(true);

        viewModel = new ViewModelProvider(this).get(OnBoardingViewModel.class);

        if (viewModel._onBoard.getValue() == null){
            viewModel.getOnBoardData();
        }

        viewModel._onBoard.observe(this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (response.getError() != null) {
                    hideLoading();
                    if (response.getError() == null){
                        showSnackBarShort("Something went wrong!!");
                    }else {
                        Constants.constant.getApiError(App.applicationContext,response.getError());
                    }
                } else if (response.getData().getData() != null) {
                    hideLoading();

                    dataItems = new ArrayList<>();
                    dataItems.addAll(response.getData().getData());
                    buildWalkThrough(dataItems);

                }
            }
        });


//        SharedPreferences settings = getSharedPreferences("FirstTimePref", 0);
//        if (settings.getBoolean("my_first_time", true)) {
//            //the app is being launched for first time, do something
//            Log.d("Comments", "First time");
//            settings.edit().putBoolean("my_first_time", false).commit();
//        }else {
//            ActivityUtil.gotoPage(OnboardingActivity.this, SplashActivity.class);
//        }




    }

    private void buildWalkThrough(List<OnBoardDataItem> dataItems) {
        adapter = new SliderAdapter(OnboardingActivity.this,dataItems);

        activityBinding.slideViewpager.setAdapter(adapter);

        addDotsIndicator(0);

        activityBinding.slideViewpager.addOnPageChangeListener(viewListener);

        activityBinding.btnNext1.setOnClickListener(this);
        activityBinding.btnPrevious.setOnClickListener(this);

    }

    public void addDotsIndicator(int position) {

        mDots = new TextView[dataItems.size()];
        activityBinding.dotsLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.dark_gray));

            activityBinding.dotsLayout.addView(mDots[i]);
        }

        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.white)); //setting currently selected dot to white
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDotsIndicator(position);

            mCurrentPage = position;

            if (position == 0) {//we are on first page
                activityBinding.btnNext1.setEnabled(true);
                activityBinding.btnPrevious.setEnabled(true);

                activityBinding.btnNext1.setText("Next");
                activityBinding.btnPrevious.setText("Skip");

            } else if (position == mDots.length - 1) { //last page
                activityBinding.btnNext1.setEnabled(true);
                activityBinding.btnPrevious.setEnabled(true);

                activityBinding.btnNext1.setText("Get Started");
                activityBinding.btnPrevious.setText("Skip");
            } else { //neither on first nor on last page
                activityBinding.btnNext1.setEnabled(true);
                activityBinding.btnPrevious.setEnabled(true);

                activityBinding.btnNext1.setText("Next");
                activityBinding.btnPrevious.setText("Skip");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next1:
                ActivityUtil.gotoPage(OnboardingActivity.this, WelcomeUser.class);

//                if (activityBinding.btnNext.getText().toString().equalsIgnoreCase("next")) {
//                    activityBinding.slideViewpager.setCurrentItem(mCurrentPage + 1);
//                } else {
//                    ActivityUtil.gotoPage(OnboardingActivity.this, WelcomeUser.class);
//                }
                break;
            case R.id.btn_previous:
                ActivityUtil.gotoPage(OnboardingActivity.this, WelcomeUser.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        viewModel._onBoard.removeObservers(this);
        viewModel = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        finishAffinity();
    }
}