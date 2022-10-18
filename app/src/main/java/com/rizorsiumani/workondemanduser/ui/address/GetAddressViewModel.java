package com.rizorsiumani.workondemanduser.ui.address;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.data.businessModels.GetAddressesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.SaveAddressModel;
import com.rizorsiumani.workondemanduser.data.remote.RemoteRepository;
import com.rizorsiumani.workondemanduser.data.remote.ResponseWrapper;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GetAddressViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<GetAddressesModel>> address = new MutableLiveData<>();
    public LiveData<ResponseWrapper<GetAddressesModel>> _address = address;


    public void get(String token) {

        address.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .getAddress(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetAddressesModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        address.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(GetAddressesModel addressesModel) {
                        address.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                addressesModel
                        ));
                    }
                });
    }

}

