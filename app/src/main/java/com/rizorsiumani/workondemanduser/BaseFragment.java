package com.rizorsiumani.workondemanduser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.rizorsiumani.workondemanduser.data.local.PreferenceRepository;
import com.rizorsiumani.workondemanduser.databinding.FragmentHomeBinding;

public abstract class BaseFragment<binding extends ViewBinding> extends Fragment {

    protected binding fragmentBinding;
    private ProgressBar progressBar;

    protected abstract binding getFragmentBinding();

    protected PreferenceRepository prefRepository = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentBinding = getFragmentBinding();

        prefRepository = new PreferenceRepository();

        return fragmentBinding.getRoot();

    }

    public PreferenceRepository getPrefRepository() {
        return prefRepository;
    }
}
