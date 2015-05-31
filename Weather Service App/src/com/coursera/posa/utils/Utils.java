package com.coursera.posa.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.coursera.posa.aidl.WeatherData;
import com.coursera.posa.json.JsonWeather;
import com.coursera.posa.json.WeatherJsonParser;

public class Utils {

	private static final String TAG = Utils.class.getCanonicalName();

	private static final String WEATHER_SERVICE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";

	private static final String WEATHER_ICON_URL = "http://openweathermap.org/img/w/";

	public static WeatherData getResults(final String city) {

		JsonWeather weather = null;

		try {

			Log.i(TAG, "Getting results for " + city);

			final URL url = new URL(WEATHER_SERVICE_URL + city);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();

			try (InputStream in = new BufferedInputStream(
					urlConn.getInputStream())) {

				final WeatherJsonParser parser = new WeatherJsonParser();

				Log.i(TAG, "parsing JSON");

				weather = parser.parseJsonStream(in);

			} finally {
				urlConn.disconnect();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (weather != null) {
			WeatherData weatherRes = new WeatherData(weather);
			weatherRes.setIcon(getWeatherIcon(weather.getWeatherIcon()));
			return weatherRes;
		}

		return null;
	}

	public static Bitmap getWeatherIcon(final String icon) {
		Bitmap mIcon = null;

		try {
			
			Log.i(TAG, "Downloading icon " + icon + " at " + WEATHER_ICON_URL + icon + ".png");
			
			URL url = new URL(WEATHER_ICON_URL + icon + ".png");

			InputStream is = (InputStream) url.openConnection().getInputStream();
			mIcon = BitmapFactory.decodeStream(is);
		} catch (Exception e) {
			return null; // Indicate a failure.
		}

		return mIcon;
	}

	public static void showToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

}
