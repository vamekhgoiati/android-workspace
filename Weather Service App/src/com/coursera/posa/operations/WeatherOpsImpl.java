package com.coursera.posa.operations;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coursera.posa.R;
import com.coursera.posa.activity.MainActivity;
import com.coursera.posa.aidl.WeatherCall;
import com.coursera.posa.aidl.WeatherData;
import com.coursera.posa.aidl.WeatherRequest;
import com.coursera.posa.aidl.WeatherResults;
import com.coursera.posa.services.WeatherServiceAsync;
import com.coursera.posa.services.WeatherServiceSync;
import com.coursera.posa.utils.GenericServiceConnection;
import com.coursera.posa.utils.Utils;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.text.GetChars;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherOpsImpl implements WeatherOps {
	
	private static final long CACHE_UPDATE_TIME = 10 * 60 * 1000L;
	
	private Map<String, WeatherData> mWeatherDataCache;
	
	private Map<String, Long> mWeatherDataUpdateCache;

	private static final String TAG = WeatherOpsImpl.class.getCanonicalName();
	
	protected WeakReference<MainActivity> mActivity;
	
	protected WeakReference<ImageView> mIconImageView;
	
	protected WeakReference<EditText> mLocationEditText;
	
	protected WeakReference<TextView> mTempTextView;
	
	protected WeakReference<TextView> mCityTextView;
	
	protected WeakReference<TextView> mSpeedTextView;
	
	protected WeakReference<TextView> mDegTextView;
	
	protected WeakReference<TextView> mHumidityTextView;
	
	protected WeakReference<TextView> mSunriseTextView;
	
	protected WeakReference<TextView> mSunsetTextView;
	
	protected WeakReference<Button> mGetWeatherSyncButton;
	
	protected WeakReference<Button> mGetWeatherAsyncButton;
	
	private GenericServiceConnection<WeatherCall> mServiceConnectionSync;
	
	private GenericServiceConnection<WeatherRequest> mServiceConnectionAsync;
	
	private List<WeatherData> mResults;
	
	public WeatherOpsImpl(MainActivity activity) {
		
		mActivity = new WeakReference<MainActivity>(activity);
		
		mWeatherDataCache = new HashMap<String, WeatherData>();
		mWeatherDataUpdateCache = new HashMap<String, Long>();
		
		initializeViewFields();
        initializeNonViewFields();
		
	}

	private void initializeViewFields() {
		
		mActivity.get().setContentView(R.layout.activity_main);
		
		mLocationEditText = new WeakReference<EditText> ((EditText) mActivity.get().findViewById(R.id.location));
		
		mIconImageView = new WeakReference<ImageView>((ImageView) mActivity.get().findViewById(R.id.weather_pic));
		
		mTempTextView = new WeakReference<TextView>(getTextView(R.id.temp_text));
		
		mCityTextView = new WeakReference<TextView>(getTextView(R.id.city_name));
		
		mSpeedTextView = new WeakReference<TextView>(getTextView(R.id.wind));
		
		mDegTextView = new WeakReference<TextView>(getTextView(R.id.deg));
		
		mHumidityTextView = new WeakReference<TextView>(getTextView(R.id.humidity));
		
		mSunriseTextView = new WeakReference<TextView>(getTextView(R.id.sunrise));
		
		mSunsetTextView = new WeakReference<TextView>(getTextView(R.id.sunset));
		
		mGetWeatherSyncButton = new WeakReference<Button>((Button) mActivity.get().findViewById(R.id.get_weather_sync_button));
		
		mGetWeatherSyncButton.get().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isInCache()) {
					Log.i(TAG, "Setting results from cache");
					setResults(Arrays.asList(mWeatherDataCache.get(mLocationEditText.get().getText().toString())));
				} else {
					getWeatherSync(v);
				}
			}
		});
		
		mGetWeatherAsyncButton = new WeakReference<Button>((Button) mActivity.get().findViewById(R.id.get_weather_async_button));
		
		mGetWeatherAsyncButton.get().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isInCache()) {
					Log.i(TAG, "Setting results from cache");
					setResults(Arrays.asList(mWeatherDataCache.get(mLocationEditText.get().getText().toString())));
				} else {
					getWeatherAsync(v);
				}
			}
		});
		
	}
	
	private boolean isInCache() {
		
		String location = mLocationEditText.get().getText().toString();
		
		if (mWeatherDataCache.containsKey(location)) {
			long timeGap = ((new Date().getTime()) - mWeatherDataUpdateCache.get(location));
			return timeGap < CACHE_UPDATE_TIME;
		} 
		
		return false;
	}

	private void initializeNonViewFields() {
		
		mServiceConnectionSync = new GenericServiceConnection<WeatherCall>(WeatherCall.class);
		
		mServiceConnectionAsync = new GenericServiceConnection<WeatherRequest>(WeatherRequest.class);
		
		if (mResults != null && !mResults.isEmpty()) {
			setResults(mResults);
		}
		
	}
	
	

	private void setResults(List<WeatherData> results) {
		
		if(results != null && !results.isEmpty()) {
			
			Log.i(TAG, "Setting results");
			
			WeatherData weather = results.get(0);
			
			mIconImageView.get().setImageBitmap(weather.getIcon());
			
			mTempTextView.get().setText( (int)(weather.getTemp() - 273.15) + "\u00b0" + " C");
			
			mCityTextView.get().setText(weather.getName());
			
			mSpeedTextView.get().setText(weather.getSpeed() + " m/s");
			
			mDegTextView.get().setText(Double.toString(weather.getDeg()));
			
			mHumidityTextView.get().setText(weather.getHumidity() + "%");
			
			mSunriseTextView.get().setText(DateFormat.format("HH:mm:ss", weather.getSunrise()));
			
			mSunsetTextView.get().setText(DateFormat.format("HH:mm:ss", weather.getSunset()));
			
		}
		
	}

	@Override
	public void bindService() {
		
		if (mServiceConnectionSync.getInterface() == null) {
			mActivity.get().bindService
				(WeatherServiceSync.makeIntent(mActivity.get()),
				 mServiceConnectionSync, 
				 Context.BIND_AUTO_CREATE);
		}
		
		if (mServiceConnectionAsync.getInterface() == null) {
			mActivity.get().bindService
				(WeatherServiceAsync.makeIntent(mActivity.get()),
				 mServiceConnectionAsync,
				 Context.BIND_AUTO_CREATE);
		}
		
		Log.i(TAG, "Services binded");
		
	}

	@Override
	public void unbindService() {
		
		if (mServiceConnectionSync.getInterface() != null) {
			mActivity.get().unbindService(mServiceConnectionSync);
		}
		
		if (mServiceConnectionAsync.getInterface() != null) {
			mActivity.get().unbindService(mServiceConnectionAsync);
		}
		
	}

	@Override
	public void getWeatherSync(View v) {
		
		Log.i(TAG, "Getting weather sync");
		
		final WeatherCall mWeatherCall = mServiceConnectionSync.getInterface();
		
		if (mWeatherCall != null) {
			
			final String city = mLocationEditText.get().getText().toString();
			
			if (!city.isEmpty()) {
				
				new AsyncTask<String, Void, List<WeatherData>>() {

					private String mCity;
					
					@Override
					protected List<WeatherData> doInBackground(String... params) {
						mCity = params[0];
						
						try {
							return mWeatherCall.getCurrentWeather(city);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
						
						return null;
					}

					@Override
					protected void onPostExecute(List<WeatherData> result) {
						if (result != null && !result.isEmpty()) {
							updateCache(result);
							setResults(result);
						} else {
							Utils.showToast(mActivity.get(),
											mActivity.get().
												getResources().
												getString(R.string.no_weather_data_message));
						}
					}
					
				}.execute(city);
				
			} else {
				Utils.showToast(mActivity.get(), 
								mActivity.get().
									getResources().
									getString(R.string.type_location_message));
			}
			
			
		}
		
	}

	private void updateCache(List<WeatherData> result) {
		if (result != null && !result.isEmpty()) {
			String location = mLocationEditText.get().getText().toString();
			WeatherData weather = result.get(0);
			mWeatherDataCache.put(location, weather);
			mWeatherDataUpdateCache.put(location, new Date().getTime());
		}
	}

	@Override
	public void getWeatherAsync(View v) {
		
		Log.i(TAG, "Getting weather async");
		
		final WeatherRequest mWeatherRequest = mServiceConnectionAsync.getInterface();
		
		if (mWeatherRequest != null) {
			
			final String city = mLocationEditText.get().getText().toString();
			
			if (!city.isEmpty()) {
				
				try {
					mWeatherRequest.getCurrentWeather(city, mWeatherResults);
				} catch (RemoteException e) {
					Log.e(TAG, "Remote Exception " + e.getMessage());
				}
				
			} else {
				Utils.showToast(mActivity.get(), 
						mActivity.get().
							getResources().
							getString(R.string.type_location_message));
			}
			
		}
		
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub

	}
	
	private TextView getTextView (int resId) {
		return (TextView) mActivity.get().findViewById(resId);
	}
	
	WeatherResults.Stub mWeatherResults = new WeatherResults.Stub() {
		
		@Override
		public void sendResults(final List<WeatherData> results) throws RemoteException {
			mActivity.get().runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					if (results != null && !results.isEmpty()) {
						updateCache(results);
						setResults(results);
					} else {
						Utils.showToast(mActivity.get(),
								mActivity.get().
									getResources().
									getString(R.string.no_weather_data_message));
					}
				}
			}); 
		}
	};

}
