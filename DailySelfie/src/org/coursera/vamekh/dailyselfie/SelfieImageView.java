package org.coursera.vamekh.dailyselfie;

import android.R.fraction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class SelfieImageView extends Fragment {
	
	private Selfie mSelfie;
	private ImageView mImageView;
	
	public SelfieImageView(Selfie selfie){
		mSelfie = selfie;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = (View)inflater.inflate(R.layout.selfie_view_large, null);
		mImageView = (ImageView) v.findViewById(R.id.selfie_view_large);
		mImageView.setImageBitmap(mSelfie.getSelfieBitmap());
		
		return v;
	}
	
	

}
