package com.activiti;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class test {

	/**
	 * @param args
	 */
	public static final String REST_URL = "http://kermit:kermit@121.43.109.179/activiti-rest/service";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(REST_URL + "/management/properties");
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode()  == 200){
				HttpEntity entity = httpResponse.getEntity();
				String response = EntityUtils.toString(entity, "utf-8");
				//parseJSONWithGSON(response);
				parseJSONWithJSONObject(response);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void parseJSONWithJSONObject(String jsonData) {
		// TODO Auto-generated method stub
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(jsonData);
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String id = jsonObject.getString("next.dbid");
				String version = jsonObject.getString("schema.version");
				String history = jsonObject.getString("schema.history");
				System.out.println(id + " " + version + " " + history);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
