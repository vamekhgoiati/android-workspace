package org.coursera.vamekh.dailyselfie;

import android.graphics.Bitmap;

public class Selfie {
	
	private String mFilename;
	private Bitmap mSelfieBitmap;
	
	public Selfie() { }
	
	public Selfie(String filename, Bitmap selfieBitmap) {
		mFilename = filename;
		mSelfieBitmap = selfieBitmap;
	}

	public String getFilename() {
		return mFilename;
	}

	public void setFilename(String filename) {
		mFilename = filename;
	}

	public Bitmap getSelfieBitmap() {
		return mSelfieBitmap;
	}

	public void setSelfieBitmap(Bitmap selfieBitmap) {
		mSelfieBitmap = selfieBitmap;
	}
		
}
