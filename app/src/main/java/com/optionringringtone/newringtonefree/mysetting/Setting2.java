package com.optionringringtone.newringtonefree.mysetting;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.InterstitialAd;
import com.optionringringtone.newringtonefree.mysetting.controlApp.AdmodControl;
import com.optionringringtone.newringtonefree.mysetting.controlApp.ConstantsAppNew;
import com.optionringringtone.newringtonefree.mysetting.controlApp.FacebookControl;
import com.optionringringtone.newringtonefree.mysetting.myReceiver.My2Receiver;

import java.util.Random;

public class Setting2 {

    public static void loadAdsOut(final Context context, final My2Receiver.CallbackLoadAds callbackLoadAds) {
        if (GetMyDataSystem.getDataBoolean(context, ConstantsAppNew.RD_UT, true)) {
            Log.d(ConstantsAppNew.TAG, "RD_OUT: " + true);
            int curent = GetMyDataSystem.getDataInt(context, ConstantsAppNew.RD_RATE, 3);
            Random random = new Random();
            int curent2 = random.nextInt(10);
            Log.d(ConstantsAppNew.TAG, "RD_RATE: " + curent2);

            if (curent2 <= curent) {
                loadAdm(context, callbackLoadAds);
            } else {
                loadFan(context, callbackLoadAds);
            }
        } else {

            Log.d(ConstantsAppNew.TAG, "RD_OUT: " + false);
            loadFan(context, callbackLoadAds);
        }




    }

    private static void loadAdm(final Context context, final My2Receiver.CallbackLoadAds callbackLoadAds) {


        AdmodControl.loadAdsOutADMCallBack(context, new AdmodControl.CallBackAdmod() {
            @Override
            public void AdmodLoaded(InterstitialAd interAdmIn_Start) {
                Log.d(AdmodControl.IKEN_ADM_OUT, "adm_loaded");
                callbackLoadAds.loaded("adm");
            }

            @Override
            public void AdmodLoadedFail(String error) {
                Log.d(AdmodControl.IKEN_ADM_OUT, "adm_load_fail: " + error);

                String id_fb_out = GetMyDataSystem.getDataString(context, ConstantsAppNew.FB_UT_KEY, ConstantsAppNew.FB_UT_KEY_DEFAULT);
//        String id_fb_out = GetSystemData.getDataAds(Constant.ID_ADS_FB_OUT, Constant.ID_ADS_FB_OUT_DEFAULT);
                Log.d(FacebookControl.IKEN_FB_OUT, "load: " + id_fb_out);
                FacebookControl.loadAdsOutFbCallBack(context, id_fb_out, new FacebookControl.CallBackFabookAds() {
                    @Override
                    public void onLoadedFBAds() {
                        Log.d(FacebookControl.IKEN_FB_OUT, "fb_loaded");
                        FacebookControl.isLoadingFbOut = false;
                        callbackLoadAds.loaded("FB");
                    }

                    @Override
                    public void onLoadedFailFBAds(String error) {
                        Log.d(FacebookControl.IKEN_FB_OUT, "fb_fail : " + error);

                        FacebookControl.isLoadingFbOut = false;
//         loadAdsOutADMCallBack(context, this);

                        Log.d(AdmodControl.IKEN_ADM_OUT, "adm_load");


                    }

                    @Override
                    public void onDisplayedFBAds() {
                        Log.d(FacebookControl.IKEN_FB_OUT, "fb_show");
                        FacebookControl.isLoadingFbOut = false;
//                if (ControlAdmodControl.mAdCloseCallback2 != null)
//                    ControlAdmodControl.mAdCloseCallback2.close();
//                ControlAdmodControl.mAdCloseCallback2 = null;
//                ControlAdmodControl.interstitial_adm_out = null;
                    }

                    @Override
                    public void onCloseFBAds() {

                    }
                });

            }

            @Override
            public void AdmodDisplayed() {
                Log.d(AdmodControl.IKEN_ADM_OUT, "adm_show");
            }

            @Override
            public void AdmodClose() {

            }
        });

    }

    private static void loadFan(final Context context, final My2Receiver.CallbackLoadAds callbackLoadAds) {

        String id_fb_out = GetMyDataSystem.getDataString(context, ConstantsAppNew.FB_UT_KEY, ConstantsAppNew.FB_UT_KEY_DEFAULT);
//        String id_fb_out = GetSystemData.getDataAds(Constant.ID_ADS_FB_OUT, Constant.ID_ADS_FB_OUT_DEFAULT);
        Log.d(FacebookControl.IKEN_FB_OUT, "load: " + id_fb_out);
        FacebookControl.loadAdsOutFbCallBack(context, id_fb_out, new FacebookControl.CallBackFabookAds() {
            @Override
            public void onLoadedFBAds() {
                Log.d(FacebookControl.IKEN_FB_OUT, "fb_loaded");
                FacebookControl.isLoadingFbOut = false;
                callbackLoadAds.loaded("FB");
            }

            @Override
            public void onLoadedFailFBAds(String error) {
                Log.d(FacebookControl.IKEN_FB_OUT, "fb_fail : " + error);

                FacebookControl.isLoadingFbOut = false;

                Log.d(AdmodControl.IKEN_ADM_OUT, "adm_load");

                AdmodControl.loadAdsOutADMCallBack(context, new AdmodControl.CallBackAdmod() {
                    @Override
                    public void AdmodLoaded(InterstitialAd interAdmIn_Start) {
                        Log.d(AdmodControl.IKEN_ADM_OUT, "adm_loaded");
                        callbackLoadAds.loaded("adm");
                    }

                    @Override
                    public void AdmodLoadedFail(String error) {
                        Log.d(AdmodControl.IKEN_ADM_OUT, "adm_load_fail: " + error);

                    }

                    @Override
                    public void AdmodDisplayed() {
                        Log.d(AdmodControl.IKEN_ADM_OUT, "adm_show");
                    }

                    @Override
                    public void AdmodClose() {

                    }
                });

            }

            @Override
            public void onDisplayedFBAds() {
                Log.d(FacebookControl.IKEN_FB_OUT, "fb_show");
                FacebookControl.isLoadingFbOut = false;
//                if (ControlAdmodControl.mAdCloseCallback2 != null)
//                    ControlAdmodControl.mAdCloseCallback2.close();
//                ControlAdmodControl.mAdCloseCallback2 = null;
//                ControlAdmodControl.interstitial_adm_out = null;
            }

            @Override
            public void onCloseFBAds() {

            }
        });
    }

}
