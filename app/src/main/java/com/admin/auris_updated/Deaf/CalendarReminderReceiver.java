package com.admin.auris_updated.Deaf;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CalendarReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

                Toast.makeText(context,intent.getStringExtra("msg"),Toast.LENGTH_LONG).show();

        }
}