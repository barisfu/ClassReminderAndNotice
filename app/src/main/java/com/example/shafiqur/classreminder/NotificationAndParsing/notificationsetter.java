package com.example.shafiqur.classreminder.NotificationAndParsing;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.example.shafiqur.classreminder.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Shafiqur on 1/14/2016.
 */

public class notificationsetter extends AppCompatActivity {
    public ArrayList<String> arrayList;
    public sqliteDatabase sqlClass;
    public SQLiteDatabase sqlsend;
   // public Cursor cursor;
   public MainActivity activity;
    public boolean afterlast;
    public StringBuilder forsaturday, formonday, fortuesday, forwednesday, forfriday, forSunday, forthursday;
    public Context context=null;
    //public Intent intent ,intent2;
    private AlarmManager alarmManager, alarmManager2;
    private PendingIntent alarmIntent=null, alarmIntentSaturday, alarmIntentsunday, alarmIntentmonday, alarmIntenttuesday, alarmIntentwednesday, alarmIntentthrusday, alarmIntentfriday;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        sqlClass = new sqliteDatabase(context);
        context = getApplication();
       // cursor = sqlClass.getInformaton(sqlClass);
       // afterlast = cursor.isAfterLast();
       // intent = new Intent(getApplication(), notificationAdapter.class);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager2 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //intent2 = new Intent(context, weekDayAdapter.class);
        forsaturday = new StringBuilder();

    }




    public void reminder(Cursor cursor) {
        System.out.print("inside Reminder\n");
        System.out.print(afterlast);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            System.out.print("Cursor\n");
            String hourdb = cursor.getString(cursor.getColumnIndex("hours"));
            String mindb = cursor.getString(cursor.getColumnIndex("minutes"));
            String ampmdb = cursor.getString(cursor.getColumnIndex("ampm"));
            String weekdaydb = cursor.getString(cursor.getColumnIndex("week_day"));
            String teacherName = cursor.getString(cursor.getColumnIndex("teachersName"));
            String coruseName = cursor.getString(cursor.getColumnIndex("CourseName"));
            String roomNumber = cursor.getString(cursor.getColumnIndex("room_no"));

            reminderSetter(hourdb, mindb, ampmdb, weekdaydb, teacherName, coruseName);



            if (weekdaydb.toLowerCase() == "sunday") {
                forSunday = new StringBuilder();
                forSunday.append(coruseName);
                forSunday.append("@");
                forSunday.append(hourdb + ":");
                forSunday.append(mindb + " ");
                forSunday.append(ampmdb);
                forSunday.append("@");
                forSunday.append(roomNumber);
                forSunday.append("\n");
            } else if (weekdaydb.toLowerCase() == "monday") {
                formonday = new StringBuilder();
                formonday.append(coruseName);
                formonday.append("@");
                formonday.append(hourdb + ":");
                formonday.append(mindb + " ");
                formonday.append(ampmdb);
                formonday.append("@");
                formonday.append(roomNumber);
                formonday.append("\n");
            } else if (weekdaydb.toLowerCase() == "tuesday") {
                fortuesday = new StringBuilder();
                fortuesday.append(coruseName);
                fortuesday.append("@");
                fortuesday.append(hourdb + ":");
                fortuesday.append(mindb + " ");
                fortuesday.append(ampmdb);
                fortuesday.append("@");
                fortuesday.append(roomNumber);
                fortuesday.append("\n");
            } else if (weekdaydb.toLowerCase() == "wednesday") {
                forwednesday = new StringBuilder();
                forwednesday.append(coruseName);
                forwednesday.append("@");
                forwednesday.append(hourdb + ":");
                forwednesday.append(mindb + " ");
                forwednesday.append(ampmdb);
                forwednesday.append("@");
                forwednesday.append(roomNumber);
                forwednesday.append("\n");
            } else if (weekdaydb.toLowerCase() == "thursday") {
                forthursday = new StringBuilder();
                forthursday.append(coruseName);
                forthursday.append("@");
                forthursday.append(hourdb + ":");
                forthursday.append(mindb + " ");
                forthursday.append(ampmdb);
                forthursday.append("@");
                forthursday.append(roomNumber);
                forthursday.append("\n");
            } else if (weekdaydb.toLowerCase() == "friday") {
                forfriday = new StringBuilder();
                forfriday.append(coruseName);
                forfriday.append("@");
                forfriday.append(hourdb + ":");
                forfriday.append(mindb + " ");
                forfriday.append(ampmdb);
                forfriday.append("@");
                forfriday.append(roomNumber);
                forfriday.append("\n");
            } else if (weekdaydb.toLowerCase() == "saturday") {

                forsaturday.append(coruseName);
                forsaturday.append("@");
                forsaturday.append(hourdb + ":");
                forsaturday.append(mindb + " ");
                forsaturday.append(ampmdb);
                forsaturday.append("@");
                forsaturday.append(roomNumber);
                forsaturday.append("\n");
            }

        }

    }

    public void reminderSetter(String h, String m, String am_pm, String w, String teacherName, String courseName) {
//        Toast.makeText(notificationsetter.this, "remindersetter", Toast.LENGTH_LONG).show();
        //set the magic here..
        System.out.print("inside ReminderSetter\n");
        Intent intent = new Intent(getApplication(),notificationAdapter.class);
        //putting Extras In intent
        intent.putExtra("teacherName", teacherName);
        intent.putExtra("courseName", courseName);
        intent.putExtra("hours", h);
        intent.putExtra("minutes", m);
        intent.putExtra("ampm", am_pm);

        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);



        //setting the dates times and others
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(h));
        calendar.set(Calendar.MINUTE, Integer.parseInt(m));
        if (w.toLowerCase() == "monday") {
            calendar.set(Calendar.DAY_OF_WEEK, 1);
        } else if (w.toLowerCase() == "tuesday") {
            calendar.set(Calendar.DAY_OF_WEEK, 2);
        } else if (w.toLowerCase() == "wednesday") {
            calendar.set(Calendar.DAY_OF_WEEK, 3);
        } else if (w.toLowerCase() == "thursday") {
            calendar.set(Calendar.DAY_OF_WEEK, 4);
        } else if (w.toLowerCase() == "friday") {
            calendar.set(Calendar.DAY_OF_WEEK, 5);
        } else if (w.toLowerCase() == "saturday") {
            calendar.set(Calendar.DAY_OF_WEEK, 6);
        } else if (w.toLowerCase() == "sunday") {
            calendar.set(Calendar.DAY_OF_WEEK, 7);
        }

        if (am_pm.toLowerCase() == "am")
            calendar.set(Calendar.AM_PM, Calendar.AM);
        else if (am_pm.toLowerCase() == "pm")
            calendar.set(Calendar.AM_PM, Calendar.PM);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntent);

    }


    public void weekSetup() {
//        Toast.makeText(notificationsetter.this, "weeksetup", Toast.LENGTH_LONG).show();
        Intent intent2 = new Intent(context, weekDayAdapter.class);

        //saturday adapter
        intent2.putExtra("dayInformation", forsaturday.toString());
        intent2.putExtra("day", "saturday");
        alarmIntentSaturday = PendingIntent.getBroadcast(context, 0, intent2, 0);

        //sunday adapter
        intent2.putExtra("dayInformation", forSunday.toString());
        intent2.putExtra("day", "sunday");
        alarmIntentsunday = PendingIntent.getBroadcast(context, 0, intent2, 0);

        //Monday adapter
        intent2.putExtra("dayInformation", formonday.toString());
        intent2.putExtra("day", "monday");
        alarmIntentmonday = PendingIntent.getBroadcast(context, 0, intent2, 0);

        //tuesday adapter
        intent2.putExtra("dayInformation", fortuesday.toString());
        intent2.putExtra("day", "tuesday");
        alarmIntenttuesday = PendingIntent.getBroadcast(context, 0, intent2, 0);

        //wednesday adapter
        intent2.putExtra("dayInformation", forwednesday.toString());
        intent2.putExtra("day", "wednesday");
        alarmIntentwednesday = PendingIntent.getBroadcast(context, 0, intent2, 0);

        //thrusday adapter
        intent2.putExtra("dayInformation", forthursday.toString());
        intent2.putExtra("day", "thursday");
        alarmIntentthrusday = PendingIntent.getBroadcast(context, 0, intent2, 0);

        //friday adapter
        intent2.putExtra("dayInformation", forfriday.toString());
        intent2.putExtra("day", "friday");
        alarmIntentfriday = PendingIntent.getBroadcast(context, 0, intent2, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 7);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntentmonday);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR, 7);
        calendar2.set(Calendar.MINUTE, 00);
        calendar2.set(Calendar.DAY_OF_WEEK, 2);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntenttuesday);

        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(Calendar.HOUR, 7);
        calendar3.set(Calendar.MINUTE, 00);
        calendar3.set(Calendar.DAY_OF_WEEK, 3);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntentwednesday);

        Calendar calendar4 = Calendar.getInstance();
        calendar4.set(Calendar.HOUR, 7);
        calendar4.set(Calendar.MINUTE, 00);
        calendar4.set(Calendar.DAY_OF_WEEK, 4);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntentwednesday);

        Calendar calendar5 = Calendar.getInstance();
        calendar5.set(Calendar.HOUR, 7);
        calendar5.set(Calendar.MINUTE, 00);
        calendar5.set(Calendar.DAY_OF_WEEK, 5);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntentwednesday);

        Calendar calendar6 = Calendar.getInstance();
        calendar6.set(Calendar.HOUR, 7);
        calendar6.set(Calendar.MINUTE, 00);
        calendar6.set(Calendar.DAY_OF_WEEK, 6);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntentwednesday);

        Calendar calendar7 = Calendar.getInstance();
        calendar7.set(Calendar.HOUR, 7);
        calendar7.set(Calendar.MINUTE, 00);
        calendar7.set(Calendar.DAY_OF_WEEK, 7);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntentwednesday);


    }


}
