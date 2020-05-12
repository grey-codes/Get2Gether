package com.get2gether;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

import com.get2gether.data.DatabaseDescription;
import com.get2gether.data.NotificationDatabaseHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.ArrayList;

public class CustomNotifications extends WakefulBroadcastReceiver {

    private static final String ACTION_START_NOTIFICATION_SERVICE = "ACTION_START_NOTIFICATION_SERVICE";
    private static final String ACTION_DELETE_NOTIFICATION = "ACTION_DELETE_NOTIFICATION";
    private static int notificationId = 1;


    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount googleAccount;
    private MeetingNetwork meetingNetwork;

    public static void setupAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getStartPendingIntent(context);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() +
                        5 * 1000,5*1000,               alarmIntent);
    }

    private ArrayList<Integer> getStoredNotifs(Context c) {
        ArrayList<Integer> al = new ArrayList<>();
        SQLiteDatabase db = new NotificationDatabaseHelper(c).getReadableDatabase();

        String[] columns = {DatabaseDescription.Notification._ID, DatabaseDescription.Notification.COLUMN_ID};

        Cursor cursor = db.query(DatabaseDescription.Notification.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            int index;

            index = cursor.getColumnIndexOrThrow(columns[0]);
            int _id = cursor.getInt(index);

            index = cursor.getColumnIndexOrThrow(columns[1]);
            int remoteid = cursor.getInt(index);

            al.add(remoteid);
        }

        return al;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //Intent serviceIntent = null;
        if (ACTION_START_NOTIFICATION_SERVICE.equals(action)) {
/*
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
            */

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.server_client_id))
                    .requestEmail()
                    .build();
            // Build a GoogleSignInClient with the options specified by gso.
            mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
            googleAccount = GoogleSignIn.getLastSignedInAccount(context);
            meetingNetwork = new MeetingNetwork(googleAccount);
            meetingNetwork.getMeetings(meetings -> {
                SQLiteDatabase db = new NotificationDatabaseHelper(context).getWritableDatabase();
                ArrayList<Integer> cachedIDs = getStoredNotifs(context);
                for (Meeting m : meetings) {
                    if (!cachedIDs.contains(m.getId())) {
                        cachedIDs.add(m.getId());
                        //add to db
// Create a new map of values, where column names are the keys
                        ContentValues values = new ContentValues();
                        //values.put(DatabaseDescription.Notification._ID, title);
                        values.put(DatabaseDescription.Notification.COLUMN_ID, m.getId());

// Insert the new row, returning the primary key value of the new row
                        long newRowId = db.insert(DatabaseDescription.Notification.TABLE_NAME, null, values);
                        System.out.println(newRowId);


                        Intent launchViewMeetings = new Intent(context, ViewMeetings.class);
                        launchViewMeetings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launchViewMeetings, 0);

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "AAAAA")
                                .setSmallIcon(R.drawable.ic_android_black_24dp)
                                .setContentTitle("Meeting Invitation")
                                .setContentText("\"" + m.getTitle() + "\" is waiting for you to accept")
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText("\"" + m.getTitle() + "\" is waiting for you to accept at times " + m.getTimeString()))
                                .setContentIntent(pendingIntent)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                        notificationManager.notify(notificationId++, builder.build());
                    }
                }
            });

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