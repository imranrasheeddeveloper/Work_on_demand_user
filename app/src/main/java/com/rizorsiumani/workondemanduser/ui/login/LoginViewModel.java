package com.rizorsiumani.workondemanduser.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.data.businessModels.LoginModel;
import com.rizorsiumani.workondemanduser.data.remote.RemoteRepository;
import com.rizorsiumani.workondemanduser.data.remote.ResponseWrapper;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<LoginModel>> loginData = new MutableLiveData<>();
    public LiveData<ResponseWrapper<LoginModel>> _loginData = loginData;

    public void login(JsonObject object) {

        loginData.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .login(object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loginData.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(LoginModel loginModel) {
                        loginData.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                loginModel
                        ));
                    }
                });
    }

}
