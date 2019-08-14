package com.optionringringtone.newringtonefree.mysetting;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.facebook.ads.InterstitialAd;
import com.optionringringtone.newringtonefree.mysetting.controlApp.AdmodControl;
import com.optionringringtone.newringtonefree.mysetting.controlApp.ConstantsAppNew;
import com.optionringringtone.newringtonefree.mysetting.controlApp.ControlFirebase;
import com.optionringringtone.newringtonefree.mysetting.controlApp.FacebookControl;

import java.util.ArrayList;

public class AllSetting {

    public static boolean screenON = false;
    public static ArrayList<InterstitialAd> listAds = new ArrayList<>();

    public static void initApplication(Context mContext) {
        FacebookControl.init(mContext);
    }

    public static void initSetting(Context mContext) {

        if (AllSetting.listAds == null) AllSetting.listAds = new ArrayList<>();
        AllSetting.listAds.clear();
        AllSetting.listAds.add(FacebookControl.interstitialAd_fb_in);
        AllSetting.listAds.add(FacebookControl.interstitialAd_fb_out);

        AdmodControl.init(mContext);
        ControlFirebase.initSetup(mContext, new ControlFirebase.CallbackFirebaseControl() {
            @Override
            public void callBackmFbclid(String mFbclid) {

            }
        });

        GetMyDataSystem.setTimeInstall(mContext);
        initNotyfy(mContext);
        CheckAll.checkStartService(mContext);
    }


    private static void initNotyfy(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = ConstantsAppNew.CHANEL_FIREBASE_MES;
            String channelName = ConstantsAppNew.CHANEL_FIREBASE_MES;
            NotificationManager notificationManager =
                    context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_DEFAULT));
        }
    }


    public static void onDestroyAct() {
        for (int i = 0; i < listAds.size(); i++) {
            if (listAds.get(i) != null) listAds.get(i).destroy();
        }

        AdmodControl.interstitial_adm_in = null;
        AdmodControl.interstitial_adm_out = null;
        FacebookControl.interstitialAd_fb_in = null;
        FacebookControl.interstitialAd_fb_out = null;
    }
}
