package com.optionringringtone.newringtonefree.Untils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;
import com.optionringringtone.newringtonefree.object.RingTone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommonUntil {

    private static String TAG = "CommonUntil";

    public static boolean isNullorEmty(Object object) {
        if (object == null)
            return true;
        if (object instanceof String) {
            if (((String) object).isEmpty())
                return true;
        } else if (object instanceof EditText) {
            return ((EditText) object).getText().toString().trim().isEmpty();
        }
        if (object instanceof List) {
            return ((List) object).isEmpty();
        }

        if (object instanceof HashMap) {
            return ((HashMap) object).isEmpty();
        }

        return object instanceof ArrayList && ((ArrayList) object).isEmpty();
    }


    public static Dialog createDialog(@NonNull Activity activity, @NonNull String mess, @Nullable String title) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        if (isNullorEmty(title))
            alert.setTitle(R.string.app_name);
        else
            alert.setTitle(title);
        alert.setMessage(mess);
        return alert.create();
    }

    public static Dialog createDialog(@NonNull Activity activity, @NonNull String mess, @Nullable String title, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        if (isNullorEmty(title))
            alert.setTitle(R.string.app_name);
        else
            alert.setTitle(title);
        alert.setMessage(mess);
        alert.setNeutralButton(R.string.ok, onClickListener);
        return alert.create();
    }

    ;

    public static ProgressDialog creProgress(@NonNull Activity activity, @NonNull String mess) {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(mess);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public static void setLayoutManager(Activity activity, RecyclerView recyclerView, int typeScroll) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(typeScroll);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public static void downLoadFile(Activity activity, RingTone ringTone, onDowLoadListener onDowLoadListener) {

        ProgressDialog progressDialog = creProgress(activity, activity.getString(R.string.downloading_pls_wait));
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(true);
        File directory = new File(Configs.FOLDER);

        if (!directory.exists()) {
            directory.mkdirs();
        }
        new AsyncTask<String, String, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... f_url) {
                String description = null;
                int count;
                try {
                    URL url = new URL(ringTone.getUrl());
                    URLConnection connection = url.openConnection();
                    connection.connect();
                    // getting file length
                    int lengthOfFile = connection.getContentLength();

                    InputStream input = new BufferedInputStream(url.openStream(), 8192);

                    String fileName = ringTone.getName() + "_ID_" + ringTone.getId() + ".mp3";
                    OutputStream output = new FileOutputStream(Configs.FOLDER + fileName);

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        publishProgress("" + (int) ((total * 100) / lengthOfFile));
                        Log.d(TAG, "Progress: " + (int) ((total * 100) / lengthOfFile));
                        output.write(data, 0, count);
                    }
                    output.flush();
                    output.close();
                    input.close();
                    description = Configs.FOLDER + fileName;
                    return description;
                } catch (Exception e) {
                    description = "DOWNLOAD_ERROR_6969" + e.getMessage();
                    Log.e("Error: download file", e.getMessage());
                }
                return description;
            }

            protected void onProgressUpdate(String... progress) {
                progressDialog.setProgress(Integer.parseInt(progress[0]));
            }

            @Override
            protected void onPostExecute(String result) {
                Log.i("ERROR", "onPostExecute: " + result);
                progressDialog.dismiss();
                if (!result.contains("DOWNLOAD_ERROR_6969")) {
                    File file = new File(result);
                    onDowLoadListener.onDownloadFinishLitener(Uri.fromFile(file));
                } else
                    onDowLoadListener.onDownloadFinishLitener(null);
            }

        }.execute();
    }

    public static void requestPermission(Activity activity, String[] permissions, int requestCode) {
        for (String permission : permissions) {
            if (!checkIfAlreadyhavePermission(activity, permission))
            {
                requestForSpecificPermission(activity, permissions, requestCode);
                break;
            }
        }
    }

    public static boolean checkIfAlreadyhavePermission(Activity activity, String permission) {
        int result = ContextCompat.checkSelfPermission(activity, permission);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkIfAlreadyhavePermission(Activity activity, String[] permissions) {
        boolean isGrantAll = true;
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(activity, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                isGrantAll = false;
            }
        }
        return isGrantAll;
    }

    private static void requestForSpecificPermission(Activity activity, String[] permissions, int reqestCode) {
        ActivityCompat.requestPermissions(activity, permissions, reqestCode);
    }

    public static boolean checkCanBeChangeSystemSetting(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(activity)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivityForResult(intent, requestCode);
                return false;
            } else
                return true;
        } else {
            if (CommonUntil.checkIfAlreadyhavePermission(activity, Manifest.permission.WRITE_SETTINGS)) {
                requestPermission(activity, new String[]{Manifest.permission.WRITE_SETTINGS}, requestCode);
                return false;
            } else
                return true;
        }
    }

    public static boolean isExistFile(String nameRingtone, String idRingtone) {
        String filePath = Configs.FOLDER + nameRingtone + "_ID_" + idRingtone + ".mp3";
        File file = new File(filePath);
        if (file.exists())
            return true;
        return false;
    }

    public static boolean isExistFileZip(String nameFile){
        String filePath=Configs.FOLDERZIP+nameFile;
        File file=new File(filePath);
        if (file.exists()){
            return true;
        }
        return false;
    }


    public static void deleteFile(String nameRingtone, String idRingtone) {
        String filePath = Configs.FOLDER + nameRingtone + "_ID_" + idRingtone + ".mp3";
        File file = new File(filePath);
        if (file.exists())
            file.delete();
    }

    public static List<RingTone> convertJsonRingtonToList(String jsonData) {
        if (jsonData == null) return null;
        List<RingTone> ringTones = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objectRingTone = jsonArray.getJSONObject(i);
                ringTones.add(new RingTone(
                        objectRingTone.getString("url"),
                        objectRingTone.getString("updated_at"),
                        objectRingTone.getString("created_at"),
                        objectRingTone.getString("filepath"),
                        objectRingTone.getString("duration"),
                        objectRingTone.getString("name"),
                        objectRingTone.getInt("id"),
                        objectRingTone.getBoolean("isDownLoad"),
                        objectRingTone.getBoolean("isPlaying")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ringTones;
    }

    public static void replaceFragment(Activity activity, Fragment fragment){
        FragmentManager fragmentManager = ((AppCompatActivity)activity).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    public interface onDowLoadListener {
        void onDownloadFinishLitener(Uri uri);
    }

    public static void showDialogInternetStatus(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("");
        builder.setMessage(activity.getResources().getString(R.string.no_internet_connection));
        builder.setCancelable(false);
        builder.setPositiveButton(activity.getResources().getString(R.string._ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
