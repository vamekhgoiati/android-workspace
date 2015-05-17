package com.myProjects.geocoordinatehistory;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapActivity extends Activity implements OnMapReadyCallback{
	
	public static final String MAP_EXTRA = "map_extra";
	
	private PolylineOptions mPolylineOptions;
	private Polyline mPolyline;
	private GoogleMap mMap;
	private MapFragment mMapFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		mPolylineOptions = (PolylineOptions) getIntent().getParcelableExtra(MAP_EXTRA);
		
		mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_activity);
		mMapFragment.getMapAsync(this);
		
	}

	@Override
	public void onMapReady(GoogleMap map) {
		if (mMap == null) {
			mMap = map;
		}
		
		mPolyline = mMap.addPolyline(mPolylineOptions);
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mPolylineOptions.getPoints().get(0), 15));
		
	}

}
