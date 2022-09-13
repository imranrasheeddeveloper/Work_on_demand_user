package com.rizorsiumani.workondemanduser.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


public class ActivityUtil {

    public static void gotoPage(Context context, Class<?> activityClass) {
        context.startActivity(new Intent(context, activityClass));
    }

    public static void gotoPage(Context context, Class<?> activityClass, Bundle bundle) {
        Intent intent = new Intent(context, activityClass);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    public static void gotoPage(Context context, Class<?> activityClass, int flags) {
        Intent intent = new Intent(context, activityClass);
        intent.addFlags(flags);
        context.startActivity(intent);
    }

}
