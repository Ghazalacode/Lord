package com.example.hossam.lord.StatsActivity.Data.Network;

import com.example.hossam.lord.LoginActivity.Data.Model.LoginResponse;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NetworkApi {

    static String BASE_API_URL = "http://retalgroups.com/";

    @POST("company.aspx")
    @FormUrlEncoded
    Call<LoginResponse> userLogin(@Field("username") String username,
                                             @Field("pwd") String password);



}
