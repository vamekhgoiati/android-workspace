package com.home.android.geoquiz;

public class TrueFalse {

	private int mQuestion;
	private boolean mTrueQuestion;
	
	public TrueFalse(int mQuestion, boolean mTrueQuestion){
		this.mQuestion = mQuestion;
		this.mTrueQuestion = mTrueQuestion;
	}

	public int getQuestion() {
		return mQuestion;
	}

	public void setQuestion(int mQuestion) {
		this.mQuestion = mQuestion;
	}

	public boolean isTrueQuestion() {
		return mTrueQuestion;
	}

	public void setTrueQuestion(boolean mTrueQuestion) {
		this.mTrueQuestion = mTrueQuestion;
	}
	
}
