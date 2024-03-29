package com.rizorsiumani.user.ui.sp_detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rizorsiumani.user.data.businessModels.GetRatingModel;
import com.rizorsiumani.user.data.businessModels.ProviderAvailabilityModel;
import com.rizorsiumani.user.data.businessModels.ProviderGalleryModel;
import com.rizorsiumani.user.data.businessModels.ProviderServicesModel;
import com.rizorsiumani.user.data.businessModels.ServiceProviderProfileModel;
import com.rizorsiumani.user.data.remote.RemoteRepository;
import com.rizorsiumani.user.data.remote.ResponseWrapper;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
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

    private final MutableLiveData<ResponseWrapper<GetRatingModel>> rating = new MutableLiveData<>();
    public LiveData<ResponseWrapper<GetRatingModel>> _rating = rating;


    public void getGallery(int id) {

        gallery.setValue(
                new ResponseWrapper<>(
                        true, null, null
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
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            gallery.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }
                    @Override
                    public void onNext(ProviderGalleryModel model) {
                        gallery.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }

    public void getServices(int id) {

        services.setValue(
                new ResponseWrapper<>(
                        true, null, null
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
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            services.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }
                    @Override
                    public void onNext(ProviderServicesModel model) {
                        services.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }

    public void getAvailability(int id) {

        available.setValue(
                new ResponseWrapper<>(
                        true, null, null
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
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            available.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }
                    @Override
                    public void onNext(ProviderAvailabilityModel model) {
                        available.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }

    public void getProfile(int id) {

        profile.setValue(
                new ResponseWrapper<>(
                        true, null, null
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
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            profile.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }
                    @Override
                    public void onNext(ServiceProviderProfileModel model) {
                        profile.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }

    public void getRating(int id) {

        rating.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .getRating(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetRatingModel>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            rating.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }
                    @Override
                    public void onNext(GetRatingModel model) {
                        rating.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }
}
