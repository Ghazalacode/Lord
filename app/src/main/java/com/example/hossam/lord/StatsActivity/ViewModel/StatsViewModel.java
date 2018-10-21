package com.example.hossam.lord.StatsActivity.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.hossam.lord.CompanyActivitiesActivity.Data.ActivitiesModel;
import com.example.hossam.lord.StatsActivity.Data.StatsRepository;

import java.util.ArrayList;

/**
 * Created by hossam on 10/8/2018.
 */

public class StatsViewModel extends ViewModel {

    StatsRepository repository;

    public ArrayList<ActivitiesModel> getData() {
        return data;
    }

    ArrayList<ActivitiesModel> data = new ArrayList<ActivitiesModel>();

    public MutableLiveData<String> getErrorLD() {
        return errorLD;
    }

    final MutableLiveData<String> errorLD ;

    public MutableLiveData<ArrayList< ArrayList<String>>> getResponse() {
        return response;
    }
    private    MutableLiveData<ArrayList< ArrayList<String>>> response ;




    public StatsViewModel() {

        response = new MutableLiveData<>();

        errorLD = new MutableLiveData<>() ;

        ArrayList<ActivitiesModel> data = new ArrayList<ActivitiesModel>();
        data.add(new ActivitiesModel( ""));

        repository = new StatsRepository();

    }
    public ArrayList<ActivitiesModel> getRecyclerData() {



        data.add(new ActivitiesModel( "عدد عمليات البيع"));
        data.add(new ActivitiesModel( "قيمة المبيعات"));
        data.add(new ActivitiesModel( "عدد عمليات الشراء"));
        data.add(new ActivitiesModel( "قيمة عمليات الشراء"));
        data.add(new ActivitiesModel( "الربح والخسارة"));
        data.add(new ActivitiesModel( "المصروفات"));
        data.add(new ActivitiesModel( "القيمة المضافة"));
        data.add(new ActivitiesModel( "أكثر منتج مبيعا"));
        data.add(new ActivitiesModel( "أكثر منتج ركودا"));
        data.add(new ActivitiesModel( "أفضل موظف مبيعا"));
        data.add(new ActivitiesModel( "أقل موظف مبيعا"));
        data.add(new ActivitiesModel( "أفضل فرع مبيعا"));

return data;
    }

    public void updateData(String company, String activity ,
                           String branch, String dateFrom ,String dateTo){

        if (branch.equals("0")){ branch = null;  }
        if (dateFrom.isEmpty()){ dateFrom = null;  }
        if (dateTo .isEmpty()){ dateTo = null;  }
        if (activity .isEmpty()){ activity = null;  }
        repository.getCompanyActivities(company ,activity, branch,dateFrom,dateTo,response ,errorLD);
    }




}