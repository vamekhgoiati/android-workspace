package com.coursera.posa.json;

public class JsonWeather {

	public static final String sys_JSON = "sys";
	public static final String main_JSON = "main";
	public static final String wind_JSON = "wind";
	public static final String name_JSON = "name";
	public static final String sunrise_JSON = "sunrise";
	public static final String sunset_JSON = "sunset";
	public static final String speed_JSON = "speed";
	public static final String deg_JSON = "deg";
	public static final String humidity_JSON = "humidity";
	public static final String temp_JSON = "temp";

	private String mName;
	private double mSpeed;
	private double mDeg;
	private double mTemp;
	private long mHumidity;
	private long mSunrise;
	private long mSunset;

	public JsonWeather() {

	}

	public JsonWeather(String name, double speed, double deg, double temp,
			long humidity, long sunrise, long sunset) {
		this.mName = name;
		this.mSpeed = speed;
		this.mDeg = deg;
		this.mTemp = temp;
		this.mHumidity = humidity;
		this.mSunrise = sunrise;
		this.mSunset = sunset;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public double getSpeed() {
		return mSpeed;
	}

	public void setSpeed(double speed) {
		this.mSpeed = speed;
	}

	public double getDeg() {
		return mDeg;
	}

	public void setDeg(double deg) {
		this.mDeg = deg;
	}

	public double getTemp() {
		return mTemp;
	}

	public void setTemp(double temp) {
		this.mTemp = temp;
	}

	public long getHumidity() {
		return mHumidity;
	}

	public void setHumidity(long humidity) {
		this.mHumidity = humidity;
	}

	public long getSunrise() {
		return mSunrise;
	}

	public void setSunrise(long sunrise) {
		this.mSunrise = sunrise;
	}

	public long getSunset() {
		return mSunset;
	}

	public void setSunset(long sunset) {
		this.mSunset = sunset;
	}

}
