package com.optionringringtone.newringtonefree.Untils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;

import androidx.annotation.NonNull;

import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;

import java.io.IOException;

import static android.media.MediaPlayer.MEDIA_ERROR_IO;
import static android.media.MediaPlayer.MEDIA_ERROR_MALFORMED;
import static android.media.MediaPlayer.MEDIA_ERROR_SERVER_DIED;
import static android.media.MediaPlayer.MEDIA_ERROR_TIMED_OUT;
import static android.media.MediaPlayer.MEDIA_ERROR_UNKNOWN;
import static android.media.MediaPlayer.MEDIA_ERROR_UNSUPPORTED;

public class MediaUntil {
    private static MediaPlayer mediaPlayer;
    private static MediaUntil mInstance;
    private static String currentId;
    public static MediaUntil getInstance(){
        if (mediaPlayer == null)
            mediaPlayer = new MediaPlayer();
        if(mInstance == null)
            mInstance = new MediaUntil();
        return mInstance;
    }

    public MediaPlayer playMusic(Activity activity, @NonNull String path, @NonNull String id, @NonNull onChangeListener onChangeListener){
        try {
            if(!CommonUntil.isNullorEmty(currentId) && currentId.equals(id)){
                mediaPlayer.start();
                onChangeListener.onReady();
            }else {
                currentId = id;
                final ProgressDialog progressDialog = CommonUntil.creProgress(activity, activity.getString(R.string.wait_process));
                progressDialog.setCancelable(false);
                progressDialog.show();
                if (mediaPlayer.isPlaying())
                    mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.setDataSource(path);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mediaPlayer.prepare();
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(mp -> {
                    progressDialog.dismiss();
                    mediaPlayer.start();
                    onChangeListener.onReady();
                });
                mediaPlayer.setOnErrorListener((mp, what, extra) -> {
//                    Log.d(TAG, "onError Media: what:" + what + "\n extrar:"+ extra);
                    if(what == MEDIA_ERROR_UNKNOWN ||
                        what == MEDIA_ERROR_SERVER_DIED ||
                            extra == MEDIA_ERROR_IO ||
                            extra == MEDIA_ERROR_MALFORMED ||
                            extra == MEDIA_ERROR_TIMED_OUT ||
                            extra == MEDIA_ERROR_UNSUPPORTED
                    ) {
                        progressDialog.dismiss();
                        Dialog dialog = CommonUntil.createDialog(activity, activity.getString(R.string.errorProcess), null);
                        dialog.show();
                        onChangeListener.onError();
                        currentId = null;
                        return true;
                    }
                    return false;
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaPlayer;
    }
    public void pause(){
        mediaPlayer.pause();
    }

    public void stop(){
        currentId = null;
        mediaPlayer.stop();
    }

    public int getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    public int getDuration(){
        return mediaPlayer.getDuration();
    }
    public interface onChangeListener {
        void onReady();

        void onError();
    }
}
