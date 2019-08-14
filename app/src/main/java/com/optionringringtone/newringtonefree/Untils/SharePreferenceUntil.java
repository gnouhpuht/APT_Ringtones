package com.optionringringtone.newringtonefree.Untils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.optionringringtone.newringtonefree.object.RingTone;

import java.util.List;

public class SharePreferenceUntil {


    private static String LIST_MUSIC = "LIST_MUSIC";


    private static SharePreferenceUntil yourPreference;
    private static SharedPreferences sharedPreferences;

    public static SharePreferenceUntil getInstance(Context context) {
        if (yourPreference == null) {
            yourPreference = new SharePreferenceUntil(context);
        }
        return yourPreference;
    }

    private SharePreferenceUntil(Context context) {
        String SHARE_PRE_NAME = "RINGTONES_2019";
        sharedPreferences = context.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
    }

    public void saveData(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public String getData(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }

    public void saveMusicLocal(List<RingTone> ringTone) {
        String jsonData = new Gson().toJson(ringTone);
        String TAG = "SharePreferenceUntil";
        Log.i(TAG, "saveMusicLocal: " + jsonData);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LIST_MUSIC, jsonData);
        editor.apply();
    }

    public String getJsonListMusic() {
        String result = sharedPreferences.getString(LIST_MUSIC, null);
        if (result != null) {
            return result;
        }
        return null;
    }
}
