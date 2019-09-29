package com.optionringringtone.newringtonefree.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.optionringringtone.newringtonefree.MainActivity;
import com.optionringringtone.newringtonefree.Untils.Configs;
import com.optionringringtone.newringtonefree.Untils.FragmentCommon;
import com.optionringringtone.newringtonefree.Untils.RetrofitUtil;
import com.optionringringtone.newringtonefree.adapter.BaseAdapterPopular;
import com.optionringringtone.newringtonefree.object.ResponseDTO;
import com.optionringringtone.newringtonefree.object.RingTone;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 */
public class PopularFragment extends FragmentCommon {

    private static final String TAG = "PopularFragment";
    private ProgressBar prLoading;
    ListView lv_lstRingtones;

    BaseAdapterPopular adapterLstRingtone;
    List<ResponseDTO> lstRingtones;
    private List<RingTone> lstRing;



    public PopularFragment() {
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_list_ringtones;
    }

    @Override
    public void init(View view) {
        lv_lstRingtones = view.findViewById(R.id.lv_lstRingtones);
        prLoading = view.findViewById(R.id.prLoading);
        lstRing = new ArrayList<>();
//        CommonUntil.setLayoutManager(activity, rc_lst_ring_stone, LinearLayout.VERTICAL);
        adapterLstRingtone = new BaseAdapterPopular(activity, lstRing);
        lv_lstRingtones.setAdapter(adapterLstRingtone);
        lv_lstRingtones.setDivider(null);


    }

    private void getDataRingtonePopular() {
        prLoading.setVisibility(View.VISIBLE);
        RetrofitUtil.getApi().getPopularRingtones(Configs.COUNTRY).enqueue(new Callback<ResponseDTO<ArrayList<RingTone>>>() {
            @Override
            public void onResponse(Call<ResponseDTO<ArrayList<RingTone>>> call, Response<ResponseDTO<ArrayList<RingTone>>> response) {
                prLoading.setVisibility(View.GONE);
                if (response.code() == 200) {
                    Log.i(TAG, "onResponse: link " + call.request().toString());
                    Log.i(TAG, "onResponse: " + response.body());
                    ArrayList<RingTone> lstData = response.body().getData();
                    if(lstData == null)
                        lstData = new ArrayList<>();
                    lstRing.addAll(lstData);
                    adapterLstRingtone.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<ArrayList<RingTone>>> call, Throwable t) {
                prLoading.setVisibility(View.GONE);
            }
        });
    }
    public void stopAudio(){
        adapterLstRingtone.stopAudio();
    }

    @Override
    public void onStart() {
        super.onStart();
        getDataRingtonePopular();
    }

    @Override
    public void onPause() {
        super.onPause();
        adapterLstRingtone.stopAudio();
    }


}
