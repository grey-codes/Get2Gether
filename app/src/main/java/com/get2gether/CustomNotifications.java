package com.get2gether;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

import java.util.Date;

public class CustomNotifications extends WakefulBroadcastReceiver {

    private static final String ACTION_START_NOTIFICATION_SERVICE = "ACTION_START_NOTIFICATION_SERVICE";
    private static final String ACTION_DELETE_NOTIFICATION = "ACTION_DELETE_NOTIFICATION";
    private static int notificationId = 1;

    public static void setupAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getStartPendingIntent(context);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() +
                        5 * 1000,5*1000,               alarmIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //Intent serviceIntent = null;
        if (ACTION_START_NOTIFICATION_SERVICE.equals(action)) {

            // Create an explicit intent for an Activity in your app
            Intent launchViewMeetings = new Intent(context, ViewMeetings.class);
            launchViewMeetings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launchViewMeetings, 0);

            Log.i(getClass().getSimpleName(), "onReceive from alarm, starting notification service");
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "AAAAA")
                    .setSmallIcon(R.drawable.ic_android_black_24dp)
                    .setContentTitle("Confirmed Meeting")
                    .setContentText("\"CS533 Group Project\" has a meeting time at 15:30-16:30")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("\"CS533 Group Project\" has a meeting time at 15:30-16:30"))
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            //notificationManager.notify(notificationId++, builder.build());
        } else if (ACTION_DELETE_NOTIFICATION.equals(action)) {
            Log.i(getClass().getSimpleName(), "onReceive delete notification action, starting notification service to handle delete");
        }
    }

    private static PendingIntent getStartPendingIntent(Context context) {
        Intent intent = new Intent(context, CustomNotifications.class);
        intent.setAction(ACTION_START_NOTIFICATION_SERVICE);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent getDeleteIntent(Context context) {
        Intent intent = new Intent(context, CustomNotifications.class);
        intent.setAction(ACTION_DELETE_NOTIFICATION);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}