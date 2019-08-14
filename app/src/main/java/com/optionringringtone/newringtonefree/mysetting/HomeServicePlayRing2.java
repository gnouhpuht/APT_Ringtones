package com.optionringringtone.newringtonefree.mysetting;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.optionringringtone.newringtonefree.MainActivity;
import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;
import com.optionringringtone.newringtonefree.mysetting.controlApp.ConstantsAppNew;
import com.optionringringtone.newringtonefree.mysetting.myReceiver.My2Receiver;
import com.optionringringtone.newringtonefree.mysetting.myReceiver.ScreenReceiver;

public class HomeServicePlayRing2 extends Service {

    //    private AlarmReceiver alarmReceiver = new AlarmReceiver();
    private My2Receiver receiver;
    private ScreenReceiver screenReceiver;

    public static boolean serviceRuning = false;

    public void onCreate() {
        super.onCreate();
        Log.d("HERE", "onCreate: ");
        serviceRuning = true;
//        EventLog.LogEventAction(this, ConstantEventLog.EVENT_SERVICE, "HomeServicePlayRing2", "onCreate");
    }

    public void deleteChannel() {
        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.deleteNotificationChannel(ConstantsAppNew.CHANEL_ID);
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
//        alarmReceiver.cancelAlarm(this);
//        alarmReceiver.setAlarm(this);
        serviceRuning = true;
    }

    //--------------------------------------------------------------//--------------------------------------------------------------
//    private int counter = 0;

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        serviceRuning = true;
//        if (intent.getAction() != null && intent.getAction().equals("xxx")) {
//            startActivity(new Intent(this, HomeActivity.class));
//            deleteChannel();
//        }


        CheckAll.CheckScreenOnOFF(this);

        IntentFilter filter2 = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter2.addAction(Intent.ACTION_SCREEN_OFF);
        screenReceiver = new ScreenReceiver();
        registerReceiver(screenReceiver, filter2);

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        receiver = new My2Receiver();
        registerReceiver(receiver, filter);


//        alarmReceiver.cancelAlarm(this);
//        alarmReceiver.setAlarm(this);
        final String is_chane = createNotificationChannels();
////        sendNotification("AAAAAAA");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent2 = new Intent(HomeServicePlayRing2.this, MainActivity.class);
//                    intent2.setAction("xxx");
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    PendingIntent mPendingIntent = PendingIntent.getActivities(HomeServicePlayRing2.this, 0,
                            new Intent[]{intent2}, PendingIntent.FLAG_ONE_SHOT);


                    Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                    Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                    Notification notification = new NotificationCompat.Builder(HomeServicePlayRing2.this, is_chane)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Listening to music makes us feel Good.")
                            .setContentText("Create your sound right now.")
                            .setAutoCancel(true)
                            .setContentIntent(mPendingIntent)
                            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                            .setDefaults(Notification.DEFAULT_ALL)
                            .addAction(android.R.color.transparent, "Create now", mPendingIntent)
                            .setPriority(NotificationCompat.PRIORITY_MAX)
                            .setCategory(Notification.CATEGORY_SERVICE)

                            .setOngoing(true)
                            .setChannelId(is_chane)
                            .setOnlyAlertOnce(true)
//                            .setContent(new RemoteViews(getPackageName(), R.layout.my_layout_bottom_sheet))
                            .build();
                    startForeground(1, notification);

                    deleteChannel();
                }
            }, 1);

        } else {
//            final NotificationManager notificationManager =
//                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//            Notification notification = new NotificationCompat.Builder(this)
//                    .setSmallIcon(android.R.color.transparent)
//                    .setContentTitle("")
//                    .setContentText("")
//                    .setSound(null)
//                    .setVibrate(null)
//                    .build();
////            notificationManager.notify(1, notification);
//            startForeground(1, notification);
        }


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        serviceRuning = false;
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        if (screenReceiver != null) {
            unregisterReceiver(screenReceiver);
            screenReceiver = null;
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("IKEN_SV", "IBinder: ");
        return null;
    }


    private String createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            final List<NotificationChannel> channels = new ArrayList<>();
//            channels.add(createAppNotificationChanel(
//                    ConstantsAppNew.CHANEL_ID,
//                    "Listening to music makes us feel Good.",
//                    "Create your sound right now.",
//                    NotificationManagerCompat.IMPORTANCE_NONE));
            try {
                final NotificationManager notificationManager = (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);
                if (notificationManager != null) {


                    //                notificationManager.createNotificationChannel(createAppNotificationChanel(
                    //                        ConstantsAppNew.CHANEL_ID,
                    //                        "Listening to music makes us feel Good.",
                    //                        "Create your sound right now.",
                    //                        NotificationManagerCompat.IMPORTANCE_NONE));
                    if (notificationManager.getNotificationChannel(ConstantsAppNew.CHANEL_ID) == null) {
                        @SuppressLint("WrongConstant") NotificationChannel mChannel = new NotificationChannel(
                                ConstantsAppNew.CHANEL_ID,
                                "Listening to music makes us feel Good.",
                                NotificationManagerCompat.IMPORTANCE_DEFAULT);
                        // Configure the notification channel.
                        mChannel.setDescription("Create your sound right now.");
                        mChannel.enableLights(true);
                        // Sets the notification light color for notifications posted to this
                        // channel, if the device supports this feature.
                        mChannel.setLightColor(Color.RED);
                        mChannel.enableVibration(true);
                        notificationManager.createNotificationChannel(mChannel);
                    }
                }
                return ConstantsAppNew.CHANEL_ID;
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        } else {
            return "";
        }
    }

//
//    private NotificationChannel createAppNotificationChanel(String chanelId, String chanelName, String chanelDescription, int chanelImportance) {
//        NotificationChannel channel = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            channel = new NotificationChannel(chanelId, chanelName, chanelImportance);
//            channel.setDescription(chanelDescription);
//
//            channel.enableLights(false);
//            channel.enableVibration(false);
//            channel.setLightColor(Color.GRAY);
//            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
//        }
//        return channel;
//    }

//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private String createNotificationChannel(String channelId, String channelName) {
//        NotificationChannel chan = new NotificationChannel(channelId,
//                channelName, NotificationManager.IMPORTANCE_NONE);
//        chan.setLightColor(Color.BLUE);
//        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
//        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(chan)
//        return channelId;
//    }


//    private void sendNotification(String messageBody) {
//        Intent intent = new Intent(this, HomeActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        String channelId = Constant.CHANEL_FIREBASE_MES;
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder =
//                new NotificationCompat.Builder(this, channelId)
//                        .setContentTitle("Music make life to good")
//                        .setContentText(messageBody)
//                        .setAutoCancel(true)
//                        .setSound(defaultSoundUri)
//                        .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        // Since android Oreo notification channel is needed.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(channelId,
//                    Constant.CHANEL_FIREBASE_MES,
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }
}
