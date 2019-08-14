package com.optionringringtone.newringtonefree.mysetting.myReceiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.optionringringtone.newringtonefree.mysetting.AllSetting;

public class ScreenReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent){

        if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
            AllSetting.screenON = true;
            Log.e("xxx", "The screen is on.");
            Log.d("iken", "The screen is on.");
        }

        else{
            AllSetting.screenON = false;
            Log.e("xxx", "The screen is off.");
            Log.d("iken", "The screen is off.");
        }
    }

}
