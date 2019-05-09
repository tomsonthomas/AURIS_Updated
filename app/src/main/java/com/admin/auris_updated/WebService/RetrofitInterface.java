package com.admin.auris_updated.WebService;

import com.admin.auris_updated.InsertPojo;
import com.admin.auris_updated.LoginPogo;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitInterface {
        @FormUrlEncoded
        @POST("user_insert.php")
        Call<InsertPojo> Register(@FieldMap  HashMap<String,String>  hashMap);
        @FormUrlEncoded
        @POST("login_insert.php")
        Call<LoginPogo> login(@Field("uname") String username,
                              @Field("pswd") String password);
    }

