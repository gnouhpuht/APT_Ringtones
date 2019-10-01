package com.optionringringtone.newringtonefree.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.facebook.ads.*;
import com.google.android.gms.ads.AdRequest;
import com.optionringringtone.newringtonefree.Untils.CommonUntil;
import com.optionringringtone.newringtonefree.Untils.FragmentCommon;
import com.optionringringtone.newringtonefree.Untils.SharePreferenceUntil;
import com.optionringringtone.newringtonefree.adapter.BaseAdapterListRingtone;
import com.optionringringtone.newringtonefree.object.RingTone;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;

public class ListRingtoneCategoryFragment extends FragmentCommon implements View.OnClickListener {
    private static final String TAG = "DetailCategoriesFragment";
    private ProgressBar prLoading;
    private ListView lv_lstRingtones;
    private SharePreferenceUntil sharePreferenceUntil;
    private BaseAdapterListRingtone adapterLstRingtone;
    private List<RingTone> lstRing;
    private String id;
    private TextView toolbar_title;
    private ImageView iv_back;
    private String title = "";
    private com.facebook.ads.AdView adView;
    private com.google.android.gms.ads.AdView banner;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        sharePreferenceUntil = SharePreferenceUntil.getInstance(getContext());
        super.onCreate(savedInstanceState);

    }
    private void loadBanner(View view){
        adView = new com.facebook.ads.AdView(getActivity(), "YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout)view.findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        adView.setAdListener(new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                requestAds(view);
//                Toast.makeText(MainActivity.this, "Error: " + adError.getErrorMessage(),
//                        Toast.LENGTH_LONG).show();
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

    private void requestAds(View view){
        banner = (com.google.android.gms.ads.AdView)view.findViewById(R.id.banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        banner.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdClosed() {
                //Khi ad bị close bởi người dùng
            }

            @Override
            public void onAdFailedToLoad(int i) {
                //Khi load quảng cáo lỗi, bạn có thể load quảng cáo của mạng khác để thay thế tại đây
                switch (i){
                    case AdRequest.ERROR_CODE_INTERNAL_ERROR:

                        break;
                    case AdRequest.ERROR_CODE_INVALID_REQUEST:

                        break;
                    case AdRequest.ERROR_CODE_NETWORK_ERROR:

                        break;
                    case AdRequest.ERROR_CODE_NO_FILL:
                        //Khi không còn quảng cáo nào phù hợp
                        break;
                }
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                // Khi quảng cáo được mở
            }

            @Override
            public void onAdLoaded() {
                //Khi quảng cáo đã load xong
            }
        });
        banner.loadAd(adRequest);
    }
    public ListRingtoneCategoryFragment setId(String id) {
        this.id = id;
        return this;
    }

    public ListRingtoneCategoryFragment setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_lst_ringtones_categories;
    }

    @Override
    public void init(View view) {
        lv_lstRingtones = view.findViewById(R.id.lv_lstRingtones);
        prLoading = view.findViewById(R.id.prLoading);
        lstRing = getRingCategory(title);
//        CommonUntil.setLayoutManager(activity, lv_lstRingtones, LinearLayout.VERTICAL);
        adapterLstRingtone = new BaseAdapterListRingtone(activity, lstRing,title);
        lv_lstRingtones.setAdapter(adapterLstRingtone);
        lv_lstRingtones.setDivider(null);
        toolbar_title = view.findViewById(R.id.toolbar_title);
        toolbar_title.setText(title);
        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        AdSettings.setIntegrationErrorMode(AdSettings.IntegrationErrorMode.INTEGRATION_ERROR_CALLBACK_MODE);
        AdSettings.addTestDevice("HASHED ID");
        loadBanner(view);
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


    public List<RingTone> getRingCategory(String name){
        List<RingTone> ringtones=new ArrayList<>();
        String path=CommonUntil.getPathCategory(name);
        File directory = new File(path);
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            RingTone ringTone = new RingTone( files[i].getAbsolutePath ()+"",
                    files[i].getName(),
                    null,
                    false,i);
            ringtones.add(ringTone);

        }
        return ringtones;
    }

}
