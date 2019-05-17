package com.dengfufu.imageloader;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		
		super.onCreate();
		//创建默认的ImageLoader配置参数
		ImageLoaderConfiguration configuration = ImageLoaderConfiguration
				.createDefault(this);
		 
		         //Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(configuration);
	}
	
}
