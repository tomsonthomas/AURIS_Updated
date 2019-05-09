package com.admin.auris_updated.Services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;

import com.admin.auris_updated.Receiver.EventReceiver;
import com.admin.auris_updated.Utilites;


import java.util.Timer;
import java.util.TimerTask;

public class EventService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            int delay = 2000;   // delay for 2 sec.
            int period = 30*1000;  // repeat every 30 sec.
            Timer timer = new Timer();
            EventReceiver receiver = new EventReceiver();
            IntentFilter intentFilter = new IntentFilter("android.intent.action.EVENT_REMINDER");
            registerReceiver(receiver, intentFilter);
            Handler handler = new Handler();
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {

                    String flag = Utilites.readCalendarEvents(getApplicationContext());
                    if (flag.equals("true")) {
                        Intent intent = new Intent("android.intent.action.EVENT_REMINDER");
                        intent.setAction("android.intent.action.EVENT_REMINDER");
                        intent.putExtra("msg", flag);
                        sendBroadcast(intent);

                    }

                }
            }, delay, period);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // do your jobs here
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;

    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
