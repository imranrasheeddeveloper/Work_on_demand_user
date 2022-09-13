package com.rizorsiumani.workondemanduser.ui.dashboard;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.ImageView;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityDashboardBinding;
import com.rizorsiumani.workondemanduser.utils.Constants;

public class Dashboard extends BaseActivity<ActivityDashboardBinding> {

    private NavController mNavController;
    boolean isHome = false;
    NavOptions options;

    @Override
    protected ActivityDashboardBinding getActivityBinding() {
        return ActivityDashboardBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            mNavController = navHostFragment.getNavController();
        }

        activityBinding.navigation.homeFragment.setImageTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));

        NavOptions.Builder builder = new NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right);

        options = builder.build();

        clickListeners(options);

    }

    private void clickListeners(NavOptions options) {
        activityBinding.navigation.bookingFragment.setOnClickListener(view -> {
            changeIconColor( activityBinding.navigation.bookingFragment,
                    activityBinding.navigation.walletFragment,
                    activityBinding.navigation.profileFragment,
                    activityBinding.navigation.homeFragment
            );
            activityBinding.navigation.bookingFragment.setImageTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));

            mNavController.navigate(R.id.bookingFragment,null, this.options);
        });

        activityBinding.navigation.walletFragment.setOnClickListener(view -> {
            changeIconColor( activityBinding.navigation.walletFragment,
                    activityBinding.navigation.bookingFragment,
                    activityBinding.navigation.profileFragment,
                    activityBinding.navigation.homeFragment
            );
            mNavController.navigate(R.id.walletFragment,null, this.options);

        });

        activityBinding.navigation.profileFragment.setOnClickListener(view -> {
            changeIconColor( activityBinding.navigation.profileFragment,
                    activityBinding.navigation.walletFragment,
                    activityBinding.navigation.bookingFragment,
                    activityBinding.navigation.homeFragment
            );
            mNavController.navigate(R.id.profileFragment,null, this.options);

        });

        activityBinding.navigation.homeFragment.setOnClickListener(view -> {
            changeIconColor( activityBinding.navigation.homeFragment,
                    activityBinding.navigation.walletFragment,
                    activityBinding.navigation.profileFragment,
                    activityBinding.navigation.bookingFragment
            );
            mNavController.navigate(R.id.homeFragment,null, this.options);

        });

        activityBinding.navigation.postJob.setOnClickListener(view -> {
            NavOptions.Builder builder = new NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setEnterAnim(R.anim.slide_up)
                    .setExitAnim(R.anim.stationary)
                    .setPopEnterAnim(R.anim.slide_down)
                    .setPopExitAnim(R.anim.stationary);

            NavOptions options1 = builder.build();
            mNavController.navigate(R.id.postJob,null, options1);

        });


    }

    private void changeIconColor(ImageView selectedTab, ImageView unselectedTab1, ImageView unselectedTab2, ImageView unselectedTab3) {
        selectedTab.setImageTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));
        unselectedTab1.setImageTintList(ColorStateList.valueOf(Color.parseColor("#8D8D8D")));
        unselectedTab2.setImageTintList(ColorStateList.valueOf(Color.parseColor("#8D8D8D")));
        unselectedTab3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#8D8D8D")));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (Constants.isHome){
            finishAffinity();
        }else {
            mNavController.navigate(R.id.homeFragment);
        }
    }
}