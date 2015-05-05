package com.example.hellomoon;

import java.net.URI;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class HelloMoonVideoFragment extends Fragment {
	
	private VideoView mVideoPlayer;
	private MediaController mMediaController;
	private SurfaceView mSurfaceView;
	private int mPosition = 0;
	
	private Button mPlayButton;
	private Button mStopButton;
	private Button mPauseButton;

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_hello_moon, container, false);
		
		mMediaController = new MediaController(getActivity().getApplicationContext());
		
		mPlayButton = (Button) v.findViewById(R.id.hellomoon_playButton);
		mStopButton = (Button) v.findViewById(R.id.hellomoon_stopButton);
		mPauseButton = (Button) v.findViewById(R.id.hellomoon_pauseButton);
		mVideoPlayer = (VideoView) v.findViewById(R.id.hello_moon_VideoView);
		mVideoPlayer.setMediaController(mMediaController);
		Uri videoUri = Uri.parse("android.resource://com.example.hellomoon/raw/apollo_17_stroll");
		mVideoPlayer.setVideoURI(videoUri);
		mVideoPlayer.setOnPreparedListener(new OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				mVideoPlayer.seekTo(mPosition);
				if(mPosition == 0){
					mVideoPlayer.start();
				} else {
					mVideoPlayer.pause();
				}
			}
		});
		
		mPlayButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mVideoPlayer.seekTo(mPosition);
				mVideoPlayer.start();
			}
		});
		
		mStopButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mVideoPlayer.pause();
				mPosition = 0;
			}
		});
		
		mPauseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPosition = mVideoPlayer.getCurrentPosition();
				mVideoPlayer.pause();
			}
		});
		
		return v;
		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mVideoPlayer.stopPlayback();
	}
	
}
