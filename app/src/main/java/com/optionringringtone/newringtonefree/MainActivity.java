package com.optionringringtone.newringtonefree;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.optionringringtone.newringtonefree.Untils.CommonUntil;
import com.optionringringtone.newringtonefree.Untils.Configs;
import com.optionringringtone.newringtonefree.Untils.SlideAnimationUtil;
import com.optionringringtone.newringtonefree.adapter.ViewPagerAdapter;
import com.optionringringtone.newringtonefree.fragment.CategoriesFragment;
import com.optionringringtone.newringtonefree.fragment.CategoryFragment;
import com.optionringringtone.newringtonefree.fragment.PopularFragment;
import com.optionringringtone.newringtonefree.mysetting.AllSetting;
import com.optionringringtone.newringtonefree.mysetting.Settting1;
import com.optionringringtone.newringtonefree.mysetting.controlApp.AdmodControl;
import com.optionringringtone.newringtonefree.mysetting.controlApp.FacebookControl;

import java.util.Locale;

import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {
    private BottomNavigationView navigation;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ViewPager viewPager;
    private RelativeLayout msplash_view;
    private ProgressBar progress_loadding;
    private FloatingActionButton fabSearch;
    private CategoryFragment categoriesFragment;
//    private CategoriesFragment categoriesFragment;
    private PopularFragment popularFragment;
    MenuItem prevMenuItem;
    private String TAG = "MainActivity";
    private int READ_WRITE_REQUEST = 1110;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        setContentView(R.layout.activity_main);
        Configs.COUNTRY = Locale.getDefault().getCountry();
        AllSetting.initSetting(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        initView();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.color_white));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //Bottom navigation + viewpager
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager.addOnPageChangeListener(onViewpagerOnpageChanger);
        setupViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkChangeReceiver);
        msplash_view.setVisibility(View.GONE);
        Settting1.onCallLoadAdsIn(MainActivity.this);
    }

    private void initView() {
        msplash_view = findViewById(R.id.splash_view);
        progress_loadding = findViewById(R.id.progress_loadding);
        msplash_view.setVisibility(View.VISIBLE);
        msplash_view.setOnClickListener(null);
        try {
            Settting1.loadAndShowStartApp(this, new Settting1.CallbackLoad() {
                @Override
                public void Loaded(InterstitialAd mInterstitialAd) {


//                mInterstitialAd2 = mInterstitialAd;
                    progress_loadding.setVisibility(View.GONE);
                    loadedDataADM(mInterstitialAd);
//                    canBack = true;
                }

                @Override
                public void LoadedFailAds() {
                    loadFailAds();
//                    canBack = true;
                }

                @Override
                public void Loaded(com.facebook.ads.InterstitialAd mInterstitialAd_start) {
//                mInterstitialAd3 = mInterstitialAd_start;
                    progress_loadding.setVisibility(View.GONE);
                    loadedDataFacebook(mInterstitialAd_start);
//                    canBack = true;
                }

                @Override
                public void Loaded() {
//                    loadedDataAPT();
//                    canBack = true;
                }

                @Override
                public void Loaded2(InterstitialAd interAdmIn_start) {
                    progress_loadding.setVisibility(View.GONE);
                    loadedDataADM2(interAdmIn_start);
//                    canBack = true;
                }
            }, new Settting1.CallbackShow() {
                @Override
                public void Displayed() {

                }

                @Override
                public void Close() {
                    msplash_view.setVisibility(View.GONE);
                    Settting1.onCallLoadAdsIn(MainActivity.this);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            new Handler().postDelayed(()->{
                msplash_view.setVisibility(View.GONE);
                Settting1.onCallLoadAdsIn(MainActivity.this);
            },1500);
        }

        drawer = findViewById(R.id.drawer_layout);
        navigation = findViewById(R.id.navigation);
        navigationView = findViewById(R.id.nav_view);
        viewPager = findViewById(R.id.viewpager);
        fabSearch = findViewById(R.id.fabSearch);
        fabSearch.setOnClickListener(this);
    }


//    public void loadedDataAPT() {
////        btContinue.setVisibility(View.VISIBLE);
////        btContinue.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
//
//        if (AdsAppota.getStartAds()) {
//            final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
//            mProgressDialog.setTitle("");
//            mProgressDialog.setMessage(getString(R.string.show_ads));
//            mProgressDialog.setCanceledOnTouchOutside(true);
//            mProgressDialog.show();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mProgressDialog.cancel();
//                    if (!AdsAppota.showAds(true, new SettingIN.CallbackShow() {
//
//                        @Override
//                        public void Displayed() {
//
//                        }
//
//                        @Override
//                        public void Close() {
//                            if (mCallbackApi != null)
//                                mCallbackApi.clickContinueFrmLoading();
//                        }
//                    })) {
//                        if (mCallbackApi != null) mCallbackApi.clickContinueFrmLoading();
//                    }
//                }
//            }, 2500);
//
//        } else {
//            if (mCallbackApi != null) mCallbackApi.clickContinueFrmLoading();
//        }
//
////            }
////        });
//    }

    public void loadedDataFacebook(final com.facebook.ads.InterstitialAd mInterstitialAd_start) {

        if (mInterstitialAd_start != null && !mInterstitialAd_start.isAdInvalidated()) {
            final ProgressDialog mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setTitle("");
            mProgressDialog.setMessage(getString(R.string.show_ads));
            mProgressDialog.setCanceledOnTouchOutside(true);
            mProgressDialog.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mProgressDialog.cancel();
                    if (!FacebookControl.showInAppFb(mInterstitialAd_start, new Settting1.CallbackShow() {

                        @Override
                        public void Displayed() {

                        }

                        @Override
                        public void Close() {
                            msplash_view.setVisibility(View.GONE);
                            Settting1.onCallLoadAdsIn(MainActivity.this);
                        }
                    })) {
                        Settting1.onCallLoadAdsIn(MainActivity.this);
                        msplash_view.setVisibility(View.GONE);
                    }
                }
            }, 2000);
//

        } else {
            msplash_view.setVisibility(View.GONE);
            Settting1.onCallLoadAdsIn(MainActivity.this);
        }
//        }
//        });
    }

    public void loadFailAds() {

        new Handler().postDelayed(()->{
            msplash_view.setVisibility(View.GONE);
            Settting1.onCallLoadAdsIn(MainActivity.this);
        },1500);
    }

    public void loadedDataADM2(final InterstitialAd interAdmIn_start) {
        if (AdmodControl.getStartAds(interAdmIn_start)) {
            final ProgressDialog mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setTitle("");
            mProgressDialog.setMessage(getString(R.string.show_ads));
            mProgressDialog.setCanceledOnTouchOutside(true);
            mProgressDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mProgressDialog.cancel();
                    if (!AdmodControl.showAdsStartADM(interAdmIn_start, new Settting1.CallbackShow() {

                        @Override
                        public void Displayed() {

                        }

                        @Override
                        public void Close() {
                            msplash_view.setVisibility(View.GONE);
                            Settting1.onCallLoadAdsIn(MainActivity.this);
                        }

                    })) {
                        msplash_view.setVisibility(View.GONE);
                        Settting1.onCallLoadAdsIn(MainActivity.this);
                    }
                }
            }, 2000);

        } else {
            msplash_view.setVisibility(View.GONE);
            Settting1.onCallLoadAdsIn(MainActivity.this);
        }

//            }
//        });
    }


    public void loadedDataADM(final InterstitialAd mInterstitialAd) {

        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            final ProgressDialog mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setTitle("");
            mProgressDialog.setMessage(getString(R.string.show_ads));
            mProgressDialog.setCanceledOnTouchOutside(true);
            mProgressDialog.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mProgressDialog.cancel();
                    if (
                            !AdmodControl.showAdsStartADM(mInterstitialAd, new Settting1.CallbackShow() {

                                @Override
                                public void Displayed() {

                                }

                                @Override
                                public void Close() {
                                    msplash_view.setVisibility(View.GONE);
                                    Settting1.onCallLoadAdsIn(MainActivity.this);
                                }
                            })) {
                        msplash_view.setVisibility(View.GONE);
                        Settting1.onCallLoadAdsIn(MainActivity.this);
                    }

                }
            }, 2000);


        } else {
            msplash_view.setVisibility(View.GONE);
            Settting1.onCallLoadAdsIn(MainActivity.this);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(0);
                    // Click here open link
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Configs.WEB_URL));
                    startActivity(browserIntent);
                    return true;
            }
            return false;
        }
    };

    private ViewPager.OnPageChangeListener onViewpagerOnpageChanger = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position != 0 && popularFragment != null)
                popularFragment.stopAudio();

            if (prevMenuItem != null) {
                prevMenuItem.setChecked(false);
            } else {
                navigation.getMenu().getItem(0).setChecked(false);
            }
            Log.d("page", "onPageSelected: " + position);
            if (position == 0)
                navigation.getMenu().getItem(position).setChecked(true);
            prevMenuItem = navigation.getMenu().getItem(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_rate) {
            // Handle the vote action
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        } else if (id == R.id.nav_folder_download) {
            Intent intent = new Intent(this, MyDownloadActivity.class);
            startActivity(intent);
            SlideAnimationUtil.overridePendingTransitionEnter(this);
        } else if (id == R.id.nav_suggest_ringtones) {
            Intent intent = new Intent(this, RequestRingtoneActivity.class);
            startActivity(intent);
            SlideAnimationUtil.overridePendingTransitionEnter(this);
            popularFragment.stopAudio();
        } else if (id == R.id.nav_mail_feedback) {
            Intent i = new Intent(Intent.ACTION_SENDTO);
            i.setData(Uri.parse(Configs.MAIL_TO));
            i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.feedback_support));
            startActivity(Intent.createChooser(i, getResources().getString(R.string.send_feedback)));
        } else if (id == R.id.nav_reset_ringtone) {
            showAlertDialogResetRingtone();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void resetAll() {

        try {
            resetRingtone();
            resetNotification();
            resetAlarm();
            CommonUntil.createDialog(this, getString(R.string.change_success), getString(R.string.app_name)).show();
            Settting1.showAdsIn(false, this, new Settting1.CallbackShow() {
                @Override
                public void Displayed() {

                }

                @Override
                public void Close() {
                    Settting1.onCallLoadAdsIn(MainActivity.this);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            CommonUntil.createDialog(this, getString(R.string.has_problem), getString(R.string.app_name)).show();
        }
    }

    private void checkPermissionForResetRingtone() {
        if (CommonUntil.checkCanBeChangeSystemSetting(this, READ_WRITE_REQUEST)) {
            resetAll();
        }
    }

    public void showAlertDialogResetRingtone() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("Are you sure you want to reset all ringtone?");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                checkPermissionForResetRingtone();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void resetRingtone() {

        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor cursor = manager.getCursor();
        while (cursor.moveToNext()) {
            String title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String uri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);
            Log.i(TAG, "resetRingtone: " + title + "-" + uri);
            Uri ringtoneURI = manager.getRingtoneUri(cursor.getPosition());
            Log.i(TAG, "resetRingtone: " + ringtoneURI.getPath());
            RingtoneManager.setActualDefaultRingtoneUri(
                    getApplicationContext(),
                    RingtoneManager.TYPE_RINGTONE, ringtoneURI);
            break;
            // Do something with the title and the URI of ringtone
        }
    }

    private void resetNotification() {

        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_NOTIFICATION);
        Cursor cursor = manager.getCursor();
        while (cursor.moveToNext()) {
            String title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String uri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);
            Log.i(TAG, "resetRingtone: " + title + "-" + uri);
            Uri ringtoneURI = manager.getRingtoneUri(cursor.getPosition());
            Log.i(TAG, "resetRingtone: " + ringtoneURI.getPath());
            RingtoneManager.setActualDefaultRingtoneUri(
                    getApplicationContext(),
                    RingtoneManager.TYPE_NOTIFICATION, ringtoneURI);
            break;
            // Do something with the title and the URI of ringtone
        }
    }

    private void resetAlarm() {

        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_ALARM);
        Cursor cursor = manager.getCursor();
        while (cursor.moveToNext()) {
            String title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String uri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);
            Log.i(TAG, "resetRingtone: " + title + "-" + uri);
            Uri ringtoneURI = manager.getRingtoneUri(cursor.getPosition());
            Log.i(TAG, "resetRingtone: " + ringtoneURI.getPath());
            RingtoneManager.setActualDefaultRingtoneUri(
                    getApplicationContext(),
                    RingtoneManager.TYPE_ALARM, ringtoneURI);
            break;
            // Do something with the title and the URI of ringtone
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        categoriesFragment = new CategoriesFragment();
        categoriesFragment = new CategoryFragment();
        popularFragment = new PopularFragment();
        adapter.addFragment(popularFragment);
        adapter.addFragment(categoriesFragment);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabSearch:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                SlideAnimationUtil.overridePendingTransitionEnter(this);
                popularFragment.stopAudio();
                break;
        }
    }


    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("app", "Network connectivity change " + isNetworkAvailable());
            if (!isNetworkAvailable()) {
                CommonUntil.showDialogInternetStatus(MainActivity.this);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == READ_WRITE_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                resetAll();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AllSetting.onDestroyAct();
    }


    private void checkPermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,},
                1);
    }
}
