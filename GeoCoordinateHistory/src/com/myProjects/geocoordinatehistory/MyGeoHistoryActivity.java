package com.myProjects.geocoordinatehistory;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MyGeoHistoryActivity extends Activity {
	
	private static final Long ALARM_DELAY = 5 * 1000L;
	private AlarmManager mAlarmManager;
	private Intent mGPSCoordinateIntent;
	private PendingIntent mGPSCoordinatePendingintent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		mGPSCoordinateIntent = new Intent(this, AlarmReciever.class);
		mGPSCoordinatePendingintent = PendingIntent.getBroadcast(this, 0, mGPSCoordinateIntent, 0);
		
		mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + ALARM_DELAY, ALARM_DELAY, mGPSCoordinatePendingintent);
		
	}
}
