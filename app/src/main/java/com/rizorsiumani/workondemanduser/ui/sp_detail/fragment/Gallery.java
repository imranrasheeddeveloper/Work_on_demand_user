package com.rizorsiumani.workondemanduser.ui.sp_detail.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.FragmentGalleryBinding;
import com.rizorsiumani.workondemanduser.ui.fragment.home.ServicesAdapter;

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
        images.add(R.drawable.dummy_sp);
        images.add(R.drawable.dummy_sp);
        images.add(R.drawable.dummy_sp);
        images.add(R.drawable.dummy_sp);
        images.add(R.drawable.dummy_sp);

        GridLayoutManager glm = new GridLayoutManager(requireContext(), 2);
        fragmentBinding.galleryList.setLayoutManager(glm);
        GalleryAdapter adapter = new GalleryAdapter(requireContext(), images);
        fragmentBinding.galleryList.setAdapter(adapter);
    }
}