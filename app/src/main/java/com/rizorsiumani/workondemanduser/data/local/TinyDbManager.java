package com.rizorsiumani.workondemanduser.data.local;

import android.util.Log;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.common.AddBookingDataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.PromoDataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.UserData;
import com.rizorsiumani.workondemanduser.ui.booking.MyCartItems;

import java.util.ArrayList;
import java.util.List;

public class TinyDbManager {

    public static final String KEY_CART = "key_cart";
    public static final String KEY_ADD_BOOKING = "key_add_booking";

    public static final String KEY_ADDRESS = "key_address";
    public static final String KEY_ADDRESS_STATUS = "key_address_status";
    public static final String KEY_USER = "key_user";
    public static final String KEY_FIRST_VISIT = "key_first_visit";
    public static final String KEY_HOUR = "key_hour";
    public static final String KEY_PROMO = "key_promo";
    public static final String KEY_SPID = "key_service_provider_id";



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
}