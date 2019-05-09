package com.admin.auris_updated.Deaf;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.auris_updated.R;
import com.admin.auris_updated.Utilites;

import java.util.ArrayList;

public class ViewAllReminders extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_reminders_items);

        ListView listView = findViewById(R.id.list);

        ArrayList<String> list= Utilites.getCalendarEvents(ViewAllReminders.this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(ViewAllReminders.this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
    }






}


