package com.dengfufu.fragment;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dengfufu.adapter.ListViewAdapter;
import com.dengfufu.ahaweather.ApiUrl;
import com.dengfufu.ahaweather.HomepageActivity;
import com.dengfufu.ahaweather.LaunchActivity;
import com.dengfufu.ahaweather.R;
import com.dengfufu.entity.ResultWrapper;
import com.dengfufu.entity.Results;
import com.dengfufu.entity.Weather_Data;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CenterFragment extends Fragment {

	HomepageActivity homepageActivity ;
	ImageView weatherImage;
	TextView temperature ;
	TextView currentCity;
	TextView pm25;
	ListView listView ;
	EditText editText;
	Button button;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.centerfragment_homepage, container, false);
		
		weatherImage = (ImageView) view.findViewById(R.id.weatherImage);
		temperature = (TextView) view.findViewById(R.id.temperature);
		currentCity = (TextView) view.findViewById(R.id.currentCity);
		pm25 = (TextView) view.findViewById(R.id.pm25);
		editText = (EditText) view.findViewById(R.id.editText);
		button = (Button) view.findViewById(R.id.button);
		listView = (ListView) view.findViewById(R.id.listView);
		
		setPageData();
		
		return view;
	}

	
	
	@Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
		homepageActivity = (HomepageActivity) activity;
	}

	public void setPageData() {
		
		Results results = homepageActivity.getResultWrapper().getResults().get(0);
		
		String imageUrl = results.getWeather_data().get(0).getDayPictureUrl();
		
		//显示图片的配置
        /*DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory()
                .cacheOnDisc()
                .build();

        ImageLoader.getInstance().displayImage(imageUrl, weatherImage, options);*/
		ImageLoader.getInstance().displayImage(imageUrl, weatherImage);
		
		String temp = results.getWeather_data().get(0).getDate();
		String currentTemperature = temp.substring(14, 17);
		temperature.setText(currentTemperature);
		
		currentCity.setText(results.getCurrentCity());
		pm25.setText("PM2.5: "+results.getPm25());
		
		button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				String[] temp = editText.getText().toString().trim().split("市");
				String seachCity = temp[0];
				if("".equals(seachCity))
					Toast.makeText(getActivity(), "请输入城市名", 0).show();
				else{
					editText.setText("");
					ApiUrl.city = seachCity;
					homepageActivity.sendEditText(editText);
				}
			}
		});
		
		ArrayList<Weather_Data> weatherData = results.getWeather_data();
		ListViewAdapter listViewAdapter = new ListViewAdapter(getActivity(),weatherData);
		listView.setAdapter(listViewAdapter);
	}
	
}
