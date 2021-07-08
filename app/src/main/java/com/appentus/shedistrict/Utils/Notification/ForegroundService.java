package com.appentus.shedistrict.Utils.Notification;


import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.appentus.shedistrict.R;
import com.appentus.shedistrict.Utils.SheDistrict;
import com.appentus.shedistrict.view.activity.SplashActivity;

import java.net.Socket;
import java.util.List;


public class ForegroundService extends Service {

    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    public static final String channelName = "Bee Service";
    private Socket mSocket;
    BroadcastReceiver connectionReceiver;
    boolean isFirst = true;
    CountDownTimer countDownTimer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("ath","push");
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        createNotificationChannel();
        Intent notificationIntent = new Intent(this, SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_logo)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        connectionReceiver = new BroadcastReceiver() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onReceive(Context context, Intent intent) {

                sendPush("ath","notyyy","1");
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectionReceiver, intentFilter);


        countDownTimer = new CountDownTimer(30000, 1000) {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            public void onTick(long millisUntilFinished) {
                long sec = millisUntilFinished / 1000;
                if (sec % 2 == 0) {
                    if (isAppIsInBackground(ForegroundService.this)) {
                    }
                }
            }

            public void onFinish() {

            }

        };


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();/*
        Util.stopNotificationSound();*/
        unregisterReceiver(connectionReceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Athmer Service",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            serviceChannel.setDescription("Searching For a Driver");
            serviceChannel.enableLights(true);
            serviceChannel.enableVibration(true);
            serviceChannel.setLightColor(Color.RED);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void sendPush(String title, String data, String type) {
        if (isAppIsInBackground(this)) {
            if (!type.equalsIgnoreCase("1")) {
                //NotificationUtil.createNotification(SheDistrict.getBaseContextApp(),title,data,type,data);
            } else if (type.equalsIgnoreCase("2")) {
                showNotificationMessage(title, data, type);
            } else {

                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancelAll();
          /*      Util.stopNotificationSound();*/
                Log.e("ath","push");
            }
        } else {
            if (type.equalsIgnoreCase("booking_request")) {
                /*Intent intent = new Intent(Constants.BOOKING_REQUEST);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                Util.notificationSound();*/

            }
        }
    }

    private void showNotificationMessage(String title, String data, String type) {
        String message = "Notification";
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            assert notificationManager != null;
            mChannel.enableVibration(true);
            notificationManager.createNotificationChannel(mChannel);
        }
       /* Intent intent = new Intent(this, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        intent.putExtra("data", data);
        intent.putExtra("from", "push");
        intent.putExtra("type", type);
//        intent.setAction(Constants.Notify_New_Rq_Pendin_Intent);*/
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(message)
                .setSound(defaultSoundUri)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);
               /* .setContentIntent(pendingIntent);*/
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
/*        stackBuilder.addNextIntent(intent);*/
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        notificationManager.cancelAll();
        notificationManager.notify(101, mBuilder.build());




    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }


        return isInBackground;
    }




}


