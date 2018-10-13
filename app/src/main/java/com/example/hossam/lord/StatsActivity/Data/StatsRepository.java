package com.example.hossam.lord.StatsActivity.Data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.hossam.lord.LoginActivity.Data.Model.LoginResponse;
import com.example.hossam.lord.LoginActivity.Data.Network.NetworkApi;
import com.example.hossam.lord.LoginActivity.Data.Network.RetrofitClient;
import com.example.hossam.lord.StatsActivity.Data.Model.ActivitiesResponse;
import com.example.hossam.lord.StatsActivity.Data.Model.ActivitiesResponseMain;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class StatsRepository {

    public StatsRepository() {
    }

    private NetworkApi networkApiClient  =null ;

    //…
     MutableLiveData<String> data = new MutableLiveData<>();
     String token ="", error  ="" ;
    public  void   getCompanyActivities(String company, String activity ,
                                        String branch, String dateFrom ,String dateTo
            ,final MutableLiveData<ArrayList< ArrayList<String>>> responseLD ) {

        networkApiClient =  RetrofitClient.getClient(NetworkApi.BASE_API_URL).create(NetworkApi.class);
        networkApiClient.getCompanyActivities(company, activity, branch, dateFrom, dateTo).enqueue(new Callback<ArrayList<ActivitiesResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ActivitiesResponse>> call, Response<ArrayList<ActivitiesResponse>> response) {
                if (response.isSuccessful()) {
// TODO post arraylist of arraylist of data ordered based on recycler order
 // TODO observe in activity when updated uupdate list
 //TODO on recycler item  selcted get arraylist from arraylist with the clicked index
                    ArrayList<String> saleNo = new ArrayList<>();
                    ArrayList<String> saleVal = new ArrayList<>();
                    ArrayList<String> purchNo = new ArrayList<>();
                    ArrayList<String> purchVal = new ArrayList<>();
                    ArrayList<String> profit = new ArrayList<>();
                    ArrayList<String> expenses = new ArrayList<>();
                    ArrayList<String> saletax = new ArrayList<>();
                    ArrayList<String> item_most = new ArrayList<>();
                    ArrayList<String> item_worst = new ArrayList<>();
                    ArrayList<String> emp_most = new ArrayList<>();
                    ArrayList<String> emp_worst = new ArrayList<>();
                    ArrayList<String> branch = new ArrayList<>();
                    ArrayList<String> dates = new ArrayList<>();
                    ArrayList< ArrayList<String>> fullRespone = new ArrayList<>();


                  //  Log.d("d", "onResponse: "+response.body().get(0).get);
                    for (int i = 0; i <response.body().size() ; i++) {
                        saleNo    .add(String.valueOf(response.body().get(i).getSaleno()));
                        saleVal   .add(String.valueOf(response.body().get(i).getSaleval()));
                        purchNo   .add(String.valueOf(response.body().get(i).getPurchno()));
                        purchVal  .add(String.valueOf(response.body().get(i).getPurchval()));
                        profit    .add(String.valueOf(response.body().get(i).getProfit()));
                        expenses    .add(String.valueOf(response.body().get(i).getExpenses()));
                        saletax     .add(String.valueOf(response.body().get(i).getSaletax()));
                        item_most  .add(response.body().get(i).getItem_most());
                        item_worst .add(response.body().get(i).getItem_worst());
                        emp_most    .add(response.body().get(i).getEmp_most());
                        emp_worst  .add(response.body().get(i).getEmp_worst());
                        branch     .add(String.valueOf(response.body().get(i).getBranch()));
                        dates     .add(String.valueOf(response.body().get(i).getDate()));

                    }
                    fullRespone.add(0,saleNo    );
                    fullRespone.add(1,saleVal   );
                    fullRespone.add(2,purchNo   );
                    fullRespone.add(3,purchVal  );
                    fullRespone.add(4,profit    );
                    fullRespone.add(5,expenses  );
                    fullRespone.add(6,saletax   );
                    fullRespone.add(7,item_most );
                    fullRespone.add(8,item_worst);
                    fullRespone.add(9,emp_most  );
                    fullRespone.add(10,emp_worst );
                    fullRespone.add(11,branch    );
                    fullRespone.add(12,dates    );
                    for (int i = 0; i <fullRespone.size() ; i++) {
                        fullRespone.get(i).removeAll(Arrays.asList(null,""));
                    }

                    Log.d("", "onResponse()  response = [" + saleNo.toString() + "]");


                    responseLD.postValue(fullRespone);
                   // Log.d("d", "onResponse: "+activity.get(0));
                    //token.postValue(user[0].getCompany_id().toString());
                } else {
                    Log.d("d", "onResponse: "+response.code());}
            }

            @Override
            public void onFailure(Call<ArrayList<ActivitiesResponse>> call, Throwable t) {
                Log.d("failure", "onFailure() called with: call = [" + call + "], t = [" + t.getMessage() + "]");
                //   data.postValue(t.getMessage());

                error = t.getMessage();


                //  token.postValue(error);
            }
        });



/*    if (token == ""){data .setValue("76868");
                            return error; }
       else { return token;}*/
    }
}
