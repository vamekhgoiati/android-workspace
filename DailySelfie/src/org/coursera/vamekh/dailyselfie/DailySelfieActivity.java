package org.coursera.vamekh.dailyselfie;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;

public class DailySelfieActivity extends FragmentActivity {
	
	private static final String TAG = "org.coursera.vamekh.dailyselfie.DailySelfieActivity";
	private static final Long ALARM_DELAY = 60 * 2 * 1000L;
	
	private AlarmManager mAlarmManager;
	private Intent mNotificationIntent;
	private PendingIntent mNotificationPendingIntent;
	private SelfieListFragment mSelfieListFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daily_selfie);
		
		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		mNotificationIntent = new Intent(this, AlarmReciever.class);
		mNotificationPendingIntent = PendingIntent.getBroadcast(this, 0, mNotificationIntent, 0);
		
		//mAlarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + ALARM_DELAY, mNotificationPendingIntent);
		mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + ALARM_DELAY, ALARM_DELAY, mNotificationPendingIntent);
		
		mSelfieListFragment = new SelfieListFragment();
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.add(R.id.fragment_container, mSelfieListFragment);
		transaction.commit();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}
	
}
