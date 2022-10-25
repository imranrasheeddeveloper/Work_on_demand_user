package com.rizorsiumani.workondemanduser.ui.sp_detail;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.data.businessModels.ProviderAvailabilityModel;
import com.rizorsiumani.workondemanduser.data.businessModels.ProviderGalleryModel;
import com.rizorsiumani.workondemanduser.data.businessModels.ProviderServicesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProviderProfileModel;
import com.rizorsiumani.workondemanduser.data.businessModels.SliderModel;
import com.rizorsiumani.workondemanduser.data.remote.RemoteRepository;
import com.rizorsiumani.workondemanduser.data.remote.ResponseWrapper;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProviderDetailViewModel extends ViewModel {


    private final MutableLiveData<ResponseWrapper<ProviderGalleryModel>> gallery = new MutableLiveData<>();
    public LiveData<ResponseWrapper<ProviderGalleryModel>> _gallery = gallery;

    private final MutableLiveData<ResponseWrapper<ProviderServicesModel>> services = new MutableLiveData<>();
    public LiveData<ResponseWrapper<ProviderServicesModel>> _services = services;

    private final MutableLiveData<ResponseWrapper<ProviderAvailabilityModel>> available = new MutableLiveData<>();
    public LiveData<ResponseWrapper<ProviderAvailabilityModel>> _available = available;

    private final MutableLiveData<ResponseWrapper<ServiceProviderProfileModel>> profile = new MutableLiveData<>();
    public LiveData<ResponseWrapper<ServiceProviderProfileModel>> _profile = profile;

    public void getGallery(int id) {

        gallery.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .getGallery(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProviderGalleryModel>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        gallery.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }
                    @Override
                    public void onNext(ProviderGalleryModel model) {
                        gallery.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                model
                        ));
                    }
                });
    }

    public void getServices(int id) {

        services.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .getServices(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProviderServicesModel>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        services.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }
                    @Override
                    public void onNext(ProviderServicesModel model) {
                        services.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                model
                        ));
                    }
                });
    }

    public void getAvailability(int id) {

        available.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .getAvailability(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProviderAvailabilityModel>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        available.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }
                    @Override
                    public void onNext(ProviderAvailabilityModel model) {
                        available.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                model
                        ));
                    }
                });
    }

    public void getProfile(int id) {

        profile.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .getProfile(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ServiceProviderProfileModel>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        profile.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }
                    @Override
                    public void onNext(ServiceProviderProfileModel model) {
                        profile.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                model
                        ));
                    }
                });
    }


}
