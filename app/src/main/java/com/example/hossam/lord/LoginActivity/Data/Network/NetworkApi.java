package com.example.hossam.lord.LoginActivity.Data.Network;

import com.example.hossam.lord.LoginActivity.Data.Model.LoginResponse;
import com.example.hossam.lord.LoginActivity.Data.Model.LoginResponseI;
import com.example.hossam.lord.StatsActivity.Data.Model.ActivitiesResponse;


import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NetworkApi {

    static String BASE_API_URL = "http://retalgroups.com/";


    @GET("company.aspx")
    Call<ArrayList<LoginResponseI>> userLogin(@Query("username") String username,
                                              @Query("pwd") String password);

    @GET("company.aspx")
    Call<ArrayList<ActivitiesResponse>> getCompanyActivities(@Query("company") String company,
                                                      @Query("activity") String activity ,
                                                      @Query("branch") String branch
            , @Query("date1") String dateFrom
            , @Query("date2") String dateTo);




}
