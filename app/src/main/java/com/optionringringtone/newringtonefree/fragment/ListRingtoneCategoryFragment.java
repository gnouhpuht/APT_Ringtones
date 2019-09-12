package com.optionringringtone.newringtonefree.fragment;

import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.optionringringtone.newringtonefree.Untils.CommonUntil;
import com.optionringringtone.newringtonefree.Untils.Configs;
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
//    private String path = Environment.getExternalStorageDirectory().toString() + Configs.PATH_STORAGE_CATEGORY + getTitle();


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
//        lstRing = getRingCategory(path);
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
        String path=getPathCategory(name);

//        String path = Environment.getExternalStorageDirectory().toString()+ Configs.PATH_STORAGE_CATEGORY+name +"/"+
//                name.replace(" ","_");
//        String path= CommonUntil.getPathCategory(name);
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

    private String getPathCategory(String name){
        String path=Environment.getExternalStorageDirectory().toString()+ Configs.PATH_STORAGE_CATEGORY+name +"/"+
                name.replace(" ","_");
        if (name=="Most Popular"||name=="Electronic Music"){
            path = Environment.getExternalStorageDirectory().toString()+ Configs.PATH_STORAGE_CATEGORY+name +"/"+
                    name.replace(" ","_");
        }else if (name=="National Anthems A-J"||name=="National Anthems K-Z"||name=="Animal Sounds"||name=="Christmas"||name=="Islamic Ringtones"){
            path = Environment.getExternalStorageDirectory().toString()+ Configs.PATH_STORAGE_CATEGORY+name ;
        }else if (name=="Alarms"||name=="Nature"||name=="Business"||name=="Halloween"||name=="Japanese"){
            path = Environment.getExternalStorageDirectory().toString()+ Configs.PATH_STORAGE_CATEGORY+name+"/"+name ;
        }else if (name=="The Best of 2019"){
            path = Environment.getExternalStorageDirectory().toString()+ Configs.PATH_STORAGE_CATEGORY+name+"/The_Best_of_2018";
        }else if (name=="Rock Music"){
            path = Environment.getExternalStorageDirectory().toString()+ Configs.PATH_STORAGE_CATEGORY+name+"/Rock";
        }else if (name=="Hip Hop Music"){
            path = Environment.getExternalStorageDirectory().toString()+ Configs.PATH_STORAGE_CATEGORY+name+"/Hip_Hop";
        }else if (name=="Notifications"){
            path = Environment.getExternalStorageDirectory().toString()+ Configs.PATH_STORAGE_CATEGORY+name+"/Notification";
        }else if (name==""){
            path = Environment.getExternalStorageDirectory().toString()+ Configs.PATH_STORAGE_CATEGORY+name+"/Notification";
        }
        return path;
    }

//    private List<RingTone> getRingCategory(String rootPath) {
//        List<RingTone> ringTones = new ArrayList<>();
//        try {
//            File rootFile = new File(rootPath);
//            File[] files = rootFile.listFiles();
//            if (files != null && files.length > 0) {
//                for (File file : files) {
//                    if (file.isDirectory()) {
//                        if (getRingCategory(file.getAbsolutePath()) != null ) {
//                            ringTones.addAll(getRingCategory(file.getAbsolutePath()));
//                        } else {
//                            break;
//                        }
//                    } else if (file.getName().endsWith(".mp3")) {
//                        RingTone ringTone = new RingTone();
//                        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
//                        metadataRetriever.setDataSource(file.getAbsolutePath());
//                        String duration = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
//                        long dura = Long.parseLong(duration);
//                        if (dura > 0) {
//                            File from = null;
//                            if (file.getName().startsWith(".")) {
//                                String newName = file.getName().substring(1);
//                                from = new File(file, newName);
//                                ringTone.setName(from.getName());
//                            } else {
//                                ringTone.setName(file.getName());
//                            }
//                            ringTone.setPath(file.getAbsolutePath());
//                            ringTone.setTimeDownload(file.lastModified());
//                            ringTones.add(ringTone);
//                        }
//                    }
//                }
//            }
//
////
////            try {
////                if (ringTones != null && ringTones.size() > 2) {
////                    Collections.sort(ringTones, new  ComparatorDataRingtoneLocal());
////                }
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
//
////            Log.d("aaaaaaaaaaaaa", "getRingCategory: "+ringTones.size());
//            return ringTones;
//        } catch (Exception e) {
//            return null;
//        }
//
//    }
}
