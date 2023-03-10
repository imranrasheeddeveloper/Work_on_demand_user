package com.rizorsiumani.user;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.rizorsiumani.user.utils.Constants;

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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("base_url")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Constants.BASE_URL = document.getData().get("base_url").toString();
                                Constants.IMG_PATH = document.getData().get("image_url").toString();
                                if (Constants.BASE_URL.isEmpty()){
                                    Constants.BASE_URL = "http://34.203.72.68:4000/";
                                    Constants.IMG_PATH = "http://34.203.72.68:4000";
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    }

    private void getDeviceToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("FCM TOKEN", "onComplete: Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Get new FCM registration token
                    Constants.constant.FCM_TOKEN = task.getResult();
                    Log.e("FCM TOKEN", Constants.constant.FCM_TOKEN);
                });
    }

//    public Socket getSocket() {
//        return mSocket;
//    }
}
