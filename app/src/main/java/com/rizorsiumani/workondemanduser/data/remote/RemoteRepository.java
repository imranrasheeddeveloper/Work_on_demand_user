package com.rizorsiumani.workondemanduser.data.remote;



import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.common.CommonResponse;
import com.rizorsiumani.workondemanduser.data.businessModels.AddBookingModel;
import com.rizorsiumani.workondemanduser.data.businessModels.BasicModel;
import com.rizorsiumani.workondemanduser.data.businessModels.CategoriesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.GetAddressesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.GetAllCardsModel;
import com.rizorsiumani.workondemanduser.data.businessModels.GetBookingsModel;
import com.rizorsiumani.workondemanduser.data.businessModels.GetFeeModel;
import com.rizorsiumani.workondemanduser.data.businessModels.GetRatingModel;
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
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProviderModel;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProviderProfileModel;
import com.rizorsiumani.workondemanduser.data.businessModels.SliderModel;
import com.rizorsiumani.workondemanduser.data.businessModels.SubCategoriesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.UpdaeAddressModel;
import com.rizorsiumani.workondemanduser.data.businessModels.UpdateUserModel;
import com.rizorsiumani.workondemanduser.data.businessModels.WalletBalanceModel;
import com.rizorsiumani.workondemanduser.data.businessModels.WalletTransactionsModel;
import com.rizorsiumani.workondemanduser.data.businessModels.booking_detail.BookingDetailModel;
import com.rizorsiumani.workondemanduser.data.businessModels.chat.GetAllMessageModel;
import com.rizorsiumani.workondemanduser.data.businessModels.chatwoot_model.ConversationModel;
import com.rizorsiumani.workondemanduser.data.businessModels.chatwoot_model.MessagesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.chatwoot_model.SendSupportMessageModel;
import com.rizorsiumani.workondemanduser.data.businessModels.inbox.GetInboxModel;
import com.rizorsiumani.workondemanduser.data.businessModels.inbox.InboxExistModel;
import com.rizorsiumani.workondemanduser.data.businessModels.job_timing.JobTimingModel;
import com.rizorsiumani.workondemanduser.data.businessModels.settings.SettingsModel;
import com.rizorsiumani.workondemanduser.utils.RetrofitInstanceProvider;

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

    public Observable<CommonResponse> upload(MultipartBody.Part object){
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

    public Observable<PostJobModel> updateJob(String token,JsonObject object){
        if (mService != null){
            return  mService.updatePostedJob(token,object);
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

    public Observable<HomeContentModel> getHomeContent(String token , JsonObject object) {

        if (mService != null) {
            return mService.getContent(token,object);
        }
        return null;
    }

    public Observable<ServiceProviderModel> getProviders(int page, String token , JsonObject object) {

        if (mService != null) {
            return mService.getServiceProviders(token,page,object);
        }
        return null;
    }

    public Observable<ServiceProviderModel> getProvidersByCat(int page, String token , JsonObject object) {

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

    public Observable<GetRatingModel> getRating(int id) {

        if (mService != null) {
            return mService.getRating(id);
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

    public Observable<BasicModel> rating(String token , JsonObject object) {

        if (mService != null) {
            return mService.rateProvider(token,object);
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

    public Observable<ServiceProviderModel> searchProvider(String token , int page,JsonObject object) {

        if (mService != null) {
            return mService.getProviderBySearch(token,page,object);
        }
        return null;
    }

    public Observable<WalletBalanceModel> balance(String token) {

        if (mService != null) {
            return mService.getBalance(token);
        }
        return null;
    }

    public Observable<WalletTransactionsModel> transactions(String token) {

        if (mService != null) {
            return mService.getTransactions(token);
        }
        return null;
    }

    public Observable<BasicModel> create(String token,JsonObject object) {

        if (mService != null) {
            return mService.createCard(token,object);
        }
        return null;
    }

    public Observable<BasicModel> remove(String token,String id) {

        if (mService != null) {
            return mService.removeCard(token,id);
        }
        return null;
    }

    public Observable<GetAllCardsModel> getCards(String token) {

        if (mService != null) {
            return mService.getCards(token);
        }
        return null;
    }

    public Observable<BasicModel> upWallet(String token,JsonObject object) {

        if (mService != null) {
            return mService.wallet_up(token,object);
        }
        return null;
    }

    public Observable<BasicModel> updateBookingStatus(String token,String status, int id) {
        if (mService != null) {
            return mService.bookingStatus(token,status,id);
        }
        return null;
    }

    public Observable<GetInboxModel> inbox(String token) {
        if (mService != null) {
            return mService.getInboxList(token);
        }
        return null;
    }

    public Observable<BasicModel> sendMsg(String token,JsonObject object) {
        if (mService != null) {
            return mService.send_message(token,object);
        }
        return null;
    }

    public Observable<GetAllMessageModel> getMessages(String token, int inbox_id) {
        if (mService != null) {
            return mService.get_messages(token,inbox_id);
        }
        return null;
    }

    public Observable<InboxExistModel> inboxExist(String token, int id) {
        if (mService != null) {
            return mService.is_inbox_exsit(token,id);
        }
        return null;
    }

    public Observable<MessagesModel> getSupportChat(String token, int accountID, int conversationID) {
        if (mService != null) {
            return mService.get_support_chat(token,accountID,conversationID);
        }
        return null;
    }

    public Observable<ConversationModel> create_conversation(String token, int account_id, JsonObject object) {
        if (mService != null) {
            return mService.createConversation(token,account_id,object);
        }
        return null;
    }

    public Observable<SendSupportMessageModel> send_support_message(String token, int conversation_id, int account_id, JsonObject object) {
        if (mService != null) {
            return mService.sendSupportQuery(token,conversation_id,account_id,object);
        }
        return null;
    }

    public Observable<JobTimingModel> getTiming(int id) {
        if (mService != null) {
            return mService.getJobTiming(id);
        }
        return null;
    }

    public Observable<BasicModel> deleteJob(String token,int id) {
        if (mService != null) {
            return mService.delete_posted_job(token,id);
        }
        return null;
    }

    public Observable<BasicModel> deleteJobTiming(String token,int id) {
        if (mService != null) {
            return mService.delete_posted_job_timing(token,id);
        }
        return null;
    }

    public Observable<GetFeeModel> bookingFee() {
        if (mService != null) {
            return mService.getBookingFee();
        }
        return null;
    }

    public Observable<BasicModel> forgot(String email) {
        if (mService != null) {
            return mService.forgot_password(email);
        }
        return null;
    }

    public Observable<BasicModel> verify_email(String email, int code) {
        if (mService != null) {
            return mService.verify_email(email,code);
        }
        return null;
    }

    public Observable<BasicModel> reset_password(String email, String password) {
        if (mService != null) {
            return mService.reset(email,password);
        }
        return null;
    }

    public Observable<SettingsModel> settings() {
        if (mService != null) {
            return mService.get_settings();
        }
        return null;
    }
}
