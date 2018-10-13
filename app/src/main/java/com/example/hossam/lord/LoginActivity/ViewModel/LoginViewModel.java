package com.example.hossam.lord.LoginActivity.ViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.hossam.lord.LoginActivity.Data.LoginRepository;

public class LoginViewModel extends ViewModel {
    LoginRepository loginRepository;



   private MutableLiveData<String> username = new  MutableLiveData<String>() ;
    private    MutableLiveData<String> password = new  MutableLiveData<String>();
    private    MutableLiveData<String> token = new  MutableLiveData<String>();
    public MutableLiveData<String> getToken() {
        return token;
    }


    public MutableLiveData<String> getUsername() {
        return username;
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }



    public LoginViewModel() {
        loginRepository = new LoginRepository();
    }

    public void attemptLogin() {
        Log.d("", "attemptLogin() called with: username = [" + username + "], password = [" + password + "]");

        // call network method using network helper



         loginRepository.userlogin(username.getValue(), password.getValue() , token);


    }
}