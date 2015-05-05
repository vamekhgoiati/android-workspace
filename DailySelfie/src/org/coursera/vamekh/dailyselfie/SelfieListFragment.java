package org.coursera.vamekh.dailyselfie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SelfieListFragment extends ListFragment {

	public static final String SELFIE_LIST = "selfieList";
	public static final String SELFIE_POSITION = "selfiePosition";
	
	private static final String TAG = "org.coursera.vamekh.dailyselfie.SelfieListFragment";
	private static final String SELFIE_ALBUM = "/Daily Selfies";
	
	static final int REQUEST_IMAGE_CAPTURE = 1;
	
	private SelfieViewAdapter mAdapter;
	private SelfieImageViewerAdapter mImageViewAdapter;
	private ViewPager mViewPager;
	
	
	private String mCurrentPhotoPath;
	private Context mContext;
	private SelfieImageViewerFragment mSelfieFragment;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
		mContext = getActivity().getApplicationContext();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		
		mAdapter = new SelfieViewAdapter(mContext);
		setListAdapter(mAdapter);
		
		try {
			if(isExternalMemoryAvailable()){
				createAlbumDir(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
			} else {
				createAlbumDir(mContext.getFilesDir());
			}
			fetchPhotos();
		} catch (IOException ex) {
			Log.e(TAG, "Selfie album directory wasn't created", ex);
		}
		
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);	
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.camera_menu_item:
				createImageFileAndOpenCamera();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		mAdapter.removeAllViews();
		fetchPhotos();
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void createImageFileAndOpenCamera() {
		Intent takeSelfieIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takeSelfieIntent.resolveActivity(mContext.getPackageManager()) != null) {
	        // Create the File where the photo should go
	        File photoFile = null;
	        try {
	            photoFile = createImageFile();
	        } catch (IOException ex) {
	            Log.e(TAG, "Error occured while creating file", ex);
	        }
	        // Continue only if the File was successfully created
	        if (photoFile != null) {
	        	takeSelfieIntent.putExtra(MediaStore.EXTRA_OUTPUT,
	                    Uri.fromFile(photoFile));
	            startActivityForResult(takeSelfieIntent, REQUEST_IMAGE_CAPTURE);
	        }
	    }
		
	}

	private File createImageFile() throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = "daily_selfie_" + timeStamp;
	    
	    File image = new File(mCurrentPhotoPath + "/" + imageFileName + ".jpg");

	    return image;
		
	}
	
	private void fetchPhotos() {
		File storageDir = new File(mCurrentPhotoPath);
		
		for(String selfie : storageDir.list()) {
			try {
				mAdapter.add(createSelfie(selfie));
			} catch (FileNotFoundException ex) {
				Log.e(TAG, "Error creating selfie", ex);
			}
			
		}
		
	}
	
	private void createAlbumDir(File directory) throws IOException{
		File selfieAlbumDir = new File(directory, SELFIE_ALBUM);
		if(!selfieAlbumDir.exists()){
			if(!selfieAlbumDir.mkdirs()){
				throw new IOException();
			}
		}
		
		mCurrentPhotoPath = selfieAlbumDir.getAbsolutePath();
	}
	
	private boolean isExternalMemoryAvailable(){
		String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
		return false;
	}
	
	private Selfie createSelfie(String filename) throws FileNotFoundException{
	    // Get the dimensions of the View
	    int targetW = 120;
	    int targetH = 100;

	    // Get the dimensions of the bitmap
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(mCurrentPhotoPath + "/" + filename, bmOptions);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;

	    // Determine how much to scale down the image
	    int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

	    // Decode the image file into a Bitmap sized to fill the View
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;

	    Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath + "/" + filename, bmOptions);
	    return new Selfie(filename, bitmap);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		mSelfieFragment = new SelfieImageViewerFragment(position, mAdapter.getList());
		
		FragmentManager fm = getActivity().getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.replace(R.id.fragment_container, mSelfieFragment);
		transaction.addToBackStack(null);
		transaction.commit();
		
		fm.executePendingTransactions();
	
	}
	
	
	
	

}
