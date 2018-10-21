package com.example.hossam.lord;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;

import com.example.hossam.lord.Utils.LocaleUtils;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MyCustomApplication extends MultiDexApplication /*instead of Application*/{
        // Called when the application is starting, before any other application objects have been created.
        // Overriding this method is totally optional!
		private Locale locale = null;



	@Override
	public void onCreate()
	{
		super.onCreate();

		LocaleUtils.setLocale(new Locale("ar"));
		LocaleUtils.updateConfig(this, getBaseContext().getResources().getConfiguration());

	}

	// Called by the system when the device configuration changes while your component is running.
        // Overriding this method is totally optional!
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		LocaleUtils.updateConfig(this, newConfig);
	}

        // This is called when the overall system is running low on memory, 
        // and would like actively running processes to tighten their belts.
        // Overriding this method is totally optional!
	@Override
	public void onLowMemory() {
	    super.onLowMemory();
	}

	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
	}
}