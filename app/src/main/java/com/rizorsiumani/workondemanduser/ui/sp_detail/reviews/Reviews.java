package com.rizorsiumani.workondemanduser.ui.sp_detail.reviews;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.GalleryDataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.RatingDataItem;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.FragmentReviewsBinding;
import com.rizorsiumani.workondemanduser.ui.sp_detail.ProviderDetailViewModel;

import java.util.ArrayList;
import java.util.List;


public class Reviews extends BaseFragment<FragmentReviewsBinding> {

    private ProviderDetailViewModel viewModel;
    List<RatingDataItem> ratingDataItems;

    @Override
    protected FragmentReviewsBinding getFragmentBinding() {
        return FragmentReviewsBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideCartButton();

        String spID = TinyDbManager.getServiceProviderID();


        viewModel = new ViewModelProvider(this).get(ProviderDetailViewModel.class);
        viewModel.getRating(Integer.parseInt(spID));
        viewModel._rating.observe(getViewLifecycleOwner(), response -> {
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
                        ratingDataItems = new ArrayList<>();
                        ratingDataItems.addAll(response.getData().getData());
                        buildRv(ratingDataItems);
                    }else {
                        showNoDataAnimation(R.raw.no_data,"Not Rated Yet.");
                    }
                }
            }
        });
    }

    private void buildRv(List<RatingDataItem> ratingDataItems) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        fragmentBinding.reviewsList.setLayoutManager(layoutManager);
        ReviewsAdapter adapter = new ReviewsAdapter(ratingDataItems, requireContext());
        fragmentBinding.reviewsList.setAdapter(adapter);
    }



}