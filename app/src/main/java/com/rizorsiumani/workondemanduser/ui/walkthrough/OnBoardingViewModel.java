package com.rizorsiumani.workondemanduser.ui.walkthrough;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rizorsiumani.workondemanduser.data.businessModels.OnBoardingModel;
import com.rizorsiumani.workondemanduser.data.remote.RemoteRepository;
import com.rizorsiumani.workondemanduser.data.remote.ResponseWrapper;

import java.io.IOException;

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
                        true, "", null
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
                            try {
                                onBoard.setValue(new ResponseWrapper<>(
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
                    public void onNext(OnBoardingModel onBoardingModel) {
                        onBoard.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                onBoardingModel
                        ));
                    }
                });
    }
}
