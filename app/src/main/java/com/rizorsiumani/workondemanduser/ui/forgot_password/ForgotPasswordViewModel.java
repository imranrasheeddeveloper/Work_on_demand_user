package com.rizorsiumani.workondemanduser.ui.forgot_password;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.rizorsiumani.workondemanduser.data.businessModels.BasicModel;
import com.rizorsiumani.workondemanduser.data.remote.RemoteRepository;
import com.rizorsiumani.workondemanduser.data.remote.ResponseWrapper;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ForgotPasswordViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<BasicModel>> password = new MutableLiveData<>();
    public LiveData<ResponseWrapper<BasicModel>> _password = password;

    public void sendEmail(String email) {

        password.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .forgot(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BasicModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            BasicModel model = new BasicModel();
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            try {
                                Gson gson = new Gson();
                                model = gson.fromJson(body.string(),BasicModel.class);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            password.setValue(new ResponseWrapper<>(
                                    false,
                                    model.getMessage(),
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(BasicModel model) {
                        password.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                model
                        ));
                    }
                });
    }

}
