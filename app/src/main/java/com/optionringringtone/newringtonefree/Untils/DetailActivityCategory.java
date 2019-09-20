package com.optionringringtone.newringtonefree.Untils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.optionringringtone.newringtonefree.mysetting.Settting1;
import com.optionringringtone.newringtonefree.object.CategoryName;
import com.optionringringtone.newringtonefree.object.RingTone;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;

public class DetailActivityCategory extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgPlayPauseMusic;
    private ImageView imgDownloadAndDeleteMusic;
    private RelativeLayout btnPlayPauseMusic;
    private LinearLayout btnSetDefaultRingtone, btnSetDefaultNotification, btnSetDefaultAlarm, btnSetRingtoneContact, btnDownLoadAndDeleteMusic;
    private TextView txtTimeDuration, txtTotalTime, txtNameMusic;
    private ImageButton btnBack;
    private TextView txtActionDownLoadOrDelete;
    private SeekBar skMusic;
    private RingTone ringTone;
    private RingTone ringToneDataPlaying;
    private boolean isPlaying;
    private MediaUntil mMediaInstance;
    private MediaPlayer mediaPlayer;
    public Handler mHandler;
    private Runnable runnable;
    private MediaUntil.onChangeListener onChangeListener;
    private static int type;
    private String TAG = "DetailActivity";
    private int DELETE_FILE_REQUEST = 1112;
    private int DOWNLOAD_FILE_REQUEST = 1111;
    private int READ_WRITE_REQUEST = 1110;
    private int PER_CONTACT = 1113;
    private int PICK_CONTACT = 1114;
    private final static int TYPE_RINGTONE_CONTACT = 9997;
    private List<RingTone> ringTonesShared = new ArrayList<>();
    private SharePreferenceUntil sharePreferenceUntil;
    private String cNumber;
    private String title="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_download);
        getSupportActionBar().hide();
        initData();

        initMusic();
    }



    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    private void initMusic() {
        mMediaInstance = MediaUntil.getInstance();
        isPlaying = false;
        runnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer.isPlaying()) {
                    skMusic.setMax(mMediaInstance.getDuration());
                    skMusic.setProgress(mMediaInstance.getCurrentPosition());
                    txtTimeDuration.setText(milliSecondsToTimer(mMediaInstance.getCurrentPosition()));
                    txtTotalTime.setText(milliSecondsToTimer(mMediaInstance.getDuration()));
                } else {
                    skMusic.setProgress(0);
                    finishMedia();
                }
                if (isPlaying)
                    mHandler.postDelayed(this, 50);
            }
        };
        onChangeListener = new MediaUntil.onChangeListener() {
            @Override
            public void onReady() {
                mHandler.post(runnable);
            }

            @Override
            public void onError() {
                finishMedia();
            }
        };
    }

    private void finishMedia() {
        isPlaying = false;
        txtTimeDuration.setText(milliSecondsToTimer(0));
        imgPlayPauseMusic.setImageDrawable(this.getResources().getDrawable(R.drawable.icons8_play_64));
    }

    public String milliSecondsToTimer(int milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    private void initData() {
        sharePreferenceUntil = SharePreferenceUntil.getInstance(this);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle == null) return;
        if (bundle.containsKey("rintoneData")) {
            ringTone = (RingTone) bundle.getSerializable("rintoneData");
        }
        if (ringTone == null) {
            showDiaLogTrouble();
            return;
        }
        if (CommonUntil.isNullorEmty(ringTone.getUrl())) {
            initView();
            showDiaLogTrouble();
            return;
        }
        title=bundle.getString("title");
        initView();
    }

    @SuppressLint("WrongViewCast")
    private void initView() {
        btnBack = findViewById(R.id.btnBack);
        imgPlayPauseMusic = findViewById(R.id.imgPlayPauseMusic);
//        imgDownloadAndDeleteMusic = findViewById(R.id.imgDownloadAndDeleteMusic);
        btnPlayPauseMusic = findViewById(R.id.btnPlayPauseMusic);
//        btnDownLoadAndDeleteMusic = findViewById(R.id.btnDownLoadAndDeleteMusic);
        btnSetDefaultRingtone = findViewById(R.id.btnSetDefaultRingtone);
        btnSetDefaultNotification = findViewById(R.id.btnSetDefaultNotification);
        btnSetDefaultAlarm = findViewById(R.id.btnSetDefaultAlarm);
        btnSetRingtoneContact = findViewById(R.id.btnSetRingtoneContact);
        txtTimeDuration = findViewById(R.id.txtTimeDuration);
        txtTotalTime = findViewById(R.id.txtTotalTime);
        skMusic = findViewById(R.id.skMusic);
//        txtActionDownLoadOrDelete = findViewById(R.id.txtActionDownLoadOrDelete);
        txtNameMusic = findViewById(R.id.txtNameMusic);
        btnBack.setOnClickListener(this);
        imgPlayPauseMusic.setOnClickListener(this);
//        imgDownloadAndDeleteMusic.setOnClickListener(this);
        btnPlayPauseMusic.setOnClickListener(this);
//        btnDownLoadAndDeleteMusic.setOnClickListener(this);
        btnSetDefaultRingtone.setOnClickListener(this);
        btnSetDefaultNotification.setOnClickListener(this);
        btnSetDefaultAlarm.setOnClickListener(this);
        btnSetRingtoneContact.setOnClickListener(this);
        mHandler = new Handler();
        txtTotalTime.setText("--");
        txtTimeDuration.setText(milliSecondsToTimer(0));
//        resetDownloadView();
        txtNameMusic.setText(ringTone.getName());
    }

//    private void resetDownloadView() {
//        if (CommonUntil.isExistFile(ringTone.getName(), ringTone.getId() + "")) {
//            txtActionDownLoadOrDelete.setText(getResources().getString(R.string.delete));
//            imgDownloadAndDeleteMusic.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_delete_black_24dp));
//        } else {
//            txtActionDownLoadOrDelete.setText(getResources().getString(R.string.download));
//            imgDownloadAndDeleteMusic.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_file_download_black_24dp));
//        }
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.imgPlayPauseMusic:
                checkBegin(ringTone);
                break;
//            case R.id.imgDownloadAndDeleteMusic:
//                showDialogConfirmDownLoad();
//                break;
//            case R.id.btnDownLoadAndDeleteMusic:
//                showDialogConfirmDownLoad();
//                break;
            case R.id.btnPlayPauseMusic:
                checkBegin(ringTone);
                break;
            case R.id.btnSetDefaultRingtone:
                type = RingtoneManager.TYPE_RINGTONE;
                showAlertDialogSetRingtone();
                break;
            case R.id.btnSetDefaultNotification:
                type = RingtoneManager.TYPE_NOTIFICATION;
                showAlertDialogSetRingtone();
                break;
            case R.id.btnSetDefaultAlarm:
                type = RingtoneManager.TYPE_ALARM;
                showAlertDialogSetRingtone();
                break;
            case R.id.btnSetRingtoneContact:
                type = TYPE_RINGTONE_CONTACT;
                showAlertDialogSetRingtone();
                break;
        }
    }


    private void showDialogConfirmDownLoad() {
        String message = "Are you sure you want to download this ringtone?";
        if (txtActionDownLoadOrDelete.getText().toString().equalsIgnoreCase(getResources().getString(R.string.delete))) {
            message = "Are you sure you want to delete this ringtone?";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (txtActionDownLoadOrDelete.getText().toString().equalsIgnoreCase(getResources().getString(R.string.delete))) {
                    //delete file
                    if (CommonUntil.checkIfAlreadyhavePermission(DetailActivityCategory.this, new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
                        //show dialog confirm delete
                        showAlertDialogDeleteFile();
                    } else {
                        CommonUntil.requestPermission(DetailActivityCategory.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS}, DELETE_FILE_REQUEST);
                    }
                } else {
                    if (CommonUntil.checkIfAlreadyhavePermission(DetailActivityCategory.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
                        downloadFileOnly();
                    } else {
                        CommonUntil.requestPermission(DetailActivityCategory.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS}, DOWNLOAD_FILE_REQUEST);
                    }
                }
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


    public void showAlertDialogDeleteFile() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("Are you sure you want to delete this ringtone?");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ringTone.setPath(null);
                CommonUntil.deleteFile(ringTone.getName(), ringTone.getId() + "");
                deleteRingtoneFromListShared();
                txtActionDownLoadOrDelete.setText(getResources().getString(R.string.download));
                imgDownloadAndDeleteMusic.setImageDrawable(DetailActivityCategory.this.getResources().getDrawable(R.drawable.ic_file_download_black_24dp));
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

    public void showAlertDialogSetRingtone() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage(getResources().getString(R.string.are_you_sure_set));
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string._ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (type == TYPE_RINGTONE_CONTACT) {
                    if (CommonUntil.checkIfAlreadyhavePermission(DetailActivityCategory.this, new String[]{Manifest.permission.READ_CONTACTS,
                            Manifest.permission.WRITE_CONTACTS})) {
                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, PICK_CONTACT);
                    } else {
                        CommonUntil.requestPermission(DetailActivityCategory.this, new String[]{Manifest.permission.READ_CONTACTS,
                                Manifest.permission.WRITE_CONTACTS}, PER_CONTACT);
                    }
                } else {
                    checkPermissionForDownloadFile();
                }

            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void checkBegin(RingTone ringToneData) {
        if (ringToneDataPlaying != null && ringToneDataPlaying.equals(ringToneData)) {
            if (!isPlaying) {
                play(ringToneData);
                imgPlayPauseMusic.setImageDrawable(this.getResources().getDrawable(R.drawable.icons8_pause_64));
            } else {
                mHandler.removeCallbacks(runnable);
                isPlaying = false;
                mMediaInstance.pause();
                imgPlayPauseMusic.setImageDrawable(this.getResources().getDrawable(R.drawable.icons8_play_64));
            }
        } else {
            play(ringToneData);
            imgPlayPauseMusic.setImageDrawable(this.getResources().getDrawable(R.drawable.icons8_pause_64));
        }
    }

    //play music
    private void play(RingTone ringToneData) {
        ringToneDataPlaying = ringToneData;
        if (!CommonUntil.isNullorEmty(ringToneData.getUrl())) {
            Log.i(TAG, "Url-play: " + ringToneData.getUrl());
            String url = ringToneData.getUrl();
            if (!CommonUntil.isNullorEmty(ringToneData.getPath()))
                url = ringToneData.getPath();
            mediaPlayer = mMediaInstance.playMusic(this, url, ringToneData.getId() + "", onChangeListener);
            isPlaying = true;
        } else {
            isPlaying = false;
            ringToneDataPlaying = null;
            CommonUntil.createDialog(this, getString(R.string.errorProcess), null).show();
        }
    }

    private void downloadFileOnly() {
        CommonUntil.downLoadFile(this, ringTone, uri -> {
            Log.i(TAG, "downloadFileOnly- URI FILE: " + uri);
            if (uri == null) {
                CommonUntil.createDialog(this, getString(R.string.has_problem), getString(R.string.app_name)).show();
                return;
            }

            // TODO: 22/06/2019 Download file success => save music to shared-file
            saveListRingtoneToShared();
            // TODO: 22/06/2019 update view download
            txtActionDownLoadOrDelete.setText(getResources().getString(R.string.delete));
            imgDownloadAndDeleteMusic.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_delete_black_24dp));


            Settting1.showAdsIn(false, this, new Settting1.CallbackShow() {
                @Override
                public void Displayed() {

                }

                @Override
                public void Close() {
                    Settting1.onCallLoadAdsIn(DetailActivityCategory.this);
                }
            });

        });
    }

    private void saveListRingtoneToShared() {
        if (ringTone == null) return;
        String jsonListData = sharePreferenceUntil.getJsonListMusic();
        ringTonesShared = CommonUntil.convertJsonRingtonToList(jsonListData);
        String filePath = Configs.FOLDER + ringTone.getName() + "_ID_" + ringTone.getId() + ".mp3";
        RingTone ringToneSave = ringTone;
        ringToneSave.setPath(filePath);
        ringToneSave.setDownLoad(true);
        if (ringTonesShared != null) {
            if (!isExistRingtoneInShared(ringTonesShared, ringToneSave.getId())) {
                ringTonesShared.add(ringToneSave);
                Gson gson = new Gson();
                String json = gson.toJson(ringToneSave);
                Log.i(TAG, "saveListRingtoneToShared: " + json);
                Log.i(TAG, "saveListRingtoneToShared: size list " + ringTonesShared.size());
                sharePreferenceUntil.saveMusicLocal(ringTonesShared);
            }
        } else {
            ringTonesShared = new ArrayList<>();
            ringTonesShared.add(ringToneSave);
            Gson gson = new Gson();
            String json = gson.toJson(ringToneSave);
            Log.i(TAG, "saveListRingtoneToShared: " + json);
            Log.i(TAG, "saveListRingtoneToShared: size list " + ringTonesShared.size());
            sharePreferenceUntil.saveMusicLocal(ringTonesShared);
        }
    }

    private boolean isExistRingtoneInShared(List<RingTone> list, int id) {
        if (list == null) return false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }

    private void deleteRingtoneFromListShared() {
        if (ringTone == null) return;
        String jsonListData = sharePreferenceUntil.getJsonListMusic();
        ringTonesShared = CommonUntil.convertJsonRingtonToList(jsonListData);
        if (ringTonesShared != null && ringTonesShared.size() != 0 && isExistRingtoneInShared(ringTonesShared, ringTone.getId())) {
            for (int i = 0; i < ringTonesShared.size(); i++) {
                if (ringTonesShared.get(i).getId() == ringTone.getId()) {
                    ringTonesShared.remove(ringTonesShared.get(i));
                }
            }
        }
        sharePreferenceUntil.saveMusicLocal(ringTonesShared);
    }

    public void setRingtone(File file) {
        //check for result
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, file.getAbsolutePath());
        values.put(MediaStore.MediaColumns.TITLE, file.getName());
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
        values.put(MediaStore.MediaColumns.SIZE, file.length());
        values.put(MediaStore.Audio.Media.ARTIST, R.string.app_name);
        values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
        values.put(MediaStore.Audio.Media.IS_ALARM, true);
        values.put(MediaStore.Audio.Media.IS_MUSIC, false);

        Uri uri = MediaStore.Audio.Media.getContentUriForPath(file.getAbsolutePath());
        ContentResolver mCr = this.getContentResolver();
        mCr.delete(uri, MediaStore.MediaColumns.DATA + "=\"" + file.getAbsolutePath() + "\"", null);
        Uri newUri = mCr.insert(uri, values);
        try {
            Uri rUri = RingtoneManager.getValidRingtoneUri(this);
            if (rUri != null)
                RingtoneManager.setActualDefaultRingtoneUri(getApplicationContext(), type, newUri);
            CommonUntil.createDialog(this, getString(R.string.change_success), getString(R.string.app_name)).show();
            Settting1.showAdsIn(false, this, new Settting1.CallbackShow() {
                @Override
                public void Displayed() {

                }

                @Override
                public void Close() {
                    Settting1.onCallLoadAdsIn(DetailActivityCategory.this);
                }
            });
        } catch (Throwable t) {
            Log.e(TAG, "catch exception" + t.getMessage());
            CommonUntil.createDialog(this, getString(R.string.change_failed), getString(R.string.app_name)).show();
        }
        Log.i(TAG, "downloadFile: type " + type);
        Log.i(TAG, "downloadFile: RingtoneManager " + RingtoneManager.getActualDefaultRingtoneUri(this, type));
        Log.i(TAG, "downloadFile: uri from file " + newUri);
    }



    private void downloadFile() {
        if (CommonUntil.isExistFileCategory(ringTone.getName(),title)) {
            String filePath=CommonUntil.getPathCategory(CategoryName.class.getName())+"/"+ ringTone.getName() + "";
            File file = new File(filePath);
            if (type == TYPE_RINGTONE_CONTACT) {
                if (setupUriForContact(cNumber, file)) {
                    CommonUntil.createDialog(this, getString(R.string.change_success), getString(R.string.app_name)).show();
                    Settting1.showAdsIn(false, this, new Settting1.CallbackShow() {
                        @Override
                        public void Displayed() {

                        }

                        @Override
                        public void Close() {
                            Settting1.onCallLoadAdsIn(DetailActivityCategory.this);
                        }
                    });
                } else {
                    CommonUntil.createDialog(this, getString(R.string.change_failed), getString(R.string.app_name)).show();
                }
            } else {
                if (CommonUntil.checkCanBeChangeSystemSetting(this, READ_WRITE_REQUEST))
                    setRingtone(file);
            }

            if (type == RingtoneManager.TYPE_RINGTONE) {
                if (setupUriForContact(cNumber, file)) {
                    setRingtone(file);
                    CommonUntil.createDialog(this, getString(R.string.change_success), getString(R.string.app_name)).show();
                    Settting1.showAdsIn(false, this, new Settting1.CallbackShow() {
                        @Override
                        public void Displayed() {

                        }

                        @Override
                        public void Close() {
                            Settting1.onCallLoadAdsIn(DetailActivityCategory.this);
                        }
                    });
                } else {
                    CommonUntil.createDialog(this, getString(R.string.change_failed), getString(R.string.app_name)).show();
                }
            } else {
                if (CommonUntil.checkCanBeChangeSystemSetting(this, READ_WRITE_REQUEST))
                    setRingtone(file);
            }
            if (type == RingtoneManager.TYPE_ALARM) {
                if (setupUriForContact(cNumber, file)) {
                    setRingtone(file);
                    CommonUntil.createDialog(this, getString(R.string.change_success), getString(R.string.app_name)).show();
                    Settting1.showAdsIn(false, this, new Settting1.CallbackShow() {
                        @Override
                        public void Displayed() {

                        }

                        @Override
                        public void Close() {
                            Settting1.onCallLoadAdsIn(DetailActivityCategory.this);
                        }
                    });
                } else {
                    CommonUntil.createDialog(this, getString(R.string.change_failed), getString(R.string.app_name)).show();
                }
            } else {
                if (CommonUntil.checkCanBeChangeSystemSetting(this, READ_WRITE_REQUEST))
                    setRingtone(file);
            }

            if (type == RingtoneManager.TYPE_NOTIFICATION) {
                setRingtone(file);
                if (setupUriForContact(cNumber, file)) {
                    CommonUntil.createDialog(this, getString(R.string.change_success), getString(R.string.app_name)).show();
                    Settting1.showAdsIn(false, this, new Settting1.CallbackShow() {
                        @Override
                        public void Displayed() {

                        }

                        @Override
                        public void Close() {
                            Settting1.onCallLoadAdsIn(DetailActivityCategory.this);
                        }
                    });
                } else {
                    CommonUntil.createDialog(this, getString(R.string.change_failed), getString(R.string.app_name)).show();
                }
            } else {
                if (CommonUntil.checkCanBeChangeSystemSetting(this, READ_WRITE_REQUEST))
                    setRingtone(file);
            }
        } else {
            CommonUntil.downLoadFile(this, ringTone, uri -> {
                if (uri == null) {
                    CommonUntil.createDialog(this, getString(R.string.has_problem), getString(R.string.app_name)).show();
                    return;
                }
                // TODO: 22/06/2019 Download file success => save music to shared-file
                saveListRingtoneToShared();
                // TODO: 22/06/2019 update view download
//                resetDownloadView();
                // Set ringtone
                if (type == TYPE_RINGTONE_CONTACT) {
                    File file = new File(uri.getPath());
                    if (setupUriForContact(cNumber, file)) {
                        CommonUntil.createDialog(this, getString(R.string.change_success), getString(R.string.app_name)).show();
                        Settting1.showAdsIn(false, this, new Settting1.CallbackShow() {
                            @Override
                            public void Displayed() {

                            }

                            @Override
                            public void Close() {
                                Settting1.onCallLoadAdsIn(DetailActivityCategory.this);
                            }
                        });
                    } else {
                        CommonUntil.createDialog(this, getString(R.string.change_failed), getString(R.string.app_name)).show();
                    }
                } else {
                    if (CommonUntil.checkCanBeChangeSystemSetting(this, READ_WRITE_REQUEST)) {
                        File file = new File(uri.getPath());
                        setRingtone(file);
                    }
                }
            });
        }
    }

    public void showDiaLogTrouble() {
        CommonUntil.createDialog(this, getString(R.string.has_problem), getString(R.string.app_name), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SlideAnimationUtil.overridePendingTransitionExit(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == READ_WRITE_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadFile();
            }
        } else if (requestCode == DOWNLOAD_FILE_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadFileOnly();
            }
        } else if (requestCode == DELETE_FILE_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showAlertDialogDeleteFile();
            }
        } else if (requestCode == PER_CONTACT) {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaInstance.stop();
        unregisterReceiver(networkChangeReceiver);
    }

    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("app", "Network connectivity change " + isNetworkAvailable());
            if (!isNetworkAvailable()) {
                CommonUntil.showDialogInternetStatus(DetailActivityCategory.this);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == READ_WRITE_REQUEST) {
                downloadFile();
            } else if (requestCode == PICK_CONTACT) {
                getContactChoose(data);
                if (CommonUntil.isNullorEmty(cNumber))
                    CommonUntil.createDialog(this, getString(R.string.errorProcess), getString(R.string.app_name)).show();
                else
                    checkPermissionForDownloadFile();
            }
        }
    }

    private void checkPermissionForDownloadFile() {
        if (CommonUntil.checkIfAlreadyhavePermission(DetailActivityCategory.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
            downloadFile();
        } else {
            CommonUntil.requestPermission(DetailActivityCategory.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS}, READ_WRITE_REQUEST);
        }
    }

    private void getContactChoose(Intent data) {
        cNumber = null;
        Uri contactData = data.getData();
        Cursor c = managedQuery(contactData, null, null, null, null);
        if (c.moveToFirst()) {


            String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

            String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            if (hasPhone.equalsIgnoreCase("1")) {
                Cursor phones = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                        null, null);
                phones.moveToFirst();
                cNumber = phones.getString(phones.getColumnIndex("data1"));
                System.out.println("number is:" + cNumber);
            }
        }
    }

    private boolean setupUriForContact(String contact, File file) {
        try {
            ContentValues values = new ContentValues();

            ContentResolver resolver = getContentResolver();
            Uri oldUri = MediaStore.Audio.Media.getContentUriForPath(file.getAbsolutePath());
            resolver.delete(oldUri, MediaStore.MediaColumns.DATA + "=\"" + file.getAbsolutePath() + "\"", null);


            String contact_number = "CONTACT_NUMBER";
            Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, contact);

            // The columns used for `Contacts.getLookupUri`
            String[] projection = new String[]{
                    ContactsContract.Contacts._ID, ContactsContract.Contacts.LOOKUP_KEY
            };

            Cursor data = getContentResolver().query(lookupUri, projection, null, null, null);

            if (data != null && data.moveToFirst()) {
                data.moveToFirst();
                // Get the contact lookup Uri
                long contactId = data.getLong(0);
                String lookupKey = data.getString(1);
                Uri contactUri = ContactsContract.Contacts.getLookupUri(contactId, lookupKey);

                values.put(MediaStore.MediaColumns.DATA, file.getAbsolutePath());
                values.put(MediaStore.MediaColumns.TITLE, "RingtoneFree");
                values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
                values.put(MediaStore.Audio.Media.IS_RINGTONE, true);

                Uri uri = MediaStore.Audio.Media.getContentUriForPath(file.getAbsolutePath());
                Uri newUri = resolver.insert(uri, values);

                if (newUri != null) {
                    String uriString = newUri.toString();
                    values.put(ContactsContract.Contacts.CUSTOM_RINGTONE, uriString);
                    Log.e("Uri String for " + ContactsContract.Contacts.CONTENT_URI, uriString);
                    long updated = resolver.update(contactUri, values, null, null);

//                    Toast.makeText(DetailActivity.this, "Updated : " + updated, Toast.LENGTH_LONG).show();
                }
                data.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
