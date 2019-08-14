package com.optionringringtone.newringtonefree;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.optionringringtone.newringtonefree.Untils.Configs;

import java.util.Locale;

import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;

public class SplashActivity extends AppCompatActivity {

    private String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        Configs.COUNTRY = Locale.getDefault().getCountry();
        new Handler().postDelayed(()->{
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        },1500);

        Log.i(TAG, "onCreate: "  + Locale.getDefault().getDisplayLanguage());
       // getLocation();
    }

//    private void getLocation() {
//        RetrofitUtil.getApi().getLocation().enqueue(new Callback<Location>() {
//            @Override
//            public void onResponse(Call<Location> call, Response<Location> response) {
//                if (response.code() == 200) {
//                    Log.i(TAG, "onResponse: link " + call.request().toString());
//                    Log.i(TAG, "onResponse: " + response.body());
//                    Configs.COUNTRY =  response.body().getCountry();
//                    new Handler().postDelayed(()->{
//                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    },1000);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Location> call, Throwable t) {
//                new Handler().postDelayed(()->{
//                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                },1000);
//            }
//        });
//    }


}
