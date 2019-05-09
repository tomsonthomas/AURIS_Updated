package com.admin.auris_updated.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.admin.auris_updated.PartialDeaf.AlertDialogueEm;
import com.admin.auris_updated.Utilites;

public class Emergencyreceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        if(Utilites.getSharedPrferencedata(context,"ems").equals("1")) {
            Intent i = new Intent(context, AlertDialogueEm.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

        }
    }


}
