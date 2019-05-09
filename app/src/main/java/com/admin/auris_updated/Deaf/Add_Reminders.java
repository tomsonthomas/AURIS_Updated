package com.admin.auris_updated.Deaf;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.admin.auris_updated.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Add_Reminders extends AppCompatActivity {
    Calendar date;
    private String timestamp;
    AppCompatEditText title,begintime,endtime,location;
    TextView save,tempetime,tempbtime;
    long temptime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_reminders);
        title= findViewById(R.id.tittle);
        begintime= findViewById(R.id.begin);
        endtime= findViewById(R.id.end);
        location= findViewById(R.id.location);
        save= findViewById(R.id.save);
        tempbtime= findViewById(R.id.tempbtime);
        tempetime= findViewById(R.id.tempetime);

//        //to get the reminder start date
        begintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker(begintime,tempbtime);
            }
        });
        //to get the reminder End date
        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker(endtime,tempetime);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (title.getText().toString().length() == 0) {
                    title.setError("Enter Tittle");
                } else if (begintime.getText().toString().length() == 0) {
                    begintime.setError("Enter Start Time");
                } else if (endtime.getText().toString().length() == 0) {
                    endtime.setError("Enter Stop Time");
                } else if (location.getText().toString().length() == 0) {
                    location.setError("Enter Location");
                } else {

                    long begin = Long.parseLong(tempbtime.getText().toString().trim());
                    long end = Long.parseLong(tempetime.getText().toString().trim());
/*********************************************** method used to add reminder/event to calender **************************/
                    addEvent(title.getText().toString(), location.getText().toString(), begin, end);

 /*********************************************** method used to add reminder/event to calender **************************/

                }
            }
        });
       checkReminder();

    }

    private void checkReminder() {

    }
    /***********************************************method used to add reminder/event to calender**************************/

    public void addEvent(String title, String location, long begin, long end) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    /*********************************************** method used to add reminder/event to calender **************************/


/***********************************************method used to get date and time of events/reminder**************************/

    public void showDateTimePicker(final EditText editText,final TextView textView) {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(Add_Reminders.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(Add_Reminders.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        String myFormat = "dd/MM/yy hh:mm a"; // your own format
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        String  formated_time = sdf.format(date.getTime());
                        timestamp=formated_time;
                        editText.setText(timestamp);
                        textView.setText(date.getTimeInMillis()+"");
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }
    /***********************************************method used to get date and time of events/reminder**************************/

    @Override
    public void onBackPressed() {
        finish();
    }
}
