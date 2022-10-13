package com.rizorsiumani.workondemanduser.ui.sub_category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rizorsiumani.workondemanduser.data.businessModels.CategoriesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.SubCategoriesModel;
import com.rizorsiumani.workondemanduser.data.remote.RemoteRepository;
import com.rizorsiumani.workondemanduser.data.remote.ResponseWrapper;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SubCategoryViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<SubCategoriesModel>> subCategory = new MutableLiveData<>();
    public LiveData<ResponseWrapper<SubCategoriesModel>> _subCategory = subCategory;

    public void subCategories(int id,int page) {

        subCategory.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .getSubCategories(id,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SubCategoriesModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        subCategory.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(SubCategoriesModel subCategoriesModel) {
                        subCategory.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                subCategoriesModel
                        ));
                    }
                });
    }


}
