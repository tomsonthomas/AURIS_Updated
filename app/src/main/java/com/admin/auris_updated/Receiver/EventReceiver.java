package com.admin.auris_updated.Receiver;
/****************************************************
 *
 *
 * --------------------------------------Broad cast receiver used for event and sound notification-----------
 *
 *
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Vibrator;

import java.util.Timer;
import java.util.TimerTask;

public class EventReceiver extends BroadcastReceiver {
Camera cam;
    Vibrator vibrator;
int count=0;
    @Override
    public void onReceive(final Context context, Intent intent) {
        int delay = 1000;   // delay for 1 sec.
        int period = 1000;  // repeat every 1 sec.
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
        public void run() {
            if(count==10){
                flashLightOff(context);
                stopVibrate(context);
                count=0;
                timer.cancel();
                return;
            }else {
                flashLightOn(context);
                startVibrate(context);
                flashLightOff(context);
                stopVibrate(context);
                count=count+1;
            }

        }
        }, delay, period);
    //Toast.makeText(context,intent.getStringExtra("msg"),Toast.LENGTH_LONG).show();

    }

    public void flashLightOn(Context context) {
        try {
            if (context.getPackageManager().hasSystemFeature( PackageManager.FEATURE_CAMERA_FLASH))
            {
                cam = Camera.open();
                Camera.Parameters p = cam.getParameters();
                p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                cam.setParameters(p);
                cam.startPreview();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
    public void flashLightOff(Context context) {
        try {
            if (context.getPackageManager().hasSystemFeature( PackageManager.FEATURE_CAMERA_FLASH))
            {
                cam.stopPreview();
                cam.release();
                cam = null;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
           // Toast.makeText(context, "Exception flashLightOff", Toast.LENGTH_SHORT).show();
        }
    }
    public void startVibrate(Context context) {
        long pattern[] = { 0, 100, 200, 300, 400 };
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, 0);
    }

    public void stopVibrate(Context context) {
        vibrator.cancel();
    }

}
