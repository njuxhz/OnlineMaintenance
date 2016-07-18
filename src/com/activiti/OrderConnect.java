package com.activiti;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.user.User;

public class OrderConnect{

	public static final int DELIVER = 1;
	public static final int ENGINEER = 2;
	public static final int SALER = 3;
	public static final int ADMIN = 4;
	
	public static final String KERMIT_REST_URL = "http://121.43.109.179/activiti-rest/service/";
	public String REST_URL;
	
	public String name, tel, company, address, timestamp, score, isedit, series, feedback, ondoor;
	public String engineerid, salerid, photourl1, photourl2, photourl3, picindex;
	public String isdeliver, isdebug, isondoor, iswarehouse, installid, warehouseid;
	public String isaccepted;
	public User user;
	
	public OrderConnect(User userr){
		user = userr;
	}
	
	public OrderConnect() {
		// TODO Auto-generated constructor stub
	}

	public JSONObject gettask() {
		// TODO Auto-generated method stub
		REST_URL = "http://121.43.109.179/activiti-rest/service/";
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user.id, user.passwd);
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpClient httpClient = new DefaultHttpClient();
		((DefaultHttpClient)httpClient).setCredentialsProvider(provider);
		HttpGet httpGet = new HttpGet(REST_URL + "runtime/tasks?sort=createTime&order=asc");
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			String response = EntityUtils.toString(entity, "utf-8");
			JSONObject jsonObject = new JSONObject(response);
			if(httpResponse.getStatusLine().getStatusCode()  == 200){
				return jsonObject;
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
		REST_URL = "http://121.43.109.179/activiti-rest/service/";
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user.id, user.passwd);
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpClient httpClient = new DefaultHttpClient();
		((DefaultHttpClient)httpClient).setCredentialsProvider(provider);
		HttpGet httpGet = new HttpGet(REST_URL + "runtime/process-instances/" + processInstanceId + "/variables");
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			String response = EntityUtils.toString(entity, "utf-8");
			JSONArray jsonArray = new JSONArray(response);
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
				}else if(jsonname.equals("Engineerid")){
					engineerid = jsonObject.getString("value");
				}else if(jsonname.equals("Salerid")){
					salerid = jsonObject.getString("value");
				}else if(jsonname.equals("Series")){
					series = jsonObject.getString("value");
				}else if(jsonname.equals("Feedback")){
					feedback = jsonObject.getString("value");
				}else if(jsonname.equals("Ondoor")){
					ondoor = jsonObject.getString("value");
				}else if(jsonname.equals("Photourl1")){
					photourl1 = jsonObject.getString("value");
				}else if(jsonname.equals("Photourl2")){
					photourl2 = jsonObject.getString("value");
				}else if(jsonname.equals("Photourl3")){
					photourl3 = jsonObject.getString("value");
				}else if(jsonname.equals("Picindex")){
					picindex = jsonObject.getString("value");
				}else if(jsonname.equals("Isdeliver")){
					isdeliver = jsonObject.getString("value");
				}else if(jsonname.equals("Isdebug")){
					isdebug = jsonObject.getString("value");
				}else if(jsonname.equals("Isondoor")){
					isondoor = jsonObject.getString("value");
				}else if(jsonname.equals("Iswarehouse")){
					iswarehouse = jsonObject.getString("value");
				}else if(jsonname.equals("Installid")){
					installid = jsonObject.getString("value");
				}else if(jsonname.equals("Warehouseid")){
					warehouseid = jsonObject.getString("value");
				}else if(jsonname.equals("Isaccepted")){
					isaccepted = jsonObject.getString("value");
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

	public void update(String id, int num, String ...args) {
		// TODO Auto-generated method stub
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user.id, user.passwd);
		provider.setCredentials(AuthScope.ANY, credentials);
		for(int i = 0; i < num; i++){
			HttpClient httpClient = new DefaultHttpClient();
			((DefaultHttpClient)httpClient).setCredentialsProvider(provider);
			String REST_URL = "http://121.43.109.179/activiti-rest/service/runtime/process-instances/" + id + "/variables/" + args[i * 2];
			HttpPut httpPut = new HttpPut(REST_URL);
			JSONObject param = new JSONObject();
			try {
				param.put("name", args[i * 2]);
				param.put("type", "string");
				param.put("value", args[i * 2 + 1]);
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
	}

	public void deletetask(String id){
		// TODO Auto-generated method stub
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user.id, user.passwd);
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpClient httpClient = new DefaultHttpClient();
		((DefaultHttpClient)httpClient).setCredentialsProvider(provider);
		JSONObject param = new JSONObject();
		JSONObject params = new JSONObject();
		JSONArray parama = new JSONArray();
		try {
			param.put("action", "complete");
			param.put("assignee", user.id);
			params.put("name", "Isdel"); params.put("value", "1"); params.put("scope", "local"); parama.put(params);
			param.put("variables", parama);
			StringEntity se = new StringEntity(param.toString());
			HttpPost post = new HttpPost("http://121.43.109.179/activiti-rest/service/runtime/tasks/" + id);
			se.setContentEncoding("UTF-8");
			se.setContentType("application/json");
			post.setEntity(se);
			HttpResponse httpResponse = httpClient.execute(post);
			System.out.println(httpResponse.getStatusLine().getStatusCode());
			HttpEntity entity = httpResponse.getEntity();
			String response = EntityUtils.toString(entity, "utf-8");
			//parseJSONWithGSON(response);
			System.out.println(response);
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
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user.id, user.passwd);
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpClient httpClient = new DefaultHttpClient();
		((DefaultHttpClient)httpClient).setCredentialsProvider(provider);
		JSONObject param = new JSONObject();
		try {
			param.put("action", "complete");
			param.put("assignee", user.id);
			StringEntity se = new StringEntity(param.toString());
			HttpPost post = new HttpPost("http://121.43.109.179/activiti-rest/service/runtime/tasks/" + id);
			se.setContentEncoding("UTF-8");
			se.setContentType("application/json");
			post.setEntity(se);
			HttpResponse httpResponse = httpClient.execute(post);
			System.out.println(httpResponse.getStatusLine().getStatusCode());
			HttpEntity entity = httpResponse.getEntity();
			String response = EntityUtils.toString(entity, "utf-8");
			//parseJSONWithGSON(response);
			System.out.println(response);
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

	public String findnewid(String processid) {
		// TODO Auto-generated method stub
		REST_URL = "http://121.43.109.179/activiti-rest/service/";
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user.id, user.passwd);
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpClient httpClient = new DefaultHttpClient();
		((DefaultHttpClient)httpClient).setCredentialsProvider(provider);
		HttpGet httpGet = new HttpGet(REST_URL + "runtime/tasks");
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			String response = EntityUtils.toString(entity, "utf-8");
			JSONObject jsonObjectdata = new JSONObject(response);
			if(httpResponse.getStatusLine().getStatusCode()  == 200){
				JSONArray jsonArray = jsonObjectdata.getJSONArray("data");
				for(int i = 0; i < jsonArray.length(); i++){
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String compare = jsonObject.getString("processInstanceId");
					if(compare.equals(processid)){
						return jsonObject.getString("id");
					}
				}
			}else{
				Log.d("findnewiderror", response);
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

	public String uploadimage(String imagePath, String id, String processid) {
		// TODO Auto-generated method stub
		FileBody fileBody = new FileBody(new File(imagePath));
		REST_URL = "http://121.43.109.179/activiti-rest/service/runtime/tasks/" + id + "/attachments";
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user.id, user.passwd);
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpClient httpClient = new DefaultHttpClient();
		((DefaultHttpClient)httpClient).setCredentialsProvider(provider);
		HttpPost httpPost = new HttpPost(REST_URL);
		MultipartEntity reqentity = new MultipartEntity();    
		try {
			reqentity.addPart("file", fileBody);
	        reqentity.addPart("name", new StringBody(processid + imagePath));
	        httpPost.setEntity(reqentity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			String response = EntityUtils.toString(entity, "utf-8");
			if(httpResponse.getStatusLine().getStatusCode() != 201){
				Log.d("imageuploaderror", response);
				return null;
			}else{
				JSONObject jsonObjectdata = new JSONObject(response);
				return jsonObjectdata.getString("url");
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

	public void createorder(int num, String ...args) {
		// TODO Auto-generated method stub
		REST_URL = "http://121.43.109.179/activiti-rest/service/runtime/process-instances";
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user.id, user.passwd);
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpClient httpClient = new DefaultHttpClient();
		((DefaultHttpClient)httpClient).setCredentialsProvider(provider);
		HttpPost httpPost = new HttpPost(REST_URL);
		HttpResponse httpResponse;
		JSONObject param = new JSONObject();
		JSONObject[] params = new JSONObject[num];
		for(int i = 0; i < num; i++) params[i] = new JSONObject();
		JSONArray parama = new JSONArray();
		try {
			for(int i = 0; i < num; i++){
				params[i].put("name", args[2 * i]);
				params[i].put("value", args[2 * i + 1]);
				parama.put(params[i]);
			}
			JSONObject paramtime = new JSONObject();
			paramtime.put("name", "Timestamp");
			paramtime.put("value", "" + System.currentTimeMillis());
			parama.put(paramtime);
			param.put("variables", parama);
			param.put("processDefinitionId", "process:1:3766");
			StringEntity se = new StringEntity(param.toString());
			se.setContentEncoding("UTF-8");
			se.setContentType("application/json");
			httpPost.setEntity(se);
			httpResponse = httpClient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			String response = EntityUtils.toString(entity, "utf-8");
			if(httpResponse.getStatusLine().getStatusCode()  != 200){
				Log.d("createordererror", response);
			}else{
				//JSONObject jsonObjectdata = new JSONObject(response);
				//return jsonObjectdata.getString("id");
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
		//return null;
	}

	public Bitmap getfile(String url) {
		// TODO Auto-generated method stub
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("kermit", "kermit");
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpClient httpClient = new DefaultHttpClient();
		((DefaultHttpClient)httpClient).setCredentialsProvider(provider);
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode()  == 200){
				HttpEntity entity = httpResponse.getEntity();
				Bitmap bitmap = null;
				InputStream is = entity.getContent();
		        bitmap = BitmapFactory.decodeStream(is);
		        is.close();
				return bitmap;
			}else{
				HttpEntity entity = httpResponse.getEntity();
				String response = EntityUtils.toString(entity, "utf-8");
				Log.d("getfileerror", response);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public int calscore(int monthmode) {
		// TODO Auto-generated method stub
		String cmpmonth = null;
		int totalscore = 0;
		if(monthmode < 10) cmpmonth = "0" + monthmode;
		else cmpmonth = "" + monthmode;
		REST_URL = "http://121.43.109.179/activiti-rest/service/";
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user.id, user.passwd);
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpClient httpClient = new DefaultHttpClient();
		((DefaultHttpClient)httpClient).setCredentialsProvider(provider);
		HttpGet httpGet = new HttpGet(REST_URL + "runtime/tasks?sort=createTime&order=asc");
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			String response = EntityUtils.toString(entity, "utf-8");
			if(httpResponse.getStatusLine().getStatusCode()  == 200){
				JSONObject jsonObjectdata = new JSONObject(response);
				JSONArray jsonArray = jsonObjectdata.getJSONArray("data");
				for(int i = 0; i < jsonArray.length(); i++){
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String cmpname = jsonObject.getString("name");
					if(cmpname.equalsIgnoreCase("CheckedOrder")){
						String ordertime = jsonObject.getString("createTime");
						String[] sourceStrArray = ordertime.split("-");
						if(cmpmonth.equals(sourceStrArray[1])){
							String processInstanceId = jsonObject.getString("processInstanceId");
							OrderConnect ordercnt = new OrderConnect(user);
							ordercnt.getattri(processInstanceId);
							if((ordercnt.engineerid.equals(user.id)) || (ordercnt.salerid.equals(user.id))){
								totalscore = totalscore + Integer.parseInt(ordercnt.score);
							}
						}
					}
				}
				return totalscore;
			}else{
				Log.d("calscoreerror", response);
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
		return 0;
	}
}