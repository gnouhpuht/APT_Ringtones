package com.optionringringtone.newringtonefree.fragment;


import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.recyclerview.widget.RecyclerView;
import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;
import com.optionringringtone.newringtonefree.Untils.CommonUntil;
import com.optionringringtone.newringtonefree.Untils.Configs;
import com.optionringringtone.newringtonefree.Untils.FragmentCommon;
import com.optionringringtone.newringtonefree.Untils.RetrofitUtil;
import com.optionringringtone.newringtonefree.adapter.AdapterCategories;
import com.optionringringtone.newringtonefree.object.CategoryOBJ;
import com.optionringringtone.newringtonefree.object.ResponseDTO;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 */
public class CategoriesFragment extends FragmentCommon {


    private static final String TAG = "CategoriesFragment";
    private ProgressBar prLoading;
    RecyclerView rc_lst_ring_stone;

    AdapterCategories adapterLstRingtone;
    private ArrayList<CategoryOBJ> lstCategories;


    @Override
    public int getLayout() {
        return R.layout.fragment_list_categories;
    }

    @Override
    public void init(View view) {
        rc_lst_ring_stone = view.findViewById(R.id.rc_lst_ring_stone);
        prLoading = view.findViewById(R.id.prLoading);
        lstCategories = new ArrayList<>();
        CommonUntil.setLayoutManager(activity, rc_lst_ring_stone, LinearLayout.VERTICAL);
        adapterLstRingtone = new AdapterCategories(activity, lstCategories);
        rc_lst_ring_stone.setAdapter(adapterLstRingtone);
       getDataRingtonePopular();
    }

    private void getDataRingtonePopular() {
        prLoading.setVisibility(View.VISIBLE);
        RetrofitUtil.getApi().getCategoriesRingtones(Configs.COUNTRY).enqueue(new Callback<ResponseDTO<ArrayList<CategoryOBJ>>>() {
            @Override
            public void onResponse(Call<ResponseDTO<ArrayList<CategoryOBJ>>> call, Response<ResponseDTO<ArrayList<CategoryOBJ>>> response) {
                prLoading.setVisibility(View.GONE);
                if (response.code() == 200) {
                    Log.i(TAG, "onResponse: link " + call.request().toString());
                    Log.i(TAG, "onResponse: " + response.body());
                    ArrayList<CategoryOBJ> lstData = response.body().getData();
                    if(lstData == null)
                        lstData = new ArrayList<>();
                    lstCategories.clear();
                    lstCategories.addAll(lstData);
                    adapterLstRingtone.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<ArrayList<CategoryOBJ>>> call, Throwable t) {
                prLoading.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
