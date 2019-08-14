package com.optionringringtone.newringtonefree.mysetting;

import android.content.Context;
import android.content.SharedPreferences;

import com.optionringringtone.newringtonefree.mysetting.controlApp.ConstantsAppNew;

import static android.content.Context.MODE_PRIVATE;

public class GetMyDataSystem {
    public static void setDataString(Context mContext, String key, String value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(mContext.getPackageName(), MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getDataString(Context mContext, String key, String mdefault) {
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getPackageName(), MODE_PRIVATE);
        return prefs.getString(key, mdefault);
    }

    public static void setDataBoolean(Context mContext, String key, boolean value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(mContext.getPackageName(), MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getDataBoolean(Context mContext, String key, boolean mdefault) {
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getPackageName(), MODE_PRIVATE);
        return prefs.getBoolean(key, mdefault);
    }

    public static void setDataLong(Context mContext, String key, long value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(mContext.getPackageName(), MODE_PRIVATE).edit();
        editor.putLong(key, value);
        editor.apply();
    }


    public static long getDataLong(Context mContext, String key, long mdefault) {
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getPackageName(), MODE_PRIVATE);
        return prefs.getLong(key, mdefault);
    }


    public static void setDataInt(Context mContext, String key, int value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(mContext.getPackageName(), MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();
    }


    public static int getDataInt(Context mContext, String key, int mdefault) {
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getPackageName(), MODE_PRIVATE);
        return prefs.getInt(key, mdefault);
    }

    //---------------------------------------------------------

    public static long getTimeInstall(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getPackageName(), MODE_PRIVATE);
        long timeInstall = prefs.getLong(ConstantsAppNew.PRE_TIME_INSTALL_APP, 0);
        return timeInstall;
    }

    public static void setTimeInstall(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getPackageName(), MODE_PRIVATE);
        long timeInstall = prefs.getLong(ConstantsAppNew.PRE_TIME_INSTALL_APP, 0);
        if (timeInstall == 0) {
//            EventLog.LogEventAction(mContext, ConstantEventLog.EVENT_IN_APP, "NewUser", "true");

            SharedPreferences.Editor editor = prefs.edit();
            long nowTime = System.currentTimeMillis();
            editor.putLong(ConstantsAppNew.PRE_TIME_INSTALL_APP, nowTime);
            editor.apply();
        } else {
//            EventLog.LogEventAction(mContext, ConstantEventLog.EVENT_IN_APP, "OpenAgain", "true");
        }
    }

    public static long getTimeLastLoadAds(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getPackageName(), MODE_PRIVATE);
        long timeLAST_LOAD_ADS = prefs.getLong(ConstantsAppNew.PRE_TIME_LAST_LOAD_ADS, 0);
        return timeLAST_LOAD_ADS;
    }

    public static void setTimeLastLoadAds(Context mContext) {
        long nowTime = System.currentTimeMillis();
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getPackageName(), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(ConstantsAppNew.PRE_TIME_LAST_LOAD_ADS, nowTime);
        editor.apply();
    }

    public static String getTimeOldDay(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getPackageName(), MODE_PRIVATE);
        String timeSPACING_OUT = prefs.getString(ConstantsAppNew.PRE_TIME_OLD_DAY, "");
        return timeSPACING_OUT;
    }

    public static void setTimeOldDay(Context mContext, String timeOldDay) {
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getPackageName(), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ConstantsAppNew.PRE_TIME_OLD_DAY, timeOldDay);
        editor.apply();
    }

}
