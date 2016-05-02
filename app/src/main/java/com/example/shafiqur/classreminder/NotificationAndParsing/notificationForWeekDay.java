package com.example.shafiqur.classreminder.NotificationAndParsing;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;

/**
 * Created by Shafiqur on 1/15/2016.
 */
public class notificationForWeekDay extends AppCompatActivity {
    Context mContext = notificationForWeekDay.this;

    public void notificationForWeekDaySetter(String weekDay, String routine) {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                mContext);
        Notification notification = mBuilder
                .setAutoCancel(true)
                .setContentTitle("Class at Room No")//need to set room number here
                .setStyle(inboxStyle)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentText(routine)
                .build();
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(11, notification);
    }
}
