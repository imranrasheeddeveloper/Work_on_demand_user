package com.rizorsiumani.workondemanduser.data.local;

import android.util.Log;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.data.businessModels.UserData;
import com.rizorsiumani.workondemanduser.ui.booking.MyCartItems;

import java.util.ArrayList;
import java.util.List;

public class TinyDbManager {

    public static final String KEY_CART = "key_cart";
    public static final String KEY_ADDRESS = "key_address";
    public static final String KEY_USER = "key_user";


    public static void saveCartData(MyCartItems data) {
        TinyDB tinyDB = new TinyDB(App.applicationContext);
        ArrayList<Object> previousItems = tinyDB.getListObject(KEY_CART, MyCartItems.class);
        previousItems.add(data);
        tinyDB.putListObject(KEY_CART, previousItems);
        Log.e("CART", "cart item saved !");
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
}
