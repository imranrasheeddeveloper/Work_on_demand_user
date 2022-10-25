package com.rizorsiumani.workondemanduser.ui.service_providers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProvidersModel;
import com.rizorsiumani.workondemanduser.data.remote.RemoteRepository;
import com.rizorsiumani.workondemanduser.data.remote.ResponseWrapper;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ServiceProviderViewModel extends ViewModel {


    private final MutableLiveData<ResponseWrapper<ServiceProvidersModel>> provider = new MutableLiveData<>();
    public LiveData<ResponseWrapper<ServiceProvidersModel>> _provider = provider;

    public void serviceProviders(int page , String token ,JsonObject object) {

        provider.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .getProviders(page,token,object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ServiceProvidersModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        provider.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(ServiceProvidersModel serviceProvidersModel) {
                        provider.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                serviceProvidersModel
                        ));
                    }
                });
    }

}

