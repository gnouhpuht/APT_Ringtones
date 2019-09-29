package com.optionringringtone.newringtonefree.mysetting.controlApp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;
import com.optionringringtone.newringtonefree.mysetting.GetMyDataSystem;
import com.optionringringtone.newringtonefree.mysetting.HomeActivity2;
import com.optionringringtone.newringtonefree.mysetting.Settting1;

import java.util.ArrayList;
import java.util.List;

public class FacebookControl {
    public static void init(Context mContext) {

        AudienceNetworkAds.initialize(mContext.getApplicationContext());
        AdSettings.addTestDevice("d0163796-81e7-4fc9-a1dc-47f15259985d");
    }

    public static InterstitialAd interstitialAd_fb_in;
    public static InterstitialAd interstitialAd_fb_out;
    public static final String IKEN_FB_IN = "IKEN_FB_IN";
    public static final String IKEN_FB_OUT = "IKEN_FB_OUT";

    public static boolean isLoadingFbOut = false;
    public static boolean isLoadingFbIn = false;



    public static void initFAN(Context context) {
        AudienceNetworkAds.initialize(context);
        AudienceNetworkAds.isInAdsProcess(context);
        AdSettings.setIntegrationErrorMode(AdSettings.IntegrationErrorMode.INTEGRATION_ERROR_CRASH_DEBUG_MODE);
        AdSettings.addTestDevice("d0163796-81e7-4fc9-a1dc-47f15259985d");
    }

    //-----------IN--------------

    public static void loadFB_Action(Context mContext, final com.facebook.ads.InterstitialAd interstitialAd_fb,
                                     final CallBackFabookAds mCallBackFabookAds) {


//        if (interstitialAd_fb == null) Log.d(IKEN_FB_IN, "[loadFB_Action] 1");
//        else if (interstitialAd_fb.isAdInvalidated()) Log.d(IKEN_FB_IN, "[loadFB_Action] 2");

        interstitialAd_fb.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad onDisplayedINMOBI callback
//                Log.d(IKEN_FB_IN, "[loadFB_Action] Interstitial ad onDisplayedINMOBI.");
                if (mCallBackFabookAds != null)
                    mCallBackFabookAds.onDisplayedFBAds();
                if (interstitialAd_fb != null) interstitialAd_fb.destroy();

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                Log.d(IKEN_FB_IN, "[loadFB_Action] Interstitial ad dismissed.");
                if (interstitialAd_fb != null) interstitialAd_fb.destroy();
                // Interstitial dismissed callback
            }

            @Override
            public void onError(Ad ad, AdError adError) {
//                Log.d(IKEN_FB_IN, "[loadFB_Action] Interstitial ad failed to load: " + adError.getErrorMessage());
                if (interstitialAd_fb != null) interstitialAd_fb.destroy();
                // Ad error callback
                if (mCallBackFabookAds != null)
                    mCallBackFabookAds.onLoadedFailFBAds(adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is onLoadedINMOBI and ready to be onDisplayedINMOBI
                if (mCallBackFabookAds != null)
                    mCallBackFabookAds.onLoadedFBAds();
                // Show the ad

            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
//                Log.d(IKEN_FB_IN, "[loadFB_Action] Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
//                Log.d(IKEN_FB_IN, "[loadFB_Action] Interstitial ad impression logged!");
            }
        });


        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        Log.d(IKEN_FB_IN, "[loadFB_Action] fb_start_load " + interstitialAd_fb.getPlacementId());
        GetMyDataSystem.setTimeLastLoadAds(mContext);
        interstitialAd_fb.loadAd();
    }


    public static void loadFb_Center_CallBack(Context mContext, final CallBackFabookAds mCallBackFabookAds) {
        if (!isLoadingFbIn && (interstitialAd_fb_in == null || interstitialAd_fb_in.isAdInvalidated()) &&
                (AdmodControl.interstitial_adm_in == null || (!AdmodControl.interstitial_adm_in.isLoading() &&
                        !AdmodControl.interstitial_adm_in.isLoaded()))) {
            isLoadingFbIn = true;


            if (interstitialAd_fb_in == null) Log.d(IKEN_FB_IN, "[loadFb_Center_CallBack] 1");
            else if (interstitialAd_fb_in.isAdInvalidated())
                Log.d(IKEN_FB_IN, "[loadFb_Center_CallBack] 2");


            String id_fb_center = GetMyDataSystem.getDataString(mContext, ConstantsAppNew.FB_CENTER_KEY, ConstantsAppNew.FB_CENTER_KEY_DEFAULT);


            Log.d(IKEN_FB_IN, "[loadFb_Center_CallBack] load: " + id_fb_center);

            interstitialAd_fb_in = new com.facebook.ads.InterstitialAd(mContext, id_fb_center);

            interstitialAd_fb_in.setAdListener(new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {
                    isLoadingFbIn = false;
                    // Interstitial ad onDisplayedINMOBI callback
//                    Log.d(IKEN_FB_IN, "[loadFb_Center_CallBack] Interstitial ad onDisplayedINMOBI.");
                    if (mCallBackFabookAds != null)
                        mCallBackFabookAds.onDisplayedFBAds();
                    interstitialAd_fb_in.destroy();

                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    isLoadingFbIn = false;
                    interstitialAd_fb_in.destroy();
                    interstitialAd_fb_in = null;
                    // Interstitial dismissed callback
                    Log.d(IKEN_FB_IN, "[loadFb_Center_CallBack] Interstitial ad dismissed.");
                    if (mCallBackFabookAds != null)
                        mCallBackFabookAds.onCloseFBAds();

                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    isLoadingFbIn = false;
                    interstitialAd_fb_in.destroy();
                    interstitialAd_fb_in = null;
                    // Ad error callback
                    if (mCallBackFabookAds != null)
                        mCallBackFabookAds.onLoadedFailFBAds(adError.getErrorMessage());


//                    Log.d(IKEN_FB_IN, "[loadFb_Center_CallBack] Interstitial ad failed to load: " + adError.getErrorMessage());
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    isLoadingFbIn = false;
                    // Interstitial ad is onLoadedINMOBI and ready to be onDisplayedINMOBI
                    if (mCallBackFabookAds != null)
                        mCallBackFabookAds.onLoadedFBAds();
                    // Show the ad

                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Ad clicked callback
//                    Log.d(IKEN_FB_IN, "[loadFb_Center_CallBack] Interstitial ad clicked!");
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Ad impression logged callback
//                    Log.d(IKEN_FB_IN, "[loadFb_Center_CallBack] Interstitial ad impression logged!");
                }
            });


            // For auto play video ads, it's recommended to load the ad
            // at least 30 seconds before it is shown
            GetMyDataSystem.setTimeLastLoadAds(mContext);
            interstitialAd_fb_in.loadAd();
            Log.d(IKEN_FB_IN, "[loadFb_Center_CallBack] fb_start_load");
        } else {
            isLoadingFbIn = false;
            Log.d(IKEN_FB_IN, "[loadFb_Center_CallBack] Can not to load FB");
//            if (interstitial_adm_in != null && interstitial_adm_in.isLoaded()) {
//                mCallBackFabookAds.loadedADM();
//            }

//            if (interstitialAd_fb_in != null && interstitialAd_fb_in.isAdLoaded()) {
//                mCallBackFabookAds.loadedFB();
//            } else {
//                mCallBackFabookAds.loadFailFB("");
//            }
//            mCallBackFabookAds.loadFailFB("");
        }
    }

    public static boolean showInAppFb(com.facebook.ads.InterstitialAd mInterstitialAd, final Settting1.CallbackShow mCallbackLoad) {

        if (mInterstitialAd == null || !mInterstitialAd.isAdLoaded()) {
            return false;
        }
        // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
        if (mInterstitialAd.isAdInvalidated()) {
            return false;
        }
        // Show the ad
        mInterstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                mCallbackLoad.Close();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        mInterstitialAd.show();
        return true;
    }


    //---------Native--------------
//---------Native--------------
//    private NativeAd nativeAd;
    public static void loadNativeAds(int curent, final Context mContext, final NativeFbCallback mNativeFbCallback) {
        String placement_id = "";
        switch (curent) {
            case 1:
                placement_id = GetMyDataSystem.getDataString(mContext, ConstantsAppNew.FB_NATIVE_POPULER_KEY, "355114345187529_356227851742845");
                break;
            case 2:
                placement_id = GetMyDataSystem.getDataString(mContext, ConstantsAppNew.FB_NATIVE_CATEGORY_KEY, "355114345187529_356227675076196");
                break;
            case 3:
                placement_id = GetMyDataSystem.getDataString(mContext, ConstantsAppNew.FB_NATIVE_DETAIL_KEY, "355114345187529_356228298409467");
                break;
            case 4:
                placement_id = GetMyDataSystem.getDataString(mContext, ConstantsAppNew.FB_NATIVE_SEARCH_KEY, "355114345187529_356227518409545");
                break;
        }

        final NativeBannerAd nativeAd = new NativeBannerAd(mContext, placement_id);

        nativeAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e(ConstantsAppNew.TAG, "Native ad finished downloading all assets.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                mNativeFbCallback.loadFail(adError.getErrorMessage());
                // Native ad failed to load
                Log.e(ConstantsAppNew.TAG, "Native ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                mNativeFbCallback.loadSuscces(nativeAd);
                // Native ad is loaded and ready to be displayed
                Log.d(ConstantsAppNew.TAG, "Native ad is loaded and ready to be displayed!");
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d(ConstantsAppNew.TAG, "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d(ConstantsAppNew.TAG, "Native ad impression logged!");
            }
        });

        // Request an ad
        nativeAd.loadAd();
    }


    public static void inflateAd(int curent, Context mContext, NativeBannerAd nativeBannerAd, NativeAdLayout nativeAdLayout) {
        nativeBannerAd.unregisterView();

        // Add the Ad view into the ad container.
        LayoutInflater inflater = LayoutInflater.from(mContext);
        // Inflate the Ad view.  The layout referenced is the one you created in the last step.
        LinearLayout adView = null;
        switch (curent) {
            case 1:
                adView = (LinearLayout) inflater.inflate(R.layout.native_fb_banner_layout1, nativeAdLayout, false);
                break;
            case 2:
                adView = (LinearLayout) inflater.inflate(R.layout.native_fb_banner_layout2, nativeAdLayout, false);
                break;
            case 3:
                adView = (LinearLayout) inflater.inflate(R.layout.native_fb_banner_layout3, nativeAdLayout, false);
                break;
            case 4:
                adView = (LinearLayout) inflater.inflate(R.layout.native_fb_banner_layout1, nativeAdLayout, false);
                break;
        }
        nativeAdLayout.addView(adView);

        // Add the AdChoices icon
        RelativeLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(mContext, nativeBannerAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        AdIconView nativeAdIconView = adView.findViewById(R.id.native_icon_view);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdCallToAction.setText(nativeBannerAd.getAdCallToAction());
        nativeAdCallToAction.setVisibility(
                nativeBannerAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdTitle.setText(nativeBannerAd.getAdvertiserName());
        nativeAdSocialContext.setText(nativeBannerAd.getAdSocialContext());
        sponsoredLabel.setText(nativeBannerAd.getSponsoredTranslation());

        // Register the Title and CTA button to listen for clicks.
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        nativeBannerAd.registerViewForInteraction(adView, nativeAdIconView, clickableViews);
    }


    public interface NativeFbCallback {
        void loadSuscces(NativeBannerAd nativeAd);

        void loadFail(String faild);
    }

    //-----------OUT--------------
    public static boolean showAdsOutFb(HomeActivity2.AdCloseCallback adCloseCallback) {
        AdmodControl.mAdCloseCallback2 = adCloseCallback;

        if (interstitialAd_fb_out == null || !interstitialAd_fb_out.isAdLoaded()) {
            return false;
        }
        // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
        if (interstitialAd_fb_out.isAdInvalidated()) {
            return false;
        }
        // Show the ad
        interstitialAd_fb_out.show();
        return true;
    }

    public static void loadAdsOutFbCallBack(final Context context, String id_ads, final CallBackFabookAds mCallBackFabookAds) {

        if (!isLoadingFbOut && (interstitialAd_fb_out == null || interstitialAd_fb_out.isAdInvalidated()) && (AdmodControl.interstitial_adm_out == null || (!AdmodControl.interstitial_adm_out.isLoading() && !AdmodControl.interstitial_adm_out.isLoaded()))) {
            isLoadingFbOut = true;


            if (interstitialAd_fb_out == null) Log.d(IKEN_FB_OUT, "1");
            else if (interstitialAd_fb_out.isAdInvalidated()) Log.d(IKEN_FB_OUT, "2");

            interstitialAd_fb_out = new com.facebook.ads.InterstitialAd(context, id_ads);
            interstitialAd_fb_out.setAdListener(new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {
                    // Interstitial ad onDisplayedINMOBI callback
//                    Log.d(IKEN_FB_OUT, "Interstitial ad onDisplayedINMOBI.");
                    isLoadingFbOut = false;
                    if (mCallBackFabookAds != null)
                        mCallBackFabookAds.onDisplayedFBAds();
                    interstitialAd_fb_out.destroy();

                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    isLoadingFbOut = false;
                    interstitialAd_fb_out.destroy();
                    // Interstitial dismissed callback
                    Log.d(IKEN_FB_OUT, "Interstitial ad dismissed.");
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    isLoadingFbOut = false;
                    interstitialAd_fb_out.destroy();
                    // Ad error callback
                    if (mCallBackFabookAds != null)
                        mCallBackFabookAds.onLoadedFailFBAds(adError.getErrorMessage());
//                    Log.d(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    isLoadingFbOut = false;
                    // Interstitial ad is onLoadedINMOBI and ready to be onDisplayedINMOBI
                    if (mCallBackFabookAds != null)
                        mCallBackFabookAds.onLoadedFBAds();
                    // Show the ad

                }

                @Override
                public void onAdClicked(Ad ad) {
                    isLoadingFbOut = false;
                    // Ad clicked callback
                    Log.d(IKEN_FB_OUT, "Interstitial ad clicked!");
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    isLoadingFbOut = false;
                    // Ad impression logged callback
                    Log.d(IKEN_FB_OUT, "Interstitial ad impression logged!");
                }
            });

            // For auto play video ads, it's recommended to load the ad
            // at least 30 seconds before it is shown
            GetMyDataSystem.setTimeLastLoadAds(context);
            interstitialAd_fb_out.loadAd();
            Log.d(IKEN_FB_OUT, "fb_start_load" + id_ads);
        } else {
            if (interstitialAd_fb_out != null && interstitialAd_fb_out.isAdLoaded()) {
                mCallBackFabookAds.onLoadedFBAds();
            }
        }
    }

    //------------------------------------------------------------------
    public interface CallBackFabookAds {
        void onLoadedFBAds();

        void onLoadedFailFBAds(String error);

        void onDisplayedFBAds();

        void onCloseFBAds();

    }

}
