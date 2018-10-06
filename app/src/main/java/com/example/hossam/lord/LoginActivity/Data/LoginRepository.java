package com.example.hossam.lord.LoginActivity.Data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.hossam.lord.LoginActivity.Data.Model.LoginResponse;
import com.example.hossam.lord.LoginActivity.Data.Network.NetworkApi;
import com.example.hossam.lord.LoginActivity.Data.Network.RetrofitClient;
import com.google.gson.JsonObject;

import org.json.JSONObject;

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
    public LiveData<String> userlogin(String username, String password) {




        networkApiClient =  RetrofitClient.getClient(NetworkApi.BASE_API_URL).create(NetworkApi.class);
        networkApiClient.userLogin(username, password).enqueue(new Callback<LoginResponse>() {
             @Override
             public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {


                 if (response.isSuccessful()) {
                     Log.d("d", "onResponse: "+response.body().toString());
                 } else {
                     Log.d("d", "onResponse: "+response.code());}


               data.postValue(response.body().getToken());
                // Log.d("", "onResponse() called with: call = [" + call + "], response = [" + response + "]");
             }

             @Override
             public void onFailure(Call<LoginResponse> call, Throwable t) {
                 Log.d("failure", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                    data.postValue(t.getMessage());
             }
         }


        );
    if (data == null){data .setValue("76868");
                            return data; }
       else { return data;}
    }
}
