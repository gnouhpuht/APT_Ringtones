package com.optionringringtone.newringtonefree.mysetting.myReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.optionringringtone.newringtonefree.mysetting.CheckAll;

public class RestartReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        CheckAll.checkStartService(context);
//        EventLog.LogEventAction(context, ConstantEventLog.EVENT_SERVICE, "RestartReceiver", "Check to Restart");
    }
}
