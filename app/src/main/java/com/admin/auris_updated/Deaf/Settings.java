package com.admin.auris_updated.Deaf;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.auris_updated.R;
import com.admin.auris_updated.SplashScreen;
import com.admin.auris_updated.Utilites;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Settings extends AppCompatActivity {
    CardView reminder,dev,emerno,nofity,usertype;
   AppCompatImageView  imageView;
    TextView textView;
    String devtype="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settings);
        reminder= findViewById(R.id.remiderlist);
        dev= findViewById(R.id.device);
        nofity= findViewById(R.id.notify);
        emerno= findViewById(R.id.notifyno);
        usertype= findViewById(R.id.usertype);
        textView= findViewById(R.id.text);
        imageView= findViewById(R.id.img);
        if (Utilites.getSharedPrferencedata(Settings.this, "type").equals("1")||Utilites.getSharedPrferencedata(Settings.this, "type").equals("3")){
            dev.setVisibility(View.GONE);
        }
        if(Utilites.getSharedPrferencedata(Settings.this,"ems").equals("1")){
            textView.setText("Turn Off message");
            imageView.setImageResource(R.drawable.turnoff);
        }
        dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText d_bsize, d_peroid, d_month, days;
                final Spinner d_type;
                Button save;
                final ArrayList<String> usertype1= new ArrayList<String>();
                final Dialog dialog = new Dialog(Settings.this);
                dialog.setContentView(R.layout.dialogue_iputsdata);
                d_type = dialog.findViewById(R.id.type);
                d_bsize = dialog.findViewById(R.id.size);
                d_peroid = dialog.findViewById(R.id.peroid);
                days = dialog.findViewById(R.id.days);
                d_month = dialog.findViewById(R.id.month);
                usertype1.add(Utilites.getSharedPrferencedata(Settings.this,"d_type"));
                usertype1.add("CIC");
                usertype1.add("ITC");
                usertype1.add("ITE");
                usertype1.add("BTE");

                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, usertype1);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                d_type.setAdapter(arrayAdapter);
                d_bsize.setText(Utilites.getSharedPrferencedata(Settings.this,"d_size"));
                d_peroid.setText(Utilites.getSharedPrferencedata(Settings.this,"d_peroid"));
                days.setText(Utilites.getSharedPrferencedata(Settings.this,"d_days"));
                d_month.setText(Utilites.getSharedPrferencedata(Settings.this,"d_month"));

                d_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        devtype = usertype1.get(position);

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
                            Utilites.setSharedpreference(Settings.this, "d_type", devtype);
                            Utilites.setSharedpreference(Settings.this, "d_size", d_bsize.getText().toString());
                            Utilites.setSharedpreference(Settings.this, "d_peroid", d_peroid.getText().toString());
                            Utilites.setSharedpreference(Settings.this, "d_month", d_month.getText().toString());
                            Utilites.setSharedpreference(Settings.this, "d_days", days.getText().toString());
                            Utilites.setSharedpreference(Settings.this, "d_saved", "1");
                            Toast.makeText(getApplicationContext(), "Data  saved ", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    }
                });

                dialog.show();

            }
        });
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this,ViewAllReminders.class);
                startActivity(intent);
            }
        });
        usertype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<String> usertype1= new ArrayList<String>();
                usertype1.add("---Select---");
                usertype1.add("Deaf");
                usertype1.add("Partially Deaf");
                usertype1.add("Cochlear Implants");
                final Dialog dialog1 = new Dialog(Settings.this);
                dialog1.setContentView(R.layout.dialogue_changeusertype);
                Spinner spinner = dialog1.findViewById(R.id.spinner);
                Button button = dialog1.findViewById(R.id.save);
                ArrayAdapter arrayAdapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,usertype1);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String user = usertype1.get(position);
                        if(user.equals("Deaf")){
                            Utilites.setSharedpreference(Settings.this, "type", "1");

                            dev.setVisibility(View.GONE);
                        }
                        else if(user.equals("Partially Deaf")){
                            Utilites.setSharedpreference(Settings.this, "type", "2");
                            dev.setVisibility(View.VISIBLE);
                        }else {
                            Utilites.setSharedpreference(Settings.this, "type", "3");
                            dev.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
                        startActivity(intent);
                        finish();
                        dialog1.cancel();
                    }
                });
                dialog1.show();
            }
        });
        emerno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog1 = new Dialog(Settings.this);
                dialog1.setContentView(R.layout.dialogue_emergency);
                final EditText editText = dialog1.findViewById(R.id.number);
                final EditText editText1 = dialog1.findViewById(R.id.number1);
                final EditText editText2 = dialog1.findViewById(R.id.number2);
                editText.setText(Utilites.getSharedPrferencedata(Settings.this,"em_phone"));
                editText1.setText(Utilites.getSharedPrferencedata(Settings.this,"em_phone1"));
                editText2.setText(Utilites.getSharedPrferencedata(Settings.this,"em_phone2"));
                Button save = dialog1.findViewById(R.id.reguser);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(editText.getText().toString().length()>0) {
                            if (Patterns.PHONE.matcher(editText.getText().toString()).matches()) {
                                Utilites.setSharedpreference(Settings.this, "em_phone", editText.getText().toString());
                                Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();

                            } else {
                                editText.setError("Enter Valid Phone Number");
                                return;
                            }
                        }
                        else {
                            editText.setError("Enter Valid Phone Number");
                            return;
                        }
                        if(editText1.getText().toString().length()>0) {
                            if (Patterns.PHONE.matcher(editText1.getText().toString()).matches()) {
                                Utilites.setSharedpreference(Settings.this, "em_phone1", editText1.getText().toString());
                                Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();

                            } else {
                                editText1.setError("Enter Valid Phone Number");
                                return;
                            }
                        }
                        if(editText2.getText().toString().length()>0) {
                            if (Patterns.PHONE.matcher(editText2.getText().toString()).matches()) {
                                Utilites.setSharedpreference(Settings.this, "em_phone2", editText2.getText().toString());
                                Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();
                                dialog1.cancel();
                            } else {
                                editText2.setError("Enter Valid Phone Number");
                                return;
                            }

                        }else {
                         dialog1.cancel();
                        }
                    }
                });
                dialog1.show();
            }
        });
        nofity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog1 = new Dialog(Settings.this);
                dialog1.setContentView(R.layout.dialogue_warning);
                TextView title = dialog1.findViewById(R.id.dialog_title);
                TextView text = dialog1.findViewById(R.id.dialog_text);
                TextView no =dialog1.findViewById(R.id.no);
                TextView yes = dialog1.findViewById(R.id.yes);
                title.setText("Alert!!!");
                if(Utilites.getSharedPrferencedata(Settings.this,"ems").equals("1")){
                    text.setText("do you want to off the Emergency message service?");

                }else {

                    text.setText("do you want to on the Emergency message service?");

                }
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
                        if(Utilites.getSharedPrferencedata(Settings.this,"ems").equals("1")){
                            Utilites.setSharedpreference(Settings.this, "ems", "0");
                            Toast.makeText(getApplicationContext(),"Data Saved",Toast.LENGTH_SHORT).show();
                            textView.setText("Turn Off message");
                            imageView.setImageResource(R.drawable.turnoff);
                            dialog1.cancel();
                        }else {
                            Utilites.setSharedpreference(Settings.this, "ems", "1");
                            Toast.makeText(getApplicationContext(),"Data Saved",Toast.LENGTH_SHORT).show();
                            textView.setText("Turn on message");
                            imageView.setImageResource(R.drawable.turnon);
                            dialog1.cancel();
                        }

                    }
                });
                dialog1.show();
            }
        });

    }



    private void showDateTimePicker(final EditText d_peroid) {
        final Calendar date;
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(Settings.this, new DatePickerDialog.OnDateSetListener() {
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


}
