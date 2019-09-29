package com.optionringringtone.newringtonefree;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.optionringringtone.newringtonefree.Untils.CommonUntil;
import com.optionringringtone.newringtonefree.Untils.MediaUntil;
import com.optionringringtone.newringtonefree.Untils.SharePreferenceUntil;
import com.optionringringtone.newringtonefree.Untils.SlideAnimationUtil;
import com.optionringringtone.newringtonefree.adapter.BaseAdapterLstRingtoneV2;
import com.optionringringtone.newringtonefree.mysetting.Settting1;
import com.optionringringtone.newringtonefree.object.RingTone;

import java.util.ArrayList;
import java.util.List;

import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;

public class MyDownloadActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView btnBack;
    private ListView rcListDownload;
    private SharePreferenceUntil sharePreferenceUntil;
    private String TAG = "MyDownloadActivity";
    private List<RingTone> ringTones = new ArrayList<>();
    private BaseAdapterLstRingtoneV2 adapterLstRingtone;
    private List<RingTone> lstRing;
    private MediaUntil mMediaInstance;
    private int sizeListDownLoad = 0;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_download);
        getSupportActionBar().hide();
        sharePreferenceUntil = SharePreferenceUntil.getInstance(this);
        initView();
        initData();
    }

    private void initData() {
        mMediaInstance = MediaUntil.getInstance();
        String jsonListData = sharePreferenceUntil.getJsonListMusic();
        ringTones = CommonUntil.convertJsonRingtonToList(jsonListData);
        Log.i(TAG, "initData: " + jsonListData);
        if (ringTones != null){
            Log.i(TAG, "initData: " + ringTones.size());
            lstRing.addAll(ringTones);
            adapterLstRingtone.notifyDataSetChanged();
            sizeListDownLoad = ringTones.size();
        }

        AdSettings.setIntegrationErrorMode(AdSettings.IntegrationErrorMode.INTEGRATION_ERROR_CALLBACK_MODE);
        AdSettings.addTestDevice("HASHED ID");
        adView = new AdView(this, "YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50);
        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout)findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        adView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Toast.makeText(MyDownloadActivity.this, "Error: " + adError.getErrorMessage(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        });


        // Request an ad
        adView.loadAd();
    }

    private void initView() {
        btnBack = findViewById(R.id.btnBack);
        rcListDownload = findViewById(R.id.rcListDownload);

        btnBack.setOnClickListener(this);

        lstRing = new ArrayList<>();
//        CommonUntil.setLayoutManager(this, rcListDownload, LinearLayout.VERTICAL);
        adapterLstRingtone = new BaseAdapterLstRingtoneV2(this, lstRing);
        rcListDownload.setAdapter(adapterLstRingtone);
        rcListDownload.setDivider(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapterLstRingtone.stopAudio();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
      adapterLstRingtone.stopAudio();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapterLstRingtone.stopAudio();
        Log.i(TAG, "onDestroy: ");
        if (adView != null) {
            adView.destroy();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SlideAnimationUtil.overridePendingTransitionExit(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String jsonListData = sharePreferenceUntil.getJsonListMusic();
        ringTones = CommonUntil.convertJsonRingtonToList(jsonListData);

        if (ringTones != null){
            if(sizeListDownLoad != ringTones.size()){
                lstRing.clear();
                lstRing.addAll(ringTones);
                adapterLstRingtone.notifyDataSetChanged();
                sizeListDownLoad = ringTones.size();
            }
        }
    }
}
