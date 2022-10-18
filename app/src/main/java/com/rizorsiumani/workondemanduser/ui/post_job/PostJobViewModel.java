package com.rizorsiumani.workondemanduser.ui.post_job;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.data.businessModels.GetAddressesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.PostImageModel;
import com.rizorsiumani.workondemanduser.data.businessModels.PostJobModel;
import com.rizorsiumani.workondemanduser.data.remote.RemoteRepository;
import com.rizorsiumani.workondemanduser.data.remote.ResponseWrapper;

import okhttp3.MultipartBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PostJobViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<PostJobModel>> job = new MutableLiveData<>();
    public LiveData<ResponseWrapper<PostJobModel>> _job= job;

    private final MutableLiveData<ResponseWrapper<PostImageModel>> job_image = new MutableLiveData<>();
    public LiveData<ResponseWrapper<PostImageModel>> _job_image= job_image;


    public void post(String token, JsonObject object) {

        job.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .postJob(token,object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostJobModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        job.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(PostJobModel model) {
                        job.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                model
                        ));
                    }
                });
    }

    public void postImage(MultipartBody.Part part) {

        job_image.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .uploadJobImage(part)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostImageModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        job_image.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(PostImageModel model) {
                        job_image.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                model
                        ));
                    }
                });
    }

}


