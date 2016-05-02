package com.example.shafiqur.classreminder.NotificationAndParsing;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;

/**
 * Created by Shafiqur on 1/15/2016.
 */
public class weekDayAdapter extends AppCompatActivity {
    public String weekDay,weekDayInformation;
    public weekDayAdapter() {
        System.out.print("Week Day inserted");


        Context mContext = weekDayAdapter.this;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            weekDayInformation = extras.getString("dayInformation");
            weekDay = extras.getString("day");
        }

        //notification building strats from Here
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                mContext);
        Notification notification = mBuilder
                .setAutoCancel(true)
                .setContentTitle(weekDay)//need to set room number here
                .setStyle(inboxStyle)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentText(weekDayInformation)
                .build();
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(11, notification);
    }
}
