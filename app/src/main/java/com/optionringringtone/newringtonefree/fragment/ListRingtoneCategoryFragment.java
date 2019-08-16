package com.optionringringtone.newringtonefree.fragment;

import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.optionringringtone.newringtonefree.Untils.Configs;
import com.optionringringtone.newringtonefree.Untils.FragmentCommon;
import com.optionringringtone.newringtonefree.adapter.BaseAdapterListRingtone;
import com.optionringringtone.newringtonefree.mysetting.controlApp.ReadJsonFile;
import com.optionringringtone.newringtonefree.object.CategoryName;
import com.optionringringtone.newringtonefree.object.detailcategory.Ringtone;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;

public class ListRingtoneCategoryFragment extends FragmentCommon implements View.OnClickListener{
    private static final String TAG = "DetailCategoriesFragment";
    private ProgressBar prLoading;
    private ListView lv_lstRingtones;
    private BaseAdapterListRingtone adapterLstRingtone;
    private List<Ringtone> lstRing;
    private String id;
    private TextView toolbar_title;
    private ImageView iv_back;
    private String title = "";
    private Ringtone ringtone;
    private List<Ringtone> ringtoneList=new ArrayList<>();

    public ListRingtoneCategoryFragment setId(String id) {
        this.id = id;
        return this;
    }

    public ListRingtoneCategoryFragment setTitle(String title) {
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
        adapterLstRingtone = new BaseAdapterListRingtone(activity, lstRing);
        lv_lstRingtones.setAdapter(adapterLstRingtone);
        lv_lstRingtones.setDivider(null);
        toolbar_title = view.findViewById(R.id.toolbar_title);
        toolbar_title.setText(title);

        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        getDataRingtonePopular("Most Popular");
    }

    private List<Ringtone> getDataRingtonePopular(String name) {
        ReadJsonFile readJsonFile=new ReadJsonFile();
        return readJsonFile.readFile(name);
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
