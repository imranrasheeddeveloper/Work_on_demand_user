package com.rizorsiumani.workondemanduser.data.remote;



import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.common.CommonResponse;
import com.rizorsiumani.workondemanduser.data.businessModels.CategoriesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.LoginModel;
import com.rizorsiumani.workondemanduser.data.businessModels.OnBoardingModel;
import com.rizorsiumani.workondemanduser.data.businessModels.SliderModel;
import com.rizorsiumani.workondemanduser.data.businessModels.SubCategoriesModel;
import com.rizorsiumani.workondemanduser.utils.RetrofitInstanceProvider;

import rx.Observable;

/**
 * this class takes ApiService to call api methods
 * that are declared in ApiService interface
 */
public class RemoteRepository {

    private static final String TAG = "RemoteRepository";

    //for providing singleton instance
    private static RemoteRepository _instance = null;
    private final ApiService mService;

    private RemoteRepository(ApiService service) {
        mService = service;
    }

    public static RemoteRepository getInstance() {
        if (_instance == null) {
            return _instance = new RemoteRepository(
                    RetrofitInstanceProvider.getInstance().getApiService());
        } else {
            return _instance;
        }
    }



    //repository is responsible for making actual API calls

    public Observable<OnBoardingModel> getOnBoarding() {

        if (mService != null) {
            return mService.getOnBoardingData();
        }
        return null;
    }

    public Observable<LoginModel> login(JsonObject object) {

        if (mService != null) {
            return mService.loginUser(object);
        }
        return null;
    }

    public Observable<CategoriesModel> getCategories(int page) {

        if (mService != null) {
            return mService.getCategories(page);
        }
        return null;
    }

    public Observable<SubCategoriesModel> getSubCategories(int id,int page) {

        if (mService != null) {
            return mService.getSubCategories(id,page);
        }
        return null;
    }

    public Observable<SliderModel> getSliderData() {

        if (mService != null) {
            return mService.getSliderData();
        }
        return null;
    }

    public Observable<CommonResponse> upload(JsonObject object){
        if (mService != null){
            return  mService.uploadImage(object);
        }
        return null;
    }

}
