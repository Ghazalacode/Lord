package com.example.hossam.lord.CompanyActivitiesActivity.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.hossam.lord.CompanyActivitiesActivity.Data.ActivitiesModel;
import com.example.hossam.lord.LoginActivity.Data.LoginRepository;

import java.util.ArrayList;

/**
 * Created by hossam on 10/7/2018.
 */

public class CompanyActivitiesViewModel extends ViewModel {


    public MutableLiveData<ArrayList<ActivitiesModel>> getActivitiesList() {
        return activitiesList;
    }

    private MutableLiveData<ArrayList<ActivitiesModel>> activitiesList = new MutableLiveData<>();




    public CompanyActivitiesViewModel() {
        activitiesList = new MutableLiveData<>();
        ArrayList<ActivitiesModel> data = new ArrayList<ActivitiesModel>();
        data.add(new ActivitiesModel( ""));
       activitiesList.setValue(data);

    }
    public void populateRecycler() {
        ArrayList<ActivitiesModel> data = new ArrayList<ActivitiesModel>();
        data.add(new ActivitiesModel( "التموينات الغذائية"));
        data.add(new ActivitiesModel( "الملابس الجاهزة"));
        data.add(new ActivitiesModel( "قطع غيار السيارات"));
        data.add(new ActivitiesModel( "المطاعم"));

        activitiesList.postValue(data);

    }




}