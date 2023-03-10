package com.rizorsiumani.user.ui.fragment.booking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.rizorsiumani.user.data.businessModels.BasicModel;
import com.rizorsiumani.user.data.businessModels.GetBookingsModel;
import com.rizorsiumani.user.data.remote.RemoteRepository;
import com.rizorsiumani.user.data.remote.ResponseWrapper;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BookingsViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<GetBookingsModel>> bookings = new MutableLiveData<>();
    public LiveData<ResponseWrapper<GetBookingsModel>> _bookings = bookings;

    private final MutableLiveData<ResponseWrapper<BasicModel>> cancel_booking = new MutableLiveData<>();
    public LiveData<ResponseWrapper<BasicModel>> _cancel_booking = cancel_booking;

    private final MutableLiveData<ResponseWrapper<BasicModel>> rate = new MutableLiveData<>();
    public LiveData<ResponseWrapper<BasicModel>> _rate = rate;

    private final MutableLiveData<ResponseWrapper<BasicModel>> booking_status = new MutableLiveData<>();
    public LiveData<ResponseWrapper<BasicModel>> _booking_status = booking_status;

    public void getBookings(String token, int page, String status) {

        bookings.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .getAllBooking(token, page, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetBookingsModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            bookings.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(GetBookingsModel model) {
                        bookings.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });

    }

    public void cancelBooking(String token, int id) {

        cancel_booking.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .cancelBooking(token, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BasicModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            cancel_booking.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(BasicModel model) {
                        cancel_booking.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });

    }

    public void rateProvider(String token, JsonObject object) {

        rate.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .rating(token, object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BasicModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            rate.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(BasicModel model) {
                        rate.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });

    }

    public void updateStatus(String token,int id, String status) {

        booking_status.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .updateBookingStatus(token,status,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BasicModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            booking_status.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(BasicModel model) {
                        booking_status.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });

    }

}
