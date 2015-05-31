package com.coursera.posa.operations;

import android.content.res.Configuration;
import android.view.View;

public interface WeatherOps {
	
	public void bindService();
	
	public void unbindService();
	
	public void getWeatherSync(View v);
	
	public void getWeatherAsync(View v);
	
	public void onConfigurationChanged(Configuration newConfig);

}
