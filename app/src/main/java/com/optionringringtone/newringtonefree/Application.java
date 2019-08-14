package com.optionringringtone.newringtonefree;

import androidx.multidex.MultiDexApplication;

import com.optionringringtone.newringtonefree.mysetting.AllSetting;

public class Application extends MultiDexApplication {
    private static Application mInstance;

    public static Application getInstance()
    {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        AllSetting.initApplication(this);
    }
}
