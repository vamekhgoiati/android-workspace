package com.myProjects.geocoordinatehistory;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ButtonsViewFragment extends Fragment {
	
	private static final String TAG = ButtonsViewFragment.class.getSimpleName();

	private static final Long ALARM_DELAY = 10 * 1000L;
	private AlarmManager mAlarmManager;
	private Intent mGPSCoordinateIntent;
	private PendingIntent mGPSCoordinatePendingintent;
	private DatabaseHelper mDbHelper;
	private Button mStartButton;
	private Button mStopButton;
	private Button mDrawMapButton;
	private Button mClearButton;
	private Context mContext;
	
	private GoogleMap map;
	private Polyline mPolyline;
	private PolylineOptions mPolylineOptions;
	private MapFragment mMapFragment;
	
	public ButtonsViewFragment(Context context) {
		mContext = context;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View buttonView = inflater.inflate(R.layout.buttons_view, null);
		
		mDbHelper = new DatabaseHelper(mContext);
		
		mStartButton = (Button) buttonView.findViewById(R.id.start_button);
		mStopButton = (Button) buttonView.findViewById(R.id.stop_button);
		mDrawMapButton = (Button) buttonView.findViewById(R.id.draw_map);
		mClearButton = (Button) buttonView.findViewById(R.id.clear_button);
		
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
		
		return buttonView;
	}
	
	private void clearDbContent() {
		mDbHelper.getWritableDatabase().delete(DatabaseHelper.TABLE_NAME, null, null);
	}

	private void drawRoute() {
		
		mPolylineOptions = new PolylineOptions();
		
		Cursor c = readLocations();
		double latitude;
		double longitude;
		
		if (c.moveToFirst()) {
			do {
				latitude = c.getDouble(c.getColumnIndex(DatabaseHelper.LATITUDE));
				longitude = c.getDouble(c.getColumnIndex(DatabaseHelper.LONGITUDE));
				mPolylineOptions.add(new LatLng(latitude, longitude));
				Log.d(TAG, "Latitude : " + latitude + "; Longtitude : " + longitude);
			} while (c.moveToNext());
		}
		
		c.close();
		
		mMapFragment = new MyMapFragment(mPolylineOptions);
		//map = mMapFragment.getMap();
		FragmentTransaction fragmentTransaction =
		         getFragmentManager().beginTransaction();
		 fragmentTransaction.replace(R.id.fragment_container, mMapFragment);
		 fragmentTransaction.addToBackStack(null).commit();
		
	}

	private Cursor readLocations() {
		return mDbHelper.getWritableDatabase()
				.query(DatabaseHelper.TABLE_NAME, DatabaseHelper.columns, null, new String[] {}, null, null, DatabaseHelper.ID);
	}

	private void enableBroadcastService(){
		
		ComponentName reciever = new ComponentName(mContext, AlarmReciever.class);
		
		PackageManager pm = mContext.getPackageManager();
		pm.setComponentEnabledSetting(reciever, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		
		mAlarmManager = (AlarmManager) mContext.getSystemService(Activity.ALARM_SERVICE);
		mGPSCoordinateIntent = new Intent(mContext, AlarmReciever.class);
		mGPSCoordinatePendingintent = PendingIntent.getBroadcast(mContext, 0, mGPSCoordinateIntent, 0);
		
		mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + ALARM_DELAY, ALARM_DELAY, mGPSCoordinatePendingintent);
		
		Log.i(TAG, "Alarm service started, Broadcast service enabled.");
		
	}
	
	private void disableBroadcastService(){
		
		if (mAlarmManager != null && mGPSCoordinatePendingintent != null) {
			mAlarmManager.cancel(mGPSCoordinatePendingintent);
			Log.i(TAG, "Alarm service canceled.");
		}
		
		ComponentName reciever = new ComponentName(mContext, AlarmReciever.class);
		
		PackageManager pm = mContext.getPackageManager();
		pm.setComponentEnabledSetting(reciever, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
		Log.i(TAG, "Broadcast disabled.");
		
	}

	
	
}
