package com.admin.auris_updated.Services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.admin.auris_updated.R;
import com.admin.auris_updated.Receiver.AlarmReciever;
import com.admin.auris_updated.Utilites;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class AlertService  extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            int delay = 2000;   // delay for 2 sec.
            int period = 60*1000;  // repeat every 3 sec.
            Timer timer = new Timer();
            AlarmReciever receiver = new AlarmReciever();
            IntentFilter intentFilter = new IntentFilter("android.intent.action.ALARM");
            registerReceiver(receiver, intentFilter);
            Handler handler = new Handler();
            final Calendar date;
            final Calendar currentDate = Calendar.getInstance();
            date = Calendar.getInstance();

            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    if (Utilites.getSharedPrferencedata(getApplicationContext(), "type").equals("2")) {
                        String myFormat = "dd/MM/yy"; // your own format
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        String formated_time = sdf.format(date.getTime());
                        String timestamp = formated_time;
                        Calendar calendar = Calendar.getInstance();
                        String strDate = Utilites.getSharedPrferencedata(getApplicationContext(), "d_peroid");
                        String days = Utilites.getSharedPrferencedata(getApplicationContext(), "d_days");
                        Date date = null;
                        try {
                            date = sdf.parse(strDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        cal.add(Calendar.DAY_OF_YEAR, Integer.parseInt(days));
                        String for_time = sdf.format(cal.getTime());
                        Date today = calendar.getTime();
                        calendar.add(Calendar.DAY_OF_YEAR, Integer.parseInt(days));
                        if (timestamp.equals(for_time)) {
                            Intent intent = new Intent("android.intent.action.ALARM");
                            intent.setAction("android.intent.action.ALARM");
                            sendBroadcast(intent);
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                            builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
                            builder.setLargeIcon(BitmapFactory.decodeResource(getApplication().getResources(), R.mipmap.ic_launcher));
                            builder.setContentTitle("AURIS");
                            builder.setContentText("Device Alert");
                            builder.setSubText("Its the time to change your Device battery.");
                            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
//
//                    // Will display the notification in the notification bar
                            notificationManager.notify(2, builder.build());
                        }


                    }
                }
            }, delay, period);
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
