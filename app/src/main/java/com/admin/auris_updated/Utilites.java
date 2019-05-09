package com.admin.auris_updated;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class Utilites {
    public static String getSharedPrferencedata(Context applicationContext, String value) {
        String id=null;
        try {

            SharedPreferences sharedPreferences = applicationContext.getSharedPreferences("Auris", MODE_PRIVATE);
            id = sharedPreferences.getString(value, String.valueOf(" "));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }
    public static void setSharedpreference(Context context,String key,String value){
        SharedPreferences sharedPreferences=context.getSharedPreferences("Auris",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
       editor.commit();

    }

    public  static  Boolean getAlarm(Context context){
        boolean flag = false;
        try {

            final String tag_alarm = "tag_alarm";
            Uri uri = Uri.parse("content://com.android.alarmclock/alarm");
            Cursor c = context.getContentResolver().query(uri, null, null, null, null);
            Log.i(tag_alarm, "no of records are " + c.getCount());
            Log.i(tag_alarm, "no of columns are " + c.getColumnCount());
            if (c != null) {
                String names[] = c.getColumnNames();
                for (String temp : names) {
                    System.out.println(temp);
                }
                if (c.moveToFirst()) {
                    do {
                        for (int j = 0; j < c.getColumnCount(); j++) {
                            flag = true;
                            Log.i(tag_alarm, c.getColumnName(j)
                                    + " which has value " + c.getString(j));
                        }
                    } while (c.moveToNext());
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }



    public static String readCalendarEvents(Context context) {
        String list = "false";
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatterr = new SimpleDateFormat("hh:mm:ss MM/dd/yy");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat startFormatter = new SimpleDateFormat("MM/dd/yy");
            Calendar calendar = Calendar.getInstance();
            String dtstart = startFormatter.format(calendar.getTime());
            Calendar startOfDay = Calendar.getInstance();
            Date datestart = null;
            try {
                datestart = formatterr.parse("00:00:00 " + dtstart);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            startOfDay.setTime(datestart);
            Long dtstatrt = startOfDay.getTimeInMillis();
            DateTime startDate = DateTime.now().withTime(0, 0, 0, 0);
            DateTime endDate = startDate.plusDays(1);

            Uri.Builder eventsUriBuilder = CalendarContract.Instances.CONTENT_URI
                    .buildUpon();
            ContentUris.appendId(eventsUriBuilder,startOfDay.getTimeInMillis());
            ContentUris.appendId(eventsUriBuilder, endDate.getMillis());

            Uri eventsUri = eventsUriBuilder.build();
            Cursor cursor = null;
            cursor = context.getContentResolver().query(
                    eventsUri,
                    new String[]{CalendarContract.Instances.CALENDAR_ID, CalendarContract.Instances.TITLE, CalendarContract.Instances.DESCRIPTION, CalendarContract.Instances.BEGIN, CalendarContract.Instances.END, CalendarContract.Instances.EVENT_LOCATION, CalendarContract.Instances.ORIGINAL_ID, CalendarContract.Instances.ORGANIZER, CalendarContract.Instances.OWNER_ACCOUNT},
                    CalendarContract.Instances.BEGIN + " >= " + dtstatrt + " and "+CalendarContract.Instances.BEGIN + " <= " + endDate.getMillis() +" and " + CalendarContract.Instances.VISIBLE + " = 0" +" or "+CalendarContract.Instances.VISIBLE + " = 1",
                    null,
                    CalendarContract.Instances.BEGIN + " ASC");
            while (cursor.moveToNext()) {
                String  eventLocation, displayName,stdate,enddate;
                displayName = cursor.getString(1);
                eventLocation = cursor.getString(5) + "";
                stdate = cursor.getString(3) + "";
                enddate = cursor.getString(4) + "";
                if (eventLocation.equals("MY EVENT LOCATION")) {

                    String mail = cursor.getString(8);
                    String time1=getDate(Long.parseLong(cursor.getString(3)),"dd/MM/yy hh:mm");
                    String time2=getDate(System.currentTimeMillis(),"dd/MM/yy hh:mm");
                    if(time1.equals(time2)){
                        list="true";
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public static ArrayList<String> getCalendarEvents(Context context) {
       ArrayList<String> list =new ArrayList<>();
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatterr = new SimpleDateFormat("hh:mm:ss MM/dd/yy");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat startFormatter = new SimpleDateFormat("MM/dd/yy");
            Calendar calendar = Calendar.getInstance();
            String dtstart = startFormatter.format(calendar.getTime());
            Calendar startOfDay = Calendar.getInstance();
            Date datestart = null;
            try {
                datestart = formatterr.parse("00:00:00 " + dtstart);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            startOfDay.setTime(datestart);
            Long dtstatrt = startOfDay.getTimeInMillis();
            DateTime startDate = DateTime.now().withTime(0, 0, 0, 0);
            DateTime endDate = startDate.plusDays(365);

            Uri.Builder eventsUriBuilder = CalendarContract.Instances.CONTENT_URI
                    .buildUpon();
            ContentUris.appendId(eventsUriBuilder,startOfDay.getTimeInMillis());
            ContentUris.appendId(eventsUriBuilder, endDate.getMillis());

            Uri eventsUri = eventsUriBuilder.build();
            Cursor cursor = null;

            cursor = context.getContentResolver().query(
                    eventsUri,
                    new String[]{CalendarContract.Instances.CALENDAR_ID, CalendarContract.Instances.TITLE, CalendarContract.Instances.DESCRIPTION, CalendarContract.Instances.BEGIN, CalendarContract.Instances.END, CalendarContract.Instances.EVENT_LOCATION, CalendarContract.Instances.ORIGINAL_ID, CalendarContract.Instances.ORGANIZER, CalendarContract.Instances.OWNER_ACCOUNT},
                    CalendarContract.Instances.BEGIN + " >= " + dtstatrt + " and "+CalendarContract.Instances.BEGIN + " <= " + endDate.getMillis() +" and " + CalendarContract.Instances.VISIBLE + " = 0" +" or "+CalendarContract.Instances.VISIBLE + " = 1",
                    null,
                    CalendarContract.Instances.BEGIN + " ASC");
            while (cursor.moveToNext()) {
                String  eventLocation, displayName,stdate,enddate;
                displayName = cursor.getString(1);
                eventLocation = cursor.getString(5) + "";
                stdate = cursor.getString(3) + "";
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTimeInMillis(Long.parseLong(stdate));
                enddate=  startFormatter.format(calendar.getTime());

               // enddate = cursor.getString(4) + "";
                if (eventLocation.equals("MY EVENT LOCATION")) {
                    list.add(enddate+"\n"+displayName+"\n");
                }


                }

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public static void normalToast(final Context context){
        Toast.makeText(context,"Alarm Detected",Toast.LENGTH_LONG).show();

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static  String listAlarms(Context context) {
        String TAG="aa";
        String nnn="aa";
        AlarmManager mAlarmManager =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        for (AlarmManager.AlarmClockInfo aci = mAlarmManager.getNextAlarmClock();
             aci != null;
             aci = mAlarmManager.getNextAlarmClock()) {
            Log.d(TAG, aci.getShowIntent().toString());
            Log.d(TAG, String.format("Trigger time: %d", aci.getTriggerTime()));
            nnn= String.valueOf(aci.getTriggerTime());
        }
        return String.format("Trigger time: %d", nnn);
    }


    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }


}
