package com.example.shafiqur.classreminder.NotificationAndParsing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Shafiqur on 9/9/2015.
 */
public class sqliteDatabase extends SQLiteOpenHelper {

    public static final String DataBaseName = "classInformation.db";
    public static final int DataBaseVersion = 1;
    public static final String createQuery= "CREATE TABLE routine_information (teachersName TEXT,courseCode TEXT,CourseName Text, hours TEXT,minutes TEXT,ampm TEXT, room_no TEXT,week_day TEXT);";
    public static final  String deleteQuery= "truncate routine_information";
    public sqliteDatabase(Context context) {
        super(context, DataBaseName, null, DataBaseVersion);

        Log.e("Database", "Table Created or found");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

          // db.execSQL(deleteQuery);
        Log.e("Entered","In Oncreate");
        db.execSQL(createQuery);
        Log.e("Database", "query executed");
    }
    public void teacher(String teacher_name,String course_code, String course_name,String hours,String minutes,String ampm,String room_no, String weekDay, SQLiteDatabase sqLiteDatabase ,int flag)
    {

        ContentValues contentValues = new ContentValues();
        contentValues.put("teachersname",teacher_name);
        contentValues.put("courseCode",course_code);
        contentValues.put("CourseName",course_name);
        contentValues.put("hours",hours);
        contentValues.put("minutes",minutes);
        contentValues.put("ampm", ampm);
        contentValues.put("room_no", room_no);
        contentValues.put("week_day", weekDay);

        //sqLiteDatabase.replace("routine_information", null, contentValues);
        sqLiteDatabase.insert("routine_information", null, contentValues);
        Log.e("Inserted","Inserted to sqlite");


    }
    public void receiveData(String teacher_name,String course_code, String course_name,String hours,String minutes,String ampm,String room_no, String weekDay, SQLiteDatabase receiveSqliteDatabase,int flag)
    {

        teacher(teacher_name, course_code, course_name, hours,minutes,ampm, room_no, weekDay, receiveSqliteDatabase,flag);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(deleteQuery);
    }
    public Cursor getInformaton(sqliteDatabase sqliteDatabaseobject) {
        SQLiteDatabase sq = sqliteDatabaseobject.getReadableDatabase();
       //teachersName TEXT,courseCode TEXT,CourseName Text, time TEXT, room_no TEXT,week_day
        String[] columns = {"teachersName", "courseCode", "CourseName", "hours","minutes","ampm", "room_no", "week_day"};
        Cursor cr = sq.query("routine_information", columns, null, null, null, null, null);
        return cr;
    }

}
