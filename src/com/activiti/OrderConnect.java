package com.activiti;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.user.User;

public class OrderConnect{

	public static final String KERMIT_REST_URL = "http://kermit:kermit@121.43.109.179/activiti-rest/service/";
	public String REST_URL;
	
	public String name, tel, company, address, timestamp, score, isedit;
	public String engineerid, salerid;
	public User user;
	
	public OrderConnect(User userr){
		user = userr;
		engineerid = salerid = null;
		REST_URL = "http://" + user.name + ":" + user.passwd + "@121.43.109.179/activiti-rest/service/";
	}
	
	public String gettask() {
		// TODO Auto-generated method stub
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(REST_URL + "runtime/tasks");
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			String response = EntityUtils.toString(entity, "utf-8");
			JSONObject jsonObject = new JSONObject(response);
			if(httpResponse.getStatusLine().getStatusCode()  == 200){
				return jsonObject.getString("data");
			}else{
				Log.d("gettaskerror", response);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void getattri(String processInstanceId) {
		// TODO Auto-generated method stub
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(REST_URL + "runtime/process-instances/" + processInstanceId + "/variables");
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			String response = EntityUtils.toString(entity, "utf-8");
			JSONArray jsonArray = new JSONArray(response);
			Log.d("getattri", response);
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String jsonname = jsonObject.getString("name");
				if(jsonname.equals("Name")){
					name = jsonObject.getString("value");
				}else if(jsonname.equals("Company")){
					company = jsonObject.getString("value");
				}else if(jsonname.equals("Tel")){
					tel = jsonObject.getString("value");
				}else if(jsonname.equals("Address")){
					address = jsonObject.getString("value");
				}else if(jsonname.equals("Score")){
					score = jsonObject.getString("value");
				}else if(jsonname.equals("Timestamp")){
					timestamp = jsonObject.getString("value");
				}else if(jsonname.equals("Isedit")){
					isedit = jsonObject.getString("value");
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		HttpClient httpClient = new DefaultHttpClient();
		HttpDelete httpDelete = new HttpDelete(REST_URL + "runtime/tasks/" + id + "?cascadeHistory=true");
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpDelete);
			HttpEntity entity = httpResponse.getEntity();
			String response = EntityUtils.toString(entity, "utf-8");
			if(httpResponse.getStatusLine().getStatusCode()  != 204){
				Log.d("deleteerror", response);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update(String id, int num, String ...args) {
		// TODO Auto-generated method stub
		HttpClient httpClient = new DefaultHttpClient();
		HttpPut httpPut = new HttpPut(REST_URL + "runtime/tasks/" + id);
		JSONObject param = new JSONObject();
		JSONObject[] params = new JSONObject[num];
		for(int i = 0; i < num; i++) params[i] = new JSONObject();
		JSONArray parama = new JSONArray();
		try {
			for(int i = 0; i < num; i++){
				params[i].put("name", args[2 * i]);
				params[i].put("value", args[2 * i + 1]);
				params[i].put("scope", "global"); 
				parama.put(params[i]);
			}
			param.put("variables", parama);
			StringEntity se = new StringEntity(param.toString());
			se.setContentEncoding("UTF-8");
			se.setContentType("application/json");
			httpPut.setEntity(se);
			HttpResponse httpResponse = httpClient.execute(httpPut);
			HttpEntity entity = httpResponse.getEntity();
			String response = EntityUtils.toString(entity, "utf-8");
			if(httpResponse.getStatusLine().getStatusCode()  != 200){
				Log.d("updateerror", response);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void completetask(String id) {
		// TODO Auto-generated method stub
		HttpPost post = new HttpPost(REST_URL + "runtime/tasks/" + id);
		HttpClient httpclient = new DefaultHttpClient();
		try {
			JSONObject param = new JSONObject();
			param.put("action", "complete");
			param.put("assignee", "kermit");
			StringEntity se = new StringEntity(param.toString());
			se.setContentEncoding("UTF-8");
			se.setContentType("application/json");
			post.setEntity(se);
			HttpResponse httpResponse = httpclient.execute(post);
			HttpEntity entity = httpResponse.getEntity();
			String response = EntityUtils.toString(entity, "utf-8");
			if(httpResponse.getStatusLine().getStatusCode()  != 200){
				Log.d("completeerror", response);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}