package com.rizorsiumani.workondemanduser;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewbinding.ViewBinding;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.rizorsiumani.workondemanduser.data.local.PreferenceRepository;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.FragmentHomeBinding;
import com.rizorsiumani.workondemanduser.ui.booking.MyCartItems;
import com.rizorsiumani.workondemanduser.ui.booking_detail.BookingDetail;
import com.rizorsiumani.workondemanduser.ui.fragment.booking.BookingsViewModel;
import com.rizorsiumani.workondemanduser.utils.Constants;
import com.wang.avi.AVLoadingIndicatorView;

public abstract class BaseFragment<binding extends ViewBinding> extends Fragment {

    protected binding fragmentBinding;
    private AVLoadingIndicatorView progressBar;

    protected abstract binding getFragmentBinding();

    ConstraintLayout cartView;
    protected PreferenceRepository prefRepository = null;
    private LottieAnimationView animationView;
    TextView cartItem;
    TextView noDataMessage;
    String cartTotal;
    BookingsViewModel viewModel;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentBinding = getFragmentBinding();
        viewModel = new ViewModelProvider(this).get(BookingsViewModel.class);

        prefRepository = new PreferenceRepository();
        progressBar = fragmentBinding.getRoot().findViewById(R.id.progressBar);
        cartView = fragmentBinding.getRoot().findViewById(R.id.cartButton);
        animationView = fragmentBinding.getRoot().findViewById(R.id.no_data_animation);
        noDataMessage = fragmentBinding.getRoot().findViewById(R.id.tv_no_data);
        cartItem = fragmentBinding.getRoot().findViewById(R.id.cartCount);


        setupUI(fragmentBinding.getRoot());


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


        cartView.setOnClickListener(view1 -> {
            Intent intent = new Intent(requireContext(), BookingDetail.class);
            startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        });

        return fragmentBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    Constants.constant.hideSoftKeyboard(requireActivity());
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

    protected void showNoDataAnimation(int rawFile,String message) {
        animationView.setAnimation(rawFile);
        animationView.setVisibility(View.VISIBLE);
        animationView.playAnimation();
        noDataMessage.setText(message);
        noDataMessage.setVisibility(View.VISIBLE);
    }

    protected void hideNoDataAnimation() {
        animationView.setVisibility(View.GONE);
        noDataMessage.setVisibility(View.GONE);
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
        Toast.makeText(fragmentBinding.getRoot().getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void showSnackBarShort(String msg) {
        Snackbar.make(fragmentBinding.getRoot(), msg, Snackbar.LENGTH_SHORT).show();
    }

    protected void showSnackBarShort(@StringRes int resID) {
        Snackbar.make(fragmentBinding.getRoot(), resID, Snackbar.LENGTH_SHORT).show();
    }

    protected void confirmationDialogue(String title , String description,String token, int bookingID,String status) {
        dialogBuilder = new AlertDialog.Builder(requireContext());
        View layoutView = getLayoutInflater().inflate(R.layout.cancel_booking_dialogue, null);
        TextView cancel = (TextView) layoutView.findViewById(R.id.cancel_dialogue);
        TextView confirm = (TextView) layoutView.findViewById(R.id.confirm);
        TextView title_D = (TextView) layoutView.findViewById(R.id.tv_cancel);
        TextView des_D = (TextView) layoutView.findViewById(R.id.tv_msg);

        title_D.setText(title);
        des_D.setText(description);

        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimations;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        cancel.setOnClickListener(view -> alertDialog.dismiss());
        confirm.setOnClickListener(view -> {
            viewModel.updateStatus(token,bookingID,status);
            viewModel._booking_status.observe(getViewLifecycleOwner(), response -> {
                if (response != null){
                    if (response.isLoading()) {
                        showLoading();
                    } else if (response.getError() != null) {
                        hideLoading();
                        alertDialog.dismiss();
                        if (response.getError() == null){
                            showSnackBarShort("Something went wrong!!");
                        }else {
                            Constants.constant.getApiError(App.applicationContext,response.getError());
                        }
                    } else if (response.getData().isSuccess()) {
                        hideLoading();
                        alertDialog.dismiss();
                        Navigation.findNavController(getView()).navigate(R.id.bookingFragment);

                    }
                }
            });

        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel._booking_status.removeObservers(this);
        viewModel = null;
    }
}
