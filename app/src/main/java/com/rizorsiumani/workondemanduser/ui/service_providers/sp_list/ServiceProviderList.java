package com.rizorsiumani.workondemanduser.ui.service_providers.sp_list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.google.gson.Gson;
import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.DataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProvidersModel;
import com.rizorsiumani.workondemanduser.databinding.FragmentServiceProviderListBinding;
import com.rizorsiumani.workondemanduser.ui.service_providers.ServiceProviderAdapter;
import com.rizorsiumani.workondemanduser.ui.sp_detail.SpProfile;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;


public class ServiceProviderList extends BaseFragment<FragmentServiceProviderListBinding> {

    ServiceProvidersModel serviceProvidersModel;

    @Override
    protected FragmentServiceProviderListBinding getFragmentBinding() {
        return FragmentServiceProviderListBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getData();

    }

    private void getData() {
        try {

            if (getArguments() != null) {
                if (getArguments().getString("service_providers") != null) {
                    String data = getArguments().getString("service_providers");
                    Gson gson = new Gson();
                    serviceProvidersModel = gson.fromJson(data, ServiceProvidersModel.class);
                    if (serviceProvidersModel.getData().size() > 0){
                        servicesProvidersRv(serviceProvidersModel.getData());
                    }
                }
            }

        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }


    private void servicesProvidersRv(List<DataItem> data) {



        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        fragmentBinding.serviceProvidersList.setLayoutManager(layoutManager);
        ServiceProviderAdapter adapter = new ServiceProviderAdapter(data, requireContext());
        fragmentBinding.serviceProvidersList.setAdapter(adapter);

        adapter.setOnProviderSelectListener(position -> {
            ActivityUtil.gotoPage(requireContext(), SpProfile.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
}