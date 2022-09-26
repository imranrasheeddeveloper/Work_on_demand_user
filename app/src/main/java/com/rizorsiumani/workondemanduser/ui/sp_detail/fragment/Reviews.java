package com.rizorsiumani.workondemanduser.ui.sp_detail.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.FragmentReviewsBinding;
import com.rizorsiumani.workondemanduser.ui.sp_detail.ReviewsAdapter;

import java.util.ArrayList;
import java.util.List;


public class Reviews extends BaseFragment<FragmentReviewsBinding> {


    @Override
    protected FragmentReviewsBinding getFragmentBinding() {
        return FragmentReviewsBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getReviews();
    }

    private void getReviews() {
        List<String> reviews = new ArrayList<>();
        reviews.add("5");
        reviews.add("4");
        reviews.add("3");

        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        fragmentBinding.reviewsList.setLayoutManager(layoutManager);
        ReviewsAdapter adapter = new ReviewsAdapter(reviews, App.applicationContext);
        fragmentBinding.reviewsList.setAdapter(adapter);
    }

}