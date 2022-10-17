package com.rizorsiumani.workondemanduser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.rizorsiumani.workondemanduser.data.local.PreferenceRepository;
import com.rizorsiumani.workondemanduser.databinding.FragmentHomeBinding;

public abstract class BaseFragment<binding extends ViewBinding> extends Fragment {

    protected binding fragmentBinding;
    private ProgressBar progressBar;

    protected abstract binding getFragmentBinding();

    protected PreferenceRepository prefRepository = null;
    LottieAnimationView animationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentBinding = getFragmentBinding();

        prefRepository = new PreferenceRepository();
        progressBar = getView().findViewById(R.id.progress);

        animationView = getView().findViewById(R.id.no_data_animation);

        return fragmentBinding.getRoot();

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

    public PreferenceRepository getPrefRepository() {
        return prefRepository;
    }

    protected void showSnackBarShort(String msg) {
        Snackbar.make(fragmentBinding.getRoot(), msg, Snackbar.LENGTH_SHORT).show();
    }

    protected void showSnackBarShort(@StringRes int resID) {
        Snackbar.make(fragmentBinding.getRoot(), resID, Snackbar.LENGTH_SHORT).show();
    }
}
