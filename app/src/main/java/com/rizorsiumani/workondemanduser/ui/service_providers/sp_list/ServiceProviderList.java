package com.rizorsiumani.workondemanduser.ui.service_providers.sp_list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.DataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProvidersModel;
import com.rizorsiumani.workondemanduser.databinding.FragmentServiceProviderListBinding;
import com.rizorsiumani.workondemanduser.ui.service_providers.ServiceProviderAdapter;
import com.rizorsiumani.workondemanduser.ui.service_providers.ServiceProviderViewModel;
import com.rizorsiumani.workondemanduser.ui.sp_detail.SpProfile;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;
import com.rizorsiumani.workondemanduser.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class ServiceProviderList extends BaseFragment<FragmentServiceProviderListBinding> {

    ServiceProvidersModel serviceProvidersModel;
    private ServiceProviderViewModel viewModel;
    String subCatID;

    @Override
    protected FragmentServiceProviderListBinding getFragmentBinding() {
        return FragmentServiceProviderListBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            subCatID = getActivity().getIntent().getStringExtra("sub_cat_id");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        viewModel = new ViewModelProvider(this).get(ServiceProviderViewModel.class);
        JsonObject object = new JsonObject();
        object.addProperty("lat", String.valueOf(Constants.latitude));
        object.addProperty("long", String.valueOf(Constants.longitude));
        object.addProperty("sub_category_id", subCatID);

        viewModel.serviceProviders(1, Constants.ACCESS_TOKEN, object);
        viewModel._provider.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (response.isLoading()) {
                    // showLoading();
                } else if (!response.getError().isEmpty()) {
                    // hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().isSuccess()) {

                    if (response.getData().getData().size() > 0) {
                        serviceProvidersModel = response.getData();
                        if (serviceProvidersModel.getData().size() > 0) {
                            LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
                            fragmentBinding.serviceProvidersList.setLayoutManager(layoutManager);
                            ServiceProviderAdapter adapter = new ServiceProviderAdapter(serviceProvidersModel.getData(), requireContext());
                            fragmentBinding.serviceProvidersList.setAdapter(adapter);
                        } else {
                            showSnackBarShort("Data not Available");
                        }
                    }
                }
            }
        });
//
//
//            if (getArguments() != null) {
//                if (getArguments().getString("service_providers") != null) {
//                    String data = getArguments().getString("service_providers");
//                    Gson gson = new Gson();
//                    serviceProvidersModel = gson.fromJson(data,ServiceProvidersModel.class);
//                    if (serviceProvidersModel.getData().size() > 0){
//                        buildRv();
//                    }else {
//                        showSnackBarShort("Data not Available");
//                    }
//                }
//            }
    }



//    private void servicesProvidersRv(List<DataItem> data) {
//
//        fragmentBinding.serviceProvidersList.hasFixedSize();
//        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
//        fragmentBinding.serviceProvidersList.setLayoutManager(layoutManager);
//        ServiceProviderAdapter adapter = new ServiceProviderAdapter(data, requireContext());
//        fragmentBinding.serviceProvidersList.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//        fragmentBinding.serviceProvidersList.setVisibility(View.VISIBLE);
//
//        adapter.setOnProviderSelectListener(position -> {
//            ActivityUtil.gotoPage(requireContext(), SpProfile.class);
//            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//        });
//    }
}