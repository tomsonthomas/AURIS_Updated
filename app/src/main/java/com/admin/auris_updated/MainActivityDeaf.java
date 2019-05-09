package com.admin.auris_updated;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.admin.auris_updated.Deaf.Add_Reminders;
import com.admin.auris_updated.Deaf.Settings;
import com.admin.auris_updated.Deaf.SpeechToText;
import com.admin.auris_updated.Deaf.TexttoSpeech;
import com.admin.auris_updated.Deaf.VideoList;
import com.admin.auris_updated.Services.AlertService;
import com.admin.auris_updated.Services.EmergencyService;
import com.admin.auris_updated.Services.EventService;

import java.util.ArrayList;

public class MainActivityDeaf extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int SELECT_VIDEO = 1;
    TextView textView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.nodata);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        startAlarmService();
        startEventService();
        startEmergencyService();
        ArrayList<String> list= Utilites.getCalendarEvents(MainActivityDeaf.this);
        if(list.size()>0){
            textView.setVisibility(View.GONE);
        }else {
            textView.setVisibility(View.VISIBLE);
        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.name1);
        ImageView navimage = (ImageView) headerView.findViewById(R.id.imageView);
        navimage.setImageResource(R.drawable.auris);
        try {
            navUsername.setText(Utilites.getSharedPrferencedata(MainActivityDeaf.this,"name"));
            TextView navUsername1 = (TextView) headerView.findViewById(R.id.textView);
            navUsername1.setText(Utilites.getSharedPrferencedata(MainActivityDeaf.this,"phone"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.caption) {
            /*Intent intent = new Intent();
            intent.setType("video/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Video"),1);*/
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI);
            i.setType("video/*");
            startActivityForResult(i, SELECT_VIDEO);
        } else if (id == R.id.talk) {
            Intent intent = new Intent(getApplicationContext(), TexttoSpeech.class);
            startActivity(intent);
        } else if (id == R.id.hear) {
            Intent intent = new Intent(getApplicationContext(), SpeechToText.class);
            startActivity(intent);
        } else if (id == R.id.around) {
            Intent intent = new Intent(getApplicationContext(), Add_Reminders.class);
            startActivity(intent);
        }  else if (id == R.id.Setting) {
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent);
        } else if (id == R.id.logout) {
            final Dialog dialog1 = new Dialog(MainActivityDeaf.this);
            dialog1.setContentView(R.layout.dialogue_warning);
            TextView title = dialog1.findViewById(R.id.dialog_title);
            TextView text = dialog1.findViewById(R.id.dialog_text);
            TextView no =dialog1.findViewById(R.id.no);
            TextView yes = dialog1.findViewById(R.id.yes);
            title.setText("Log Out!!!");
            text.setText("Do you want to log out?");
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog1.cancel();
                }
            });
            yes.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {
                  Utilites.setSharedpreference(MainActivityDeaf.this,"loginstatus","0");
                    Intent intent = new Intent(MainActivityDeaf.this,Login.class);
                    startActivity(intent);
                    finish();
                }
            });
            dialog1.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri selectedImageUri = data.getData();
                // MEDIA GALLERY
                String  selectedImagePath = getPath(selectedImageUri);
                System.out.print(selectedImagePath);
                // File source = new File(selectedImagePath);
                // String target = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/target.mp3";
                //  Uri uri = Uri.parse(selectedImagePath); //Declare your url here.
                Intent intent = new Intent(getApplicationContext(), VideoList.class);
                intent.putExtra("path",selectedImagePath);
                startActivity(intent);
            }
        }
    }

    //UPDATED!
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    private void startAlarmService() {
        Intent intent = new Intent(this, AlertService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        }else {
            startService(intent);
        }
    }
    private void startEventService() {
        Intent intent = new Intent(this, EventService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        }else {
            startService(intent);
        }

    }
    private void startEmergencyService() {

        Intent intent = new Intent(this, EmergencyService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        }else {
            startService(intent);
        }

    }
}
