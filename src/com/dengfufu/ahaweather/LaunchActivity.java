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
		// ����LocationClient��

		LocationClientOption option = new LocationClientOption();
		option.setIsNeedAddress(true);
		// ��ѡ���Ƿ���Ҫ��ַ��Ϣ��Ĭ��Ϊ����Ҫ��������Ϊfalse
		// �����������Ҫ��õ�ǰ��ĵ�ַ��Ϣ���˴�����Ϊtrue

		mLocationClient.setLocOption(option);
		// mLocationClientΪ�ڶ�����ʼ������LocationClient����
		// �轫���úõ�LocationClientOption����ͨ��setLocOption�������ݸ�LocationClient����ʹ��
		// ����LocationClientOption�����ã��������ο���LocationClientOption�����ϸ˵��

		mLocationClient.registerLocationListener(myListener);
		// ע���������
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
					//Log.i("��������", jsonString);
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
				//��ת����һ��Activity
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
			// �˴���BDLocationΪ��λ�����Ϣ�࣬ͨ�����ĸ���get�����ɻ�ȡ��λ��ص�ȫ�����
			// ����ֻ�оٲ��ֻ�ȡ��ַ��صĽ����Ϣ
			// ��������Ϣ��ȡ˵�����������ο���BDLocation���е�˵��

			city = location.getCity(); // ��ȡ����
			if (city != null) {
				String[] temp = city.split("��");
				city = temp[0];
				ApiUrl.setCity(city);
				Toast.makeText(LaunchActivity.this, "��λ�ɹ��� ��ǰ������" + city, 0)
						.show();
				flag = true;
			} else
				Toast.makeText(LaunchActivity.this, "��λʧ�ܣ� �������磡", 0).show();
			mLocationClient.stop();
		}
	}

}

// AK = qzDdwSbediYfBQ70enrNwj1mFuaPL4Ko