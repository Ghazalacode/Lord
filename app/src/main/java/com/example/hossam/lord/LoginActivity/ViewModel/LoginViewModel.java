package com.example.hossam.lord.LoginActivity.ViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.widget.Toast;

import com.example.hossam.lord.LoginActivity.Data.LoginRepository;
import com.example.hossam.lord.LoginActivity.View.LoginActivity;
import com.example.hossam.lord.Utils.StringUtils;

import java.util.ArrayList;

public class LoginViewModel extends ViewModel {
    LoginRepository loginRepository;

    public MutableLiveData<ArrayList<String>> getResponse() {
        return response;
    }

    private    MutableLiveData<ArrayList<String>> response ;

   private MutableLiveData<String> username = new  MutableLiveData<String>() ;
    private    MutableLiveData<String> password = new  MutableLiveData<String>();
    private    MutableLiveData<String> error = new  MutableLiveData<String>();

    public MutableLiveData<String> getToast() {
        return toast;
    }

    private    MutableLiveData<String> toast = new  MutableLiveData<String>();

    public MutableLiveData<String> getComID() {
        return comID;
    }

    private    MutableLiveData<String> comID = new  MutableLiveData<String>();
    public MutableLiveData<String> geterror() {
        return error;
    }


    public MutableLiveData<String> getUsername() {
        return username;
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }



    public LoginViewModel() {

        loginRepository = new LoginRepository();

        response = new MutableLiveData<>();
        comID  =new MutableLiveData<>();
    }

    public void attemptLogin() {
        Log.d("", "attemptLogin() called with: username = [" + username + "], password = [" + password + "]");

        // call network method using network helper

if (StringUtils.isNullOrEmpty(username.getValue()) || StringUtils.isNullOrEmpty(password.getValue()) ){
 toast.postValue("تأكد من ملأ جميع الخانات ");
}else{
    loginRepository.userlogin(username.getValue(), password.getValue() , error ,response , comID);
}

    }

}