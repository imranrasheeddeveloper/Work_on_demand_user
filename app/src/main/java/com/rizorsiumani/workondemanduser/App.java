package com.rizorsiumani.workondemanduser;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.rizorsiumani.workondemanduser.utils.Constants;

public class App extends Application {
    public static Context applicationContext = null;
    @Override
    public void onCreate() {
        super.onCreate();
        if (applicationContext == null) {
            applicationContext = this;
        }


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
}
