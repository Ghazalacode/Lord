package com.example.hossam.lord.LoginActivity.View;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.*;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hossam.lord.CompanyActivitiesActivity.View.CompanyActivitiesActivity;
import com.example.hossam.lord.LoginActivity.ViewModel.LoginViewModel;
import com.example.hossam.lord.R;
import com.example.hossam.lord.Utils.LocaleUtils;
import com.example.hossam.lord.Utils.StringUtils;
import com.example.hossam.lord.databinding.ActivityLoginBinding;

import java.util.ArrayList;

import okhttp3.Route;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
//TODO handle error messages returning from login
public class LoginActivity extends AppCompatActivity implements LifecycleOwner {
    private static final String TAG ="LoginActivity" ;

    String comID = "" ,comName = "" ;
    ArrayList<String> activities  ;
    private LoginViewModel loginViewModel;

    private  LifecycleRegistry mLifecycleRegistry ;

    public LoginActivity() {

        LocaleUtils.updateConfig(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

              CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("JF-Flat-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)

                .build()
        );

        final ActivityLoginBinding binding  = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
         binding.setLoginViewModel(loginViewModel);
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);

        binding.etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                loginViewModel.getUsername().postValue(editable.toString());

            }
        });
        binding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                loginViewModel.getPassword().postValue(editable.toString());

            }
        });
        binding.tvAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this, AboutActivity.class);
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                startActivity(i);
            }
        });
        activities=new ArrayList<>();
        loginViewModel.geterror().observe(LoginActivity.this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d(TAG, "onChanged() called with: s = [" + s + "]");
                Toast.makeText(LoginActivity.this, s,
                        Toast.LENGTH_LONG).show();
            }
        });

        loginViewModel.getComID().observe(LoginActivity.this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                String[] parts = s.split("/");
                comID = parts[0];
               comName = parts[1];

            }
        });

        loginViewModel.getResponse().observe(LoginActivity.this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(@Nullable ArrayList<String> activitiesLD) {
                activities = activitiesLD;

                if (comID.isEmpty()==false && activities.size()!=0){
                    Intent i=new Intent(LoginActivity.this, CompanyActivitiesActivity.class);
                    i.putStringArrayListExtra("activities", activities);
                    i.putExtra("comID", comID);
                    i.putExtra("comName", comName);
                    startActivity(i);

                }
            }
        });

        loginViewModel.getToast().observe(LoginActivity.this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(LoginActivity.this, s,
                        Toast.LENGTH_LONG).show();
            }
        });
 }

    private int validate(String username, String password) {
//// might add some other validation

        if (StringUtils.isNullOrEmpty(username)) {
            return 0;
        }else  if (StringUtils.isNullOrEmpty(password)) {
            return 1;
        } if (StringUtils.isNullOrEmpty(username)  && StringUtils.isNullOrEmpty(password) ) {
            return 2;
        }else { return 3;  }

    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }
    @Override
    public void onStart() {
        super.onStart();
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
