package com.optionringringtone.newringtonefree.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import com.google.gson.Gson;
import com.optionringringtone.newringtonefree.Untils.CommonUntil;
import com.optionringringtone.newringtonefree.Untils.Configs;
import com.optionringringtone.newringtonefree.Untils.DetailActivityCategory;
import com.optionringringtone.newringtonefree.Untils.SharePreferenceUntil;
import com.optionringringtone.newringtonefree.mysetting.Settting1;
import com.optionringringtone.newringtonefree.object.RingTone;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;

public class DialogFragmentMoreCategory extends DialogFragment implements View.OnClickListener{
    private LinearLayout btnSetDefaultRingtone;
    private LinearLayout btnSetDefaultNotification;
    private LinearLayout btnSetDefaultAlarm;
    private LinearLayout btnSetRingtoneContact;
    private TextView txtNameMusic;
    private int type;
    private int TYPE_RINGTONE_CONTACT = 1111;
    private int PICK_CONTACT = 1112;
    private int PER_CONTACT = 1113;
    private String cNumber;
    private int READ_WRITE_REQUEST = 1114;
    private RingTone ringTone;
    private SharePreferenceUntil sharePreferenceUntil;
    private List<RingTone> ringTonesShared = new ArrayList<>();
    private String TAG = "DialogFragmentMoreFucntion";
//    private LinearLayout btnDownLoadAndDeleteMusic;
//    private ImageView imgDownloadAndDeleteMusic;
//    private TextView  txtActionDownLoadOrDelete;
    private int DELETE_FILE_REQUEST = 1112;
    private int DOWNLOAD_FILE_REQUEST = 1111;

    public DialogFragmentMoreCategory setRingTone(RingTone ringTone) {
        this.ringTone = ringTone;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_more_category, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        sharePreferenceUntil = SharePreferenceUntil.getInstance(getActivity());
        btnSetDefaultRingtone = view.findViewById(R.id.btnSetDefaultRingtone);
        btnSetDefaultNotification = view.findViewById(R.id.btnSetDefaultNotification);
        btnSetDefaultAlarm = view.findViewById(R.id.btnSetDefaultAlarm);
        btnSetRingtoneContact = view.findViewById(R.id.btnSetRingtoneContact);
        txtNameMusic = view.findViewById(R.id.txtNameMusic);
        txtNameMusic.setText(ringTone.getName());
//        btnDownLoadAndDeleteMusic=view.findViewById(R.id.btnDownLoadAndDeleteMusic);
//        imgDownloadAndDeleteMusic = view.findViewById(R.id.imgDownloadAndDeleteMusic);
//        txtActionDownLoadOrDelete = view.findViewById(R.id.txtActionDownLoadOrDelete);
//
        btnSetDefaultRingtone.setOnClickListener(this);
        btnSetDefaultNotification.setOnClickListener(this);
        btnSetDefaultAlarm.setOnClickListener(this);
        btnSetRingtoneContact.setOnClickListener(this);
//        btnDownLoadAndDeleteMusic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
//            case R.id.imgDownloadAndDeleteMusic:
//                showDialogConfirmDownLoad();
//                break;
//            case R.id.btnDownLoadAndDeleteMusic:
//                showDialogConfirmDownLoad();
//                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
    }

    public void showAlertDialogSetRingtone() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("");
        builder.setMessage(getResources().getString(R.string.are_you_sure_set));
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string._ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (type == TYPE_RINGTONE_CONTACT) {
                    if (checkIfAlreadyhavePermission(new String[]{Manifest.permission.READ_CONTACTS,
                            Manifest.permission.WRITE_CONTACTS})) {
                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, PICK_CONTACT);
                    } else {
                        requestPermission(new String[]{Manifest.permission.READ_CONTACTS,
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == READ_WRITE_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadFile();
            }
        } else if (requestCode == PER_CONTACT) {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == READ_WRITE_REQUEST) {
                downloadFile();
            } else if (requestCode == PICK_CONTACT) {
                getContactChoose(data);
                if (CommonUntil.isNullorEmty(cNumber))
                    CommonUntil.createDialog(getActivity(), getString(R.string.errorProcess), getString(R.string.app_name)).show();
                else
                    checkPermissionForDownloadFile();
            }
        }
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
        ContentResolver mCr = getActivity().getContentResolver();
        mCr.delete(uri, MediaStore.MediaColumns.DATA + "=\"" + file.getAbsolutePath() + "\"", null);
        Uri newUri = mCr.insert(uri, values);
        try {
            Uri rUri = RingtoneManager.getValidRingtoneUri(getActivity());
            if (rUri != null)
                RingtoneManager.setActualDefaultRingtoneUri(getActivity().getApplicationContext(), type, newUri);
            CommonUntil.createDialog(getActivity(), getString(R.string.change_success), getString(R.string.app_name)).show();
            Settting1.showAdsIn(false, getContext(), new Settting1.CallbackShow() {
                @Override
                public void Displayed() {

                }

                @Override
                public void Close() {
                    Settting1.onCallLoadAdsIn(getContext());
                }
            });
        } catch (Throwable t) {
            Log.e(TAG, "catch exception" + t.getMessage());
            CommonUntil.createDialog(getActivity(), getString(R.string.change_failed), getString(R.string.app_name)).show();
        }
        Log.i(TAG, "downloadFile: type " + type);
        Log.i(TAG, "downloadFile: RingtoneManager " + RingtoneManager.getActualDefaultRingtoneUri(getActivity(), type));
        Log.i(TAG, "downloadFile: uri from file " + newUri);
    }


    private void downloadFile() {
        if (CommonUntil.isExistFileCategory(ringTone.getName())) {
//            String filePath= Environment.getExternalStorageDirectory().getPath() +Configs.PATH_STORAGE_CATEGORY+"Most Popular/Most_Popular/"+ringTone.getName()+"";
//            File file = new File(filePath);
//            if (type == TYPE_RINGTONE_CONTACT) {
//                if (setupUriForContact(cNumber, file)) {
//                    CommonUntil.createDialog(getActivity(), getString(R.string.change_success), getString(R.string.app_name)).show();
//                    Settting1.showAdsIn(false, getContext(), new Settting1.CallbackShow() {
//                        @Override
//                        public void Displayed() {
//
//                        }
//
//                        @Override
//                        public void Close() {
//                            Settting1.onCallLoadAdsIn(getContext());
//                        }
//                    });
//                } else {
//                    CommonUntil.createDialog(getActivity(), getString(R.string.change_failed), getString(R.string.app_name)).show();
//                }
//            } else {
//                if (CommonUntil.checkCanBeChangeSystemSetting(getActivity(), READ_WRITE_REQUEST))
//                    setRingtone(file);
//            }
//        } else {
//            CommonUntil.downLoadFile(getActivity(), ringTone, uri -> {
//                if (uri == null) {
//                    CommonUntil.createDialog(getActivity(), getString(R.string.has_problem), getString(R.string.app_name)).show();
//                    return;
//                }
//
//                saveListRingtoneToShared();
//                // Set ringtone
//                if (type == TYPE_RINGTONE_CONTACT) {
//                    File file = new File(uri.getPath());
//                    if (setupUriForContact(cNumber, file)) {
//                        CommonUntil.createDialog(getActivity(), getString(R.string.change_success), getString(R.string.app_name)).show();
//                        Settting1.showAdsIn(false, getContext(), new Settting1.CallbackShow() {
//                            @Override
//                            public void Displayed() {
//
//                            }
//
//                            @Override
//                            public void Close() {
//                                Settting1.onCallLoadAdsIn(getContext());
//                            }
//                        });
//                    } else {
//                        CommonUntil.createDialog(getActivity(), getString(R.string.change_failed), getString(R.string.app_name)).show();
//                    }
//                } else {
//                    if (CommonUntil.checkCanBeChangeSystemSetting(getActivity(), READ_WRITE_REQUEST)) {
//                        File file = new File(uri.getPath());
//                        setRingtone(file);
//                    }
//                }
//            });
            String filePath = Environment.getExternalStorageDirectory().getPath() + Configs.PATH_STORAGE_CATEGORY + "Most Popular/Most_Popular/" + ringTone.getName() + "";
            File file = new File(filePath);
            if (type == TYPE_RINGTONE_CONTACT) {
                if (setupUriForContact(cNumber, file)) {
                    CommonUntil.createDialog(getActivity(), getString(R.string.change_success), getString(R.string.app_name)).show();
                    Settting1.showAdsIn(false, getContext(), new Settting1.CallbackShow() {
                        @Override
                        public void Displayed() {

                        }

                        @Override
                        public void Close() {
                            Settting1.onCallLoadAdsIn(getContext());
                        }
                    });
                } else {
                    CommonUntil.createDialog(getActivity(), getString(R.string.change_failed), getString(R.string.app_name)).show();
                }
            } else {
                if (CommonUntil.checkCanBeChangeSystemSetting(getActivity(), READ_WRITE_REQUEST))
                    setRingtone(file);
            }

            if (type == RingtoneManager.TYPE_RINGTONE) {
                if (setupUriForContact(cNumber, file)) {
                    setRingtone(file);
                    CommonUntil.createDialog(getActivity(), getString(R.string.change_success), getString(R.string.app_name)).show();
                    Settting1.showAdsIn(false, getContext(), new Settting1.CallbackShow() {
                        @Override
                        public void Displayed() {

                        }

                        @Override
                        public void Close() {
                            Settting1.onCallLoadAdsIn(getContext());
                        }
                    });
                } else {
                    CommonUntil.createDialog(getActivity(), getString(R.string.change_failed), getString(R.string.app_name)).show();
                }
            } else {
                if (CommonUntil.checkCanBeChangeSystemSetting(getActivity(), READ_WRITE_REQUEST))
                    setRingtone(file);
            }
            if (type == RingtoneManager.TYPE_ALARM) {
                if (setupUriForContact(cNumber, file)) {
                    setRingtone(file);
                    CommonUntil.createDialog(getActivity(), getString(R.string.change_success), getString(R.string.app_name)).show();
                    Settting1.showAdsIn(false, getContext(), new Settting1.CallbackShow() {
                        @Override
                        public void Displayed() {

                        }

                        @Override
                        public void Close() {
                            Settting1.onCallLoadAdsIn(getContext());
                        }
                    });
                } else {
                    CommonUntil.createDialog(getActivity(), getString(R.string.change_failed), getString(R.string.app_name)).show();
                }
            } else {
                if (CommonUntil.checkCanBeChangeSystemSetting(getActivity(), READ_WRITE_REQUEST))
                    setRingtone(file);
            }

            if (type == RingtoneManager.TYPE_NOTIFICATION) {
                setRingtone(file);
                if (setupUriForContact(cNumber, file)) {
                    CommonUntil.createDialog(getActivity(), getString(R.string.change_success), getString(R.string.app_name)).show();
                    Settting1.showAdsIn(false, getContext(), new Settting1.CallbackShow() {
                        @Override
                        public void Displayed() {

                        }

                        @Override
                        public void Close() {
                            Settting1.onCallLoadAdsIn(getContext());
                        }
                    });
                } else {
                    CommonUntil.createDialog(getActivity(), getString(R.string.change_failed), getString(R.string.app_name)).show();
                }
            } else {
                if (CommonUntil.checkCanBeChangeSystemSetting(getActivity(), READ_WRITE_REQUEST))
                    setRingtone(file);
            }
        } else {
            CommonUntil.downLoadFile(getActivity(), ringTone, uri -> {
                if (uri == null) {
                    CommonUntil.createDialog(getActivity(), getString(R.string.has_problem), getString(R.string.app_name)).show();
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
                        CommonUntil.createDialog(getActivity(), getString(R.string.change_success), getString(R.string.app_name)).show();
                        Settting1.showAdsIn(false, getContext(), new Settting1.CallbackShow() {
                            @Override
                            public void Displayed() {

                            }

                            @Override
                            public void Close() {
                                Settting1.onCallLoadAdsIn(getContext());
                            }
                        });
                    } else {
                        CommonUntil.createDialog(getActivity(), getString(R.string.change_failed), getString(R.string.app_name)).show();
                    }
                } else {
                    if (CommonUntil.checkCanBeChangeSystemSetting(getActivity(), READ_WRITE_REQUEST)) {
                        File file = new File(uri.getPath());
                        setRingtone(file);
                    }
                }
            });
        }
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
                sharePreferenceUntil.saveMusicLocal(ringTonesShared);
            }
        } else {
            ringTonesShared = new ArrayList<>();
            ringTonesShared.add(ringToneSave);
            Gson gson = new Gson();
            String json = gson.toJson(ringToneSave);
            sharePreferenceUntil.saveMusicLocal(ringTonesShared);
        }
    }

    private void checkPermissionForDownloadFile() {
        if (checkIfAlreadyhavePermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
            downloadFile();
        } else {
            requestPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS}, READ_WRITE_REQUEST);
        }
    }

    private void getContactChoose(Intent data) {
        cNumber = null;
        Uri contactData = data.getData();
        Cursor c = getActivity().managedQuery(contactData, null, null, null, null);
        if (c.moveToFirst()) {


            String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

            String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            if (hasPhone.equalsIgnoreCase("1")) {
                Cursor phones = getActivity().getContentResolver().query(
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

            ContentResolver resolver = getActivity().getContentResolver();
            Uri oldUri = MediaStore.Audio.Media.getContentUriForPath(file.getAbsolutePath());
            resolver.delete(oldUri, MediaStore.MediaColumns.DATA + "=\"" + file.getAbsolutePath() + "\"", null);


            String contact_number = "CONTACT_NUMBER";
            Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, contact);

            // The columns used for `Contacts.getLookupUri`
            String[] projection = new String[]{
                    ContactsContract.Contacts._ID, ContactsContract.Contacts.LOOKUP_KEY
            };

            Cursor data = getActivity().getContentResolver().query(lookupUri, projection, null, null, null);

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

    private boolean isExistRingtoneInShared(List<RingTone> list, int id) {
        if (list == null) return false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void requestPermission(String[] permissions, int requestCode) {
        for (String permission : permissions) {
            if (!checkIfAlreadyhavePermission(getActivity(), permission)) {
                requestForSpecificPermission(permissions, requestCode);
                break;
            }
        }
    }

    public boolean checkIfAlreadyhavePermission(Activity activity, String permission) {
        int result = ContextCompat.checkSelfPermission(activity, permission);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkIfAlreadyhavePermission(String[] permissions) {
        boolean isGrantAll = true;
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(getActivity(), permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                isGrantAll = false;
            }
        }
        return isGrantAll;
    }

    private void requestForSpecificPermission(String[] permissions, int reqestCode) {
        requestPermissions(permissions, reqestCode);
    }

    public boolean checkCanBeChangeSystemSetting(int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(getActivity())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                DialogFragmentMoreCategory.this.startActivityForResult(intent, requestCode);
                return false;
            } else
                return true;
        } else {
            if (checkIfAlreadyhavePermission(getActivity(), Manifest.permission.WRITE_SETTINGS)) {
                requestPermission(new String[]{Manifest.permission.WRITE_SETTINGS}, requestCode);
                return false;
            } else
                return true;
        }
    }

//    private void showDialogConfirmDownLoad() {
//        String message = "Are you sure you want to download this ringtone?";
//        if (txtActionDownLoadOrDelete.getText().toString().equalsIgnoreCase(getResources().getString(R.string.delete))) {
//            message = "Are you sure you want to delete this ringtone?";
//        }
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("");
//        builder.setMessage(message);
//        builder.setCancelable(false);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                if (txtActionDownLoadOrDelete.getText().toString().equalsIgnoreCase(getResources().getString(R.string.delete))) {
//                    //delete file
//                    if (CommonUntil.checkIfAlreadyhavePermission(getActivity(), new String[]
//                            {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
//                        //show dialog confirm delete
//                        showAlertDialogDeleteFile();
//                    } else {
//                        CommonUntil.requestPermission(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS}, DELETE_FILE_REQUEST);
//                    }
//                } else {
//                    if (CommonUntil.checkIfAlreadyhavePermission(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
//                        downloadFileOnly();
//                    } else {
//                        CommonUntil.requestPermission(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS}, DOWNLOAD_FILE_REQUEST);
//                    }
//                }
//            }
//        });
//        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }


//    public void showAlertDialogDeleteFile() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("");
//        builder.setMessage("Are you sure you want to delete this ringtone?");
//        builder.setCancelable(false);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                ringTone.setPath(null);
//                CommonUntil.deleteFile(ringTone.getName(), ringTone.getId() + "");
//                deleteRingtoneFromListShared();
//                txtActionDownLoadOrDelete.setText(getResources().getString(R.string.download));
//                imgDownloadAndDeleteMusic.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_file_download_black_24dp));
//            }
//        });
//        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
//    private void downloadFileOnly() {
//        CommonUntil.downLoadFile(getActivity(), ringTone, uri -> {
//            Log.i(TAG, "downloadFileOnly- URI FILE: " + uri);
//            if (uri == null) {
//                CommonUntil.createDialog(getActivity(), getString(R.string.has_problem), getString(R.string.app_name)).show();
//                return;
//            }
//
//            // TODO: 22/06/2019 Download file success => save music to shared-file
//            saveListRingtoneToShared();
//            // TODO: 22/06/2019 update view download
//            txtActionDownLoadOrDelete.setText(getResources().getString(R.string.delete));
//            imgDownloadAndDeleteMusic.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_delete_black_24dp));
//
//
//            Settting1.showAdsIn(false, getActivity(), new Settting1.CallbackShow() {
//                @Override
//                public void Displayed() {
//
//                }
//
//                @Override
//                public void Close() {
//                    Settting1.onCallLoadAdsIn(getActivity());
//                }
//            });
//
//        });
//    }


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
}
