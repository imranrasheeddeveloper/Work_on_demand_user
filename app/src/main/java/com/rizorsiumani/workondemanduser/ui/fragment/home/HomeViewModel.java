package com.rizorsiumani.workondemanduser.ui.fragment.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rizorsiumani.workondemanduser.data.businessModels.CategoriesModel;
import com.rizorsiumani.workondemanduser.data.remote.RemoteRepository;
import com.rizorsiumani.workondemanduser.data.remote.ResponseWrapper;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<CategoriesModel>> category = new MutableLiveData<>();
    public LiveData<ResponseWrapper<CategoriesModel>> _category = category;

    private final MutableLiveData<ResponseWrapper<CategoriesModel>> ddCategory = new MutableLiveData<>();
    public LiveData<ResponseWrapper<CategoriesModel>> _ddCategory = ddCategory;

    public void categories(int page) {

        category.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .getCategories(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoriesModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            category.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(CategoriesModel categoriesModel) {
                        category.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                categoriesModel
                        ));
                    }
                });
    }

    public void dropDownCategories() {

        ddCategory.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .getDropDownCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoriesModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            ddCategory.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(CategoriesModel categoriesModel) {
                        ddCategory.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                categoriesModel
                        ));
                    }
                });
    }


}
