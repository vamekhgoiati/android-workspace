package com.coursera.posa.services;

import java.util.ArrayList;
import java.util.List;

import com.coursera.posa.aidl.WeatherData;
import com.coursera.posa.aidl.WeatherRequest;
import com.coursera.posa.aidl.WeatherResults;
import com.coursera.posa.utils.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class WeatherServiceAsync extends LifecycleLoggingService {
	
	public static Intent makeIntent(Context context) {
		return new Intent(context, WeatherServiceAsync.class);
	}
	
	
	
	@Override
	public IBinder onBind(Intent intent) {
		return mWeatherRequestImpl;
	}



	WeatherRequest.Stub mWeatherRequestImpl = new WeatherRequest.Stub() {
		
		@Override
		public void getCurrentWeather(String Weather, WeatherResults results)
				throws RemoteException {
			
			List<WeatherData> weatherDataList = new ArrayList<>();
			
			WeatherData weatherData = Utils.getResults(Weather);
			
			if (weatherData != null) {
				weatherDataList.add(weatherData);
			}
			
			results.sendResults(weatherDataList);
			
		}
	};

}
