package com.rizorsiumani.workondemanduser.ui.service_providers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProviderModel;
import com.rizorsiumani.workondemanduser.data.remote.RemoteRepository;
import com.rizorsiumani.workondemanduser.data.remote.ResponseWrapper;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ServiceProviderViewModel extends ViewModel {


    private final MutableLiveData<ResponseWrapper<ServiceProviderModel>> provider = new MutableLiveData<>();
    public LiveData<ResponseWrapper<ServiceProviderModel>> _provider = provider;

    private final MutableLiveData<ResponseWrapper<ServiceProviderModel>> by_cat_provider = new MutableLiveData<>();
    public LiveData<ResponseWrapper<ServiceProviderModel>> _by_cat_provider = by_cat_provider;

    private final MutableLiveData<ResponseWrapper<ServiceProviderModel>> search_provider = new MutableLiveData<>();
    public LiveData<ResponseWrapper<ServiceProviderModel>> _search_provider = search_provider;


    public void serviceProviders(int page , String token ,JsonObject object) {

        provider.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .getProviders(page,token,object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ServiceProviderModel>() {
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
                    public void onNext(ServiceProviderModel ServiceProviderModel) {
                        provider.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                ServiceProviderModel
                        ));
                    }
                });
    }

    public void catServiceProviders(int page , String token ,JsonObject object) {

        by_cat_provider.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .getProvidersByCat(page,token,object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ServiceProviderModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        by_cat_provider.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(ServiceProviderModel ServiceProviderModel) {
                        by_cat_provider.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                ServiceProviderModel
                        ));
                    }
                });
    }

    public void serviceProvidersSearch(int page , String token ,JsonObject object) {

        search_provider.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .searchProvider(token,page,object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ServiceProviderModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        search_provider.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(ServiceProviderModel ServiceProviderModel) {
                        search_provider.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                ServiceProviderModel
                        ));
                    }
                });
    }


}

