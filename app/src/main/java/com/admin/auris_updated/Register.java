package com.admin.auris_updated;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.admin.auris_updated.WebService.RetrofitInterface;
import com.admin.auris_updated.WebService.getRetrofitInstance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register  extends AppCompatActivity {
    TextInputEditText  edt_name,edt_username,edt_password,edt_phone,edt_confirmpassword,age;
    Button regiser,dev,emergency;
    Spinner spinner;
    String devtype="";
    ArrayList<String> usertype= new ArrayList<String>();
    ArrayList<String> usertype1= new ArrayList<String>();
    String select="---Select---";
    RadioButton radioButton1;
    String gender="Male";
    RadioGroup radioGroup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        edt_name= findViewById(R.id.name);
        edt_phone= findViewById(R.id.mobile);
        edt_username= findViewById(R.id.username);
        edt_password= findViewById(R.id.password);
        age= findViewById(R.id.age);
        spinner= findViewById(R.id.spinner);
        radioGroup =findViewById(R.id.radiogroup);
        edt_confirmpassword= findViewById(R.id.confirm_password);
        regiser= findViewById(R.id.registeruser);
        dev= findViewById(R.id.devdetails);
        emergency= findViewById(R.id.btnemergency);
        usertype.add("---Select---");
        usertype.add("Deaf");
        usertype.add("Partially Deaf");
        usertype.add("Cochlear Implants");
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected

                if(checkedId == R.id.male) {
                    gender ="Male";
                } else if(checkedId == R.id.fmale) {

                    gender ="Female";

                }
            }

        });
      ArrayAdapter arrayAdapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,usertype);
      arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
spinner.setAdapter(arrayAdapter);
spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String user = usertype.get(position);
        if(user.equals("Deaf")){
            select ="1";
            dev.setVisibility(View.GONE);
        }
        else if(user.equals("Partially Deaf")){
            select="2";
            dev.setVisibility(View.VISIBLE);
        }else {
            select="3";
            dev.setVisibility(View.GONE);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});
        edt_phone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(edt_name.length()<=3){
                edt_name.setError("Enter name(Min Length 4 characters)");
                }
                return false;
            }
        });
        edt_username.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(edt_phone.length()<10){
                edt_phone.setError("Enter Mobile Number");
                }
                if(select.equals("---Select---")){
                    Toast.makeText(getApplicationContext(),"Select User Type",Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
        edt_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(edt_username.length()<=3){
                edt_username.setError("Enter username(Min Length 4 characters)");
                }
                return false;
            }
        });

        dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText d_bsize, d_peroid, d_month, days;
                final Spinner d_type;
                Button save;
                final Dialog dialog = new Dialog(Register.this);
                dialog.setContentView(R.layout.dialogue_iputsdata);
                d_type = dialog.findViewById(R.id.type);
                d_bsize = dialog.findViewById(R.id.size);
                d_peroid = dialog.findViewById(R.id.peroid);
                days = dialog.findViewById(R.id.days);
                d_month = dialog.findViewById(R.id.month);
                usertype1.add("CIC");
                usertype1.add("ITC");
                usertype1.add("ITE");
                usertype1.add("BTE");

                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, usertype1);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                d_type.setAdapter(arrayAdapter);
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
                            Utilites.setSharedpreference(Register.this, "d_type", devtype);
                            Utilites.setSharedpreference(Register.this, "d_size", d_bsize.getText().toString());
                            Utilites.setSharedpreference(Register.this, "d_peroid", d_peroid.getText().toString());
                            Utilites.setSharedpreference(Register.this, "d_month", d_month.getText().toString());
                            Utilites.setSharedpreference(Register.this, "d_days", days.getText().toString());
                            Utilites.setSharedpreference(Register.this, "d_saved", "1");
                            Toast.makeText(getApplicationContext(), "Data  saved ", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    }
                });

                dialog.show();
            }
        });
emergency.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final Dialog dialog1 = new Dialog(Register.this);
        dialog1.setContentView(R.layout.dialogue_emergency);
        final EditText editText = dialog1.findViewById(R.id.number);
        final EditText editText1 = dialog1.findViewById(R.id.number1);
        final EditText editText2 = dialog1.findViewById(R.id.number2);

        Button save = dialog1.findViewById(R.id.reguser);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().length()>0) {
                    if (Patterns.PHONE.matcher(editText.getText().toString()).matches()) {
                        Utilites.setSharedpreference(Register.this, "em_phone", editText.getText().toString());
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
                        Utilites.setSharedpreference(Register.this, "em_phone1", editText1.getText().toString());
                        Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();

                    } else {
                        editText1.setError("Enter Valid Phone Number");
                        return;
                    }
                }
                if(editText2.getText().toString().length()>0) {
                    if (Patterns.PHONE.matcher(editText2.getText().toString()).matches()) {
                        Utilites.setSharedpreference(Register.this, "em_phone2", editText2.getText().toString());
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
regiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_name,str_phone,str_username,str_password,str_confirmpass,age1;
                str_name= edt_name.getText().toString().trim();
                str_phone= edt_phone.getText().toString().trim();
                str_username= edt_username.getText().toString().trim();
                str_password= edt_password.getText().toString().trim();
                str_confirmpass= edt_confirmpassword.getText().toString().trim();
                age1= age.getText().toString().trim();
             if(str_name.length()>0 && str_phone.length() >0 && str_username.length()>0 &&age.length()>0&&gender.length()>0){
                 if(str_password.equals(str_confirmpass)){
                    setData(str_name,str_phone,str_username,str_password,select,gender,age1);
                 }
                 else {
                     edt_confirmpassword.setError("Password Mismatch Occured");
                 }
             } else {
                 Toast.makeText(Register.this,"Please fill all fields and try again",Toast.LENGTH_LONG).show();
             }

            }
        });


    }

    private void setData( String str_name, String str_phone, String str_username, String str_password, String select,String gender, String age) {
       final ProgressDialog progressDialog =new ProgressDialog(Register.this);
      progressDialog.setMessage("Please wait....");
      progressDialog.show();
        try {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name", str_name);
            hashMap.put("uname", str_username);
            hashMap.put("phn", str_phone);
            hashMap.put("pswd", str_password);
            hashMap.put("type", select);
            hashMap.put("gender", gender);
            hashMap.put("age", age);
            final RetrofitInterface retrofitInterface = getRetrofitInstance.getRetrofitInstance().create(RetrofitInterface.class);
            retrofitInterface.Register(hashMap).enqueue(new Callback<InsertPojo>() {
                @Override
                public void onResponse(Call<InsertPojo> call, Response<InsertPojo> response) {
                    if (!response.body().getError()) {
                       progressDialog.cancel();
                        Toast.makeText(Register.this,"Use username and password to login",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<InsertPojo> call, Throwable t) {
                    progressDialog.cancel();
                    Toast.makeText(Register.this,"Some thing went wrong,Please try again!",Toast.LENGTH_LONG).show();                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void showDateTimePicker(final EditText d_peroid) {
        final Calendar date;
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener() {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),Login.class);
        startActivity(intent);
        finish();
    }
}
