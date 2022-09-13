package com.rizorsiumani.workondemanduser.ui.walkthrough;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityOnboardingBinding;
import com.rizorsiumani.workondemanduser.ui.splash.SplashActivity;
import com.rizorsiumani.workondemanduser.ui.welcome_user.WelcomeUser;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

public class OnboardingActivity extends BaseActivity<ActivityOnboardingBinding> implements View.OnClickListener {


    SliderAdapter adapter;

    private int mCurrentPage;
    private TextView[] mDots;


    @Override
    protected ActivityOnboardingBinding getActivityBinding() {
        return ActivityOnboardingBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void onStart() {
        super.onStart();

//        SharedPreferences settings = getSharedPreferences("FirstTimePref", 0);
//        if (settings.getBoolean("my_first_time", true)) {
//            //the app is being launched for first time, do something
//            Log.d("Comments", "First time");
//            settings.edit().putBoolean("my_first_time", false).commit();
//        }else {
//            ActivityUtil.gotoPage(OnboardingActivity.this, SplashActivity.class);
//        }


        adapter = new SliderAdapter(this);

        activityBinding.slideViewpager.setAdapter(adapter);

        addDotsIndicator(0);

        activityBinding.slideViewpager.addOnPageChangeListener(viewListener);

        activityBinding.btnNext.setOnClickListener(this);
        activityBinding.btnPrevious.setOnClickListener(this);


    }
    public void addDotsIndicator(int position) {

        mDots = new TextView[3];
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
                activityBinding.btnNext.setEnabled(true);
                activityBinding.btnPrevious.setEnabled(true);

                activityBinding.btnNext.setText("Next");
                activityBinding.btnPrevious.setText("Skip");

            } else if (position == mDots.length - 1) { //last page
                activityBinding.btnNext.setEnabled(true);
                activityBinding.btnPrevious.setEnabled(true);

                activityBinding.btnNext.setText("Get Started");
                activityBinding.btnPrevious.setText("Skip");
            } else { //neither on first nor on last page
                activityBinding.btnNext.setEnabled(true);
                activityBinding.btnPrevious.setEnabled(true);

                activityBinding.btnNext.setText("Next");
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
            case R.id.btn_next:
                if (activityBinding.btnNext.getText().toString().equalsIgnoreCase("next")) {
                    activityBinding.slideViewpager.setCurrentItem(mCurrentPage + 1);
                } else {
                    ActivityUtil.gotoPage(OnboardingActivity.this, WelcomeUser.class);
                }
                break;
            case R.id.btn_previous:
                ActivityUtil.gotoPage(OnboardingActivity.this, WelcomeUser.class);
                break;
            default:
                break;
        }
    }

}