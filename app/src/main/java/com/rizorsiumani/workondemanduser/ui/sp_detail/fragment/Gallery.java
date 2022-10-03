package com.rizorsiumani.workondemanduser.ui.sp_detail.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.FragmentGalleryBinding;

import java.util.ArrayList;
import java.util.List;


public class Gallery extends BaseFragment<FragmentGalleryBinding> {


    @Override
    protected FragmentGalleryBinding getFragmentBinding() {
        return FragmentGalleryBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buildRv();

    }

    private void buildRv() {

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.dummy_sp);
        images.add(R.drawable.ic_car_three);
        images.add(R.drawable.ic_three);
        images.add(R.drawable.ic_one);
        images.add(R.drawable.ic_car_one);
        images.add(R.drawable.ic_two);

        GridLayoutManager glm = new GridLayoutManager(requireContext(), 2);
        fragmentBinding.galleryList.setLayoutManager(glm);
        GalleryAdapter adapter = new GalleryAdapter(requireContext(), images);
        fragmentBinding.galleryList.setAdapter(adapter);
    }
}