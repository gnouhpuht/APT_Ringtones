package com.optionringringtone.newringtonefree.mysetting.myReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

import com.optionringringtone.newringtonefree.mysetting.CheckAll;
import com.optionringringtone.newringtonefree.mysetting.GetMyDataSystem;
import com.optionringringtone.newringtonefree.mysetting.HomeActivity2;
import com.optionringringtone.newringtonefree.mysetting.Setting2;
import com.optionringringtone.newringtonefree.mysetting.controlApp.ConstantsAppNew;

import java.util.Random;


public class My2Receiver extends BroadcastReceiver {
    private boolean loading = false;
    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d("IKEN_OUT", "My2Receiver onReceive: 0");
        // Setting.checkStartService(context);

    }

    public interface CallbackLoadAds {
        public void loaded(String network);

        public void loadFail();
    }
}
