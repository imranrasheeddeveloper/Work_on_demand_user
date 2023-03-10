package com.rizorsiumani.user.data.local;



import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.rizorsiumani.user.App;
import com.rizorsiumani.user.utils.Constants;

public class PreferenceRepository {

    private static SharedPreferences preferences = null;

    private SharedPreferences getInstance() {
        if (preferences == null) {
            return preferences
                    = App.applicationContext.
                    getSharedPreferences(Constants.constant.APP_PREFERENCES, Context.MODE_PRIVATE);
        } else {
            return preferences;
        }
    }

    //repository set methods
    public void setString(@NonNull String key, @NonNull String value) {
        //use apply() instead of commit()
        getInstance().edit().putString(key, value).apply();
    }

    public void setBoolean(@NonNull String key, boolean value) {
        //use apply() instead of commit()
        getInstance().edit().putBoolean(key, value).apply();
    }

    public void setInt(@NonNull String key, int value) {
        //use apply() instead of commit()
        getInstance().edit().putInt(key, value).apply();
    }

    //repository get methods

    public String getString(@NonNull String key) {
        return getInstance().getString(key, "nil");
    }

    public boolean getBoolean(@NonNull String key) {
        return getInstance().getBoolean(key, false);
    }

    public int getInt(@NonNull String key) {
        return getInstance().getInt(key, -1);
    }



}
