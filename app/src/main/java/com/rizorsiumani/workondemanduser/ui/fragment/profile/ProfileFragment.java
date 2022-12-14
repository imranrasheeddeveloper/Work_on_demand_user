package com.rizorsiumani.workondemanduser.ui.fragment.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.UserData;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.FragmentProfileBinding;
import com.rizorsiumani.workondemanduser.ui.all_posted_jobs.AllPostedJobs;
import com.rizorsiumani.workondemanduser.ui.booking_detail.BookingDetail;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.ui.edit_profile.EditProfile;
import com.rizorsiumani.workondemanduser.ui.notification.Notification;
import com.rizorsiumani.workondemanduser.ui.support_chat.SupportChat;
import com.rizorsiumani.workondemanduser.ui.webview.WebView;
import com.rizorsiumani.workondemanduser.ui.welcome_user.WelcomeUser;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;
import com.rizorsiumani.workondemanduser.utils.AppBarStateChangeListener;
import com.rizorsiumani.workondemanduser.utils.Constants;


public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    @Override
    protected FragmentProfileBinding getFragmentBinding() {
        return FragmentProfileBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Dashboard.hideTabs(false);

        hideCartButton();
        if (TinyDbManager.getCartData().size() > 0) {
            fragmentBinding.tvCart.setVisibility(View.VISIBLE);
        } else {
            fragmentBinding.tvCart.setVisibility(View.GONE);
        }

        setProfileInfo();
        clickListeners();

        fragmentBinding.appBar1.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state.equals(State.COLLAPSED)) {

                    fragmentBinding.toolbarData.setVisibility(View.VISIBLE);
                } else if (state.equals(State.EXPANDED)) {
                    fragmentBinding.toolbarData.setVisibility(View.GONE);
                } else if ((state.equals(State.IDLE))) {

                }
            }
        });
    }

    private void setProfileInfo() {
        try {

            if (TinyDbManager.getUserInformation() != null) {
                UserData userData = TinyDbManager.getUserInformation();
                fragmentBinding.username1.setText(userData.getFirstName() + " " + userData.getLastName());
                fragmentBinding.username.setText(userData.getFirstName() + " " + userData.getLastName());
                fragmentBinding.userNumber.setText(userData.getPhoneNumber());
                fragmentBinding.userNumber1.setText(userData.getPhoneNumber());
                Glide.with(requireContext())
                        .load(userData.getImage())
                        .placeholder(R.color.teal_700)
                        .into(fragmentBinding.userImage);
                Glide.with(requireContext())
                        .load(userData.getImage())
                        .placeholder(R.color.teal_700)
                        .into(fragmentBinding.userImage1);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void clickListeners() {


        fragmentBinding.tvNotifications.setOnClickListener(view -> {
            ActivityUtil.gotoPage(requireContext(), Notification.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        fragmentBinding.tvCart.setOnClickListener(view -> {
            ActivityUtil.gotoPage(requireContext(), BookingDetail.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        fragmentBinding.tvInvite.setOnClickListener(view -> {
            Constants.constant.shareApp(requireContext());
        });

        fragmentBinding.tvPersonalDetails.setOnClickListener(view -> {
            ActivityUtil.gotoPage(requireContext(), EditProfile.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        fragmentBinding.tvBookings.setOnClickListener(view -> {
            Dashboard.goToBooking();
        });

        fragmentBinding.tvJobs.setOnClickListener(view -> {
            ActivityUtil.gotoPage(requireContext(), AllPostedJobs.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        fragmentBinding.tvSupportChat.setOnClickListener(view -> {
            ActivityUtil.gotoPage(requireContext(), SupportChat.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        fragmentBinding.booking.setOnClickListener(view -> {
            Dashboard.goToBooking();
        });

        fragmentBinding.wallet.setOnClickListener(view -> {
            Dashboard.goToWallet();
        });

        fragmentBinding.tvLogout.setOnClickListener(view -> {
            showLogoutDialogue();
        });

        fragmentBinding.tvPrivacyPolicy.setOnClickListener(view -> {
            moveToWebView("Privacy Policy", "http://www.rizorsiumani.com.mt/privacy.html");
        });

        fragmentBinding.tvTerms.setOnClickListener(view -> {
            moveToWebView("Terms & Conditions", "http://www.rizorsiumani.com.mt/terms.html");
        });

        fragmentBinding.tvReachUs.setOnClickListener(view -> {
            moveToWebView("Reach Us", "http://www.rizorsiumani.com.mt/contact.html");
        });

        fragmentBinding.tvAboutUs.setOnClickListener(view -> {
            moveToWebView("Work On Demand", "http://www.rizorsiumani.com.mt/index.html");
        });
    }

    private void moveToWebView(String title, String url) {
        Intent intent = new Intent(requireContext(), WebView.class);
        intent.putExtra("web_title", title);
        intent.putExtra("web_url", url);
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    private void showLogoutDialogue() {
        dialogBuilder = new AlertDialog.Builder(requireContext());
        View layoutView = getLayoutInflater().inflate(R.layout.logout_confirmation_dialogue, null);
        TextView cancel = (TextView) layoutView.findViewById(R.id.cancel_logout);
        TextView logout = (TextView) layoutView.findViewById(R.id.confirm_logout);

        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimations;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        cancel.setOnClickListener(view -> alertDialog.dismiss());
        logout.setOnClickListener(view -> {
            prefRepository.setString("token", "nil");
            ActivityUtil.gotoPage(requireContext(), WelcomeUser.class);
            alertDialog.dismiss();
            requireActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

    }
}