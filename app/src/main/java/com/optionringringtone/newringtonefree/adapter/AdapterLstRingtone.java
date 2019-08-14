package com.optionringringtone.newringtonefree.adapter;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.optionringringtone.newringtonefree.DetailActivity;
import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;
import com.optionringringtone.newringtonefree.Untils.CommonUntil;
import com.optionringringtone.newringtonefree.Untils.MediaUntil;
import com.optionringringtone.newringtonefree.Untils.SlideAnimationUtil;
import com.optionringringtone.newringtonefree.fragment.DialogFragmentMoreFucntion;
import com.optionringringtone.newringtonefree.object.RingTone;

import java.util.List;

public class AdapterLstRingtone extends RecyclerView.Adapter<AdapterLstRingtone.ViewHolder> {
    private static final String TAG = "AdapterLstRingtone";
    private List<RingTone> lstRingStone;
    private RingTone ringToneDataPlaying;
    private int idPlaying;

    private static SeekBar skPlaying;
    private static ImageView ivStatus;
    private Activity activity;

    private static MediaUntil mMediaInstance;
    private static MediaPlayer mediaPlayer;
    private Handler mHandler;
    private Runnable runnable;
    private MediaUntil.onChangeListener onChangeListener;

    public AdapterLstRingtone(Activity activity, List<RingTone> lstRingStone) {
        this.lstRingStone = lstRingStone;
        this.activity = activity;
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_adapter_music, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final RingTone ringToneData = lstRingStone.get(i);
        if (ringToneData == null) return;
        if (idPlaying == ringToneData.getId()) {
            skPlaying = viewHolder.sk_progress;
            ivStatus = viewHolder.iv_icon_status;
            if (ringToneData.isChangeStatus()) {
                if (!ringToneData.isPlaying()) {
                    play(ringToneData);
                    viewHolder.iv_icon_status.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_pause_circle));
                } else {
                    ringToneData.setChangeStatus(false);
                    viewHolder.sk_progress.setMax(mediaPlayer.getDuration());
                    viewHolder.sk_progress.setProgress(mediaPlayer.getCurrentPosition());
                    ringToneData.setPlaying(false);
                    viewHolder.iv_icon_status.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_play_circle));
                }
            } else {
                if (ringToneData.isPlaying()) {
                    viewHolder.iv_icon_status.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_pause_circle));
                } else {
                    viewHolder.iv_icon_status.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_play_circle));
                }
            }
        } else {
            viewHolder.sk_progress.setProgress(0);
            if (ringToneData.isDownLoad())
                viewHolder.iv_icon_status.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_cloud_download));
            else
                viewHolder.iv_icon_status.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_head_phone));
        }

        viewHolder.tv_name.setText(ringToneData.getName());

        viewHolder.iv_icon_status.setOnClickListener(v -> {
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
        viewHolder.ln_layout_parent_item.setOnClickListener(v ->{
            Intent intent = new Intent(activity, DetailActivity.class);
            intent.putExtra("rintoneData", ringToneData);
            activity.startActivity(intent);
            SlideAnimationUtil.overridePendingTransitionEnter(activity);
        });

        viewHolder.iv_btn_more_func.setOnClickListener(view -> {
            DialogFragmentMoreFucntion dialogFragmentMoreFucntion = new DialogFragmentMoreFucntion();
            dialogFragmentMoreFucntion.setRingTone(ringToneData);
            dialogFragmentMoreFucntion.show(((AppCompatActivity)activity).getSupportFragmentManager(),"");
        });
    }


    //play music
    private void play(RingTone ringToneData) {
        ringToneData.setChangeStatus(false);
        if (!CommonUntil.isNullorEmty(ringToneData.getUrl())) {
            Log.i(TAG, "Url-play: " + ringToneData.getUrl());
            mediaPlayer = mMediaInstance.playMusic(activity, ringToneData.getUrl(), ringToneData.getId() + "", onChangeListener);
            ringToneData.setPlaying(true);
        } else {

            ringToneData.setPlaying(false);
            idPlaying = -1;
            CommonUntil.createDialog(activity, activity.getString(R.string.errorProcess), null).show();
        }
    }

    private void finish() {
//        ringToneDataPlaying.setChangeStatus(true);
        if (ringToneDataPlaying.isDownLoad())
            ivStatus.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_cloud_download));
        else
            ivStatus.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_head_phone));
    }

    public void stopAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mMediaInstance.stop();
            skPlaying.setProgress(0);
        }
        if(ringToneDataPlaying != null)
            ringToneDataPlaying.setChangeStatus(true);
    }

    @Override
    public int getItemCount() {
        return lstRingStone.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_icon_status;
        ImageButton  iv_btn_more_func;
        TextView tv_name;
        SeekBar sk_progress;
        LinearLayout ln_layout_parent_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_btn_more_func = itemView.findViewById(R.id.iv_btn_more_func);
            iv_icon_status = itemView.findViewById(R.id.iv_icon_status);
            tv_name = itemView.findViewById(R.id.tv_name);
            sk_progress = itemView.findViewById(R.id.sk_progress);
            ln_layout_parent_item = itemView.findViewById(R.id.ln_layout_parent_item);
        }
    }
}
