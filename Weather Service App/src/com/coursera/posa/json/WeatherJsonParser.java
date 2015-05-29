package com.coursera.posa.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.JsonReader;

public class WeatherJsonParser {

	JsonWeather weather;

	private final String TAG = this.getClass().getCanonicalName();

	public WeatherJsonParser() {
		weather = new JsonWeather();
	}

	public JsonWeather parseJsonStream(InputStream in) throws IOException {

		try (JsonReader reader = new JsonReader(new InputStreamReader(in,
				"UTF-8"))) {

			return parseWeatherServiceResults(reader);
		}

	}

	private JsonWeather parseWeatherServiceResults(JsonReader reader)
			throws IOException {

		reader.beginObject();

		try {

			while (reader.hasNext()) {
				String name = reader.nextName();
				switch (name) {
				case JsonWeather.sys_JSON:
					setSysValues(reader);
					break;
				case JsonWeather.main_JSON:
					setMainValues(reader);
					break;
				case JsonWeather.wind_JSON:
					setWindValues(reader);
					break;
				case JsonWeather.name_JSON:
					weather.setName(reader.nextString());
					break;
				default:
					reader.skipValue();
					break;
				}
			}

		} finally {
			reader.endObject();
		}

		return weather;
	}

	private void setWindValues(JsonReader reader) throws IOException {

		try {
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				switch (name) {
				case JsonWeather.speed_JSON:
					weather.setSpeed(reader.nextDouble());
					break;
				case JsonWeather.deg_JSON:
					weather.setDeg(reader.nextDouble());
					break;
				default:
					reader.skipValue();
					break;
				}
			}

		} finally {
			reader.endObject();
		}

	}

	private void setMainValues(JsonReader reader) throws IOException {
		
		try {
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				switch (name) {
				case JsonWeather.temp_JSON:
					weather.setTemp(reader.nextDouble());
					break;
				case JsonWeather.humidity_JSON:
					weather.setHumidity(reader.nextLong());
					break;
				default:
					reader.skipValue();
					break;
				}
			}

		} finally {
			reader.endObject();
		}
	}

	private void setSysValues(JsonReader reader) throws IOException {

		try {
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				switch (name) {
				case JsonWeather.sunrise_JSON:
					weather.setSunrise(reader.nextLong());
					break;
				case JsonWeather.sunset_JSON:
					weather.setSunset(reader.nextLong());
					break;
				default:
					reader.skipValue();
					break;
				}
			}

		} finally {
			reader.endObject();
		}
		
	}

}
