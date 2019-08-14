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
import com.optionringringtone.newringtonefree.adapter.BaseAdapterLstRingtoneV2;
import com.optionringringtone.newringtonefree.object.CategoryName;
import com.optionringringtone.newringtonefree.object.detailcategory.Ringtone;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
        getDataRingtonePopular();
    }

    private List<Ringtone> getDataRingtonePopular() {
        try {

            JSONObject jsonObj = new JSONObject(loadJsonInStorage());
            JSONArray data  = jsonObj.getJSONArray("ringtones");
            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);


                String file_name = c.getString("file_name");

                ringtone=new Ringtone();
                ringtone.setFileName(file_name);

                ringtoneList.add(ringtone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ringtoneList;
    }

    public String loadJsonInStorage(){
        CategoryName categoryName=new CategoryName();
        String jsonStr = null;

        try {
            File yourFile = new File(Environment.getExternalStorageDirectory(), Configs.PATH_STORAGE_CATEGORY+"/"+categoryName.getEn()+"ringtones.json");
            FileInputStream stream = new FileInputStream(yourFile);
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

            jsonStr = Charset.defaultCharset().decode(bb).toString();
            stream.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return jsonStr;
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
