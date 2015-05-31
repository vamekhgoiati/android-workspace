package com.coursera.posa.activity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.coursera.posa.R;
import com.coursera.posa.json.JsonWeather;
import com.coursera.posa.json.WeatherJsonParser;
import com.coursera.posa.operations.WeatherOps;
import com.coursera.posa.operations.WeatherOpsImpl;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends LifecycleLoggingActivity {
	
	private WeatherOps mWeatherOps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mWeatherOps = new WeatherOpsImpl(this);
		
		mWeatherOps.bindService();
		
	}
	
	@Override
    protected void onDestroy() {
        // Unbind from the Service.
        mWeatherOps.unbindService();

        // Always call super class for necessary operations when an
        // Activity is destroyed.
        super.onDestroy();
    }
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mWeatherOps.onConfigurationChanged(newConfig);
    }
	
	
}
