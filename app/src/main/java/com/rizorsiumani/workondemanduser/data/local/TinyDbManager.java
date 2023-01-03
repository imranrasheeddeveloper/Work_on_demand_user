package com.rizorsiumani.workondemanduser.data.local;

import android.util.Log;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.common.AddBookingDataItem;
import com.rizorsiumani.workondemanduser.common.BookingTimingItem;
import com.rizorsiumani.workondemanduser.data.businessModels.CardsDataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.CategoriesDataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.PromoDataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProviderModel;
import com.rizorsiumani.workondemanduser.data.businessModels.UserData;
import com.rizorsiumani.workondemanduser.ui.booking.MyCartItems;
import com.rizorsiumani.workondemanduser.ui.booking_detail.model.DataItem;
import com.rizorsiumani.workondemanduser.ui.job_timing.AvailabilitiesItem;

import java.util.ArrayList;
import java.util.List;

public class TinyDbManager {

    public static final String KEY_CART = "key_cart";
    public static final String KEY_ADD_BOOKING = "key_add_booking";

    public static final String KEY_ADDRESS = "key_address";
    public static final String KEY_SELECTED_ADDRESS = "key_selected_address";

    public static final String KEY_ADDRESS_STATUS = "key_address_status";
    public static final String KEY_USER = "key_user";
    public static final String KEY_FIRST_VISIT = "key_first_visit";
    public static final String KEY_HOUR = "key_hour";
    public static final String KEY_PROMO = "key_promo";
    public static final String KEY_SPID = "key_service_provider_id";
    public static final String KEY_CARD = "key_card";
    public static final String KEY_TIMING = "key_timing";
    public static final String KEY_TYPE = "key_user_type";
    public static final String KEY_PROVIDER = "key_provider";
    public static final String KEY_CATEGORY= "key_category";
    public static final String KEY_BOOKING_TIMING= "key_booking_timing";



    public static void saveCartData(MyCartItems data) {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        ArrayList<Object> previousItems = tinyDB.getListObject(KEY_CART, MyCartItems.class);
        previousItems.add(data);
        tinyDB.putListObject(KEY_CART, previousItems);
        Log.e("CART", "cart item saved !");
    }

    public static void saveBookingList(AddBookingDataItem data) {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        ArrayList<Object> previousItems = tinyDB.getListObject(KEY_ADD_BOOKING, AddBookingDataItem.class);
        previousItems.add(data);
        tinyDB.putListObject(KEY_ADD_BOOKING, previousItems);
        Log.e("ADD BOOKING", "booking item saved !");
    }



    public static List<AddBookingDataItem> getBookingList() {

        TinyDB tinyDB = new TinyDB(App.applicationContext);
        ArrayList<Object> items = tinyDB.getListObject(KEY_ADD_BOOKING, AddBookingDataItem.class);
        ArrayList<AddBookingDataItem> cartItems = new ArrayList<>();
        ;

        if (!items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {

                AddBookingDataItem item = (AddBookingDataItem) items.get(i);
                cartItems.add(item);
            }
        }
        return cartItems;
    }
    public static void saveUserType(String data) {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        tinyDB.putString(KEY_TYPE, data);
        Log.e("user", " saved !");
    }

    public static String getUserType() {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        return tinyDB.getString(KEY_TYPE);
    }


    public static List<MyCartItems> getCartData() {

        TinyDB tinyDB = new TinyDB(App.applicationContext);
        ArrayList<Object> items = tinyDB.getListObject(KEY_CART, MyCartItems.class);
        ArrayList<MyCartItems> cartItems = new ArrayList<>();
        ;

        if (!items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {

                MyCartItems item = (MyCartItems) items.get(i);
                cartItems.add(item);
            }
        }
        return cartItems;
    }

    public static void removeCartItem(MyCartItems items) {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        ArrayList<Object> previousItems = tinyDB.getListObject(KEY_CART, MyCartItems.class);
        int keyLocation = previousItems.indexOf(items);
        previousItems.remove(keyLocation + 1);
        tinyDB.putListObject(KEY_CART, previousItems);
        Log.e("CART", previousItems.size() + " cart item removed successfully !");

    }

    public static void clearCart() {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        ArrayList<Object> previousItems = tinyDB.getListObject(KEY_CART, MyCartItems.class);
        previousItems.clear();
        tinyDB.putListObject(KEY_CART, previousItems);
        Log.e("CART", previousItems.size() + " cart cleared successfully!");

    }

    public static void clearBookingList() {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        ArrayList<Object> previousItems = tinyDB.getListObject(KEY_ADD_BOOKING, AddBookingDataItem.class);
        previousItems.clear();
        tinyDB.putListObject(KEY_ADD_BOOKING, previousItems);
        Log.e("booking", previousItems.size() + " booking cleared successfully!");

    }

    public static void saveCurrentAddress(String address) {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        tinyDB.putString(KEY_ADDRESS, address);

    }

    public static String getCurrentAddress() {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        return tinyDB.getString(KEY_ADDRESS);
    }

    public static void saveSelectedAddress(String address) {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        tinyDB.putString(KEY_SELECTED_ADDRESS, address);

    }

    public static String getSelectedAddress() {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        return tinyDB.getString(KEY_SELECTED_ADDRESS);
    }

    public static void saveUserData(UserData data) {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        tinyDB.putObject(KEY_USER, data);
        Log.e("USER", "user information saved !");
    }

    public static UserData getUserInformation(){
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        return tinyDB.getObject(KEY_USER,UserData.class);
    }

    public static void saveVisit(boolean status) {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        tinyDB.putBoolean(KEY_FIRST_VISIT, status);
    }


    public static boolean getVisit(){
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        return tinyDB.getBoolean(KEY_FIRST_VISIT);
    }

    public static void savePromo(PromoDataItem promoDataItem){
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        tinyDB.putObject(KEY_PROMO, promoDataItem);
        Log.e("PROMO CODE", "saved !");
    }

    public static PromoDataItem getPromo(){
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        return tinyDB.getObject(KEY_PROMO,PromoDataItem.class);
    }

    public static void saveServiceProviderID(String ID) {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        tinyDB.putString(KEY_SPID,ID);

    }

    public static String getServiceProviderID() {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        return tinyDB.getString(KEY_SPID);
    }

    public static void selectedCard(CardsDataItem dataItem) {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        tinyDB.putObject(KEY_CARD, dataItem);
        Log.e("CARD", "saved !");
    }

    public static CardsDataItem getSelectedCard(){
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        return tinyDB.getObject(KEY_CARD,CardsDataItem.class);
    }

    public static void saveTiming(AvailabilitiesItem dayTimeModelList) {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        tinyDB.putObject(KEY_TIMING, dayTimeModelList);
    }

    public static List<AvailabilitiesItem> getTiming() {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        ArrayList<Object> items = tinyDB.getListObject(KEY_TIMING, AvailabilitiesItem.class);
        ArrayList<AvailabilitiesItem> timeModels = new ArrayList<>();
        if (!items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {

                AvailabilitiesItem item = (AvailabilitiesItem) items.get(i);
                timeModels.add(item);
            }
        }
        return timeModels;
    }

    public static void clearTiming() {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        ArrayList<Object> previousItems = tinyDB.getListObject(KEY_TIMING, AvailabilitiesItem.class);
        previousItems.clear();
        tinyDB.putListObject(KEY_TIMING, previousItems);
        Log.e("TIMING", previousItems.size() + " Timing List cleared successfully!");

    }

    public static void saveProviderForMapScreen(ServiceProviderModel data) {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        tinyDB.putObject(KEY_PROVIDER, data);
    }

    public static ServiceProviderModel getProviderMap(){
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        return tinyDB.getObject(KEY_PROVIDER,ServiceProviderModel.class);
    }

    public static void saveCategory(CategoriesDataItem categoriesDataItem) {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        tinyDB.putObject(KEY_CATEGORY, categoriesDataItem);
    }

    public static CategoriesDataItem getCategory(){
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        return tinyDB.getObject(KEY_CATEGORY,CategoriesDataItem.class);
    }

    public static void saveBookingTiming(BookingTimingItem bookingTimingItem) {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        ArrayList<Object> previousItems = tinyDB.getListObject(KEY_BOOKING_TIMING, BookingTimingItem.class);
        previousItems.add(bookingTimingItem);
        tinyDB.putListObject(KEY_BOOKING_TIMING, previousItems);
    }

    public static List<BookingTimingItem> getBookingTiming() {

        TinyDB tinyDB = new TinyDB(App.applicationContext);
        ArrayList<Object> items = tinyDB.getListObject(KEY_BOOKING_TIMING, BookingTimingItem.class);
        ArrayList<BookingTimingItem> bookingTimingItems = new ArrayList<>();
        ;

        if (!items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {

                BookingTimingItem item = (BookingTimingItem) items.get(i);
                bookingTimingItems.add(item);
            }
        }
        return bookingTimingItems;
    }

    public static void removeBookingTiming(BookingTimingItem bookingTimingItem) {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        ArrayList<Object> previousItems = tinyDB.getListObject(KEY_BOOKING_TIMING, BookingTimingItem.class);
        int keyLocation = previousItems.indexOf(bookingTimingItem);
        previousItems.remove(keyLocation + 1);
        tinyDB.putListObject(KEY_BOOKING_TIMING, previousItems);
    }

    public static void clearBookingTiming() {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        ArrayList<Object> previousItems = tinyDB.getListObject(KEY_BOOKING_TIMING, BookingTimingItem.class);
        previousItems.clear();
        tinyDB.putListObject(KEY_BOOKING_TIMING, previousItems);

    }
}
