package com.rizorsiumani.workondemanduser.common;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.rizorsiumani.workondemanduser.utils.Configration;
import com.rizorsiumani.workondemanduser.utils.NotificationsUtils;

import org.json.JSONObject;

public class FirebaseCloudMessagingService extends FirebaseMessagingService {

    public static final MutableLiveData<String> deviceToken = new MutableLiveData<>();
    private static final String TAG = "FirebaseCloudMessagingS";
    private NotificationsUtils notificationUtils;

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        if (!s.isEmpty()) {
            deviceToken.postValue(s);
            Log.e(TAG, "onNewToken: " + s);
        } else {
            Log.e(TAG, "onNewToken: no new token");
        }
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e(TAG, "From: " + remoteMessage.getFrom());


        if (remoteMessage.getData().size() > 0) {
            final String notiType = remoteMessage.getData().get("type");
            Log.e(TAG, "Data Payload: " + notiType);

            if (notiType != null) {
                if (notiType.equals("new_message")) {
                    Intent intent = new Intent(Configration.CHAT_MSG_NOTIFICATION);
                    intent.putExtra("type", remoteMessage.getData().get("type"));
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

                    Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
                    handleNotification(remoteMessage.getNotification().getBody(), remoteMessage.getData().get("type"));
                }
            }

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message, String logoutType) {
        if (!NotificationsUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Configration.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            pushNotification.putExtra("type", logoutType);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

//             play notification sound
            NotificationsUtils NotificationsUtils = new NotificationsUtils(getApplicationContext());
            NotificationsUtils.playNotificationSound();
        } else {
            // If the app is in background, firebase itself handles the notification
            Log.e(TAG, "handleNotification: app is in background");
        }
    }//handleNotification


    private void handleDataMessage(JSONObject json) {
    }


}
