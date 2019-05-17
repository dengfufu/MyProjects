package com.dengfufu.ahaweather;

public class ApiUrl {
	String url = null;
	public static String city = null;
	static String ak = "UyE14f1rkIQgE8S4elV0xZTAViTgqE0k";//微信小程序申请的ak，只因这个才有天气api
	
	public static void setCity(String city) {
		ApiUrl.city = city;
	}
	
	public static String getUrl() {
		return "http://api.map.baidu.com/telematics/v3/weather?location="+ city
				+"&output=json&ak=" + ak;
	}
}

//	示例：http://api.map.baidu.com/telematics/v3/weather?location=长沙&output=json&ak=UyE14f1rkIQgE8S4elV0xZTAViTgqE0k
//	返回-3表示城市名不正确