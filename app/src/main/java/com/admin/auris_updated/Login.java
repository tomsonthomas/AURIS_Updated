package com.admin.auris_updated;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.auris_updated.PartialDeaf.MainActivityPartialDeaf;
import com.admin.auris_updated.WebService.RetrofitInterface;
import com.admin.auris_updated.WebService.getRetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class Login extends AppCompatActivity {
    TextInputEditText username, paswword;
    Button Btn_Login;
    TextView Btn_Register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username = findViewById(R.id.name);
        paswword = findViewById(R.id.password);
        Btn_Login = findViewById(R.id.login);
        Btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
try {
                final ProgressDialog progressDialog =new ProgressDialog(Login.this);
                progressDialog.setMessage("Please wait....");
                progressDialog.show();

    String name, pass;
    name = username.getText().toString().trim();
    pass = paswword.getText().toString().trim();
    final RetrofitInterface retrofitInterface = getRetrofitInstance.getRetrofitInstance().create(RetrofitInterface.class);
    retrofitInterface.login(name, pass).enqueue(new Callback<LoginPogo>() {
        @Override
        public void onResponse(Call<LoginPogo> call, Response<LoginPogo> response) {
            if (!response.body().getStatus().equals("1")) {
                try {
                    progressDialog.cancel();
                    Utilites.setSharedpreference(Login.this, "name", response.body().getInfo().get(0).getName());
                    Utilites.setSharedpreference(Login.this, "uname", response.body().getInfo().get(0).getUname());
                    Utilites.setSharedpreference(Login.this, "phone", response.body().getInfo().get(0).getPhn());
                    Utilites.setSharedpreference(Login.this, "password", response.body().getInfo().get(0).getPswd());
                    Utilites.setSharedpreference(Login.this, "type", response.body().getInfo().get(0).getType());
                    Utilites.setSharedpreference(Login.this, "gender", response.body().getInfo().get(0).getGender());
                    Utilites.setSharedpreference(Login.this, "age", response.body().getInfo().get(0).getAge());


                    if (Utilites.getSharedPrferencedata(Login.this, "type").equals("1")) {
                        Toast.makeText(getApplicationContext(), "login sucess", Toast.LENGTH_LONG).show();
                        Utilites.setSharedpreference(Login.this, "loginstatus", "1");
                        Intent intent = new Intent(Login.this, MainActivityDeaf.class);
                        startActivity(intent);
                        finish();
                    } else if (Utilites.getSharedPrferencedata(Login.this, "type").equals("2")) {
                        Toast.makeText(getApplicationContext(), "login sucess", Toast.LENGTH_LONG).show();
                        Utilites.setSharedpreference(Login.this, "loginstatus", "1");
                        Intent intent = new Intent(Login.this, MainActivityPartialDeaf.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "login sucess", Toast.LENGTH_LONG).show();
                        Utilites.setSharedpreference(Login.this, "loginstatus", "1");
                        Intent intent = new Intent(Login.this, MainActivityPartialDeaf.class);
                        startActivity(intent);
                        finish();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Please enter correct user name and password", Toast.LENGTH_LONG).show();

                }

            }
        }

        @Override
        public void onFailure(Call<LoginPogo> call, Throwable t) {
              progressDialog.cancel();
            Toast.makeText(getApplicationContext(), "Please enter correct user name and password", Toast.LENGTH_LONG).show();
        }
    });
}catch (Exception e){
    e.printStackTrace();
}
            }
        });
        Btn_Register = findViewById(R.id.register);
        Btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });

    }





    @Override
    public void onBackPressed() {

        final Dialog dialog1 = new Dialog(Login.this);
        dialog1.setContentView(R.layout.dialogue_warning);
        TextView title = dialog1.findViewById(R.id.dialog_title);
        TextView text = dialog1.findViewById(R.id.dialog_text);
        TextView no =dialog1.findViewById(R.id.no);
        TextView yes = dialog1.findViewById(R.id.yes);
        title.setText("Exit!!!");
        text.setText("Are you want to Exit?");
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
                // DBTransactionFunctions.updateConfigvalues("login_status","0");
                finish();

            }
        });
        dialog1.show();
    }
}
