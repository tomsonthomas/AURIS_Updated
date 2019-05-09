package com.admin.auris_updated.PartialDeaf;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.auris_updated.R;
import com.admin.auris_updated.Services.EmergencyService;
import com.admin.auris_updated.Utilites;

public class AlertDialogueEm extends Activity {
    Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Dialog dialog1 = new Dialog(this);
        dialog1.setContentView(R.layout.dialogue_warning);
        TextView title = dialog1.findViewById(R.id.dialog_title);
        TextView text = dialog1.findViewById(R.id.dialog_text);
        TextView no =dialog1.findViewById(R.id.no);
        TextView yes = dialog1.findViewById(R.id.yes);
       // vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        //long pattern[] = {0, 100, 200, 300, 400};
        //vibrator.vibrate(pattern, 0);

        title.setText("Emergency Message Alert");
        text.setText("Tap to Send Emegency message.");
        no.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                dialog1.cancel();
                //vibrator.cancel();
                Utilites.setSharedpreference(AlertDialogueEm.this, "ems", "0");
                Intent intent = new Intent(AlertDialogueEm.this, EmergencyService.class);
                stopService(intent);
                finish();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                //vibrator.cancel();
                if(Utilites.getSharedPrferencedata(AlertDialogueEm.this,"ems").equals("1")){
                    String phone=Utilites.getSharedPrferencedata(AlertDialogueEm.this,"em_phone");
                    if(phone.length()>3) {
                        sendSMS(AlertDialogueEm.this, Utilites.getSharedPrferencedata(AlertDialogueEm.this, "em_phone"), "HELP.! I am in trouble.!");
                        sendSMS(AlertDialogueEm.this, Utilites.getSharedPrferencedata(AlertDialogueEm.this, "em_phone1"), "HELP.! I am in trouble.!");
                        sendSMS(AlertDialogueEm.this, Utilites.getSharedPrferencedata(AlertDialogueEm.this, "em_phone2"), "HELP.! I am in trouble.!");
                    }else{
                        Toast.makeText(AlertDialogueEm.this, "Please add Emergency Number", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }else {
                    Toast.makeText(AlertDialogueEm.this, "Please turn on Message notification", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        dialog1.show();

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void sendSMS(Context context, String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(AlertDialogueEm.this, "Message Sent", Toast.LENGTH_LONG).show();

            finish();
        } catch (Exception ex) {
            Toast.makeText(context,ex.toString(),
                    Toast.LENGTH_LONG).show();

            finish();
            ex.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}

