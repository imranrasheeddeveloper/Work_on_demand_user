package com.rizorsiumani.workondemanduser.ui.promo_code;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.data.businessModels.HomeContentModel;
import com.rizorsiumani.workondemanduser.data.businessModels.PromoActivationModel;
import com.rizorsiumani.workondemanduser.data.businessModels.PromotionModel;
import com.rizorsiumani.workondemanduser.data.remote.RemoteRepository;
import com.rizorsiumani.workondemanduser.data.remote.ResponseWrapper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PromotionViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<PromotionModel>> promotion = new MutableLiveData<>();
    public LiveData<ResponseWrapper<PromotionModel>> _promotion = promotion;

    private final MutableLiveData<ResponseWrapper<PromoActivationModel>> activate = new MutableLiveData<>();
    public LiveData<ResponseWrapper<PromoActivationModel>> _activate = activate;

    public void getCodes() {
        promotion.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .getPromocodes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PromotionModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        promotion.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(PromotionModel model) {
                        promotion.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                model
                        ));
                    }
                });
    }

    public void activate(String code) {
        activate.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .activateCode(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PromoActivationModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        activate.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(PromoActivationModel model) {
                        activate.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                model
                        ));
                    }
                });
    }

}


