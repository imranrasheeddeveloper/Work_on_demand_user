package com.rizorsiumani.user.ui.fragment.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.JsonObject;
import com.rizorsiumani.user.App;
import com.rizorsiumani.user.BaseFragment;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.UserData;
import com.rizorsiumani.user.data.local.TinyDbManager;
import com.rizorsiumani.user.databinding.FragmentProfileBinding;
import com.rizorsiumani.user.ui.all_posted_jobs.AllPostedJobs;
import com.rizorsiumani.user.ui.booking_detail.BookingDetail;
import com.rizorsiumani.user.ui.cards.GetAllCards;
import com.rizorsiumani.user.ui.dashboard.Dashboard;
import com.rizorsiumani.user.ui.edit_profile.EditProfile;
import com.rizorsiumani.user.ui.notification.Notification;
import com.rizorsiumani.user.ui.support_chat.SupportChat;
import com.rizorsiumani.user.ui.support_chat.SupportChatViewModel;
import com.rizorsiumani.user.ui.webview.WebView;
import com.rizorsiumani.user.ui.welcome_user.WelcomeUser;
import com.rizorsiumani.user.utils.ActivityUtil;
import com.rizorsiumani.user.utils.AppBarStateChangeListener;
import com.rizorsiumani.user.utils.Constants;


public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    SupportChatViewModel chatViewModel;

    @Override
    protected FragmentProfileBinding getFragmentBinding() {
        return FragmentProfileBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chatViewModel = new ViewModelProvider(this).get(SupportChatViewModel.class);

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

        getConversationId();
    }

    private void getConversationId() {
        try {
            if (prefRepository.getInt("CONVERSATION_ID") == 0 || prefRepository.getInt("CONVERSATION_ID") == -1){
                if (!Constants.CHATWOOT_API_KEY.isEmpty()){
                    JsonObject object = new JsonObject();
                    object.addProperty("source_id",prefRepository.getString("SourceID"));
                    object.addProperty("inbox_id", Constants.INBOX_ID);
                    object.addProperty("contact_id", prefRepository.getString("ContactID"));
                    chatViewModel.createConversation(Constants.CHATWOOT_API_KEY, Integer.parseInt(Constants.ACCOUNT_ID),object);
                    chatViewModel._conversation.observe(getViewLifecycleOwner(), response -> {
                        if (response != null){
                            if (response.isLoading()){
                                showLoading();
                            } else if (response.getError() != null) {
                                hideLoading();
                                if (response.getError() == null){
                                    showSnackBarShort("Something went wrong!!");
                                }else {
                                    Constants.constant.getApiError(App.applicationContext,response.getError());
                                }
                            } else if (response.getData() != null) {
                                prefRepository.setInt("CONVERSATION_ID",response.getData().getId());
                            }
                        }
                    });

                }
            }

        }catch (NullPointerException | IllegalStateException e){
            e.printStackTrace();
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
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
                        .load(Constants.IMG_PATH + userData.getImage())
                        .placeholder(R.color.teal_700)
                        .into(fragmentBinding.userImage);
                Glide.with(requireContext())
                        .load(Constants.IMG_PATH + userData.getImage())
                        .placeholder(R.color.teal_700)
                        .into(fragmentBinding.userImage1);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void clickListeners() {


        fragmentBinding.tvPayment.setOnClickListener(view -> {
            ActivityUtil.gotoPage(requireContext(), GetAllCards.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
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
            if (prefRepository.getInt("CONVERSATION_ID") != 0) {
                ActivityUtil.gotoPage(requireContext(), SupportChat.class);
                requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
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