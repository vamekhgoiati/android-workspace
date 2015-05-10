package com.myProjects.geocoordinatehistory;

import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

public class AlarmReciever extends BroadcastReceiver implements LocationListener{
	
	private static final String TAG = "com.myprojects.geocoordinatehistory.AlarmReciever";
	public static final String INTENT_FILTER = "com.myprojects.geocoordinatehistory.AlarmReciever";
	private static final int NOTIFICATION_ID = 1;
	
	private Location mCurrentLocation;
	private LocationManager mLocationManager;
	private DatabaseHelper mDbHelper;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		mDbHelper = new DatabaseHelper(context);
		
		Log.d(TAG, "Broadcast recieved");
		
		if (mLocationManager != null) {
			mCurrentLocation = getCurrentLocation();
			if (mCurrentLocation != null) {
				insertLocation(mCurrentLocation);
				Log.d(TAG, "Database insert --- Latitude: " + mCurrentLocation.getLatitude() + "; Longtitude: " + mCurrentLocation.getLongitude());
			}
		}
		
	}

	private void insertLocation(Location currentLocation) {
		
		ContentValues values = new ContentValues();
		
		values.put(DatabaseHelper.LATITUDE, currentLocation.getLatitude());
		values.put(DatabaseHelper.LONGTITUDE, currentLocation.getLongitude());
		
		mDbHelper.getWritableDatabase().insert(DatabaseHelper.TABLE_NAME, null, values);
		
		values.clear();
		
	}

	private Location getCurrentLocation() {
		
		Location bestResult = null;
		float bestAccuracy = Float.MAX_VALUE;
		long bestAge = Long.MIN_VALUE;

		List<String> matchingProviders = mLocationManager.getAllProviders();

		for (String provider : matchingProviders) {

			mLocationManager.requestLocationUpdates(provider, 0, 0, this);	
			
			Location location = mLocationManager.getLastKnownLocation(provider);

			if (location != null) {

				float accuracy = location.getAccuracy();
				long time = location.getTime();

				if (accuracy < bestAccuracy) {

					bestResult = location;
					bestAccuracy = accuracy;
					bestAge = time;

				}
			}
		}
		
		// TODO Auto-generated method stub
		return bestResult;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

}
