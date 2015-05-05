package com.home.android.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class DateTimeChooser extends DialogFragment {
	
	public static String CHOOSER_TIME = "com.home.android.criminalIntent.time_chooser";
	public static String CHOOSER_DATE = "com.home.android.criminalIntent.date_chooser";
	
	private Button mDateButton;
	private Button mTimeButton;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_chooser, null);
		
		mDateButton = (Button) v.findViewById(R.id.chooser_date_button);
		mTimeButton = (Button) v.findViewById(R.id.chooser_time_button);
		
		mDateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendResult(Activity.RESULT_OK, CHOOSER_DATE);
			}
		});
		
		mTimeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendResult(Activity.RESULT_OK, CHOOSER_TIME);
			}
		});
				
		return new AlertDialog.Builder(getActivity())
		.setView(v)
		.setTitle(R.string.chooser_title)
		.create();
		
	}

	public void sendResult(int resultCode, String stringExtra){
		
		if(getTargetFragment() == null){
			return;
		}
		
		Intent i = new Intent();
		i.putExtra(stringExtra, true);
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	
		dismiss();
		
	}
	
}
