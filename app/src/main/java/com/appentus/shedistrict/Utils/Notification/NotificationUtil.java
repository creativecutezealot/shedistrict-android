package com.appentus.shedistrict.Utils.Notification;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.appentus.shedistrict.R;
import com.appentus.shedistrict.network.AppConstant;
import com.appentus.shedistrict.view.activity.ChatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;;


public class NotificationUtil {
    private static NotificationManager notifManager;


    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void createNotification(Context context, String aMessage, String message, String type, JSONObject ordData) throws JSONException {
        final int NOTIFY_ID = 0; // ID of notification
        String id = "101"; // default_channel_id
        String title = "102";// Default Channel
        Intent intent = null;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;


        if (type.equalsIgnoreCase(AppConstant.NotificationEventFilter.INSTANCE.getInvitation_send())|| type.equalsIgnoreCase(AppConstant.NotificationEventFilter.INSTANCE.getInvitation_deny())) {
            intent = new Intent(context, ChatActivity.class);
            intent.setAction(AppConstant.NotificationEventFilter.INSTANCE.getInvitation_send());
            intent.putExtra("type", type);
            intent.putExtra("id", ordData.getJSONObject("result").getString("id"));
            intent.putExtra("isUpdateUi", true);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }else if(type.equalsIgnoreCase(AppConstant.NotificationEventFilter.INSTANCE.getInvitation_accept())){

            intent = new Intent(context, ChatActivity.class);
            intent.setAction(AppConstant.NotificationEventFilter.INSTANCE.getInvitation_send());
            intent.putExtra("type", type);
            intent.putExtra("id", ordData.getJSONObject("result").getString("id"));
            intent.putExtra("isUpdateUi", true);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        }


       // if (notifManager == null) {
            notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
      //  }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title + "\n" + message, importance);
                mChannel.enableVibration(true);
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(R.drawable.ic_logo)   // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setSound(defaultSoundUri);
        } else {
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(R.drawable.ic_logo)   // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setSound(defaultSoundUri)
                    .setPriority(10);
        }



/*

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            assert notifManager != null;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title + "\n" + message, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);
            assert intent != null;
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(R.drawable.ic_logo)   // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        } else {
            builder = new NotificationCompat.Builder(context, id);

            assert intent != null;
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(R.drawable.ic_logo)   // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(10);
        }
        android.app.Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);

*/



    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    private static boolean isAppIsInBackground(Context context) {
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



