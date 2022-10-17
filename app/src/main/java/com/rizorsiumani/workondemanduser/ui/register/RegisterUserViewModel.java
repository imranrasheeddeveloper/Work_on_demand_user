package com.rizorsiumani.workondemanduser.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.data.businessModels.RegistrationModel;
import com.rizorsiumani.workondemanduser.data.remote.RemoteRepository;
import com.rizorsiumani.workondemanduser.data.remote.ResponseWrapper;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterUserViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<RegistrationModel>> regData = new MutableLiveData<>();
    public LiveData<ResponseWrapper<RegistrationModel>> _regData = regData;

    public void registerUser(JsonObject object) {

        regData.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .register(object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegistrationModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        regData.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(RegistrationModel registrationModel) {
                        regData.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                registrationModel
                        ));
                    }
                });
    }

}
