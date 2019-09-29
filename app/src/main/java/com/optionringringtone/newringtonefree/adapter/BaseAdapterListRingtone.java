package com.optionringringtone.newringtonefree.adapter;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.optionringringtone.newringtonefree.Untils.CommonUntil;
import com.optionringringtone.newringtonefree.DetailActivityCategory;
import com.optionringringtone.newringtonefree.Untils.MediaUntil;
import com.optionringringtone.newringtonefree.Untils.SlideAnimationUtil;
import com.optionringringtone.newringtonefree.fragment.DialogFragmentMoreCategory;
import com.optionringringtone.newringtonefree.object.RingTone;
import java.util.List;
import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;

public class BaseAdapterListRingtone extends BaseAdapter {
    private static final String TAG = "AdapterLstRingtone";
    private List<RingTone> lstRingStone;
    private RingTone ringToneDataPlaying;
    private int idPlaying;
    private int oldPlaying = -1;

    private static SeekBar skPlaying;
    private static ImageView ivStatus;
    private Activity activity;

    private static MediaUntil mMediaInstance;
    private static MediaPlayer mediaPlayer;
    private Handler mHandler;
    private Runnable runnable;
    private MediaUntil.onChangeListener onChangeListener;
    private String title="";

    public BaseAdapterListRingtone(Activity activity, List<RingTone> lstRingStone,String title) {
        this.lstRingStone = lstRingStone;
        this.activity = activity;
        this.title=title;
        mMediaInstance = MediaUntil.getInstance();
        idPlaying = -1;
        stopAudio();
        mHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer.isPlaying()) {
                    skPlaying.setMax(mMediaInstance.getDuration());
                    skPlaying.setProgress(mediaPlayer.getCurrentPosition());
                } else {
                    skPlaying.setProgress(0);
                    finish();
                }
                if (ringToneDataPlaying.isPlaying())
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
                finish();
            }
        };
    }


    //play music
    private void play(RingTone ringToneData) {
        ringToneData.setChangeStatus(false);
        if (!CommonUntil.isNullorEmty(ringToneData.getUrl())
                || !CommonUntil.isNullorEmty(ringToneData.getPath())) {
            Log.i(TAG, "Url-play: " + ringToneData.getUrl());
            String url = ringToneData.getUrl();
            if (!CommonUntil.isNullorEmty(ringToneData.getPath()))
                url = ringToneData.getPath();
            mediaPlayer = mMediaInstance.playMusic(activity, url, ringToneData.getId() + "", onChangeListener);
            ringToneData.setPlaying(true);
        } else {
            ringToneData.setPlaying(false);
            idPlaying = -1;
            CommonUntil.createDialog(activity, activity.getString(R.string.errorProcess), null).show();
        }
    }

    private void finish() {
//        ringToneDataPlaying.setChangeStatus(true);
        idPlaying = -1;
        if (ringToneDataPlaying.isDownLoad())
            ivStatus.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_cloud_download));
        else
            ivStatus.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_head_phone));
    }

    public void stopAudio() {
        if (mediaPlayer != null) {
            mMediaInstance.stop();
            if (skPlaying != null)
                skPlaying.setProgress(0);
            idPlaying = -1;
        }
        if (ringToneDataPlaying != null)
            ringToneDataPlaying.setChangeStatus(true);
    }

    @Override
    public int getCount() {
        return lstRingStone.size();
    }

    @Override
    public Object getItem(int position) {
        return lstRingStone.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_music, parent, false);
        ImageButton iv_btn_more_func = convertView.findViewById(R.id.iv_btn_more_func);
        ImageButton iv_icon_status = convertView.findViewById(R.id.iv_icon_status);
        TextView tv_name = convertView.findViewById(R.id.tv_name);
        SeekBar sk_progress = convertView.findViewById(R.id.sk_progress);
        LinearLayout ln_layout_parent_item = convertView.findViewById(R.id.ln_layout_parent_item);

        final RingTone ringToneData = lstRingStone.get(position);
        if (ringToneData == null) return convertView;
        if (idPlaying == ringToneData.getId()) {
            skPlaying = sk_progress;
            ivStatus = iv_icon_status;
            if (ringToneData.isChangeStatus()) {
                if (!ringToneData.isPlaying()) {
                    play(ringToneData);
                    iv_icon_status.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_pause_circle));
                } else {
                    ringToneData.setChangeStatus(false);
                    sk_progress.setMax(mediaPlayer.getDuration());
                    sk_progress.setProgress(mediaPlayer.getCurrentPosition());
                    ringToneData.setPlaying(false);
                    iv_icon_status.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_play_circle));
                }
            } else {
                if (ringToneData.isPlaying()) {
                    iv_icon_status.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_pause_circle));
                } else {
                    iv_icon_status.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_play_circle));
                }
            }
        } else {
            sk_progress.setProgress(0);
            if (ringToneData.isDownLoad())
                iv_icon_status.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_cloud_download));
            else
                iv_icon_status.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_head_phone));
        }

        tv_name.setText(ringToneData.getName());

        iv_icon_status.setOnClickListener(v -> {
            //neu chon bai moi reset seekbar dang play
            if (idPlaying != ringToneData.getId()) {
                ringToneData.setPlaying(false);
                if (skPlaying != null)
                    skPlaying.setProgress(0);
            }
            ringToneData.setChangeStatus(true);
            mHandler.removeCallbacks(runnable);
            idPlaying = ringToneData.getId();
            ringToneDataPlaying = ringToneData;
            if (mediaPlayer != null)
                mediaPlayer.pause();
            notifyDataSetChanged();
        });
        ln_layout_parent_item.setOnClickListener(v -> {
            Intent intent = new Intent(activity, DetailActivityCategory.class);
            intent.putExtra("rintoneData", ringToneData);
            Bundle bundle=new Bundle();
            bundle.putString("title",title);
            intent.putExtras(bundle);
            activity.startActivity(intent);
            SlideAnimationUtil.overridePendingTransitionEnter(activity);
        });

        iv_btn_more_func.setOnClickListener(view -> {
            DialogFragmentMoreCategory dialogFragmentMoreFucntion = new DialogFragmentMoreCategory();
            dialogFragmentMoreFucntion.setRingTone(ringToneData,title);
            dialogFragmentMoreFucntion.show(((AppCompatActivity) activity).getSupportFragmentManager(), "");
        });
        return convertView;
    }
}
