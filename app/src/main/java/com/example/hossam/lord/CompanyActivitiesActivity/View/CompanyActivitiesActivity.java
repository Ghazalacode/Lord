package com.example.hossam.lord.CompanyActivitiesActivity.View;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.example.hossam.lord.CompanyActivitiesActivity.Adapters.ActivitiesRecyclerAdapter;
import com.example.hossam.lord.CompanyActivitiesActivity.Data.ActivitiesModel;
import com.example.hossam.lord.CompanyActivitiesActivity.ViewModel.CompanyActivitiesViewModel;
import com.example.hossam.lord.R;

import com.example.hossam.lord.databinding.ActivityCompanyBinding;


import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class  CompanyActivitiesActivity extends AppCompatActivity implements LifecycleOwner{

    ActivitiesRecyclerAdapter adapter;


    private CompanyActivitiesViewModel viewModel;

    private  LifecycleRegistry mLifecycleRegistry ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("JF-Flat-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)

                .build()
        );

   final ActivityCompanyBinding binding  = DataBindingUtil.setContentView(this, R.layout.activity_company);
        viewModel = ViewModelProviders.of(this).get(CompanyActivitiesViewModel.class);
        binding.setViewModel(viewModel);
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);

        // set up the RecyclerView


        binding.recylerActivities.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ActivitiesRecyclerAdapter(this, viewModel.getActivitiesList());

        binding.recylerActivities.setAdapter(adapter);

        viewModel.getActivitiesList().observe(CompanyActivitiesActivity.this, new Observer<ArrayList<ActivitiesModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ActivitiesModel> activitiesModels) {
                adapter.notifyDataSetChanged();

            }
        });


    }

    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
