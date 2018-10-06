package com.example.hossam.lord.LoginActivity.ViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.hossam.lord.LoginActivity.Data.LoginRepository;

public class LoginViewModel extends ViewModel {
    LoginRepository loginRepository;

    public LoginViewModel() {
        loginRepository = new LoginRepository();
    }

    public LiveData<String> attemptLogin(String username, String password) {
        Log.d("", "attemptLogin() called with: username = [" + username + "], password = [" + password + "]");

        // call network method using network helper



        // send state to activity
       return loginRepository.userlogin(username, password);
    }
}