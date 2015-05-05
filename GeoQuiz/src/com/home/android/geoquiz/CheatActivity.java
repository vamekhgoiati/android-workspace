package com.home.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {
	
	public static final String EXTRA_ANSWER_IS_TRUE = "com.home.android.geoquiz.answer_is_true";
	public static final String EXTRA_ANSWER_SHOWN = "com.home.android.geoquiz.answer_shown";
	
	private boolean answerIsTrue;
	private Button mShowAnswerButton;
	private TextView mAnswerTextView;
	private boolean answerIsShown;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
		setAnswerShown(false);
		
		mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
		mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
		
		mShowAnswerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(answerIsTrue){
					mAnswerTextView.setText(R.string.true_button);
				} else {
					mAnswerTextView.setText(R.string.false_button);
				}
				
				setAnswerShown(true);

			}
		});
		
		if (savedInstanceState != null) {
			answerIsShown = savedInstanceState.getBoolean(EXTRA_ANSWER_SHOWN);
			setAnswerShown(answerIsShown);
		}
		
	}
	
	private void setAnswerShown(boolean answerShown){
		answerIsShown = answerShown;
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_SHOWN, answerIsShown);
		setResult(RESULT_OK, data);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(EXTRA_ANSWER_SHOWN, answerIsShown);
	}
	
	
	
}
