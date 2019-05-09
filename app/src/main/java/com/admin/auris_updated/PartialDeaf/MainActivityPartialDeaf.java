package com.admin.auris_updated.PartialDeaf;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.auris_updated.Deaf.Add_Reminders;
import com.admin.auris_updated.Deaf.Settings;
import com.admin.auris_updated.Deaf.SpeechToText;
import com.admin.auris_updated.Deaf.TexttoSpeech;
import com.admin.auris_updated.Deaf.VideoList;
import com.admin.auris_updated.Login;
import com.admin.auris_updated.R;
import com.admin.auris_updated.Services.AlertService;
import com.admin.auris_updated.Services.EmergencyService;
import com.admin.auris_updated.Services.EventService;
import com.admin.auris_updated.Utilites;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivityPartialDeaf extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<String> usertype= new ArrayList<String>();
    String devtype="";
    private static final int SELECT_VIDEO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_partial_deaf);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        startAlarmService();
        startEventService();
        startEmergencyService();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.text1);
        ImageView navimage = (ImageView) headerView.findViewById(R.id.imageView);
        navimage.setImageResource(R.drawable.auris);
        try {
            navUsername.setText(Utilites.getSharedPrferencedata(MainActivityPartialDeaf.this,"name"));
            TextView navUsername1 = (TextView) headerView.findViewById(R.id.textView2);
            navUsername1.setText(Utilites.getSharedPrferencedata(MainActivityPartialDeaf.this,"phone"));
        }
        catch (Exception e){
            e.printStackTrace();
        }


        String id;
        try {
            id = Utilites.getSharedPrferencedata(MainActivityPartialDeaf.this, "type");
        }catch (Exception e){
            e.printStackTrace();
        }
        try {


            if (Utilites.getSharedPrferencedata(MainActivityPartialDeaf.this, "type").equals("2") && !Utilites.getSharedPrferencedata(MainActivityPartialDeaf.this, "d_saved").equals("1")) {

                final EditText d_bsize, d_peroid, d_month, days;
                final Spinner d_type;
                Button save;
                final Dialog dialog = new Dialog(MainActivityPartialDeaf.this);
                dialog.setContentView(R.layout.dialogue_iputsdata);
                d_type = dialog.findViewById(R.id.type);
                d_bsize = dialog.findViewById(R.id.size);
                d_peroid = dialog.findViewById(R.id.peroid);
                days = dialog.findViewById(R.id.days);
                d_month = dialog.findViewById(R.id.month);
                usertype.add("CIC");
                usertype.add("ITC");
                usertype.add("ITE");
                usertype.add("BTE");

                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, usertype);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                d_type.setAdapter(arrayAdapter);
                d_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        devtype = usertype.get(position);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                save = dialog.findViewById(R.id.registeruser);
                d_peroid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDateTimePicker(d_peroid);

                    }
                });
                d_month.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDateTimePicker(d_month);

                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean flag = true;
                        if (devtype.length() == 0) {
                            Toast.makeText(getApplicationContext(), "Enter Valid Data", Toast.LENGTH_SHORT).show();
                            flag = false;
                        }
                        if (d_bsize.getText().toString().length() == 0) {
                            Toast.makeText(getApplicationContext(), "Enter Batery size ", Toast.LENGTH_SHORT).show();
                            flag = false;
                        }
                        if (d_peroid.getText().toString().length() == 0) {
                            Toast.makeText(getApplicationContext(), "Enter Valid Peroid", Toast.LENGTH_SHORT).show();
                            flag = false;
                        }
                        if (days.getText().toString().length() == 0) {
                            Toast.makeText(getApplicationContext(), "Enter Valid Days", Toast.LENGTH_SHORT).show();
                            flag = false;
                        }
                        if (d_month.getText().toString().length() == 0) {
                            Toast.makeText(getApplicationContext(), "Enter Valid Month", Toast.LENGTH_SHORT).show();
                            flag = false;
                        }
                        if (flag) {
                            Utilites.setSharedpreference(MainActivityPartialDeaf.this, "d_type", devtype);
                            Utilites.setSharedpreference(MainActivityPartialDeaf.this, "d_size", d_bsize.getText().toString());
                            Utilites.setSharedpreference(MainActivityPartialDeaf.this, "d_peroid", d_peroid.getText().toString());
                            Utilites.setSharedpreference(MainActivityPartialDeaf.this, "d_month", d_month.getText().toString());
                            Utilites.setSharedpreference(MainActivityPartialDeaf.this, "d_days", days.getText().toString());
                            Utilites.setSharedpreference(MainActivityPartialDeaf.this, "d_saved", "1");
                            Toast.makeText(getApplicationContext(), "Data  saved ", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    }
                });

                dialog.show();
            }
        }catch (Exception e){
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
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.caption) {
            Intent intent = new Intent();
            intent.setType("video/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Video"),1);
            // Handle the camera action
          /*  Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI);
            i.setType("video/*");
            startActivityForResult(i, SELECT_VIDEO);*/
        }
       /* else if(id==R.id.amplify){
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI);
            i.setType("video/*");
            startActivityForResult(i, SELECT_VIDEO);
        }*/
            else if (id == R.id.talk) {
            Intent intent = new Intent(getApplicationContext(), TexttoSpeech.class);
            startActivity(intent);
        } else if (id == R.id.hear) {
            Intent intent = new Intent(getApplicationContext(), SpeechToText.class);
            startActivity(intent);
        }else if (id == R.id.around) {
            Intent intent = new Intent(getApplicationContext(), Add_Reminders.class);
            startActivity(intent);
        } else if (id == R.id.Setting) {
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent);
        } else if (id == R.id.logout) {
            final Dialog dialog1 = new Dialog(MainActivityPartialDeaf.this);
            dialog1.setContentView(R.layout.dialogue_warning);
            TextView title = dialog1.findViewById(R.id.dialog_title);
            TextView text = dialog1.findViewById(R.id.dialog_text);
            TextView no =dialog1.findViewById(R.id.no);
            TextView yes = dialog1.findViewById(R.id.yes);
            title.setText("Log Out!!!");
            text.setText("Are you want to log out?");
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
                    Utilites.setSharedpreference(MainActivityPartialDeaf.this,"loginstatus","0");
                    Intent intent = new Intent(MainActivityPartialDeaf.this, Login.class);
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri selectedImageUri = data.getData();
                // MEDIA GALLERY
                String  selectedImagePath = getPath(MainActivityPartialDeaf.this,selectedImageUri);
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

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    private void showDateTimePicker(final EditText d_peroid) {
        final Calendar date;
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(MainActivityPartialDeaf.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                {
                    String myFormat = "dd/MM/yy"; // your own format
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    String  formated_time = sdf.format(date.getTime());
                    String timestamp = formated_time;
                    d_peroid.setText(timestamp);
                }
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(Context context, Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static MultipartBody.Part getMultiPartBody(String key, String mMediaUrl) {
        if (mMediaUrl != null) {
            File file = new File(mMediaUrl);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            return MultipartBody.Part.createFormData(key, file.getName(), requestFile);
        } else {
            return MultipartBody.Part.createFormData(key, "");
        }
    }
}
