package com.admin.auris_updated;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.admin.auris_updated.PartialDeaf.MainActivityPartialDeaf;
import com.admin.auris_updated.Services.AlertService;
import com.admin.auris_updated.Services.EmergencyService;
import com.admin.auris_updated.Services.EventService;


public class SplashScreen extends AppCompatActivity {
    Animation animFadeIn;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN);
        setContentView(R.layout.layout_splash);
        ImageView imageView = findViewById(R.id.imageview);
        checkRunTimePermission();

        }
    private void startAlarmService() {
        Intent intent = new Intent(this, AlertService.class);
        startService(intent);
    }
    private void startEventService() {
        Intent intent = new Intent(this, EventService.class);
        startService(intent);

    }
    private void startEmergencyService() {

        Intent intent = new Intent(this, EmergencyService.class);
        startService(intent);

    }
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(getApplicationContext(), "Please Grand All Permisions,OtherWise Some Fuctionalities May not Work Properly", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }
    }
    private void checkRunTimePermission() {
        String[] permissionArrays = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.VIBRATE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.SEND_SMS};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionArrays, 11111);
        } else {
            // if already permition granted
            // PUT YOUR ACTION (Like Open cemara etc..)
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean openActivityOnce = true;
        boolean openDialogOnce = true;
        if (requestCode == 11111) {
            boolean isPermitted=false;
            for (int i = 0; i < grantResults.length; i++) {
                String permission = permissions[i];

                isPermitted = grantResults[i] == PackageManager.PERMISSION_GRANTED;

                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    // user rejected the permission
                    boolean showRationale = shouldShowRequestPermissionRationale(permission);
                    if (!showRationale) {
                        //execute when 'never Ask Again' tick and permission dialog not show
                    } else {
                        if (openDialogOnce) {
                            alertView();
                        }
                    }
                }
            }

            if (isPermitted){

                new Handler().postDelayed(new Runnable() {
                    @Override

                    public void run() {
                        if(Utilites.getSharedPrferencedata(SplashScreen.this,"loginstatus").equals("1")) {
                            if(Utilites.getSharedPrferencedata(SplashScreen.this,"type").equals("1")){
                                Intent i = new Intent(SplashScreen.this, MainActivityDeaf.class);
                                startActivity(i);

                                // close this activity
                                finish();
                            }else if(Utilites.getSharedPrferencedata(SplashScreen.this,"type").equals("2")){
                                Intent i = new Intent(SplashScreen.this, MainActivityPartialDeaf.class);
                                startActivity(i);
                                finish();
                            }else {
                                Intent i = new Intent(SplashScreen.this, MainActivityPartialDeaf.class);
                                startActivity(i);
                                finish();
                            }

                        }else {
                            Intent i = new Intent(SplashScreen.this, Login.class);
                            startActivity(i);
                            finish();
                        }
                    }

                }, 3* 1000); // wait for 3 seconds
            }

        }
    }

    private void alertView() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(SplashScreen.this);

        dialog.setTitle("Permission Denied")
                .setInverseBackgroundForced(true)
                //.setIcon(R.drawable.ic_info_black_24dp)
                .setMessage("Without those permission the app is unable to save your profile. App needs to save profile image in your external storage and also need to get profile image from camera or external storage.Are you sure you want to deny this permission?")

                .setNegativeButton("I'M SURE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.dismiss();
                    }
                })
                .setPositiveButton("RE-TRY", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.dismiss();
                        checkRunTimePermission();

                    }
                }).show();
    }


}

