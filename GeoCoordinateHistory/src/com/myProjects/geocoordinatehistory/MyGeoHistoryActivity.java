package com.myProjects.geocoordinatehistory;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyGeoHistoryActivity extends Activity {
	
	private static final String TAG = MyGeoHistoryActivity.class.getSimpleName();
	
	private static final Long ALARM_DELAY = 10 * 1000L;
	private AlarmManager mAlarmManager;
	private Intent mGPSCoordinateIntent;
	private PendingIntent mGPSCoordinatePendingintent;
	private DatabaseHelper mDbHelper;
	private Button mStartButton;
	private Button mStopButton;
	private Button mDrawMapButton;
	private Button mClearButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mDbHelper = new DatabaseHelper(getApplicationContext());
		
		mStartButton = (Button) findViewById(R.id.start_button);
		mStopButton = (Button) findViewById(R.id.stop_button);
		mDrawMapButton = (Button) findViewById(R.id.draw_map);
		mClearButton = (Button) findViewById(R.id.clear_button);
		
		mStartButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				enableBroadcastService();
			}
		});
		
		mStopButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				disableBroadcastService();
			}
		});
		
		mDrawMapButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				drawRoute();
			}
		});
		
		mClearButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				clearDbContent();
			}
		});
		
	}
	
	private void clearDbContent() {
		mDbHelper.getWritableDatabase().delete(DatabaseHelper.TABLE_NAME, null, null);
	}

	private void drawRoute() {
		
		Cursor c = readLocations();
		double latitude;
		double longtitude;
		
		if (c.moveToFirst()) {
			do {
				latitude = c.getDouble(c.getColumnIndex(DatabaseHelper.LATITUDE));
				longtitude = c.getDouble(c.getColumnIndex(DatabaseHelper.LONGTITUDE));
				Log.d(TAG, "Latitude : " + latitude + "; Longtitude : " + longtitude);
			} while (c.moveToNext());
		}
		
		c.close();
		
	}

	private Cursor readLocations() {
		return mDbHelper.getWritableDatabase()
				.query(DatabaseHelper.TABLE_NAME, DatabaseHelper.columns, null, new String[] {}, null, null, DatabaseHelper.ID);
	}

	private void enableBroadcastService(){
		
		ComponentName reciever = new ComponentName(this, AlarmReciever.class);
		
		PackageManager pm = getPackageManager();
		pm.setComponentEnabledSetting(reciever, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		
		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		mGPSCoordinateIntent = new Intent(this, AlarmReciever.class);
		mGPSCoordinatePendingintent = PendingIntent.getBroadcast(this, 0, mGPSCoordinateIntent, 0);
		
		mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + ALARM_DELAY, ALARM_DELAY, mGPSCoordinatePendingintent);
		
		Log.i(TAG, "Alarm service started, Broadcast service enabled.");
		
	}
	
	private void disableBroadcastService(){
		
		if (mAlarmManager != null && mGPSCoordinatePendingintent != null) {
			mAlarmManager.cancel(mGPSCoordinatePendingintent);
			Log.i(TAG, "Alarm service canceled.");
		}
		
		ComponentName reciever = new ComponentName(this, AlarmReciever.class);
		
		PackageManager pm = getPackageManager();
		pm.setComponentEnabledSetting(reciever, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
		Log.i(TAG, "Broadcast disabled.");
		
	}
	
}
