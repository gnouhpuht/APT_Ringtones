package com.optionringringtone.newringtonefree.mysetting;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.optionringringtone.newringtonefree.mysetting.controlApp.AdmodControl;
import com.optionringringtone.newringtonefree.mysetting.controlApp.ConstantsAppNew;
import com.optionringringtone.newringtonefree.mysetting.controlApp.FacebookControl;

public class HomeActivity2 extends Activity {

    public static boolean alarmActivityShow = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

//        if (GetSystemData.getCanShowOUT_LSC(this)) {
//            final Window win = getWindow();
//            win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
//            win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
//        }

        super.onCreate(savedInstanceState);

//        EventLog.LogEventAction(this, ConstantEventLog.EVENT_OUT_APP, "Open Again", "true");

//        int curent = GetSystemData.getCurentShowAds(HomeActivity2.this);
        int curent = GetMyDataSystem.getDataInt(this, ConstantsAppNew.CURENT_SHOWED_ADS, 0);
        curent = curent + 1;
        GetMyDataSystem.setDataInt(this, ConstantsAppNew.CURENT_SHOWED_ADS, curent);

        if (FacebookControl.showAdsOutFb(new AdCloseCallback() {
            @Override
            public void close() {
                alarmActivityShow = false;
                finish();
            }
        })) {
            alarmActivityShow = true;
            FacebookControl.interstitialAd_fb_out.setAdListener(null);
            FacebookControl.interstitialAd_fb_out = null;
            Log.d(FacebookControl.IKEN_FB_OUT, "fb_show");
        } else {

            Log.d(AdmodControl.IKEN_ADM_OUT, "adm_show");
            AdmodControl.showAdsOutADM(new AdCloseCallback() {
                @Override
                public void close() {
                    alarmActivityShow = false;
                    finish();
                }
            });

        }

//        if (SettingOUT.showAdsOutADM(new AdCloseCallback() {
//                @Override
//                public void close() {
//                    alarmActivityShow = false;
//                    adsClose();
//                }
//            })) {
//                alarmActivityShow = true;
//                SettingOUT.interstitial_adm_out.setAdListener(null);
//                SettingOUT.interstitial_adm_out = null;
//
//                Log.d(SettingOUT.IKEN_ADM_OUT, "adm_show");
//            }
//        }
    }

    @Override
    protected void onPause() {
        alarmActivityShow = false;
        finish();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        alarmActivityShow = false;
        AllSetting.onDestroyAct();
        super.onDestroy();
    }

    public interface AdCloseCallback {
        public void close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
