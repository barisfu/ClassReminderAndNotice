package com.example.shafiqur.classreminder.NotificationAndParsing;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by Shafiqur on 1/15/2016.
 */
public class notificationAdapter extends AppCompatActivity {
    public String teacherName, courseName, hours, minutes, am_pm;

    public notificationAdapter() {

        Toast.makeText(notificationAdapter.this,"Notification Adapter",Toast.LENGTH_SHORT).show();
        Context mContext = notificationAdapter.this;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            teacherName = extras.getString("teacherName");
            courseName = extras.getString("courseName");
            hours = extras.getString("hours");
            minutes = extras.getString("minutes");
            am_pm = extras.getString("ampm");
        }

        //notification building strats from Here
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
        Notification notification = mBuilder.setTicker(courseName)
                .setAutoCancel(true)
                .setContentTitle("Class at Room No")//need to set room number here
                .setStyle(inboxStyle)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentText(teacherName + courseName + hours + minutes + am_pm)
                .build();
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(11, notification);


    }
}
