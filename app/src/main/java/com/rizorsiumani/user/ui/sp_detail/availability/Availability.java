package com.rizorsiumani.user.ui.sp_detail.availability;

import android.os.Bundle;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import com.rizorsiumani.user.App;
import com.rizorsiumani.user.BaseFragment;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.AvailabilityDataItem;
import com.rizorsiumani.user.data.local.TinyDbManager;
import com.rizorsiumani.user.databinding.FragmentAvailabilityBinding;
import com.rizorsiumani.user.ui.sp_detail.ProviderDetailViewModel;
import com.rizorsiumani.user.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class Availability extends BaseFragment<FragmentAvailabilityBinding> {

    ProviderDetailViewModel viewModel;
    List<AvailabilityDataItem> lst;

    @Override
    protected FragmentAvailabilityBinding getFragmentBinding() {
        return FragmentAvailabilityBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideCartButton();

        String spID = TinyDbManager.getServiceProviderID();


        viewModel = new ViewModelProvider(this).get(ProviderDetailViewModel.class);
        if (viewModel._available.getValue() == null) {
            viewModel.getAvailability(Integer.parseInt(spID));
        }

        viewModel._available.observe(getViewLifecycleOwner(), response -> {
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
                    if (response.getData().getData().size() > 0) {
                        hideNoDataAnimation();
                        lst = new ArrayList<>();
                        lst.addAll(response.getData().getData());
                        buildRv(lst);
                    } else {
                        showNoDataAnimation(R.raw.no_job,"Not Added Yet");
                    }
                }
            }
        });
    }

    private void buildRv(List<AvailabilityDataItem> lst) {
        fragmentBinding.availableTimeList.setHasFixedSize(true);
        fragmentBinding.availableTimeList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(fragmentBinding.availableTimeList);
        AvailabilityAdapter adapter = new AvailabilityAdapter(lst, requireContext());
        fragmentBinding.availableTimeList.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        viewModel._available.removeObservers(this);
        viewModel = null;
    }
}