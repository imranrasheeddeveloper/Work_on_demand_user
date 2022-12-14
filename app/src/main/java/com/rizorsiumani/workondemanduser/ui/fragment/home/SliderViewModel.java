package com.rizorsiumani.workondemanduser.ui.fragment.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rizorsiumani.workondemanduser.data.businessModels.SliderModel;
import com.rizorsiumani.workondemanduser.data.remote.RemoteRepository;
import com.rizorsiumani.workondemanduser.data.remote.ResponseWrapper;

import java.io.IOException;

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
                        true, "", null
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
                            try {
                                slider.setValue(new ResponseWrapper<>(
                                        false,
                                        body.string(),
                                        null
                                ));
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onNext(SliderModel sliderModel) {
                        slider.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                sliderModel
                        ));
                    }
                });
    }


}

