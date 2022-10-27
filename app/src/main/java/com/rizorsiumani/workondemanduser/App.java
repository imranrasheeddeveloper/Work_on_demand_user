package com.rizorsiumani.workondemanduser;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.rizorsiumani.workondemanduser.utils.Constants;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class App extends Application {

    public static Context applicationContext = null;
    public static Socket mSocket;

    @Override
    public void onCreate() {
        super.onCreate();

        if (applicationContext == null) {
            applicationContext = this;
        }

//        try {
//            mSocket = IO.socket("");
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }


        getDeviceToken();

    }

    private void getDeviceToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("FCM TOKEN", "onComplete: Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Get new FCM registration token
                    Constants.FCM_TOKEN = task.getResult();
                    Log.e("FCM TOKEN", Constants.FCM_TOKEN);
                });
    }

//    public Socket getSocket() {
//        return mSocket;
//    }
}
