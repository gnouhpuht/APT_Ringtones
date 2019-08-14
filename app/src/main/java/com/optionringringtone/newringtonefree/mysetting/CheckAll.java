package com.optionringringtone.newringtonefree.mysetting;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.BuildConfig;
import com.optionringringtone.newringtonefree.mysetting.controlApp.AdmodControl;
import com.optionringringtone.newringtonefree.mysetting.controlApp.ConstantsAppNew;
import com.optionringringtone.newringtonefree.mysetting.controlApp.FacebookControl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CheckAll {
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        boolean isConnect = (netInfo != null && netInfo.isConnected());
        if (!isConnect) Log.d("IKEN_OUT NOT LOAD", "isConnected");
        return isConnect;
    }

    public static boolean CheckShowAdsOUT(Context mContext) {
//        return true;
        CheckScreenOnOFF(mContext);
        if (AllSetting.screenON
                && GetMyDataSystem.getDataBoolean(mContext, ConstantsAppNew.CAN_UT, false)
                && isConnected(mContext)
                && CheckTimeCanStartShowOUT(mContext)
                && CheckTimeCanLoadOUT(mContext)
                && CheckNumberCurentShowAdsOUT(mContext)) {
            return true;
        } else {
            return false;
        }
    }

    public static void CheckScreenOnOFF(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            boolean isScreenOn = pm.isInteractive();
            AllSetting.screenON = isScreenOn;
        } else {
            boolean isScreenOn = pm.isScreenOn();
            AllSetting.screenON = isScreenOn;
        }
        Log.d("IKEN_CHECK", "screenON ? = " + AllSetting.screenON);

    }

    public static boolean CheckShowAdsIN(Context mContext) {
        if (isConnected(mContext)
                && CheckTimeCanLoadIN(mContext)
                && (!FacebookControl.isLoadingFbIn)
                && ((FacebookControl.interstitialAd_fb_in == null) || (FacebookControl.interstitialAd_fb_in.isAdInvalidated()) || (!FacebookControl.interstitialAd_fb_in.isAdLoaded()))
                && ((AdmodControl.interstitial_adm_in == null) || (!AdmodControl.interstitial_adm_in.isLoading()) || (!AdmodControl.interstitial_adm_in.isLoaded()))
//                && (ControlAdsInMobi.mInMobiInterstitial_in == null)
        ) {
            return true;
        } else
            return false;
    }

    public static boolean CheckTimeCanStartShowOUT(Context context) {
        long timeCanShowAdsOUT;
        if (BuildConfig.DEBUG) {
            Log.e(ConstantsAppNew.TAG, "setA: is DEBUG 2");
            timeCanShowAdsOUT = GetMyDataSystem.getDataLong(context, ConstantsAppNew.TIME_CAN_SHOW_UT_DEBUG, ConstantsAppNew.TIME_CAN_SHOW_UT_DEBUG_DEFAULT);
        } else {
            Log.e(ConstantsAppNew.TAG, "setA: is NOT DEBUG 2");
            timeCanShowAdsOUT = GetMyDataSystem.getDataLong(context, ConstantsAppNew.TIME_CAN_SHOW_UT, ConstantsAppNew.TIME_CAN_SHOW_UT_DEFAULT);
        }
        long timeInstall = GetMyDataSystem.getTimeInstall(context);
        long nowTime = System.currentTimeMillis();

        if (nowTime - timeInstall > timeCanShowAdsOUT) {
            return true;
        }
        Log.d("IKEN_OUT NOT LOAD", "CheckTimeCanStartShowOUT");
        return false;
    }

    public static boolean CheckTimeCanLoadIN(Context context) {
        long nowTime = System.currentTimeMillis();
        long timeSpacingLoadIN = GetMyDataSystem.getDataLong(context, ConstantsAppNew.TIME_SPACING_LOAD_IN, ConstantsAppNew.TIME_SPACING_LOAD_IN_DEFAULT);
        Log.d("CheckTimeCanLoadIN", "CheckTimeCanLoadIN: "+timeSpacingLoadIN);
        long lastTimeLoad = GetMyDataSystem.getTimeLastLoadAds(context);
        if (nowTime - lastTimeLoad > timeSpacingLoadIN) {
            return true;
        }
        return false;
    }

    public static boolean CheckTimeCanLoadOUT(Context context) {
        long nowTime = System.currentTimeMillis();
        if (BuildConfig.DEBUG) {
            Log.e(ConstantsAppNew.TAG, "setA: is DEBUG 3");
            long timeSpacingLoadOUT = GetMyDataSystem.getDataLong(context, ConstantsAppNew.TIME_SPACING_LOAD_UT_DEGBUG, ConstantsAppNew.TIME_SPACING_LOAD_UT_DEGBUG_DEFAULT);
            long lastTimeLoad = GetMyDataSystem.getTimeLastLoadAds(context);
            if (nowTime - lastTimeLoad > timeSpacingLoadOUT) {
                return true;
            }

            Log.d("IKEN_OUT NOT LOAD", "CheckTimeCanLoadOUT");
            return false;
        } else {
            Log.e(ConstantsAppNew.TAG, "setA: is NOT DEBUG 3");
            long timeSpacingLoadOUT = GetMyDataSystem.getDataLong(context, ConstantsAppNew.TIME_SPACING_LOAD_UT, ConstantsAppNew.TIME_SPACING_LOAD_UT_DEFAULT);
            long lastTimeLoad = GetMyDataSystem.getTimeLastLoadAds(context);
            if (nowTime - lastTimeLoad > timeSpacingLoadOUT) {
                return true;
            }

            Log.d("IKEN_OUT NOT LOAD", "CheckTimeCanLoadOUT");
            return false;
        }
    }

    public static boolean CheckNewDay(Context context) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
        String nowDay = sdf.format(currentTime);
        String old_day = GetMyDataSystem.getTimeOldDay(context);

        if (nowDay.equals(old_day)) {
            return false;
        }
        GetMyDataSystem.setTimeOldDay(context, nowDay);
        return true;
    }

    public static boolean CheckNumberCurentShowAdsOUT(Context context) {
        if (CheckNewDay(context)) {
            GetMyDataSystem.setDataInt(context, ConstantsAppNew.CURENT_SHOWED_ADS, 0);
            return true;
        } else {
            int CURENT_SHOW_ADS;
            if (BuildConfig.DEBUG) {
                Log.e(ConstantsAppNew.TAG, "setA: is DEBUG 4");
                CURENT_SHOW_ADS = 1000;
            } else {
                Log.e(ConstantsAppNew.TAG, "setA: is NOT DEBUG 4");
                CURENT_SHOW_ADS = GetMyDataSystem.getDataInt(context, ConstantsAppNew.CURENT_SHOW_ADS, ConstantsAppNew.CURENT_SHOW_ADS_DEFAULT);
            }
            if (GetMyDataSystem.getDataInt(context, ConstantsAppNew.CURENT_SHOWED_ADS, 0) >= CURENT_SHOW_ADS) {
                Log.d("IKEN_OUT NOT LOAD", "CheckNumberCurentShowAdsOUT");
                return false;
            } else {
                return true;
            }
        }
    }

    public static boolean CheckServiceRunning(Class<?> serviceClass, Context context) {
//        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        for (ActivityManager.RunningServiceInfo servicefolder : manager.getRunningServices(Integer.MAX_VALUE)) {
//            if (serviceClass.getName().equals(servicefolder.servicefolder.getClassName())) {
//                Log.d("IKEN_SV", "running");
//                return true;
//            }
//        }
//        Log.d("IKEN_SV", "not running");

        return HomeServicePlayRing2.serviceRuning;
    }

    public static void checkStartService(final Context context) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
        try {
            if (!CheckAll.CheckServiceRunning(HomeServicePlayRing2.class, context)) {
//                HomeServicePlayRing2.enqueueWork(context, new Intent());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(new Intent(context, HomeServicePlayRing2.class));
                } else {
                    context.startService(new Intent(context, HomeServicePlayRing2.class));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            new CountDownTimer(1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    final NotificationManager notificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        notificationManager.deleteNotificationChannel(ConstantsAppNew.CHANEL_ID);
                    }
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


//        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public static boolean checkLocationPermission(Context mContext) {
        if (mContext != null) {
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    ActivityCompat.requestPermissions((Activity) mContext,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);

                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions((Activity) mContext,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                }
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
