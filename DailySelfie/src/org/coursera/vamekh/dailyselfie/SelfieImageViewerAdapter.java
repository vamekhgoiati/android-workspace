package org.coursera.vamekh.dailyselfie;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SelfieImageViewerAdapter extends FragmentPagerAdapter {

	private List<Selfie> mSelfieList;
	private LayoutInflater mInflater;
	
	public SelfieImageViewerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public void setList(List<Selfie> selfieList) {
		mSelfieList = selfieList;
	}
	
	public void setInflater(LayoutInflater inflater) {
		mInflater = inflater;
	}
	
	@Override
	public int getCount() {
		return mSelfieList.size();
	}

	@Override
	public Fragment getItem(int position) {
		return new SelfieImageView(mSelfieList.get(position));
	}
	
	

}
