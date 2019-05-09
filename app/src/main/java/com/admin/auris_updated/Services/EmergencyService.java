package com.admin.auris_updated.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.admin.auris_updated.PartialDeaf.AlertDialogueEm;
import com.admin.auris_updated.R;
import com.admin.auris_updated.Receiver.Emergencyreceiver;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class EmergencyService  extends Service {
    MediaRecorder mMediaRecorder;
    private Vibrator vibrator;

    @Override
    public void onCreate() {
        super.onCreate();

         mMediaRecorder = new MediaRecorder();
        start(mMediaRecorder);
       /* try {
            int delay = 2000;   // delay for 5 sec.
            int period = 2000;  // repeat every 3 sec.
            Timer timer = new Timer();
            Emergencyreceiver receiver = new Emergencyreceiver();
            IntentFilter intentFilter = new IntentFilter("android.intent.action.EVENT_EMEGENCY");
            registerReceiver(receiver, intentFilter);
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    double amp = 0.00;
                    SoundMeter soundMeter = new SoundMeter();
                    amp= soundMeter.start();
                  //  amp = soundMeter.getAmplitude();
               //     System.out.print(amp);
                    //   soundMeter.stop();
                    if (amp > 100.00) {
                        Intent intent = new Intent("android.intent.action.EVENT_EMEGENCY");
                        intent.setAction("android.intent.action.EVENT_EMEGENCY");
                        intent.putExtra("msg", " ");
                        sendBroadcast(intent);
                        //soundMeter.stop();
                    } else {
                        //    soundMeter.stop();
                    }

                }
            }, delay, period);

        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }
    public void start(MediaRecorder recorder) {
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile("/dev/null");

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new RecorderTask(recorder), 0,10*1000);// will update Max Amplitude Value every 10 second

        try {
            recorder.prepare();
            recorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private class RecorderTask extends TimerTask { ;
        private MediaRecorder recorder;

        public RecorderTask(MediaRecorder recorder) {
            this.recorder = recorder;
        }


        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            Emergencyreceiver receiver = new Emergencyreceiver();
            IntentFilter intentFilter = new IntentFilter("android.intent.action.EVENT_EMEGENCY");
            registerReceiver(receiver, intentFilter);
            double amp=20 * Math.log10(recorder.getMaxAmplitude() / 2700.0);
            if (amp > 5.00) {
               /* if(Utilites.getSharedPrferencedata(getApplicationContext(),"ems").equals("1")) {
                    Intent intent = new Intent("android.intent.action.EVENT_EMEGENCY");
                    intent.setAction("android.intent.action.EVENT_EMEGENCY");
                    sendBroadcast(intent);
                }*/
                //vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                //vibrator.vibrate(5000);


                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                    builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
                    Intent intent1 = new Intent(getApplicationContext(), AlertDialogueEm.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent1, 0);
                    builder.setContentIntent(pendingIntent);
                    builder.setLargeIcon(BitmapFactory.decodeResource(getApplication().getResources(), R.mipmap.ic_launcher));
                    builder.setContentTitle("AURIS");
                    builder.setContentText("Emergency Message Alert");
                    builder.setSubText("Tap to Send Emegency message.");

                  NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
//
//                    // Will display the notification in the notification bar
                  notificationManager.notify(1, builder.build());





                //soundMeter.stop();
            } else {
                //    soundMeter.stop();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
    public void startVibrate(Context context) {
        long pattern[] = {0, 100, 200, 300, 400};
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, 0);
    }

/*    public class SoundMeter {

        private MediaRecorder mRecorder = null;

        public double start() {
            if (mRecorder == null) {
                try {

                    mRecorder = new MediaRecorder();
                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mRecorder.setOutputFile("/dev/null");
                    mRecorder.getMaxAmplitude();
                    mRecorder.prepare();
                    mRecorder.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return mRecorder.getMaxAmplitude();
        }*/

      /*  public void stop() {
            if (mRecorder != null) {
                try {
                    mRecorder.stop();
                    mRecorder.release();
                    mRecorder = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public double getAmplitude() {
            double amp = 0.0;

            try {
                if (mRecorder != null)
                    //  return 20 * Math.log10(mRecorder.getMaxAmplitude() / 2700.0);
                    amp = mRecorder.getMaxAmplitude();
                else
                    amp = 0;

            } catch (Exception e) {
                e.printStackTrace();
                amp = 0;
            }
            return amp;

        }
    }*/
}
