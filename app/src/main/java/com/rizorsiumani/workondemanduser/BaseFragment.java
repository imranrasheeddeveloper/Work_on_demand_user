package com.rizorsiumani.workondemanduser;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.rizorsiumani.workondemanduser.data.local.PreferenceRepository;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.FragmentHomeBinding;
import com.rizorsiumani.workondemanduser.ui.booking.MyCartItems;
import com.rizorsiumani.workondemanduser.ui.booking_detail.BookingDetail;
import com.wang.avi.AVLoadingIndicatorView;

public abstract class BaseFragment<binding extends ViewBinding> extends Fragment {

    protected binding fragmentBinding;
    private AVLoadingIndicatorView progressBar;

    protected abstract binding getFragmentBinding();

    ConstraintLayout cartView;
    protected PreferenceRepository prefRepository = null;
    private LottieAnimationView animationView;
    TextView cartItem;
    String cartTotal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentBinding = getFragmentBinding();

        prefRepository = new PreferenceRepository();
        progressBar = fragmentBinding.getRoot().findViewById(R.id.progressBar);
        cartView = fragmentBinding.getRoot().findViewById(R.id.cartButton);
        animationView = fragmentBinding.getRoot().findViewById(R.id.no_data_animation);
        cartItem = fragmentBinding.getRoot().findViewById(R.id.cartCount);

        animationView = fragmentBinding.getRoot().findViewById(R.id.no_data_animation);

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
            requireActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        return fragmentBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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
        Toast.makeText(fragmentBinding.getRoot().getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void showSnackBarShort(String msg) {
        Snackbar.make(fragmentBinding.getRoot(), msg, Snackbar.LENGTH_SHORT).show();
    }

    protected void showSnackBarShort(@StringRes int resID) {
        Snackbar.make(fragmentBinding.getRoot(), resID, Snackbar.LENGTH_SHORT).show();
    }
}
