package com.rizorsiumani.user.ui.fragment.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rizorsiumani.user.data.businessModels.SliderModel;
import com.rizorsiumani.user.data.remote.RemoteRepository;
import com.rizorsiumani.user.data.remote.ResponseWrapper;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SliderViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<SliderModel>> slider = new MutableLiveData<>();
    public LiveData<ResponseWrapper<SliderModel>> _slider = slider;

    public void getSliderInfo() {

        slider.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .getSliderData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SliderModel>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            slider.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }
                    @Override
                    public void onNext(SliderModel sliderModel) {
                        slider.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                sliderModel
                        ));
                    }
                });
    }


}

