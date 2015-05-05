package org.coursera.vamekh.dailyselfie;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SelfieImageViewerFragment extends Fragment{
	
	private ViewPager mSelfieView;
	private SelfieImageViewerAdapter mViewAdapter;
	private List<Selfie> mSelfieList;
	private int initialPosition;

	public SelfieImageViewerFragment(int position, List<Selfie> selfieList) {
		initialPosition = position;
		mSelfieList = selfieList;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setRetainInstance(true);
		super.onCreate(savedInstanceState);
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		mViewAdapter = new SelfieImageViewerAdapter(getActivity().getSupportFragmentManager());
		mViewAdapter.setList(mSelfieList);
		mViewAdapter.setInflater(getActivity().getLayoutInflater());
		
		View view = inflater.inflate(R.layout.pager_fragment, null);
		
		mSelfieView = (ViewPager) view.findViewById(R.id.selfie_pager);
		mSelfieView.setAdapter(mViewAdapter);
		
		//mViewAdapter.instantiateItem(mSelfieView, initialPosition);
		
		mSelfieView.setCurrentItem(initialPosition);
		
		return view;
	}
	
	
	
}
