package com.optionringringtone.newringtonefree;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.optionringringtone.newringtonefree.Untils.CommonUntil;
import com.optionringringtone.newringtonefree.Untils.MediaUntil;
import com.optionringringtone.newringtonefree.Untils.SharePreferenceUntil;
import com.optionringringtone.newringtonefree.Untils.SlideAnimationUtil;
import com.optionringringtone.newringtonefree.adapter.BaseAdapterLstRingtoneV2;
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
