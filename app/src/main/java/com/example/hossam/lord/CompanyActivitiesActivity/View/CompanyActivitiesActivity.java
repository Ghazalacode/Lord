package com.example.hossam.lord.CompanyActivitiesActivity.View;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.example.hossam.lord.CompanyActivitiesActivity.Adapters.ActivitiesRecyclerAdapter;
import com.example.hossam.lord.CompanyActivitiesActivity.Data.ActivitiesModel;
import com.example.hossam.lord.CompanyActivitiesActivity.ViewModel.CompanyActivitiesViewModel;
import com.example.hossam.lord.LoginActivity.View.LoginActivity;
import com.example.hossam.lord.R;

import com.example.hossam.lord.StatsActivity.Adapters.RecyclerItemClickListener;
import com.example.hossam.lord.StatsActivity.View.StatsActivity;
import com.example.hossam.lord.Utils.LocaleUtils;
import com.example.hossam.lord.Utils.StringUtils;
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

      final  ArrayList<String> activities = getIntent().getExtras().getStringArrayList("activities");
        final String comName = getIntent().getStringExtra("comName");
        final String comID = getIntent().getStringExtra("comID");

        final ActivityCompanyBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_company);
        viewModel = ViewModelProviders.of(this).get(CompanyActivitiesViewModel.class);
        binding.setViewModel(viewModel);
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);


        binding.tvCompanyName.setText(comName);
        // set up the RecyclerView
        binding.recylerActivities.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ActivitiesRecyclerAdapter(this, activities);

        binding.recylerActivities.setAdapter(adapter);
        binding.recylerActivities.addOnItemTouchListener(new RecyclerItemClickListener(CompanyActivitiesActivity.this, binding.recylerActivities, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

              if ( StringUtils.isNullOrEmpty(comID) || StringUtils.isNullOrEmpty(activities.get(position)) ){
                  Log.e( "d: either is null ","id"+comID +"activity"+ activities.get(position));
              }else{
                  Intent i=new Intent(CompanyActivitiesActivity.this, StatsActivity.class);
                  i.putExtra("activity", activities.get(position));
                  i.putExtra("comID", comID);
                  i.putExtra("comName", comName);
                  i.putStringArrayListExtra("activities" , activities);

                  Log.e( "act / comID: ",activities.get(position) +"/"+comID );

                  startActivity(i);
              }



            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));


        binding.ivCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clearing back stack task >> hittin back won't get him back to application  LOGOUT
                Intent intent = new Intent(CompanyActivitiesActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
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

    public CompanyActivitiesActivity() {

        LocaleUtils.updateConfig(this);
    }
}
