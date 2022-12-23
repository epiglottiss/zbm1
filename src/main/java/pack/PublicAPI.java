package pack;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PublicAPI {
	HttpURLConnection conn = null;
	Integer wifiCount = null;

	public void requestAllWiFi() {
		try {
			int startIdx = 1;
			int interval = 1000;
			do {
				requestWiFi(startIdx, startIdx+ interval-1);
				System.out.println(startIdx + "~" + (startIdx+interval-1) +" added.");
				startIdx +=interval;
			} while(startIdx < wifiCount);			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void requestWiFi(Integer reqStartIdx, Integer reqEndIdx) throws IOException {
		StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
		urlBuilder.append("/" +  URLEncoder.encode("4448524374657069383870435a7849","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
		urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
		urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
		urlBuilder.append("/" + URLEncoder.encode(reqStartIdx.toString(),"UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
		urlBuilder.append("/" + URLEncoder.encode(reqEndIdx.toString(),"UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
		
		URL url = new URL(urlBuilder.toString());
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
		BufferedReader rd;

		// 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				return;
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
				sb.append(line);
		}
		rd.close();
		conn.disconnect();
		
		String json = sb.toString();
		JsonElement element = JsonParser.parseString(json);
		JsonObject object= element.getAsJsonObject();
		JsonObject infoObject = object.get("TbPublicWifiInfo").getAsJsonObject();
		
		if(wifiCount == null) {
			wifiCount = infoObject.get("list_total_count").getAsInt();
		}
		
		JsonObject resultObject = infoObject.get("RESULT").getAsJsonObject();
		String resultCode= resultObject.get("CODE").getAsString();
		if(!resultCode.equals("INFO-000")) {
			System.out.println("Error result code : " + resultCode);
			return;
		}
		JsonArray wifiJsonArray = infoObject.get("row").getAsJsonArray();
		
		ArrayList<WiFi> wifiJavaArray = new ArrayList<WiFi>();
		for(int i=0; i<wifiJsonArray.size();i++) {
			Gson gson = new Gson();
			WiFi wifi = new WiFi();
			try {
				wifi = gson.fromJson(wifiJsonArray.get(i), WiFi.class);	
			}
			catch(Exception e) {
				e.printStackTrace();
			}
						
			wifiJavaArray.add(wifi);			
		}
		WiFiData wifiData = new WiFiData();
		wifiData.insertList(wifiJavaArray);
	}
}
