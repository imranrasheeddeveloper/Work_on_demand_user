package com.rizorsiumani.workondemanduser.ui.fragment.booking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rizorsiumani.workondemanduser.data.businessModels.BasicModel;
import com.rizorsiumani.workondemanduser.data.businessModels.GetBookingsModel;
import com.rizorsiumani.workondemanduser.data.remote.RemoteRepository;
import com.rizorsiumani.workondemanduser.data.remote.ResponseWrapper;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BookingsViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<GetBookingsModel>> bookings = new MutableLiveData<>();
    public LiveData<ResponseWrapper<GetBookingsModel>> _bookings = bookings;

    private final MutableLiveData<ResponseWrapper<BasicModel>> cancel_booking = new MutableLiveData<>();
    public LiveData<ResponseWrapper<BasicModel>> _cancel_booking = cancel_booking;

    public void getBookings(String token, int page, String status) {

        bookings.setValue(
                new ResponseWrapper<>(
                        true, "", null
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
                        bookings.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(GetBookingsModel model) {
                        bookings.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                model
                        ));
                    }
                });

    }

    public void cancelBooking(String token, int id) {

        cancel_booking.setValue(
                new ResponseWrapper<>(
                        true, "", null
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
                        cancel_booking.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(BasicModel model) {
                        cancel_booking.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                model
                        ));
                    }
                });

    }


}
