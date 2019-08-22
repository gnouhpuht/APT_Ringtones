package com.optionringringtone.newringtonefree.fragment;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.optionringringtone.newringtonefree.Untils.Configs;
import com.optionringringtone.newringtonefree.Untils.FragmentCommon;
import com.optionringringtone.newringtonefree.Untils.SharePreferenceUntil;
import com.optionringringtone.newringtonefree.adapter.BaseAdapterListRingtone;
import com.optionringringtone.newringtonefree.adapter.BaseAdapterLstRingtoneV2;
import com.optionringringtone.newringtonefree.mysetting.ReadRingCategory;
import com.optionringringtone.newringtonefree.object.RingTone;
import com.optionringringtone.newringtonefree.object.detailcategory.Ringtone;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;

public class ListRingtoneCategoryFragment extends FragmentCommon implements View.OnClickListener{
    private static final String TAG = "DetailCategoriesFragment";
    private ProgressBar prLoading;
    private ListView lv_lstRingtones;
    private SharePreferenceUntil sharePreferenceUntil;
//    private BaseAdapterListRingtone adapterLstRingtone;
    private BaseAdapterListRingtone adapterLstRingtone;
    private List<RingTone> lstRing;
    private String id;
    private TextView toolbar_title;
    private ImageView iv_back;
    private String title = "";
    private ReadRingCategory readRingCategory;

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
    public String getTitle(){
        return title;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_lst_ringtones_categories;
    }

    @Override
    public void init(View view) {
        readRingCategory=new ReadRingCategory();
        lv_lstRingtones = view.findViewById(R.id.lv_lstRingtones);
        prLoading = view.findViewById(R.id.prLoading);
//        lstRing = new ArrayList<>();
        lstRing=(ArrayList)readRingCategory.getPlayList() ;

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
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
//        Cursor cursor=getActivity().getContentResolver().query(MediaStore.Audio.Media.getContentUriForPath(
//                Configs.PATH_STORAGE_CATEGORY+"/"+name),
//                null,selection,null,null);
        Cursor cursor=getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,selection,null,null);

        String dataPath="_data";
        int indexDataPath=cursor.getColumnIndex(dataPath);
        String displayName="_display_name";
        int indexDisplayName=cursor.getColumnIndex(displayName);
        String duration="duration";
        int indexDuration=cursor.getColumnIndex(duration);
        String artist="artist";
        int indexArtist=cursor.getColumnIndex(artist);
        cursor.moveToFirst();


        while (!cursor.isAfterLast()){
            String path=cursor.getString(indexDataPath);
            String nameRing=cursor.getString(indexDisplayName);
            String artistData=cursor.getString(indexArtist);
            String durationData=cursor.getString(indexDuration);

            RingTone ringtone=new RingTone();
            ringtone.setPath(path);
            ringtone.setName(nameRing);
            ringtone.setDuration(durationData);
            ringtones.add(ringtone);
            cursor.moveToNext();

        }
        cursor.close();
        return ringtones;
    }
}
