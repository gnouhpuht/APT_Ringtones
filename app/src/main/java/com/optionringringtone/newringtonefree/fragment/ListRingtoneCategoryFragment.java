package com.optionringringtone.newringtonefree.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        sharePreferenceUntil = SharePreferenceUntil.getInstance(getContext());
        super.onCreate(savedInstanceState);
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
        adapterLstRingtone = new BaseAdapterListRingtone(activity, lstRing);
        lv_lstRingtones.setAdapter(adapterLstRingtone);
        lv_lstRingtones.setDivider(null);
        toolbar_title = view.findViewById(R.id.toolbar_title);
        toolbar_title.setText(title);
        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

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
                    null);
            ringtones.add(ringTone);

        }
        return ringtones;
    }

}
