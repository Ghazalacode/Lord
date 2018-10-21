package com.example.hossam.lord.LoginActivity.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hossam.lord.R;
import com.example.hossam.lord.Utils.LocaleUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AboutActivity extends AppCompatActivity {

    public AboutActivity() {
        LocaleUtils.updateConfig(this);
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_about);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView login = (TextView)findViewById(R.id.tvLogin) ;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AboutActivity.this, LoginActivity.class);
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                startActivity(i);
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
