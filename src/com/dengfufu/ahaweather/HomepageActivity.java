package com.dengfufu.ahaweather;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dengfufu.entity.ResultWrapper;
import com.dengfufu.fragment.CenterFragment;
import com.dengfufu.fragment.LifeIndexFragment;
import com.google.gson.Gson;

public class HomepageActivity extends FragmentActivity {

	ResultWrapper resultWrapper ;
	TextView weatherForecast;
	TextView lifeIndex;
	EditText editText;
	public static String currentCity = null;
	CenterFragment centerFragment = new CenterFragment();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage);
		
		Intent intent = getIntent();
		String jsonString = intent.getStringExtra("jsonString_data");
		Gson gson = new Gson();
		resultWrapper = gson.fromJson(jsonString, ResultWrapper.class);
		
		centerFragment = new CenterFragment();
		FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager. beginTransaction();
        transaction.replace(R.id.framelayout, centerFragment);
        transaction.commit();
        
        View view = findViewById(R.id.bottom_homepage);
        weatherForecast = (TextView) view.findViewById(R.id.weather_forecast);
        lifeIndex = (TextView) view.findViewById(R.id.lifeindex);
        weatherForecast.setTextColor(Color.WHITE);
        weatherForecast.setTextSize(25);
        lifeIndex.setTextColor(Color.GRAY);
        lifeIndex.setTextSize(20);
        
        lifeIndex.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				LifeIndexFragment lifeIndexFragment = new LifeIndexFragment();
				FragmentManager fragmentManager = getSupportFragmentManager();
		        FragmentTransaction transaction = fragmentManager. beginTransaction();
		        transaction.replace(R.id.framelayout, lifeIndexFragment);
		        transaction.commit();
		        lifeIndex.setTextColor(Color.WHITE);
		        lifeIndex.setTextSize(25);
		        weatherForecast.setTextColor(Color.GRAY);
		        weatherForecast.setTextSize(20);
			}
        	
        });
        
        weatherForecast.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				centerFragment = new CenterFragment();
				FragmentManager fragmentManager = getSupportFragmentManager();
		        FragmentTransaction transaction = fragmentManager. beginTransaction();
		        transaction.replace(R.id.framelayout, centerFragment);
		        transaction.commit();
		        weatherForecast.setTextColor(Color.WHITE);
		        weatherForecast.setTextSize(25);
		        lifeIndex.setTextColor(Color.GRAY);
		        lifeIndex.setTextSize(20);
			}
        	
        });
	}
	
	public void setResultWrapper(ResultWrapper resultWrapper) {
		this.resultWrapper = resultWrapper;
	}
	
	public ResultWrapper getResultWrapper() {
		return resultWrapper;
	}

	public void sendEditText(EditText editText) {
		this.editText = editText;
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				sendHttpRequest();
			}
		}).start();
		
	}
	
	private void sendHttpRequest() {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(ApiUrl.getUrl());
		HttpResponse response = null;
		String jsonString = null;
		Message message = Message.obtain();
		try {
			response = client.execute(get);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			message.arg1 = 0;
		}
		HttpEntity entity = response.getEntity();
		try {
			jsonString = EntityUtils.toString(entity);
			//Log.i("天气数据", jsonString);
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			message.arg1 = 0;
		}
		Gson gson = new Gson();
		ResultWrapper resultWrapper = gson.fromJson(jsonString, ResultWrapper.class);
		if(resultWrapper.getError()== 0) {
			message.arg1 = 1;
			message.obj = jsonString;
		}
		else if(resultWrapper.getError()== -3){//输错城市名
			message.arg1 = -3;
		}
		else message.arg1 = 0;
		handler.sendMessage(message);
	}
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			super.handleMessage(msg);
			if(msg.arg1 == 1){
				//跳转至下一个Activity
				String jsonString =  (String)msg.obj;
				Gson gson = new Gson();
				ResultWrapper wrapper = gson.fromJson(jsonString, ResultWrapper.class);
				setResultWrapper(wrapper);
				centerFragment.setPageData();
				
				weatherForecast.setFocusable(true);
				weatherForecast.setFocusableInTouchMode(true);
				weatherForecast.requestFocus();
				closeInputKeyBoard();
			}
			else if(msg.arg1 == -3){
				Toast.makeText(HomepageActivity.this, "请输入正确的城市名！", 0).show();
			}
			else
				Toast.makeText(HomepageActivity.this, "网络出错！", 0).show();
		}

	};
	
	private void closeInputKeyBoard() {
		
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
	}
}
