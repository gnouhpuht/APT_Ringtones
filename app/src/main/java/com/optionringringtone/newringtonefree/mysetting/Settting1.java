package com.optionringringtone.newringtonefree.mysetting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.InterstitialAd;
import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;
import com.optionringringtone.newringtonefree.mysetting.controlApp.AdmodControl;
import com.optionringringtone.newringtonefree.mysetting.controlApp.ConstantsAppNew;
import com.optionringringtone.newringtonefree.mysetting.controlApp.FacebookControl;

import java.util.Random;

public class Settting1 {


    private static void loadStartWithFacebook(final Context mContext, final CallbackLoad mCallBackLoad, final CallbackShow mCallbackShow) {

        String id_fb = GetMyDataSystem.getDataString(mContext, ConstantsAppNew.FB_START_KEY, ConstantsAppNew.FB_START_KEY_DEFAULT);
        final com.facebook.ads.InterstitialAd mInterstitialAd_Start = new com.facebook.ads.InterstitialAd(mContext, id_fb);
        FacebookControl.loadFB_Action(mContext, mInterstitialAd_Start, new FacebookControl.CallBackFabookAds() {
            @Override
            public void onLoadedFBAds() {
                Log.d(FacebookControl.IKEN_FB_IN, "[loadAndShowStartApp] fb_loaded");
//                    disableDialog(dialogLoading);
                if (mCallBackLoad != null) mCallBackLoad.Loaded(mInterstitialAd_Start);
            }

            @Override
            public void onLoadedFailFBAds(String error) {
                Log.d(FacebookControl.IKEN_FB_IN, "[loadAndShowStartApp] fb_fail: " + error);
                if (mInterstitialAd_Start != null) mInterstitialAd_Start.destroy();
//                if (mCallBackLoad != null) mCallBackLoad.LoadedFailAds();

//                Log.d(AdsAppota.IKEN_APP_IN, "[loadAndShowStartApp] app_start_load");
//                AdsAppota.loadAdsAppStart(mContext, new AdsAppota.CallBackAdsApp() {
//                    @Override
//                    public void onAdClosed() {
//                        mCallbackShow.Close();
//                    }
//
//                    @Override
//                    public void onAdLoaded() {
//                        Log.d(AdsAppota.IKEN_APP_IN, "[loadAndShowStartApp] app_loaded");
//                        if (mCallBackLoad != null) mCallBackLoad.Loaded();
//
//                    }
//
//                    @Override
//                    public void onAdFailedToLoad(int i) {
//
//                        Log.d(AdsAppota.IKEN_APP_IN, "[loadAndShowStartApp] app_Fail " + i);
//                        if (mCallBackLoad != null) mCallBackLoad.LoadedFailAds();
//                    }
//                });
                String id_adm;
                id_adm = GetMyDataSystem.getDataString(mContext, ConstantsAppNew.ADM_START_KEY, ConstantsAppNew.ADM_START_KEY_DEFAULT);

                final InterstitialAd interAdmIn_Start = new InterstitialAd(mContext);
                interAdmIn_Start.setAdUnitId(id_adm);
                AdmodControl.loadAdm_Action(mContext, interAdmIn_Start, new AdmodControl.CallBackAdmod() {
                    @Override
                    public void AdmodLoaded(InterstitialAd interAdmIn_Start) {

                        Log.d(AdmodControl.IKEN_ADM_IN, "[loadAndShowStartApp] adm_Loaded: ");
                        if (mCallBackLoad != null) mCallBackLoad.Loaded2(interAdmIn_Start);
                    }

                    @Override
                    public void AdmodLoadedFail(String error) {
                        Log.d(AdmodControl.IKEN_ADM_IN, "[loadAndShowStartApp] adm_fail: " + error);
                        if (mCallBackLoad != null) mCallBackLoad.LoadedFailAds();
                    }

                    @Override
                    public void AdmodDisplayed() {

                    }

                    @Override
                    public void AdmodClose() {
                        Log.d(AdmodControl.IKEN_ADM_IN, "[loadAndShowStartApp] close");
                        mCallbackShow.Close();

                    }
                });
            }

            @Override
            public void onDisplayedFBAds() {
                Log.d(FacebookControl.IKEN_FB_IN, "[loadAndShowStartApp] fb_show");
                if (mInterstitialAd_Start != null) mInterstitialAd_Start.destroy();
            }

            @Override
            public void onCloseFBAds() {
                mCallbackShow.Close();
                if (mInterstitialAd_Start != null) mInterstitialAd_Start.destroy();
            }
        });
    }

    public static void loadStartWithAdm(final Context mContext, final CallbackLoad mCallBackLoad, final CallbackShow mCallbackShow) {

        String id_adm;
//        if (BuildConfig.DEBUG) {
        id_adm = GetMyDataSystem.getDataString(mContext, ConstantsAppNew.ADM_START_KEY, ConstantsAppNew.ADM_START_KEY_DEFAULT);
//        } else {
//            id_adm = ConstantsAppNew.ADM_START_KEY_DEFAULT;
//        }
        final InterstitialAd interAdmIn_Start = new InterstitialAd(mContext);
        interAdmIn_Start.setAdUnitId(id_adm);
        AdmodControl.loadAdm_Action(mContext, interAdmIn_Start, new AdmodControl.CallBackAdmod() {
            @Override
            public void AdmodLoaded(InterstitialAd interAdmIn_Start) {

                Log.d(AdmodControl.IKEN_ADM_IN, "[loadAndShowStartApp] adm_Loaded: ");
                if (mCallBackLoad != null) mCallBackLoad.Loaded2(interAdmIn_Start);
            }

            @Override
            public void AdmodLoadedFail(String error) {
                Log.d(AdmodControl.IKEN_ADM_IN, "[loadAndShowStartApp] adm_fail: " + error);

//                Log.d(FacebookControl.IKEN_FB_IN, "[loadAndShowStartApp] fb_fail: " + error);
//                if (interAdmIn_Start != null) interAdmIn_Start.destroy();
//                if (mCallBackLoad != null) mCallBackLoad.LoadedFailAds();

                /*Log.d(AdsAppota.IKEN_APP_IN, "[loadAndShowStartApp] app_start_load");
                AdsAppota.loadAdsAppStart(mContext, new AdsAppota.CallBackAdsApp() {
                    @Override
                    public void onAdClosed() {
                        mCallbackShow.Close();
                    }

                    @Override
                    public void onAdLoaded() {
                        Log.d(AdsAppota.IKEN_APP_IN, "[loadAndShowStartApp] app_loaded");
                        if (mCallBackLoad != null) mCallBackLoad.Loaded();

                    }

                    @Override
                    public void onAdFailedToLoad(int i) {

                        Log.d(AdsAppota.IKEN_APP_IN, "[loadAndShowStartApp] app_Fail " + i);
                        if (mCallBackLoad != null) mCallBackLoad.LoadedFailAds();
                    }
                });*/

                String id_fb = GetMyDataSystem.getDataString(mContext, ConstantsAppNew.FB_START_KEY, ConstantsAppNew.FB_START_KEY_DEFAULT);
                final com.facebook.ads.InterstitialAd mInterstitialAd_Start = new com.facebook.ads.InterstitialAd(mContext, id_fb);
                FacebookControl.loadFB_Action(mContext, mInterstitialAd_Start, new FacebookControl.CallBackFabookAds() {
                    @Override
                    public void onLoadedFBAds() {
                        Log.d(FacebookControl.IKEN_FB_IN, "[loadAndShowStartApp] fb_loaded");
//                    disableDialog(dialogLoading);
                        if (mCallBackLoad != null) mCallBackLoad.Loaded(mInterstitialAd_Start);
                    }

                    @Override
                    public void onLoadedFailFBAds(String error) {
                        Log.d(FacebookControl.IKEN_FB_IN, "[loadAndShowStartApp] fb_fail: " + error);
                        if (mInterstitialAd_Start != null) mInterstitialAd_Start.destroy();
                        if (mCallBackLoad != null) mCallBackLoad.LoadedFailAds();

                    }

                    @Override
                    public void onDisplayedFBAds() {
                        Log.d(FacebookControl.IKEN_FB_IN, "[loadAndShowStartApp] fb_show");
                        if (mInterstitialAd_Start != null) mInterstitialAd_Start.destroy();
                    }

                    @Override
                    public void onCloseFBAds() {
                        mCallbackShow.Close();
                        if (mInterstitialAd_Start != null) mInterstitialAd_Start.destroy();
                    }
                });
            }

            @Override
            public void AdmodDisplayed() {

            }

            @Override
            public void AdmodClose() {

                mCallbackShow.Close();
                Log.d(AdmodControl.IKEN_ADM_IN, "[loadAndShowStartApp] close");

            }
        });

    }

    public static boolean loadAndShowStartApp(final Context mContext, final CallbackLoad mCallBackLoad,
                                              final CallbackShow mCallbackShow) {
        if (!CheckAll.isConnected(mContext)) {
            new AlertDialog.Builder(mContext)
                    .setTitle(mContext.getResources().getString(R.string.no_connect_internet))
                    .setMessage(mContext.getResources().getString(R.string.check_your_internet2))
                    .setIcon(R.drawable.ic_sad)
                    .show();

            return false;
        }

        if (mContext != null && !((Activity) mContext).isFinishing()) {

            if (GetMyDataSystem.getDataBoolean(mContext, ConstantsAppNew.RD_START, true)) {
                Log.d(ConstantsAppNew.TAG, "RD_START: " + true);
                int curent = GetMyDataSystem.getDataInt(mContext, ConstantsAppNew.RD_RATE, 3);
                Random random = new Random();
                int curent2 = random.nextInt(10);
                Log.d(ConstantsAppNew.TAG, "RD_RATE: " + curent2);

                if (curent2 <= curent) {
                    loadStartWithAdm(mContext, mCallBackLoad, mCallbackShow);
                } else {
                    loadStartWithFacebook(mContext, mCallBackLoad, mCallbackShow);
                }
            } else {

                Log.d(ConstantsAppNew.TAG, "RD_START: " + false);
                loadStartWithFacebook(mContext, mCallBackLoad, mCallbackShow);
            }
            return true;
        }
        return false;
//        long timeNow = System.currentTimeMillis();
//        if (timeNow - timeClick > 10000) {
//            timeClick = timeNow;
//        } else {
//            return;
//        }

//        switch (type) {
//            case 0:
//
//                if (mContext != null && !((Activity) mContext).isFinishing()) {
//
//                    String id_fb = GetMyDataSystem.getDataString(mContext, ConstantsAppNew.FB_START_KEY, ConstantsAppNew.FB_START_KEY_DEFAULT);

//
////            Log.e(FacebookControl.IKEN_FB_IN, "[loadAndShowStartApp] load: " + id_fb);
//
////            dialogLoading.setMessage("Refresh data...");
////            dialogLoading.setIndeterminate(false);
////            dialogLoading.setCanceledOnTouchOutside(false);
////            dialogLoading.show();
//
//
//                    final com.facebook.ads.InterstitialAd mInterstitialAd_Start = new com.facebook.ads.InterstitialAd(mContext, id_fb);

//                    FacebookControl.loadFB_Action(mContext, mInterstitialAd_Start, new FacebookControl.CallBackFabookAds() {
//                        @Override
//                        public void onLoadedFBAds() {
//                            Log.d(FacebookControl.IKEN_FB_IN, "[loadAndShowStartApp] fb_loaded");
////                    disableDialog(dialogLoading);
//                            if (mCallBackLoad != null) mCallBackLoad.Loaded(mInterstitialAd_Start);
//                        }
//
//                        @Override
//                        public void onLoadedFailFBAds(String error) {
//                            Log.d(FacebookControl.IKEN_FB_IN, "[loadAndShowStartApp] fb_fail: " + error);
//                            if (mInterstitialAd_Start != null) mInterstitialAd_Start.destroy();
//                            if (mCallBackLoad != null) mCallBackLoad.LoadedFailFB(error);
//                            // load plan 2
////
////            Log.d(AdmodControl.IKEN_ADM_IN, "[loadAndShowStartApp] adm_start_load");

//                        }
//
//                        @Override
//                        public void onDisplayedFBAds() {
//                            Log.d(FacebookControl.IKEN_FB_IN, "[loadAndShowStartApp] fb_show");
//                            if (mInterstitialAd_Start != null) mInterstitialAd_Start.destroy();
//                        }
//
//                        @Override
//                        public void onCloseFBAds() {
//                            if (mInterstitialAd_Start != null) mInterstitialAd_Start.destroy();
//                        }
//                    });

//                }

//
//                break;
//            case 1:
//                break;
//        }
    }


    private static boolean is_loadIn = false;

    public static void startLoadAdsCenter(final Context mContext) {
//        Setting.initFB(context);


        is_loadIn = true;
        if (GetMyDataSystem.getDataBoolean(mContext, ConstantsAppNew.RD_IN, true)) {
            Log.d(ConstantsAppNew.TAG, "RD_IN: " + true);
            int curent = GetMyDataSystem.getDataInt(mContext, ConstantsAppNew.RD_RATE, 3);
            Random random = new Random();
            int curent2 = random.nextInt(10);
            Log.d(ConstantsAppNew.TAG, "RD_RATE: " + curent2);


            if (curent2 <= curent) {

                Log.d(AdmodControl.IKEN_ADM_IN, "[startLoadAdsCenter] adm_start_load");
                AdmodControl.loadAdm_Center(new AdmodControl.CallBackAdmod() {
                    @Override
                    public void AdmodLoaded(InterstitialAd interAdmIn_Start) {
                        is_loadIn = false;
                        Log.d(AdmodControl.IKEN_ADM_IN, "[startLoadAdsCenter] adm_loaded");

                    }

                    @Override
                    public void AdmodLoadedFail(String error) {
                        Log.d(AdmodControl.IKEN_ADM_IN, "[startLoadAdsCenter] adm_fail: " + error);
                        AdmodControl.interstitial_adm_in = null;

                        FacebookControl.loadFb_Center_CallBack(mContext, new FacebookControl.CallBackFabookAds() {
                            @Override
                            public void onLoadedFBAds() {
                                is_loadIn = false;
                                Log.d(FacebookControl.IKEN_FB_IN, "[startLoadAdsCenter] fb_loaded");
                                FacebookControl.isLoadingFbIn = false;
                            }

                            @Override
                            public void onLoadedFailFBAds(String error) {
                                is_loadIn = false;
                                Log.d(FacebookControl.IKEN_FB_IN, "[startLoadAdsCenter] fb_fail : " + error);
                                FacebookControl.isLoadingFbIn = false;
                            }

                            @Override
                            public void onDisplayedFBAds() {
                                Log.d(FacebookControl.IKEN_FB_IN, "[startLoadAdsCenter] fb_show");
//                              AdmodControl.interstitial_adm_in = null;
                                FacebookControl.isLoadingFbIn = false;
                            }

                            @Override
                            public void onCloseFBAds() {
//                                startLoadAdsCenter(mContext);
                            }
                        });

                    }

                    @Override
                    public void AdmodDisplayed() {

                    }

                    @Override
                    public void AdmodClose() {
                        AdmodControl.interstitial_adm_in = null;
                    }
                }, mContext);

//                Log.d(AdsAppota.IKEN_APP_IN, "[startLoadAdsCenter] app_start_load");
//                AdsAppota.loadAdsAppIN(mContext, new AdsAppota.CallBackAdsApp() {
//                    @Override
//                    public void onAdClosed() {
////                        startLoadAdsCenter(mContext);
//                    }
//
//                    @Override
//                    public void onAdLoaded() {
//                        is_loadIn = false;
//                        Log.d(AdsAppota.IKEN_APP_IN, "[startLoadAdsCenter] app_loaded");
//                    }
//
//                    @Override
//                    public void onAdFailedToLoad(int i) {
//
//                        Log.d(AdsAppota.IKEN_APP_IN, "[startLoadAdsCenter] app_Fail " + i);
//
//                        FacebookControl.loadFb_Center_CallBack(mContext, new FacebookControl.CallBackFabookAds() {
//                            @Override
//                            public void onLoadedFBAds() {
//                                is_loadIn = false;
//                                Log.d(FacebookControl.IKEN_FB_IN, "[startLoadAdsCenter] fb_loaded");
//                                FacebookControl.isLoadingFbIn = false;
//                            }
//
//                            @Override
//                            public void onLoadedFailFBAds(String error) {
//                                is_loadIn = false;
//                                Log.d(FacebookControl.IKEN_FB_IN, "[startLoadAdsCenter] fb_fail : " + error);
//                                FacebookControl.isLoadingFbIn = false;
//                            }
//
//                            @Override
//                            public void onDisplayedFBAds() {
//                                Log.d(FacebookControl.IKEN_FB_IN, "[startLoadAdsCenter] fb_show");
////                              AdmodControl.interstitial_adm_in = null;
//                                FacebookControl.isLoadingFbIn = false;
//                            }
//
//                            @Override
//                            public void onCloseFBAds() {
////                                startLoadAdsCenter(mContext);
//                            }
//                        });
//                    }
//                });
                return;
            }
        } else Log.d(ConstantsAppNew.TAG, "RD_IN: " + false);

        FacebookControl.loadFb_Center_CallBack(mContext, new FacebookControl.CallBackFabookAds() {
            @Override
            public void onLoadedFBAds() {
                is_loadIn = false;
                Log.d(FacebookControl.IKEN_FB_IN, "[startLoadAdsCenter] fb_loaded");
                FacebookControl.isLoadingFbIn = false;
            }

            @Override
            public void onLoadedFailFBAds(String error) {
                Log.d(FacebookControl.IKEN_FB_IN, "[startLoadAdsCenter] fb_fail : " + error);
                FacebookControl.isLoadingFbIn = false;
                Log.d(AdmodControl.IKEN_ADM_IN, "[startLoadAdsCenter] adm_start_load");
                AdmodControl.loadAdm_Center(new AdmodControl.CallBackAdmod() {
                    @Override
                    public void AdmodLoaded(InterstitialAd interAdmIn_Start) {
                        is_loadIn = false;
                        Log.d(AdmodControl.IKEN_ADM_IN, "[startLoadAdsCenter] adm_loaded");

                    }

                    @Override
                    public void AdmodLoadedFail(String error) {
                        is_loadIn = false;
                        Log.d(AdmodControl.IKEN_ADM_IN, "[startLoadAdsCenter] adm_fail: " + error);
                        AdmodControl.interstitial_adm_in = null;
                    }

                    @Override
                    public void AdmodDisplayed() {

                    }

                    @Override
                    public void AdmodClose() {
                        AdmodControl.interstitial_adm_in = null;
                    }
                }, mContext);

//                Log.d(AdsAppota.IKEN_APP_IN, "[startLoadAdsCenter] app_start_load");
//                AdsAppota.loadAdsAppIN(mContext, new AdsAppota.CallBackAdsApp() {
//                    @Override
//                    public void onAdClosed() {
////                        startLoadAdsCenter(mContext);
//                    }
//
//                    @Override
//                    public void onAdLoaded() {
//                        is_loadIn = false;
//                        Log.d(AdsAppota.IKEN_APP_IN, "[startLoadAdsCenter] app_loaded");
//                    }
//
//                    @Override
//                    public void onAdFailedToLoad(int i) {
//                        is_loadIn = false;
//                        Log.d(AdsAppota.IKEN_APP_IN, "[startLoadAdsCenter] app_Fail " + i);
//                    }
//                });
            }

            @Override
            public void onDisplayedFBAds() {
                Log.d(FacebookControl.IKEN_FB_IN, "[startLoadAdsCenter] fb_show");
//                AdmodControl.interstitial_adm_in = null;
                FacebookControl.isLoadingFbIn = false;
            }

            @Override
            public void onCloseFBAds() {

            }
        });
//            @Override
//            public void loadedADM() {
//                Log.d(AdmodControl.IKEN_ADM_IN, "[startLoadAdsCenter] adm_loaded");
//            }
//
//            @Override
//            public void displayedAdsADM() {
//                AdmodControl.interstitial_adm_in = null;
//                Log.d(AdmodControl.IKEN_ADM_IN, "[startLoadAdsCenter] adm_show");
//            }
//
//            @Override
//            public void loadFailADM(int error) {
//                AdmodControl.interstitial_adm_in = null;
//                Log.d(AdmodControl.IKEN_ADM_IN, "[startLoadAdsCenter] adm_fail : " + error);
//            }
    }


    public static boolean showAdsIn(boolean isStart, Context mContext, Settting1.CallbackShow mCallbackShow) {
        if (isStart) {
//            if (GetMyDataSystem.getDataBoolean(mContext, ConstantsAppNew.RD_IN, false)) {
//                if (!AdsAppota.showAds(isStart, mCallbackLoad)) {
//                    if (!FacebookControl.showInAppFb(FacebookControl.interstitialAd_fb_in, mCallbackLoad)) {
//                        return false;
//                    }
//                    return true;
//                }
//                return true;
//            } else {
//                if (!FacebookControl.showInAppFb(FacebookControl.interstitialAd_fb_in, mCallbackLoad)) {
//                    if (!AdsAppota.showAds(isStart, mCallbackLoad)) {
//                        return false;
//                    }
//
//                    return true;
//                }
            return true;
//            }
        } else {
            if (GetMyDataSystem.getDataBoolean(mContext, ConstantsAppNew.RD_IN, true)) {
                if (!AdmodControl.showAdsInADM(AdmodControl.interstitial_adm_in)) {
                    if (!FacebookControl.showInAppFb(FacebookControl.interstitialAd_fb_in, mCallbackShow)) {
                        return false;
                    }
                    return true;
                }
                return true;
            } else {
                if (!FacebookControl.showInAppFb(FacebookControl.interstitialAd_fb_in, mCallbackShow)) {
                    if (!AdmodControl.showAdsInADM(AdmodControl.interstitial_adm_in)) {
                        return false;
                    }

                    return true;
                }
                return true;
            }
        }
//        return true;
//            AdmodControl.showAdsInADM(AdmodControl.interstitial_adm_in);
//            showAdsInADM(interstitial_adm_in);
    }

    public static void onCallLoadAdsIn(Context mContext) {
        if (mContext != null) {
//            if (is_loadDa){
            Log.d(ConstantsAppNew.TAG, "is_loadDa: " + is_loadIn);
//            } else {
//
//            }

            if (CheckAll.CheckShowAdsIN(mContext)) {

                startLoadAdsCenter(mContext);
            }
        }
    }

    public static void showAdmodControl(InterstitialAd mInterstitialAd) {
        AdmodControl.showAdsInADM(mInterstitialAd);
    }

    public interface CallbackLoadNative {
        void Loaded();

        void LoadFaild();
    }

    public interface CallbackLoad {
        void Loaded(InterstitialAd interAdmIn_Start);

        void LoadedFailAds();


        void Loaded(com.facebook.ads.InterstitialAd mInterstitialAd_start);

        void Loaded();

        void Loaded2(InterstitialAd interAdmIn_start);
    }

    public interface CallbackShow {

        void Displayed();

        void Close();
    }
}
