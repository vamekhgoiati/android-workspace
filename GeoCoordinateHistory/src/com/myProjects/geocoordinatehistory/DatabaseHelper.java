package com.myProjects.geocoordinatehistory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "gps_locations";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String ID = "id";
	public static String[] columns = {ID, LATITUDE, LONGITUDE};
	
	private static final String CREATE_CMD = 
			"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
											+ LATITUDE + " REAL NOT NULL, "
											+ LONGITUDE + " REAL NOT NULL)";
	
	private static final int VERSION = 1;
	private static final String DB_NAME = "locations_db";
	
	private final Context mContext;
												
	
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_CMD);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	public void deleteDatabase(){
		mContext.deleteDatabase(DB_NAME);
	}

}
