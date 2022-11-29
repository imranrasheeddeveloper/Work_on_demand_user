package com.rizorsiumani.workondemanduser.ui.sp_detail.gallery;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.data.businessModels.GalleryDataItem;
import com.rizorsiumani.workondemanduser.databinding.FragmentGalleryBinding;
import com.rizorsiumani.workondemanduser.ui.sp_detail.ProviderDetailViewModel;

import java.util.ArrayList;
import java.util.List;


public class Gallery extends BaseFragment<FragmentGalleryBinding> {

    private ProviderDetailViewModel viewModel;
    List<GalleryDataItem> galleryDataItems;

    @Override
    protected FragmentGalleryBinding getFragmentBinding() {
        return FragmentGalleryBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hideCartButton();

        String spID = getActivity().getIntent().getStringExtra("service_provider_id");


        viewModel = new ViewModelProvider(this).get(ProviderDetailViewModel.class);
        viewModel.getGallery(Integer.parseInt(spID));
        viewModel._gallery.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (response.isLoading()) {
                     showLoading();
                } else if (!response.getError().isEmpty()) {
                     hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                      hideLoading();
                    if (response.getData().getData().size() > 0){
                        hideNoDataAnimation();
                         galleryDataItems = new ArrayList<>();
                         galleryDataItems.addAll(response.getData().getData());
                         buildRv(galleryDataItems);
                    }else {
                        showNoDataAnimation();
                    }
                }
            }
        });


    }

    private void buildRv(List<GalleryDataItem> galleryDataItems) {


        GridLayoutManager glm = new GridLayoutManager(requireContext(), 2);
        fragmentBinding.galleryList.setLayoutManager(glm);
        GalleryAdapter adapter = new GalleryAdapter(requireContext(), galleryDataItems);
        fragmentBinding.galleryList.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        viewModel._gallery.removeObservers(this);
        viewModel = null;
    }
}