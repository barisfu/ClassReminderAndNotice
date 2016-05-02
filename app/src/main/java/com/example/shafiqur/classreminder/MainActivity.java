package com.example.shafiqur.classreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shafiqur.classreminder.NotificationAndParsing.notificationAdapter;
import com.example.shafiqur.classreminder.NotificationAndParsing.notificationsetter;
import com.example.shafiqur.classreminder.NotificationAndParsing.sqliteDatabase;
import com.example.shafiqur.classreminder.NotificationAndParsing.weekDayAdapter;
import com.example.shafiqur.classreminder.helper.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    public Button fetchButton, ab, sqlGetDataButton,setReminder;
    public TextView tv;
    public RequestQueue queue;
    public sqliteDatabase sqlClass;
    public  SQLiteDatabase sqlsend;
    public  Context context =MainActivity.this;
    private PrefManager pref;

    public ArrayList<String> arrayList;

    // public Cursor cursor;
    public MainActivity activity;
    public boolean afterlast;
    public StringBuilder forsaturday, formonday, fortuesday, forwednesday, forfriday, forSunday, forthursday;

    //public Intent intent ,intent2;
    public AlarmManager alarmManager, alarmManager2;
    public PendingIntent alarmIntent, alarmIntentSaturday, alarmIntentsunday, alarmIntentmonday, alarmIntenttuesday, alarmIntentwednesday, alarmIntentthrusday, alarmIntentfriday;

    public final String serverAddress="http://www.inanik.info/project/values.php";



    public static final  String deleteQuery= "truncate routine_information";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);
        sqlClass= new sqliteDatabase(context);

        fetchButton = (Button) findViewById(R.id.b1);
        setReminder= (Button)findViewById(R.id.setReminder);
        //context = MainActivity.this;
       // ab = (Button) findViewById(R.id.array);

        pref = new PrefManager(getApplicationContext());



        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            Toast.makeText(MainActivity.this, "Net Connected", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(MainActivity.this, "Not Connected To the Internet ", Toast.LENGTH_SHORT).show();
            //tv.setText("Net Connected");
        }

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(serverAddress,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray jsonArray) {
                                //Toast.makeText(MainActivityParse.this,jsonArray.toString(),Toast.LENGTH_SHORT).show();
                                context.deleteDatabase("classInformation.db");
                                String jsonResponseArray = "";
//                                tv.setText("");
                                int flagForDatabaseDelete = 1;

                                // sqlClass.onUpgrade(sqlsend, (int) (System.currentTimeMillis()/1000), (int) (System.currentTimeMillis()/1000)+5);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        JSONObject teacher = (JSONObject) jsonArray.get(i);
                                        String name = teacher.getString("teacher_name");
                                        String course_code = teacher.getString("course_code");
                                        String course_name = teacher.getString("course_name");
                                        String hours = teacher.getString("hours");
                                        String minutes= teacher.getString("minutes");
                                        String ampm = teacher.getString("ampm");
                                        String roomNo = teacher.getString("room_no");
                                        String week_day = teacher.getString("week_day");
                                        jsonResponseArray = name + " " + course_code + " " + course_name + " " + hours + " "+minutes+" "+ ampm+" " + roomNo + " " + week_day + "\n\n";
                                        sqlsend = sqlClass.getWritableDatabase();
                                        sqlClass.receiveData(name, course_code, course_name, hours, minutes, ampm, roomNo, week_day, sqlsend, flagForDatabaseDelete);
                                        sqlClass.close();
                                        //tv.append(name+" "+course_code+"\n");

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    flagForDatabaseDelete = 2;
                                }
                                Toast.makeText(MainActivity.this,"Sync Complete",Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                               // tv.setText(volleyError.toString());
                            }
                        }
                );
                queue.add(jsonArrayRequest);
            }

        });

       /* sqlGetDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //copied from previous project
                // table info  //teachersName TEXT,courseCode TEXT,CourseName Text, time TEXT, room_no TEXT,week_day
                StringBuilder stringBuilder = new StringBuilder();
                tv.setText("");
                Cursor crx = sqlClass.getInformaton(sqlClass);
                crx.moveToFirst();

                while (!crx.isAfterLast()) {
                    stringBuilder.append(crx.getString(crx.getColumnIndex("teachersName")));
                    stringBuilder.append(" ");
                    stringBuilder.append(crx.getString(crx.getColumnIndex("courseCode")));
                    stringBuilder.append(" ");
                    stringBuilder.append(crx.getString(crx.getColumnIndex("CourseName")));
                    stringBuilder.append(" ");
                    stringBuilder.append(crx.getString(crx.getColumnIndex("hours")));
                    stringBuilder.append(" ");
                    stringBuilder.append(crx.getString(crx.getColumnIndex("minutes")));
                    stringBuilder.append(" ");
                    stringBuilder.append(crx.getString(crx.getColumnIndex("ampm")));
                    stringBuilder.append(" ");
                    stringBuilder.append(crx.getString(crx.getColumnIndex("room_no")));
                    stringBuilder.append(" ");
                    stringBuilder.append(crx.getString(crx.getColumnIndex("week_day")));
                    stringBuilder.append("\n");
                    crx.moveToNext();
                }
                crx.close();
                String informationFromDatabase = stringBuilder.toString();
               // Toast.makeText(MainActivityParse.this, informationFromDatabase, Toast.LENGTH_LONG).show();
                //tv.setText("Data From SQLITE Database \n" + informationFromDatabase);
                //tv.setTextColor(Color.BLUE);
            }
        });
        */
        setReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               notificationsetter notificationsetterobject= new notificationsetter();
                //Cursor crx2 = sqlClass.getInformaton(sqlClass);
                //notificationsetterobject.reminder(crx2);
                //notificationsetterobject.weekSetup();
               // Intent intent = new Intent(MainActivity.this,notificationsetter.class);
               // startActivity(intent);

                System.out.print("inside Reminder\n");
                System.out.print(afterlast);

                Cursor cursor = sqlClass.getInformaton(sqlClass);
                System.out.print("Total Row Count" + cursor.getCount());
                cursor.moveToFirst();
                for(int i=0;i<cursor.getCount();i++)
                //while (!cursor.isAfterLast())
                {
                    System.out.print("Cursor\n");
                    String hourdb = cursor.getString(cursor.getColumnIndex("hours"));
                    String mindb = cursor.getString(cursor.getColumnIndex("minutes"));
                    String ampmdb = cursor.getString(cursor.getColumnIndex("ampm"));
                    String weekdaydb = cursor.getString(cursor.getColumnIndex("week_day"));
                    String teacherName = cursor.getString(cursor.getColumnIndex("teachersName"));
                    String coruseName = cursor.getString(cursor.getColumnIndex("CourseName"));
                    String roomNumber = cursor.getString(cursor.getColumnIndex("room_no"));

                    reminderSetter(hourdb, mindb, ampmdb, weekdaydb, teacherName, coruseName);
                    forSunday = new StringBuilder();
                    formonday = new StringBuilder();
                    fortuesday = new StringBuilder();
                    forwednesday = new StringBuilder();
                    forthursday = new StringBuilder();
                    forfriday = new StringBuilder();
                    forsaturday = new StringBuilder();

                    if (weekdaydb.toLowerCase() == "sunday") {

                        forSunday.append(coruseName);
                        forSunday.append("@");
                        forSunday.append(hourdb + ":");
                        forSunday.append(mindb + " ");
                        forSunday.append(ampmdb);
                        forSunday.append("@");
                        forSunday.append(roomNumber);
                        forSunday.append("\n");
                        System.out.print(forSunday.toString());
                    }

                    else if (weekdaydb.toLowerCase() == "monday") {

                        formonday.append(coruseName);
                        formonday.append("@");
                        formonday.append(hourdb + ":");
                        formonday.append(mindb + " ");
                        formonday.append(ampmdb);
                        formonday.append("@");
                        formonday.append(roomNumber);
                        formonday.append("\n");
                    } else if (weekdaydb.toLowerCase() == "tuesday") {

                        fortuesday.append(coruseName);
                        fortuesday.append("@");
                        fortuesday.append(hourdb + ":");
                        fortuesday.append(mindb + " ");
                        fortuesday.append(ampmdb);
                        fortuesday.append("@");
                        fortuesday.append(roomNumber);
                        fortuesday.append("\n");
                    } else if (weekdaydb.toLowerCase() == "wednesday") {

                        forwednesday.append(coruseName);
                        forwednesday.append("@");
                        forwednesday.append(hourdb + ":");
                        forwednesday.append(mindb + " ");
                        forwednesday.append(ampmdb);
                        forwednesday.append("@");
                        forwednesday.append(roomNumber);
                        forwednesday.append("\n");
                    } else if (weekdaydb.toLowerCase() == "thursday") {

                        forthursday.append(coruseName);
                        forthursday.append("@");
                        forthursday.append(hourdb + ":");
                        forthursday.append(mindb + " ");
                        forthursday.append(ampmdb);
                        forthursday.append("@");
                        forthursday.append(roomNumber);
                        forthursday.append("\n");
                    } else if (weekdaydb.toLowerCase() == "friday") {

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
                String SundayClasses = forSunday.toString();
                String MondayClasses= formonday.toString();
                String tuesdayClasses= fortuesday.toString();
                String wednesdayClasses=forwednesday.toString();
                String thrusdayClasses= forthursday.toString();
                String fridayClasses= forfriday.toString();
                String saturdayClasses= forsaturday.toString();


                //weekdaysetup
                alarmManager2 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Intent intent2 = new Intent(getApplication(), weekDayAdapter.class);

                //saturday adapter
                //intent2.putExtra("dayInformation", forsaturday.toString());
                intent2.putExtra("dayInformation", saturdayClasses);

                intent2.putExtra("day", "saturday");
                alarmIntentSaturday = PendingIntent.getBroadcast(context, 0, intent2, 0);

                Calendar calendar6 = Calendar.getInstance();
                calendar6.set(Calendar.HOUR, 7);
                calendar6.set(Calendar.MINUTE, 00);
                calendar6.set(Calendar.DAY_OF_WEEK, 6);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar6.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntentSaturday);



                //sunday adapter
                intent2.putExtra("dayInformation", SundayClasses);
                intent2.putExtra("day", "sunday");
                alarmIntentsunday = PendingIntent.getBroadcast(context, 0, intent2, 0);

                Calendar calendar7 = Calendar.getInstance();
                calendar7.set(Calendar.HOUR, 7);
                calendar7.set(Calendar.MINUTE, 00);
                calendar7.set(Calendar.DAY_OF_WEEK, 7);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar7.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntentsunday);



                //Monday adapter
                intent2.putExtra("dayInformation", MondayClasses);
                intent2.putExtra("day", "monday");
                alarmIntentmonday = PendingIntent.getBroadcast(context, 0, intent2, 0);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR, 7);
                calendar.set(Calendar.MINUTE, 00);
                calendar.set(Calendar.DAY_OF_WEEK, 1);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntentmonday);


                //tuesday adapter
                intent2.putExtra("dayInformation", tuesdayClasses);
                intent2.putExtra("day", "tuesday");
                alarmIntenttuesday = PendingIntent.getBroadcast(context, 0, intent2, 0);

                Calendar calendar2 = Calendar.getInstance();
                calendar2.set(Calendar.HOUR, 7);
                calendar2.set(Calendar.MINUTE, 00);
                calendar2.set(Calendar.DAY_OF_WEEK, 2);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntenttuesday);

                //wednesday adapter
                intent2.putExtra("dayInformation", wednesdayClasses);
                intent2.putExtra("day", "wednesday");
                alarmIntentwednesday = PendingIntent.getBroadcast(context, 0, intent2, 0);


                Calendar calendar3 = Calendar.getInstance();
                calendar3.set(Calendar.HOUR, 7);
                calendar3.set(Calendar.MINUTE, 0);
                calendar3.set(Calendar.DAY_OF_WEEK, 3);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntentwednesday);

                //thrusday adapter
                intent2.putExtra("dayInformation", thrusdayClasses);
                intent2.putExtra("day", "thursday");
                alarmIntentthrusday = PendingIntent.getBroadcast(context, 0, intent2, 0);

                Calendar calendar4 = Calendar.getInstance();
                calendar4.set(Calendar.HOUR, 7);
                calendar4.set(Calendar.MINUTE, 00);
                calendar4.set(Calendar.DAY_OF_WEEK, 4);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntentthrusday);


                //friday adapter
                intent2.putExtra("dayInformation", fridayClasses);
                intent2.putExtra("day", "friday");
                alarmIntentfriday = PendingIntent.getBroadcast(context, 0, intent2, 0);



                Calendar calendar5 = Calendar.getInstance();
                calendar5.set(Calendar.HOUR, 7);
                calendar5.set(Calendar.MINUTE, 00);
                calendar5.set(Calendar.DAY_OF_WEEK, 5);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntentfriday);

            }
            public void reminderSetter(String h, String m, String am_pm, String w, String teacherName, String courseName) {
//        Toast.makeText(notificationsetter.this, "remindersetter", Toast.LENGTH_LONG).show();
                //set the magic here..
                System.out.print("inside ReminderSetter\n");
                Intent intent = new Intent(getApplication(),notificationAdapter.class);
                alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                //putting Extras In intent
                intent.putExtra("teacherName", teacherName);
                intent.putExtra("courseName", courseName);
                intent.putExtra("hours", h);
                intent.putExtra("minutes", m);
                intent.putExtra("ampm", am_pm);

                alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

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
        });

    }
    public void fetch()
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(serverAddress,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        //Toast.makeText(MainActivityParse.this,jsonArray.toString(),Toast.LENGTH_SHORT).show();
                        context.deleteDatabase("classInformation.db");
                        String jsonResponseArray = "";
                      //
                      //  tv.setText("");
                        int flagForDatabaseDelete = 1;

                        // sqlClass.onUpgrade(sqlsend, (int) (System.currentTimeMillis()/1000), (int) (System.currentTimeMillis()/1000)+5);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject teacher = (JSONObject) jsonArray.get(i);
                                String name = teacher.getString("teacher_name");
                                String course_code = teacher.getString("course_code");
                                String course_name = teacher.getString("course_name");
                                String hours = teacher.getString("hours");
                                String minutes= teacher.getString("minutes");
                                String ampm = teacher.getString("ampm");
                                String roomNo = teacher.getString("room_no");
                                String week_day = teacher.getString("week_day");
                                jsonResponseArray = name + " " + course_code + " " + course_name + " " + hours + " "+minutes+" "+ ampm+" " + roomNo + " " + week_day + "\n\n";

                                sqlsend = sqlClass.getWritableDatabase();
                                sqlClass.receiveData(name, course_code, course_name, hours, minutes, ampm, roomNo, week_day, sqlsend, flagForDatabaseDelete);
                                sqlClass.close();



                                //tv.append(name+" "+course_code+"\n");

                            } catch (JSONException e) {
                                e.printStackTrace();
                              //  tv.setText(e.toString());
                              //  tv.setTextColor(Color.RED);
                            }
                         //   tv.append(jsonResponseArray);
                            flagForDatabaseDelete = 2;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                     //   tv.setText(volleyError.toString());
                    }
                }
        );
        queue.add(jsonArrayRequest);
        Toast.makeText(MainActivity.this,"Sync Complete",Toast.LENGTH_LONG).show();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
