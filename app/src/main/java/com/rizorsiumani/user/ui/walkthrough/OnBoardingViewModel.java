package com.rizorsiumani.user.ui.walkthrough;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rizorsiumani.user.data.businessModels.OnBoardingModel;
import com.rizorsiumani.user.data.remote.RemoteRepository;
import com.rizorsiumani.user.data.remote.ResponseWrapper;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OnBoardingViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<OnBoardingModel>> onBoard = new MutableLiveData<>();
    public LiveData<ResponseWrapper<OnBoardingModel>> _onBoard = onBoard;

    public void getOnBoardData() {

        onBoard.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .getOnBoarding()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OnBoardingModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            onBoard.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(OnBoardingModel onBoardingModel) {
                        onBoard.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                onBoardingModel
                        ));
                    }
                });
    }
}
