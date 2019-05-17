package com.dengfufu.entity;

import java.util.ArrayList;

public class Results {
	String currentCity;
	String pm25;
	ArrayList<Index> index;
	ArrayList<Weather_Data> weather_data;
	public String getCurrentCity() {
		return currentCity;
	}
	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}
	public String getPm25() {
		return pm25;
	}
	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}
	public ArrayList<Index> getIndex() {
		return index;
	}
	public void setIndex(ArrayList<Index> index) {
		this.index = index;
	}
	public ArrayList<Weather_Data> getWeather_data() {
		return weather_data;
	}
	public void setWeather_data(ArrayList<Weather_Data> weather_data) {
		this.weather_data = weather_data;
	}
	
	
}
