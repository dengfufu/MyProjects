package com.dengfufu.ahaweather;

public class ApiUrl {
	String url = null;
	public static String city = null;
	static String ak = "UyE14f1rkIQgE8S4elV0xZTAViTgqE0k";//΢��С���������ak��ֻ�������������api
	
	public static void setCity(String city) {
		ApiUrl.city = city;
	}
	
	public static String getUrl() {
		return "http://api.map.baidu.com/telematics/v3/weather?location="+ city
				+"&output=json&ak=" + ak;
	}
}

//	ʾ����http://api.map.baidu.com/telematics/v3/weather?location=��ɳ&output=json&ak=UyE14f1rkIQgE8S4elV0xZTAViTgqE0k
//	����-3��ʾ����������ȷ