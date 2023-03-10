package com.rizorsiumani.user.ui.booking_detail;

import com.google.gson.JsonObject;
import com.rizorsiumani.user.data.businessModels.AddBookingModel;
import com.rizorsiumani.user.data.businessModels.GetFeeModel;
import com.rizorsiumani.user.data.businessModels.PaymentGatewayModel;
import com.rizorsiumani.user.data.remote.RemoteRepository;
import com.rizorsiumani.user.data.remote.ResponseWrapper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BookingDetailViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<PaymentGatewayModel>> payment = new MutableLiveData<>();
    public LiveData<ResponseWrapper<PaymentGatewayModel>> _payment = payment;

    private final MutableLiveData<ResponseWrapper<AddBookingModel>> add_booking = new MutableLiveData<>();
    public LiveData<ResponseWrapper<AddBookingModel>> _add_booking = add_booking;

    private final MutableLiveData<ResponseWrapper<GetFeeModel>> fee = new MutableLiveData<>();
    public LiveData<ResponseWrapper<GetFeeModel>> _fee = fee;

    public void getPaymentMethods() {

        payment.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .getPaymentMethod()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PaymentGatewayModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            payment.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(PaymentGatewayModel model) {
                        payment.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }

    public void addBooking(String token , JsonObject object) {

        add_booking.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .booking(token,object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddBookingModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            add_booking.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(AddBookingModel model) {
                        add_booking.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }

    public void getBookingFee() {

        fee.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .bookingFee()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetFeeModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            fee.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(GetFeeModel model) {
                        fee.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }

}

