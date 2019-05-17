package com.dengfufu.ahaweather;

import java.io.IOException;

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
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.dengfufu.entity.ResultWrapper;
import com.google.gson.Gson;

public class LaunchActivity extends Activity {

	public LocationClient mLocationClient = null;
	private MyLocationListener myListener = new MyLocationListener();
	public String city;
	boolean flag ;
	Message message = Message.obtain();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);

		mLocationClient = new LocationClient(getApplicationContext());
		// 声明LocationClient类

		LocationClientOption option = new LocationClientOption();
		option.setIsNeedAddress(true);
		// 可选，是否需要地址信息，默认为不需要，即参数为false
		// 如果开发者需要获得当前点的地址信息，此处必须为true

		mLocationClient.setLocOption(option);
		// mLocationClient为第二步初始化过的LocationClient对象
		// 需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
		// 更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明

		mLocationClient.registerLocationListener(myListener);
		// 注册监听函数
		mLocationClient.start();
		
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while( flag == false ){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				sendHttpRequest();
			}

			private void sendHttpRequest() {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(ApiUrl.getUrl());
				HttpResponse response = null;
				String jsonString = null;
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
				else message.arg1 = 0;
				handler.sendMessage(message);
			}
			
		}).start();
	}
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			super.handleMessage(msg);
			if(msg.arg1 == 1){
				//跳转至下一个Activity
				Intent intent = new Intent(LaunchActivity.this, HomepageActivity.class);
				intent.putExtra("jsonString_data", (String)msg.obj);
				startActivity(intent);
				finish();
			}
			else
				finish();
		}
		
	};

	class MyLocationListener extends BDAbstractLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			// 此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
			// 以下只列举部分获取地址相关的结果信息
			// 更多结果信息获取说明，请参照类参考中BDLocation类中的说明

			city = location.getCity(); // 获取城市
			if (city != null) {
				String[] temp = city.split("市");
				city = temp[0];
				ApiUrl.setCity(city);
				Toast.makeText(LaunchActivity.this, "定位成功！ 当前城市是" + city, 0)
						.show();
				flag = true;
			} else
				Toast.makeText(LaunchActivity.this, "定位失败， 请检查网络！", 0).show();
			mLocationClient.stop();
		}
	}

}

// AK = qzDdwSbediYfBQ70enrNwj1mFuaPL4Ko