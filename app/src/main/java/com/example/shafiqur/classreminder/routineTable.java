package com.example.shafiqur.classreminder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.shafiqur.classreminder.NotificationAndParsing.sqliteDatabase;

/**
 * Created by Shafiqur on 1/16/2016.
 */
public class routineTable extends Fragment {
    @Nullable

    public sqliteDatabase sqlClass;
    public SQLiteDatabase sqlsend;
    public TableLayout tableLayout;
    public MainActivity mainActivityObject;
    public Context context;
    public String[] saturdaysArray, sundaysArray, mondaysArray, tuesdaysArray, wednesdaysArray, thursdaysArray, fridayssArray;

    public routineTable() {

    }

    static void dynamicRowData(String day, String nine, String ten, String twelve, String onehalf, String three) {
        dynamicRowData(day, nine, ten, twelve, onehalf, three);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivityObject = new MainActivity();

        saturdaysArray = new String[5];
        sundaysArray = new String[5];
        mondaysArray = new String[5];
        tuesdaysArray = new String[5];
        wednesdaysArray = new String[5];
        thursdaysArray = new String[5];
        fridayssArray = new String[5];


        sqlClass = new sqliteDatabase(getActivity());
        context = getActivity();

        View rootView = inflater.inflate(R.layout.routinetable, container, false);
        tableLayout = (TableLayout) rootView.findViewById(R.id.tableLayout);
        TableRow row = new TableRow(getActivity());
        View table_row_view = rootView.inflate(getActivity(), R.layout.routinetable, row);
        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tableLayout.addView(row, new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


        //working with internal database
        //copied from http://stackoverflow.com/questions/10317989/android-fragment-table-display


        TableRow titlerow;
        TextView tl1, tl2, tl3, tl4, t5, t6;
        titlerow = new TableRow(getActivity());
        titlerow.setPadding(10, 10, 0, 10);


        tl1 = new TextView(getActivity());
        tl1.setTextColor(getActivity().getResources().getColor(R.color.text_color));
        tl1.setTextSize(12);
        tl2 = new TextView(getActivity());
        tl2.setTextColor(getActivity().getResources().getColor(R.color.text_color));
        tl2.setTextSize(12);

        tl3 = new TextView(getActivity());
        tl3.setTextColor(getActivity().getResources().getColor(R.color.text_color));
        tl3.setTextSize(12);

        tl4 = new TextView(getActivity());
        tl4.setTextColor(getActivity().getResources().getColor(R.color.text_color));
        tl4.setTextSize(12);

        t5 = new TextView(getActivity());
        t5.setTextColor(getActivity().getResources().getColor(R.color.text_color));
        t5.setTextSize(12);

        t6 = new TextView(getActivity());
        t6.setTextColor(getActivity().getResources().getColor(R.color.text_color));
        t6.setTextSize(12);

        tl1.setText("Day");
        tl2.setText("09:00");
        tl3.setText("10:30");
        tl4.setText("12:00");
        t5.setText("01:30");
        t6.setText("03:00");

        titlerow.addView(tl1);
        titlerow.addView(tl2);
        titlerow.addView(tl3);
        titlerow.addView(tl4);
        titlerow.addView(t5);
        titlerow.addView(t6);

        tableLayout.addView(titlerow);

        //database crawling here

        Cursor cursor = sqlClass.getInformaton(sqlClass);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String hourdb = cursor.getString(cursor.getColumnIndex("hours"));
            String mindb = cursor.getString(cursor.getColumnIndex("minutes"));
            String ampmdb = cursor.getString(cursor.getColumnIndex("ampm"));
            String weekdaydb = cursor.getString(cursor.getColumnIndex("week_day"));
            String teacherName = cursor.getString(cursor.getColumnIndex("teachersName"));
            String coruseName = cursor.getString(cursor.getColumnIndex("CourseName"));
            String roomNumber = cursor.getString(cursor.getColumnIndex("room_no"));


            if (weekdaydb.toLowerCase().equals("saturday")) {
                if (hourdb.equals("9")) {
                    saturdaysArray[0] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("10")) {
                    saturdaysArray[1] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("12")) {
                    saturdaysArray[2] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("1")) {
                    saturdaysArray[3] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("3")) {
                    saturdaysArray[4] = coruseName + "\n" + roomNumber;
                }

            } else if (weekdaydb.toLowerCase().equals("sunday")) {
                if (hourdb.equals("9")) {
                    sundaysArray[0] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("10")) {
                    sundaysArray[1] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("12")) {
                    sundaysArray[2] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("1")) {
                    sundaysArray[3] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("3")) {
                    sundaysArray[4] = coruseName + "\n" + roomNumber;
                }

            } else if (weekdaydb.toLowerCase().equals("monday")) {
                if (hourdb.equals("9")) {
                    mondaysArray[0] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("10")) {
                    mondaysArray[1] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("12")) {
                    mondaysArray[2] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("1")) {
                    mondaysArray[3] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("3")) {
                    mondaysArray[4] = coruseName + "\n" + roomNumber;
                }

            } else if (weekdaydb.toLowerCase().equals("tuesday")) {
                if (hourdb.equals("9")) {
                    tuesdaysArray[0] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("10")) {
                    tuesdaysArray[1] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("12")) {
                    tuesdaysArray[2] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("1")) {
                    tuesdaysArray[3] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("3")) {
                    tuesdaysArray[4] = coruseName + "\n" + roomNumber;
                }

            } else if (weekdaydb.toLowerCase().equals("wednesday")) {
                if (hourdb.equals("9")) {
                    wednesdaysArray[0] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("10")) {
                    wednesdaysArray[1] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("12")) {
                    wednesdaysArray[2] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("1")) {
                    wednesdaysArray[3] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("3")) {
                    wednesdaysArray[4] = coruseName + "\n" + roomNumber;
                }

            } else if (weekdaydb.toLowerCase().equals("thursday")) {
                if (hourdb.equals("9")) {
                    thursdaysArray[0] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("10")) {
                    thursdaysArray[1] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("12")) {
                    thursdaysArray[2] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("1")) {
                    thursdaysArray[3] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("3")) {
                    thursdaysArray[4] = coruseName + "\n" + roomNumber;
                }

            } else if (weekdaydb.toLowerCase().equals("friday")) {
                if (hourdb.equals("9")) {
                    fridayssArray[0] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("10")) {
                    fridayssArray[1] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("12")) {
                    fridayssArray[2] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("1")) {
                    fridayssArray[3] = coruseName + "\n" + roomNumber;
                } else if (hourdb.equals("3")) {
                    fridayssArray[4] = coruseName + "\n" + roomNumber;
                }

            }
            cursor.moveToNext();
        }//while ends here

        dynamicRow("Saturday", saturdaysArray[0], saturdaysArray[1], saturdaysArray[2], saturdaysArray[3], saturdaysArray[4]);
        dynamicRow("Sunday", sundaysArray[0], sundaysArray[1], sundaysArray[2], sundaysArray[3], sundaysArray[4]);
        dynamicRow("Monday", mondaysArray[0], mondaysArray[1], mondaysArray[2], mondaysArray[3], mondaysArray[4]);
        dynamicRow("Tuesday", tuesdaysArray[0], tuesdaysArray[1], tuesdaysArray[2], tuesdaysArray[3], tuesdaysArray[4]);
        dynamicRow("Wednesday", wednesdaysArray[0], wednesdaysArray[1], wednesdaysArray[2], wednesdaysArray[3], wednesdaysArray[4]);
        dynamicRow("Thursday", thursdaysArray[0], thursdaysArray[1], thursdaysArray[2], thursdaysArray[3], thursdaysArray[4]);
        dynamicRow("Friday", fridayssArray[0], fridayssArray[1], fridayssArray[2], fridayssArray[3], fridayssArray[4]);

        return rootView;
    }

    public void dynamicRow(String day, String nine,
                           String ten, String twelve, String onehalf, String three) {

        TableRow row;
        TextView t1, t2, t3, t4, t5, t6;
        row = new TableRow(getActivity());
        row.setPadding(10, 0, 0, 0);

        t1 = new TextView(getActivity());
        t1.setTextColor(getActivity().getResources().getColor(R.color.text_color));
        t1.setTextSize(12);
        t2 = new TextView(getActivity());
        t2.setTextColor(getActivity().getResources().getColor(R.color.text_color));
        t2.setTextSize(12);
        t3 = new TextView(getActivity());
        t3.setTextColor(getActivity().getResources().getColor(R.color.text_color));
        t3.setTextSize(12);
        t4 = new TextView(getActivity());
        t4.setTextColor(getActivity().getResources().getColor(R.color.text_color));
        t4.setTextSize(12);

        t5 = new TextView(getActivity());
        t5.setTextColor(getActivity().getResources().getColor(R.color.text_color));
        t5.setTextSize(12);

        t6 = new TextView(getActivity());
        t6.setTextColor(getActivity().getResources().getColor(R.color.text_color));
        t6.setTextSize(12);

        t1.setText(day);
        t2.setText(nine);
        t3.setText(ten);
        t4.setText(twelve);
        t5.setText(onehalf);
        t6.setText(three);

        row.addView(t1);
        row.addView(t2);
        row.addView(t3);
        row.addView(t4);
        row.addView(t5);
        row.addView(t6);

        tableLayout.addView(row);
    }

}
