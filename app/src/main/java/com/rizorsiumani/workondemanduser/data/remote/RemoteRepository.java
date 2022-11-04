package com.rizorsiumani.workondemanduser.data.remote;



import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.common.CommonResponse;
import com.rizorsiumani.workondemanduser.data.businessModels.AddBookingModel;
import com.rizorsiumani.workondemanduser.data.businessModels.BasicModel;
import com.rizorsiumani.workondemanduser.data.businessModels.BookingDetailData;
import com.rizorsiumani.workondemanduser.data.businessModels.BookingDetailModel;
import com.rizorsiumani.workondemanduser.data.businessModels.CategoriesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.GetAddressesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.GetBookingsModel;
import com.rizorsiumani.workondemanduser.data.businessModels.HomeContentModel;
import com.rizorsiumani.workondemanduser.data.businessModels.LoginModel;
import com.rizorsiumani.workondemanduser.data.businessModels.NotificationModel;
import com.rizorsiumani.workondemanduser.data.businessModels.OnBoardingModel;
import com.rizorsiumani.workondemanduser.data.businessModels.PaymentGatewayModel;
import com.rizorsiumani.workondemanduser.data.businessModels.PostImageModel;
import com.rizorsiumani.workondemanduser.data.businessModels.PostJobModel;
import com.rizorsiumani.workondemanduser.data.businessModels.PostedJobsModel;
import com.rizorsiumani.workondemanduser.data.businessModels.PromoActivationModel;
import com.rizorsiumani.workondemanduser.data.businessModels.PromotionModel;
import com.rizorsiumani.workondemanduser.data.businessModels.ProviderAvailabilityModel;
import com.rizorsiumani.workondemanduser.data.businessModels.ProviderGalleryModel;
import com.rizorsiumani.workondemanduser.data.businessModels.ProviderServicesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.RegistrationModel;
import com.rizorsiumani.workondemanduser.data.businessModels.SaveAddressModel;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProviderProfileModel;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProvidersModel;
import com.rizorsiumani.workondemanduser.data.businessModels.SliderModel;
import com.rizorsiumani.workondemanduser.data.businessModels.SubCategoriesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.UpdaeAddressModel;
import com.rizorsiumani.workondemanduser.data.businessModels.UpdateUserModel;
import com.rizorsiumani.workondemanduser.utils.RetrofitInstanceProvider;

import org.json.JSONObject;

import okhttp3.MultipartBody;
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

    public Observable<RegistrationModel> register(JsonObject object){
        if (mService != null){
            return  mService.registerUser(object);
        }
        return null;
    }

    public Observable<UpdateUserModel> updateUser(String token,JsonObject object){
        if (mService != null){
            return  mService.updateUser(token , object);
        }
        return null;
    }


    public Observable<SaveAddressModel> saveAddress(String token, JsonObject object){
        if (mService != null){
            return  mService.saveAddress(token , object);
        }
        return null;
    }

    public Observable<UpdaeAddressModel> updateAddress(String token, JsonObject object){
        if (mService != null){
            return  mService.updateAddress(token , object);
        }
        return null;
    }

    public Observable<GetAddressesModel> getAddress(String token){
        if (mService != null){
            return  mService.getAddresses(token);
        }
        return null;
    }

    public Observable<PostJobModel> postJob(String token,JsonObject object){
        if (mService != null){
            return  mService.postNewJob(token,object);
        }
        return null;
    }

    public Observable<PostImageModel> uploadJobImage(MultipartBody.Part part){
        if (mService != null){
            return  mService.uploadJobImage(part);
        }
        return null;
    }

    public Observable<PostedJobsModel> getPostedJobs(String token){
        if (mService != null){
            return  mService.getAllPostedJobs(token);
        }
        return null;
    }

    public Observable<CategoriesModel> getDropDownCategories() {

        if (mService != null) {
            return mService.getDropDownCategories();
        }
        return null;
    }

    public Observable<SubCategoriesModel> getDropDownSubCategories(int id) {

        if (mService != null) {
            return mService.getDropDownSubCategories(id);
        }
        return null;
    }

    public Observable<HomeContentModel> getHomeContent(String token ,JsonObject object) {

        if (mService != null) {
            return mService.getContent(token,object);
        }
        return null;
    }

    public Observable<ServiceProvidersModel> getProviders(int page, String token , JsonObject object) {

        if (mService != null) {
            return mService.getServiceProviders(token,page,object);
        }
        return null;
    }

    public Observable<ServiceProvidersModel> getProvidersByCat(int page, String token , JsonObject object) {

        if (mService != null) {
            return mService.getServiceProvidersByCat(token,page,object);
        }
        return null;
    }

    public Observable<ProviderGalleryModel> getGallery(int id) {

        if (mService != null) {
            return mService.getGallery(id);
        }
        return null;
    }

    public Observable<ProviderServicesModel> getServices(int id) {

        if (mService != null) {
            return mService.getServices(id);
        }
        return null;
    }

    public Observable<ProviderAvailabilityModel> getAvailability(int id) {

        if (mService != null) {
            return mService.getProviderAvailability(id);
        }
        return null;
    }

    public Observable<ServiceProviderProfileModel> getProfile(int id) {

        if (mService != null) {
            return mService.getProviderProfile(id);
        }
        return null;
    }

    public Observable<PaymentGatewayModel> getPaymentMethod() {

        if (mService != null) {
            return mService.getPaymentMethods();
        }
        return null;
    }

    public Observable<PromotionModel> getPromocodes() {

        if (mService != null) {
            return mService.getPromotions();
        }
        return null;
    }

    public Observable<PromoActivationModel> activateCode(String code) {

        if (mService != null) {
            return mService.activate(code);
        }
        return null;
    }

    public Observable<AddBookingModel> booking(String token , JsonObject object) {

        if (mService != null) {
            return mService.addBooking(token,object);
        }
        return null;
    }

    public Observable<GetBookingsModel> getAllBooking(String token , int page , String status) {

        if (mService != null) {
            return mService.getBooking(token,page,status);
        }
        return null;
    }

    public Observable<BasicModel> cancelBooking(String token , int id) {

        if (mService != null) {
            return mService.cancelBooking(token,id);
        }
        return null;
    }
    public Observable<BasicModel> rescheduleBooking(String token , JsonObject object) {

        if (mService != null) {
            return mService.reschedule(token,object);
        }
        return null;
    }

    public Observable<BookingDetailModel> getDetails(String token , int id) {

        if (mService != null) {
            return mService.getBookingDetails(token,id);
        }
        return null;
    }

    public Observable<NotificationModel> notifications(String token , int page) {

        if (mService != null) {
            return mService.getNotifications(token,page);
        }
        return null;
    }

    public Observable<ServiceProvidersModel> searchProvider(String token , int page,JsonObject object) {

        if (mService != null) {
            return mService.getProviderBySearch(token,page,object);
        }
        return null;
    }
}
