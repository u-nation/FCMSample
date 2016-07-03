package com.u_nation.fcm_sample;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.RemoteMessage.Notification;

import java.util.Map;

import timber.log.Timber;

public class MyMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> fcm_data = remoteMessage.getData();
        Notification fcm_notification = remoteMessage.getNotification();

        Timber.i("payload data = %s", fcm_data.size() == 0 ? "no data" : fcm_data.toString());
        Timber.i("payload notification = %s", fcm_notification == null ? "no notification" : "title = " + fcm_notification.getTitle() + " body = " + fcm_notification.getBody());

        if (fcm_data.size() > 0)
            sendNotification(fcm_data.get("title"), fcm_data.get("body"));
        else if (fcm_notification != null)
            sendNotification(fcm_notification.getTitle(), fcm_notification.getBody());
    }

    private void sendNotification(String title, String body) {
        try {
            PowerManager.WakeLock wakelock = ((PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "");
            wakelock.acquire();

            Intent intent = new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
            /*DroidKaigiの通知アイコンを使用させて*/
            builder.setSmallIcon(R.drawable.ic_stat_notification);
            builder.setVibrate(new long[]{0, 200});
            builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            builder.setContentTitle(title == null ? getString(R.string.app_name) : title);
            builder.setContentText(body);
            builder.setColor(0xffffa714);
            builder.setAutoCancel(true);
            builder.setVibrate(new long[]{0, 200});
            builder.setSound(alarmSound);
            builder.setContentIntent(contentIntent);
            builder.setAutoCancel(true);

            NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle(builder);
            bigTextStyle.setBigContentTitle(title == null ? getString(R.string.app_name) : title);
            bigTextStyle.bigText(body);

            NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, bigTextStyle.build());
            wakelock.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}