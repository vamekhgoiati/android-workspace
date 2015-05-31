package com.coursera.posa.services;

import java.util.ArrayList;
import java.util.List;

import com.coursera.posa.aidl.WeatherCall;
import com.coursera.posa.aidl.WeatherData;
import com.coursera.posa.utils.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class WeatherServiceSync extends LifecycleLoggingService {
	
	public static Intent makeIntent(Context context) {
		return new Intent(context, WeatherServiceSync.class);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mWeatherCallImpl;
	}
	
	WeatherCall.Stub mWeatherCallImpl = new WeatherCall.Stub() {
		
		@Override
		public List<WeatherData> getCurrentWeather(String Weather)
				throws RemoteException {
			
			List<WeatherData> weatherDataList = new ArrayList<WeatherData>();
			
			WeatherData weatherData = Utils.getResults(Weather);
			
			if (weatherData != null) {
				weatherDataList.add(weatherData);
			}
			
			return weatherDataList;
		}
	};

}
