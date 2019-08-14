package com.optionringringtone.newringtonefree;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.optionringringtone.newringtonefree.Untils.CommonUntil;
import com.optionringringtone.newringtonefree.Untils.Configs;
import com.optionringringtone.newringtonefree.Untils.RetrofitUtil;
import com.optionringringtone.newringtonefree.Untils.SlideAnimationUtil;
import com.optionringringtone.newringtonefree.adapter.BaseAdapterLstRingtoneV2;
import com.optionringringtone.newringtonefree.adapter.SearchAdapter;
import com.optionringringtone.newringtonefree.object.KeyWord;
import com.optionringringtone.newringtonefree.object.ResponseDTO;
import com.optionringringtone.newringtonefree.object.RingTone;

import java.util.ArrayList;
import java.util.List;

import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, SearchAdapter.itemClickListSearch,
        TextView.OnEditorActionListener {

    private ImageView btnBack, btnSearchToolBar;
    private EditText edtSearch;
    private RecyclerView rcListKeySearch;
    private ListView rcListSearchResult;
    private FloatingActionButton fabRequestRingtone;
    private TextView tvNotFound;
    private ProgressBar prLoading;
    private SearchAdapter searchAdapter;
    private List<KeyWord> keySearchObjects;
    private List<RingTone> lstRing;
    private BaseAdapterLstRingtoneV2 adapterLstRingtone;
    private boolean isShowListSearch = true;
    private String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();
        initView();
        initData();
    }

    private void initData() {
        keySearchObjects = new ArrayList<>();
        searchAdapter = new SearchAdapter(keySearchObjects, this, this);
        rcListKeySearch.setAdapter(searchAdapter);

        lstRing = new ArrayList<>();
        adapterLstRingtone = new BaseAdapterLstRingtoneV2(this, lstRing);
        rcListSearchResult.setAdapter(adapterLstRingtone);
        rcListSearchResult.setDivider(null);
        getSuggestKey();
    }


    private void initView() {
        btnBack = findViewById(R.id.btnBack);
        rcListKeySearch = findViewById(R.id.rcListKeySearch);
        rcListSearchResult = findViewById(R.id.rcListSearchResult);
        btnSearchToolBar = findViewById(R.id.btnSearchToolBar);
        edtSearch = findViewById(R.id.edtSearch);
        fabRequestRingtone = findViewById(R.id.fabRequestRingtone);
        prLoading = findViewById(R.id.prLoading);
        tvNotFound = findViewById(R.id.txtNotFound);

        btnBack.setOnClickListener(this);
        btnSearchToolBar.setOnClickListener(this);
        fabRequestRingtone.setOnClickListener(this);
        edtSearch.setOnEditorActionListener(this);

        CommonUntil.setLayoutManager(this, rcListKeySearch, LinearLayout.VERTICAL);
//        CommonUntil.setLayoutManager(this, rcListSearchResult, LinearLayout.VERTICAL);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SlideAnimationUtil.overridePendingTransitionExit(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                if (isShowListSearch)
                    onBackPressed();
                else {
                    handleShowList();
                }
                break;
            case R.id.btnSearchToolBar:
                if (!edtSearch.getText().toString().equalsIgnoreCase("")) {
                    // Call api search ringtone
                    handleSearchRingtone(edtSearch.getText().toString());
                }
                break;
            case R.id.fabRequestRingtone:
                Intent intent = new Intent(this, RequestRingtoneActivity.class);
                startActivity(intent);
                SlideAnimationUtil.overridePendingTransitionEnter(this);
                break;
        }
    }

    private void showListResult() {
        rcListKeySearch.setVisibility(View.GONE);
        rcListSearchResult.setVisibility(View.VISIBLE);
        isShowListSearch = false;
    }


    private void handleSearchRingtone(String keySearch) {
        prLoading.setVisibility(View.VISIBLE);
        lstRing.clear();
        RetrofitUtil.getApi().getRingtoneByKey(keySearch).enqueue(new Callback<ResponseDTO<ArrayList<RingTone>>>() {
            @Override
            public void onResponse(Call<ResponseDTO<ArrayList<RingTone>>> call, Response<ResponseDTO<ArrayList<RingTone>>> response) {
                prLoading.setVisibility(View.GONE);
                if (response.code() == 200) {
                    Log.i(TAG, "onResponse: link " + call.request().toString());
                    Log.i(TAG, "onResponse: " + response.body());
                    ArrayList<RingTone> lstData = response.body().getData();
                    if(CommonUntil.isNullorEmty(lstData))
                    {
                         tvNotFound.setVisibility(View.VISIBLE);
                    }else {
                        tvNotFound.setVisibility(View.GONE);
                        lstRing.addAll(lstData);

                    }
                    adapterLstRingtone.notifyDataSetChanged();
                    //show list ringtone result
                    showListResult();
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<ArrayList<RingTone>>> call, Throwable t) {
                prLoading.setVisibility(View.GONE);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void getSuggestKey() {
        prLoading.setVisibility(View.VISIBLE);
        RetrofitUtil.getApi().getSuggestKeyWord(Configs.COUNTRY).enqueue(new Callback<ResponseDTO<ArrayList<KeyWord>>>() {
            @Override
            public void onResponse(Call<ResponseDTO<ArrayList<KeyWord>>> call, Response<ResponseDTO<ArrayList<KeyWord>>> response) {
                prLoading.setVisibility(View.GONE);
                if (response.code() == 200) {
                    Log.i(TAG, "onResponse: link " + call.request().toString());
                    Log.i(TAG, "onResponse: " + response.body());
                    ArrayList<KeyWord> lstData = response.body().getData();
                    if(lstData == null)
                        lstData = new ArrayList<>();
                    keySearchObjects.addAll(lstData);
                    searchAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<ArrayList<KeyWord>>> call, Throwable t) {
                prLoading.setVisibility(View.GONE);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void handleShowList() {
        if (isShowListSearch) {
            rcListKeySearch.setVisibility(View.GONE);
            rcListSearchResult.setVisibility(View.VISIBLE);
            isShowListSearch = false;
        } else {
            rcListKeySearch.setVisibility(View.VISIBLE);
            rcListSearchResult.setVisibility(View.GONE);
            tvNotFound.setVisibility(View.GONE);
            isShowListSearch = true;
        }
    }

    @Override
    public void onClick(int position) {
        // Click ket search
        if (keySearchObjects.get(position).getName() != null) {
            edtSearch.setText(keySearchObjects.get(position).getName());
            handleSearchRingtone(keySearchObjects.get(position).getName());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        edtSearch.setText("");
        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("app", "Network connectivity change " + isNetworkAvailable());
            if (!isNetworkAvailable()) {
                CommonUntil.showDialogInternetStatus(SearchActivity.this);
            }
        }
    };

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            handleSearchRingtone(edtSearch.getText().toString());
            return true;
        }
        return false;
    }
}
