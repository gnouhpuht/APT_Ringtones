package com.optionringringtone.newringtonefree.mysetting.controlApp;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.NativeAd;
import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;
import com.optionringringtone.newringtonefree.mysetting.GetMyDataSystem;
import com.optionringringtone.newringtonefree.mysetting.HomeActivity2;
import com.optionringringtone.newringtonefree.mysetting.Settting1;

public class AdmodControl {

    public static InterstitialAd interstitial_adm_out;
    public static InterstitialAd interstitial_adm_in;
    public static final String IKEN_ADM_IN = "IKEN_ADM_IN";
    public static final String IKEN_ADM_OUT = "IKEN_ADM_OUT";
    public static HomeActivity2.AdCloseCallback mAdCloseCallback2 = null;

    public static void init(Context context) {
        MobileAds.initialize(context.getApplicationContext(), context.getResources().getString(R.string.app_id_ads));
    }

    public interface NativeAdmCallback {
        void loadSuscces(NativeAd nativeAd);

        void loadFail(String faild);
    }

    //---------IN--------------
//    public static void loadAndShowInADM() {
//        if (mContext != null) {
//            final String id_adm_btClick;
//            if (BuildConfig.DEBUG) {
//                id_adm_btClick = Constant.ID_ADS_ADM_Debug;
//
//            } else {
//                id_adm_btClick = Constant.ID_ADS_ADM_Interstitial_IN_BUTTON_CLICK;
//            }
//
//            final InterstitialAd interAdmIn_Click = new InterstitialAd(mContext);
//            interAdmIn_Click.setAdUnitId(id_adm_btClick);
//
//            loadAdm_Action(interAdmIn_Click, new CallBackAdmod() {
//                @Override
//                public void AdmodLoaded(InterstitialAd interAdmIn_Start) {
//                    showAdsInADM(interAdmIn_Click);
//                    Log.d(IKEN_ADM_IN, "[loadAndShowInADM] adm_loaded");
//                }
//
//                @Override
//                public void AdmodLoadedFail(String error) {
//                    Log.d(IKEN_ADM_IN, "[loadAndShowInADM] adm_fail: " + error);
//                    interstitial_adm_in = null;
//                }
//
//                @Override
//                public void AdmodDisplayed() {
//
//                    Log.d(IKEN_ADM_IN, "[loadAndShowInADM] adm_show");
//                }
//
//                @Override
//                public void AdmodClose() {
//                    Log.d(IKEN_ADM_IN, "[loadAndShowInADM] adm_close");
//                    interstitial_adm_in = null;
//                }
//            });
//        }
//    }

    public static void loadAdm_Action(Context mContext, final InterstitialAd mInterstitialAd_ADM, final CallBackAdmod mCallBackAdmod) {

        Log.d(IKEN_ADM_IN, "[loadAdm_Action] adm_start_load");
        Log.d(IKEN_ADM_IN, "[loadAdm_Action] load: " + mInterstitialAd_ADM.getAdUnitId());

        mInterstitialAd_ADM.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                if (mCallBackAdmod != null)
                    mCallBackAdmod.AdmodClose();
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                if (mCallBackAdmod != null)
                    mCallBackAdmod.AdmodLoadedFail("" + i);
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (mCallBackAdmod != null)
                    mCallBackAdmod.AdmodLoaded(mInterstitialAd_ADM);
            }


        });
        GetMyDataSystem.setTimeLastLoadAds(mContext);
        mInterstitialAd_ADM.loadAd(new AdRequest.Builder()
                .addTestDevice("D7E7ED86A3ACD0E6127D9C2598BE212B")
                .addTestDevice("D7E7ED86A3ACD0E6127D9C2598BE212B")
//                .addTestDevice(Constant.ID_DIRIVER_FOR_ADM2)
                .build());

    }

    public static void loadAdm_Center(final CallBackAdmod mCallBackAdmod, Context mContext) {
        if (mContext != null) {

            if (interstitial_adm_in == null || (!interstitial_adm_in.isLoading() && !interstitial_adm_in.isLoaded())) {

                final String id_adm_center;
                id_adm_center = GetMyDataSystem.getDataString(mContext, ConstantsAppNew.ADM_CENTER_KEY, ConstantsAppNew.ADM_START_KEY_Center);

                Log.d(IKEN_ADM_IN, "[loadAdm_Center] adm_start_load");
                Log.d(IKEN_ADM_IN, "[loadAdm_Center] load: " + id_adm_center);
//            if (interstitial_adm_in == null) Log.d(IKEN_ADM_IN, "[loadAdm_Center] 1");
//            else if (interstitial_adm_in.isLoading()) Log.d(IKEN_ADM_IN, "[loadAdm_Center] 2");
//            else if (interstitial_adm_in.isLoaded()) Log.d(IKEN_ADM_IN, "[loadAdm_Center] 3");

                interstitial_adm_in = new InterstitialAd(mContext);
                interstitial_adm_in.setAdUnitId(id_adm_center);
                interstitial_adm_in.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        if (mCallBackAdmod != null)
                            mCallBackAdmod.AdmodDisplayed();
                        super.onAdClosed();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        if (mCallBackAdmod != null)
                            mCallBackAdmod.AdmodLoadedFail("" + i);
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        if (mCallBackAdmod != null)
                            mCallBackAdmod.AdmodLoaded(interstitial_adm_in);
                    }


                });
                GetMyDataSystem.setTimeLastLoadAds(mContext);
                interstitial_adm_in.loadAd(
                        new AdRequest
                                .Builder()
//                                .addTestDevice(Constant.ID_DIRIVER_FOR_ADM)
//                                .addTestDevice(Constant.ID_DIRIVER_FOR_ADM2)
                                .build());
            } else {
                Log.d(IKEN_ADM_IN, "[loadAdm_Center] Can not to load ADM");
            }
        }
    }

    public static boolean showAdsInADM(InterstitialAd interstitial_adm_in) {
        if (interstitial_adm_in != null && interstitial_adm_in.isLoaded()) {
            interstitial_adm_in.show();
            return true;
        } else {
            return false;
        }
    }

    public static boolean getStartAds(InterstitialAd interAdmIn_start) {
        if (interAdmIn_start != null && interAdmIn_start.isLoaded()) {
            return true;
        } else {
            return false;
        }
    }

    //---------OUT-------------
    public static void loadAdsOutADMCallBack(Context context, final CallBackAdmod callBackAdmod) {
        if (interstitial_adm_out == null || (!interstitial_adm_out.isLoading() && !interstitial_adm_out.isLoaded())) {
            String id_adm_start;
            id_adm_start = GetMyDataSystem.getDataString(context, ConstantsAppNew.ADM_UT_KEY, ConstantsAppNew.ADM_UT_KEY_DEFAULT);
            Log.d(IKEN_ADM_OUT, "adm_start_load: " + id_adm_start);
            if (interstitial_adm_out == null) Log.d(IKEN_ADM_OUT, "1");
            else if (interstitial_adm_out.isLoading()) Log.d(IKEN_ADM_OUT, "2");
            else if (interstitial_adm_out.isLoaded()) Log.d(IKEN_ADM_OUT, "3");

            interstitial_adm_out = new InterstitialAd(context);

            interstitial_adm_out.setAdUnitId(id_adm_start);

            interstitial_adm_out.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    if (callBackAdmod != null)
                        callBackAdmod.AdmodDisplayed();
                    super.onAdClosed();
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    if (callBackAdmod != null)
                        callBackAdmod.AdmodLoadedFail("" + i);
                    super.onAdFailedToLoad(i);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    if (callBackAdmod != null)
                        callBackAdmod.AdmodLoaded(interstitial_adm_out);
                }
            });
            GetMyDataSystem.setTimeLastLoadAds(context);
            interstitial_adm_out.loadAd(
                    new AdRequest
                            .Builder()
                            .addTestDevice("D7E7ED86A3ACD0E6127D9C2598BE212B")
//                            .addTestDevice(Constant.ID_DIRIVER_FOR_ADM2)
                            .build());
        } else {
            if (interstitial_adm_out != null && interstitial_adm_out.isLoaded() && !HomeActivity2.alarmActivityShow) {
                callBackAdmod.AdmodLoaded(interstitial_adm_out);
            }
        }
    }

    public static boolean showAdsOutADM(HomeActivity2.AdCloseCallback adCloseCallback) {
        mAdCloseCallback2 = adCloseCallback;

        if (interstitial_adm_out != null && interstitial_adm_out.isLoaded()) {
            interstitial_adm_out.show();
            return true;
        }
        return false;
    }

    public static boolean showAdsStartADM(InterstitialAd mInterstitialAd, final Settting1.CallbackShow mCallbackShow) {

        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    mCallbackShow.Close();
                }
            });
            return true;
        }
        return false;
    }

    //------------------------------------------------------------------
    public interface CallBackAdmod {
        void AdmodLoaded(InterstitialAd interAdmIn_Start);

        void AdmodLoadedFail(String error);

        void AdmodDisplayed();

        void AdmodClose();

    }

}
