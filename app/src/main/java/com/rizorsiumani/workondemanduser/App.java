package com.rizorsiumani.workondemanduser;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    public static Context applicationContext = null;
    @Override
    public void onCreate() {
        super.onCreate();
        if (applicationContext == null) {
            applicationContext = this;
        }
    }
}
