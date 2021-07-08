package com.appentus.shedistrict.Utils.Notification;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.provider.CalendarContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.appentus.shedistrict.R;
import com.appentus.shedistrict.Utils.PrefManager;
import com.appentus.shedistrict.models.EventBean;
import com.appentus.shedistrict.network.AppConstant;
import com.appentus.shedistrict.view.activity.ChatActivity;
import com.appentus.shedistrict.view.activity.SplashActivity;
import com.appentus.shedistrict.view.activity.WriteBioActivity;
import com.appentus.shedistrict.view.activity.WriteBioCameraActivity;
import com.appentus.shedistrict.view.activity.WriteBioVideoActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class
FCMEvent extends FirebaseMessagingService {

    String channelId = "bee-01";
    String channelName = "bee";

    Context context;
    public static String token;
    BroadcastReceiver broadcastReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        FCMEvent.this.getSharedPreferences("token_shared", MODE_PRIVATE).edit().putString("token", s).apply();
        token = getSharedPreferences("token_shared", MODE_PRIVATE).getString("token", "");

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.e("onMessageReceived: ", "cfasf" + remoteMessage.getData().toString());
        Log.e("onMessageReceivedjbjbj:", remoteMessage.getData().toString());
        try {

            JSONObject js = new JSONObject(remoteMessage.getData().get("data"));
            JSONObject result = js.getJSONObject("result");

            showNotificationMessage(result, js);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showNotificationMessage(JSONObject result, JSONObject base) throws JSONException {
        Intent intentchat = new Intent();
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);
        }

        if (base.getString("type").equalsIgnoreCase(AppConstant.NotificationEventFilter.INSTANCE.getInvitation_send()) || base.getString("type").equalsIgnoreCase(AppConstant.NotificationEventFilter.INSTANCE.getInvitation_accept()) || base.getString("type").equalsIgnoreCase(AppConstant.NotificationEventFilter.INSTANCE.getInvitation_deny())) {

            Intent intentbrodcast = new Intent(AppConstant.NotificationEventFilter.INSTANCE.getInvitation_send());
            //  intentbrodcast.setAction(AppConstant.NotificationEventFilter.INSTANCE.getInvitation_send());
            intentbrodcast.putExtra("type", base.getString("type"));
            intentbrodcast.putExtra("notificationid", result.getString("id"));
            intentbrodcast.putExtra("id", result.getString("sender_id"));
            intentbrodcast.putExtra("text", result.getString("friend_name"));
            LocalBroadcastManager.getInstance(this).sendBroadcast(intentbrodcast);
        }
        if (base.getString("type").equalsIgnoreCase(AppConstant.NotificationEventFilter.INSTANCE.getNewMessage())) {
            intentchat = new Intent(this, ChatActivity.class);
            intentchat.putExtra("type", base.getString("type"));
            intentchat.putExtra("text", result.getString("friend_name"));
            intentchat.putExtra("id", result.getString("sender_id"));
            Log.e("dffdgd", result.getString("sender_id"));

        }
        if (base.getString("type").equalsIgnoreCase(AppConstant.NotificationEventFilter.INSTANCE.getVerify())) {
            if (result.getJSONArray("user_details").getJSONObject(0).getString("user_intro_video").isEmpty()) {
                intentchat = new Intent(this, WriteBioCameraActivity.class);
                intentchat.putExtra("type", base.getString("type"));
                intentchat.putExtra("imageurl", result.getJSONArray("user_details").getJSONObject(0).getString("user_bio_image"));
                intentchat.putExtra("userstatus", result.getString("user_status"));
                intentchat.putExtra("detail_user_id", result.getJSONArray("user_details").getJSONObject(0).getString("detail_user_id"));
            } else {
                intentchat = new Intent(this, WriteBioVideoActivity.class);
                intentchat.putExtra("type", base.getString("type"));
                intentchat.putExtra("vediourl", result.getJSONArray("user_details").getJSONObject(0).getString("user_intro_video"));
                intentchat.putExtra("userstatus", result.getString("user_status"));
                intentchat.putExtra("detail_user_id", result.getJSONArray("user_details").getJSONObject(0).getString("detail_user_id"));
            }
        }

        if (base.getString("type").equalsIgnoreCase(AppConstant.NotificationEventFilter.INSTANCE.getUnverify())) {
            if (result.getJSONArray("user_details").getJSONObject(0).getString("user_intro_video").isEmpty()) {

                intentchat = new Intent(this, WriteBioCameraActivity.class);
                intentchat.putExtra("type", base.getString("type"));
                intentchat.putExtra("imageurl", result.getJSONArray("user_details").getJSONObject(0).getString("user_bio_image"));
                intentchat.putExtra("userstatus", result.getString("user_status"));
                intentchat.putExtra("detail_user_id", result.getJSONArray("user_details").getJSONObject(0).getString("detail_user_id"));

            } else {

                intentchat = new Intent(this, WriteBioVideoActivity.class);
                intentchat.putExtra("type", base.getString("type"));
                intentchat.putExtra("vediourl", result.getJSONArray("user_details").getJSONObject(0).getString("user_intro_video"));
                intentchat.putExtra("userstatus", result.getString("user_status"));
                intentchat.putExtra("detail_user_id", result.getJSONArray("user_details").getJSONObject(0).getString("detail_user_id"));

            }
        }

        if (base.getString("type").equalsIgnoreCase(AppConstant.NotificationEventFilter.INSTANCE.getAccept_meet())) {

            intentchat = new Intent(Intent.ACTION_INSERT);
            intentchat.setType("vnd.android.cursor.item/event");
            intentchat.putExtra(CalendarContract.Events.TITLE, base.getString("title"));
            intentchat.putExtra(CalendarContract.Events.EVENT_LOCATION, result.getString("meeting_location"));
            intentchat.putExtra(CalendarContract.Events.DESCRIPTION, base.getString("body"));
            intentchat.putExtra(CalendarContract.Events.ALLOWED_REMINDERS, true);

            String[] dateStrs = result.getString("meeting_date").split("/");

            GregorianCalendar calDate = new GregorianCalendar(Integer.valueOf(dateStrs[0]), Integer.valueOf(dateStrs[1]) - 1, Integer.valueOf(dateStrs[2]));
            //calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, true)
            //var sdf = SimpleDateFormat("HH:mm:ss")
            //var date = Date(sdf.format(result[i].meeting_time)).time - (15*60)

            String time = result.getString("meeting_date") + " " + result.getString("meeting_time");
            Log.e("erfejtbjdgdfgek", time);
            long timeInMilSeconds = milliseconds(time);
            //  Log.e("timeInMilSeconds",timeInMilSeconds.toString())
            intentchat.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, timeInMilSeconds);
           // startActivity(calIntent);

        }
        assert intentchat != null;
        intentchat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentchat, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_logo)
                .setColor(getResources().getColor(R.color.txtDarkpink))
                .setContentTitle("SheDistrict")
                .setContentText(base.getString("body"))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(intentchat);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        notificationManager.cancelAll();
        notificationManager.notify(101, mBuilder.build());

    }
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
        }
        else
        {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }

    public long milliseconds(String date)
    {
        //String date_ = date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
        try
        {
            Date mDate = sdf.parse(date);
            long timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
            return timeInMilliseconds;
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

}
