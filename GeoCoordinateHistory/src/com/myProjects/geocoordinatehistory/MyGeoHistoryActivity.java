package com.myProjects.geocoordinatehistory;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyGeoHistoryActivity extends Activity {
	
	private static final Long ALARM_DELAY = 10 * 1000L;
	private AlarmManager mAlarmManager;
	private Intent mGPSCoordinateIntent;
	private PendingIntent mGPSCoordinatePendingintent;
	private DatabaseHelper mDbHelper;
	private Button mStartButton;
	private Button mStopButton;
	private Button mDrawMapButton;
	private AlarmReciever mAlarmReciever;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mStartButton = (Button) findViewById(R.id.start_button);
		mStopButton = (Button) findViewById(R.id.stop_button);
		mDrawMapButton = (Button) findViewById(R.id.draw_map);
		
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
		
	}
	
	private void enableBroadcastService(){
		
		ComponentName reciever = new ComponentName(this, AlarmReciever.class);
		
		PackageManager pm = getPackageManager();
		pm.setComponentEnabledSetting(reciever, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		
		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		mGPSCoordinateIntent = new Intent(this, AlarmReciever.class);
		mGPSCoordinatePendingintent = PendingIntent.getBroadcast(this, 0, mGPSCoordinateIntent, 0);
		
		mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + ALARM_DELAY, ALARM_DELAY, mGPSCoordinatePendingintent);
		
	}
	
	private void disableBroadcastService(){
		
		ComponentName reciever = new ComponentName(this, AlarmReciever.class);
		
		PackageManager pm = getPackageManager();
		pm.setComponentEnabledSetting(reciever, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
		
	}
	
}
