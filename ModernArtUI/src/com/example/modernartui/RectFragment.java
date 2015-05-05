package com.example.modernartui;

import java.util.Random;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class RectFragment extends Fragment {

	private SeekBar mColorSeekBar;
	private View mRedRect;
	private View mBlueRect;
	private View mPurpleRect;
	private View mBlackRect;
	private int mRed;
	private int mBlack;
	private int mBlue;
	private int mPurple;
	 
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.rectangle_fragment, null);
		
		mColorSeekBar = (SeekBar) v.findViewById(R.id.color_seekBar);
		mRedRect = (View) v.findViewById(R.id.rect_red);
		mBlueRect = (View) v.findViewById(R.id.rect_blue);
		mPurpleRect = (View) v.findViewById(R.id.rect_purple);
		mBlackRect = (View) v.findViewById(R.id.rect_black);
		mRed = getResources().getColor(R.color.red);
		mBlack = getResources().getColor(R.color.black);
		mBlue = getResources().getColor(R.color.blue);
		mPurple = getResources().getColor(R.color.purple);
		
		mColorSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				updateColors(progress);
				
			}
		});
		
		updateColors(mColorSeekBar.getProgress());
		
		return v;
	}

	private void updateColors(int progress) {
		
		mRedRect.setBackgroundColor(mRed + progress);
		mBlueRect.setBackgroundColor(mBlue + progress);
		mBlackRect.setBackgroundColor(mBlack + progress);
		mPurpleRect.setBackgroundColor(mPurple + progress);
 		
	}
	
	
}
