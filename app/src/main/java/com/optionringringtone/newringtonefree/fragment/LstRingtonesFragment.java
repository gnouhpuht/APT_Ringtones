package com.optionringringtone.newringtonefree.fragment;


import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;
import com.optionringringtone.newringtonefree.Untils.FragmentCommon;
import com.optionringringtone.newringtonefree.Untils.RetrofitUtil;
import com.optionringringtone.newringtonefree.adapter.BaseAdapterLstRingtoneV2;
import com.optionringringtone.newringtonefree.object.RingTone;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 */
public class LstRingtonesFragment extends FragmentCommon implements View.OnClickListener {

    private static final String TAG = "DetailCategoriesFragment";
    private ProgressBar prLoading;
    private ListView lv_lstRingtones;
    private BaseAdapterLstRingtoneV2 adapterLstRingtone;
    private List<RingTone> lstRing;
    private String id;
    private TextView toolbar_title;
    private ImageView iv_back;
    private String title = "";

    public LstRingtonesFragment setId(String id) {
        this.id = id;
        return this;
    }

    public LstRingtonesFragment setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_lst_ringtones_categories;
    }

    @Override
    public void init(View view) {
        lv_lstRingtones = view.findViewById(R.id.lv_lstRingtones);
        prLoading = view.findViewById(R.id.prLoading);
        lstRing = new ArrayList<>();
//        CommonUntil.setLayoutManager(activity, lv_lstRingtones, LinearLayout.VERTICAL);
        adapterLstRingtone = new BaseAdapterLstRingtoneV2(activity, lstRing);
        lv_lstRingtones.setAdapter(adapterLstRingtone);
        lv_lstRingtones.setDivider(null);
        toolbar_title = view.findViewById(R.id.toolbar_title);
        toolbar_title.setText(title);

        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        getDataRingtonePopular();
    }

    private void getDataRingtonePopular() {
        prLoading.setVisibility(View.VISIBLE);
        RetrofitUtil.getApi().getDetailsCategories(id).enqueue(new Callback<ArrayList<RingTone>>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<ArrayList<RingTone>> call, Response<ArrayList<RingTone>> response) {
                if (response.code() == 200) {
                    Log.i(TAG, "onResponse: link " + call.request().toString());
                    Log.i(TAG, "onResponse: " + response.body());
//                    lstRing.clear();
                    ArrayList<RingTone> lstData = response.body();
                    if (lstData == null)
                        lstData = new ArrayList<>();
                    lstRing.addAll(lstData);
                    adapterLstRingtone.notifyDataSetChanged();
                    prLoading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RingTone>> call, Throwable t) {
                prLoading.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        adapterLstRingtone.stopAudio();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;
        }
    }
}
