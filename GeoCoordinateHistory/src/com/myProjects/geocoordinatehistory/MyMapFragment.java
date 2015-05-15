package com.myProjects.geocoordinatehistory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MyMapFragment extends MapFragment implements OnMapReadyCallback{

	private PolylineOptions mPolyLineOptions;
	private Polyline mPolyline;
	private GoogleMap map;
	
	public MyMapFragment(PolylineOptions polylineOptions) {
		mPolyLineOptions = polylineOptions;
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getMapAsync(this);
		return super.onCreateView(inflater, container, savedInstanceState);
	}



	@Override
	public void onMapReady(GoogleMap map) {
		map.addPolyline(mPolyLineOptions);
	}
	
	

}
