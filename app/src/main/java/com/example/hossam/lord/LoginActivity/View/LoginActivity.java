package com.example.hossam.lord.LoginActivity.View;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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

import com.example.hossam.lord.LoginActivity.ViewModel.LoginViewModel;
import com.example.hossam.lord.R;
import com.example.hossam.lord.Utils.StringUtils;
import com.example.hossam.lord.databinding.ActivityLoginBinding;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity implements LifecycleOwner {
    private static final String TAG ="LoginActivity" ;
    private LoginViewModel loginViewModel;

    private  LifecycleRegistry mLifecycleRegistry ;

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

        loginViewModel.getToken().observe(LoginActivity.this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d(TAG, "onChanged() called with: s = [" + s + "]");
                Toast.makeText(LoginActivity.this, s,
                        Toast.LENGTH_LONG).show();
            }
        });

/*binding.btnLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
String username = binding.etUsername.getText().toString();
String password = binding.etPassword.getText().toString();

if ( validate(username , password) == 3 ){
    Log.d(TAG, "onClick() called with: validate =3 [" + username + "]");*/

/*}
else if (validate(username , password) == 3){
    // TODO set error for et and show snackbar
    Toast.makeText(LoginActivity.this, "Invalid Username value",
            Toast.LENGTH_LONG).show();
}



    }
});*/


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
