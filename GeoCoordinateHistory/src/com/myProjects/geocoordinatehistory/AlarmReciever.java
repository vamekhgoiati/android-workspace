package com.myProjects.geocoordinatehistory;

import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
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
	private static final int NOTIFICATION_ID = 1;
	
	private Location mCurrentLocation;
	private LocationManager mLocationManager;
	private Intent mGPSCoordinateIntent;
	private PendingIntent mGPSCoordinatePendingIntent;

	@Override
	public void onReceive(Context context, Intent intent) {
		
		mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
		Log.d(TAG, "Broadcast recieved");
		
		if (mLocationManager != null) {
			mCurrentLocation = getCurrentLocation();
			if (mCurrentLocation != null) {
				Log.d(TAG, "Latitude: " + mCurrentLocation.getLatitude() + "; Longtitude: " + mCurrentLocation.getLongitude());
			}
		}
		
	}

	private Location getCurrentLocation() {
		
		Location bestResult = null;
		float bestAccuracy = Float.MAX_VALUE;
		long bestAge = Long.MIN_VALUE;

		List<String> matchingProviders = mLocationManager.getAllProviders();

		for (String provider : matchingProviders) {

			mLocationManager.requestLocationUpdates(provider, 4 * 1000L, 0f, this);	
			
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
