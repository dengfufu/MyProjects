package com.dengfufu.adapter;

import java.util.ArrayList;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengfufu.ahaweather.R;
import com.dengfufu.entity.Weather_Data;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ListViewAdapter extends BaseAdapter {
	
	ArrayList<Weather_Data> weatherData = null;
	FragmentActivity fragmentActivity = null;

	public ListViewAdapter(FragmentActivity fragmentActivity, ArrayList<Weather_Data> weatherData) {
		// TODO Auto-generated constructor stub
		this.weatherData = weatherData;
		this.fragmentActivity = fragmentActivity;
	}

	@Override
	public int getCount() {

		return weatherData.size();
	}

	@Override
	public Object getItem(int position) {

		return weatherData.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		convertView = View.inflate(fragmentActivity, R.layout.item_listview, null);
		
		TextView zhouji = (TextView) convertView.findViewById(R.id.zhouji);
		ImageView dayPicture =(ImageView) convertView.findViewById(R.id.dayPicture);
		ImageView nightPicture =(ImageView) convertView.findViewById(R.id.nightPicture);
		TextView weather = (TextView) convertView.findViewById(R.id.weather);
		TextView temperature = (TextView) convertView.findViewById(R.id.temperature);
		TextView wind = (TextView) convertView.findViewById(R.id.wind);
		
		if(position == 0) zhouji.setText("今天");
		else zhouji.setText(weatherData.get(position).getDate());
		
		DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().build();
		ImageLoader.getInstance().displayImage(weatherData.get(position).getDayPictureUrl(), dayPicture,options);
		ImageLoader.getInstance().displayImage(weatherData.get(position).getNightPictureUrl(), nightPicture,options);
		
		weather.setText(weatherData.get(position).getWeather());
		temperature.setText(weatherData.get(position).getTemperature());
		wind.setText(weatherData.get(position).getWind());
		
		return convertView;
	}

	//使item不可点击
	@Override
	public boolean isEnabled(int position) {
		
		return false;
	}
}
