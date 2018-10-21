package com.example.hossam.lord.LoginActivity.Data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.hossam.lord.LoginActivity.Data.Model.LoginResponse;
import com.example.hossam.lord.LoginActivity.Data.Model.LoginResponseI;
import com.example.hossam.lord.LoginActivity.Data.Network.NetworkApi;
import com.example.hossam.lord.LoginActivity.Data.Network.RetrofitClient;
import com.example.hossam.lord.StatsActivity.Data.Model.ActivitiesResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class LoginRepository {

    public LoginRepository() {
    }

    private NetworkApi networkApiClient  =null ;

    //…
     MutableLiveData<String> data = new MutableLiveData<>();

    public  void   userlogin(String username, String password , final MutableLiveData<String> error ,
                             final MutableLiveData<ArrayList<String>> responseLD, final MutableLiveData<String> comIDD) {

        networkApiClient =  RetrofitClient.getClient(NetworkApi.BASE_API_URL).create(NetworkApi.class);
        networkApiClient.userLogin(username, password).enqueue(new Callback<ArrayList<LoginResponseI>>() {
             @Override
             public void onResponse(Call<ArrayList<LoginResponseI>> call, Response<ArrayList<LoginResponseI>> response) {


                 if (response.isSuccessful()) {
                     Log.d("d", "onResponse: "+response.body().toString());
                     if( response.body().size()==0 ){ error.postValue("Invalid username or password");
                     return;
                     }

                    // LoginResponse  user =response.body().get(0);
                     //Log.d("d", "onResponse: "+user.toString());
                     final String comID  =  String.valueOf( response.body().get(0).getCompanyId()) ;

                     networkApiClient.getCompanyActivities(comID , null ,null , null ,null).enqueue(
                             new Callback<ArrayList<ActivitiesResponse>>() {
                         @Override
                         public void onResponse(Call<ArrayList<ActivitiesResponse>> call, Response<ArrayList<ActivitiesResponse>> response) {
                             if (response.isSuccessful()) {
                                 String comName  =  String.valueOf( response.body().get(0).getCompanyname()) ;
                                 comIDD.postValue(comID + "/"+comName);
                                 ArrayList<String> activities = new ArrayList<>();
                                 for (int i = 0; i <response.body().size() ; i++) {
                                     activities.add(response.body().get(i).getActivity());
                                 }
                                 Log.e( "act: ",activities.toString() );

                                 // remove duplicate value from branches

                                 Set<String> hs = new LinkedHashSet<>();
                                 hs.addAll(activities);
                                 activities.clear();
                                 activities.addAll(hs);
                                 responseLD.postValue(activities);


                             }


                         }

                         @Override
                         public void onFailure(Call<ArrayList<ActivitiesResponse>> call, Throwable t) {
                                   error.postValue(t.getMessage());
                         }
                     });
                   //  error.postValue(String.valueOf());
                 } else {
                     Log.d("d", "onResponse: "+response.code());}


            //   data.postValue(response.body().geterror());
                // Log.d("", "onResponse() called with: call = [" + call + "], response = [" + response + "]");
             }

             @Override
             public void onFailure(Call<ArrayList<LoginResponseI>> call, Throwable t) {
                 Log.d("failure", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                 //   data.postValue(t.getMessage());
                 error.postValue(t.getMessage());
             }
         }


        );
/*    if (error == ""){data .setValue("76868");
                            return error; }
       else { return error;}*/
    }
}
