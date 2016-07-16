package com.activiti;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import com.user.User;

public class UserConnect {

	public static final int DELIVER = 1;
	public static final int ENGINEER = 2;
	public static final int SALER = 3;
	public static final int ADMIN = 4;
	
	public static final String KERMIT_REST_URL = "http://121.43.109.179/activiti-rest/service/";
	public String REST_URL;
	
	public User auth(String username, String userpasswd) {
		// TODO Auto-generated method stub
		REST_URL = KERMIT_REST_URL + "identity/users/" + username;
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("kermit", "kermit");
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpClient httpClient = new DefaultHttpClient();
		((DefaultHttpClient)httpClient).setCredentialsProvider(provider);
		HttpGet httpGet = new HttpGet(REST_URL);
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			String response = EntityUtils.toString(entity, "utf-8");
			JSONObject jsonObjectdata = new JSONObject(response);
			if(httpResponse.getStatusLine().getStatusCode()  == 200){
				//JSONArray jsonArray = jsonObjectdata.getJSONArray("data");
				String passwd, mode;
				passwd = jsonObjectdata.getString("firstName");
				mode = jsonObjectdata.getString("lastName");
				if(passwd.equals(userpasswd)){
					return new User(username, Integer.parseInt(mode), userpasswd);
				}
			}else{
				Log.d("userautherror", response);
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

	public boolean modifier(User user, String passwd) {
		// TODO Auto-generated method stub
		if(edit(user, "" + user.mode, passwd)) return true;
		return false;
	}

	public boolean edit(User user, String mode, String passwd) {
		// TODO Auto-generated method stub
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user.id, user.passwd);
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpClient httpClient = new DefaultHttpClient();
		((DefaultHttpClient)httpClient).setCredentialsProvider(provider);
		String REST_URL = "http://121.43.109.179/activiti-rest/service/identity/users/" + user.id;
		HttpPut httpPut = new HttpPut(REST_URL);
		JSONObject param = new JSONObject();
		try {
			param.put("firstName", passwd);
			param.put("lastName", mode);
			param.put("email", "*");
			param.put("password", passwd);
			StringEntity se = new StringEntity(param.toString());
			se.setContentEncoding("UTF-8");
			se.setContentType("application/json");
			httpPut.setEntity(se);
			HttpResponse httpResponse = httpClient.execute(httpPut);
			HttpEntity entity = httpResponse.getEntity();
			String response = EntityUtils.toString(entity, "utf-8");
			if(httpResponse.getStatusLine().getStatusCode()  != 200){
				Log.d("editusererror", response);
				return false;
			}else return true;
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
		return false;
	}

	public void delete(User user) {
		// TODO Auto-generated method stub
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("kermit", "kermit");
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpClient httpClient = new DefaultHttpClient();
		((DefaultHttpClient)httpClient).setCredentialsProvider(provider);
		String REST_URL = "http://121.43.109.179/activiti-rest/service/identity/users/" + user.id;
		HttpDelete httpDelete = new HttpDelete(REST_URL);
		try {
			HttpResponse httpResponse = httpClient.execute(httpDelete);
			if(httpResponse.getStatusLine().getStatusCode()  != 204){
				Log.d("deleteusererror", "error");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void create(String name, String mode, String passwd) {
		// TODO Auto-generated method stub
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("kermit", "kermit");
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpClient httpClient = new DefaultHttpClient();
		((DefaultHttpClient)httpClient).setCredentialsProvider(provider);
		String REST_URL = "http://121.43.109.179/activiti-rest/service/identity/users";
		HttpPost httpPost = new HttpPost(REST_URL);
		JSONObject param = new JSONObject();
		try {
			param.put("id", name);
			param.put("firstName", passwd);
			param.put("lastName", mode);
			param.put("email", "*");
			param.put("password", passwd);
			StringEntity se = new StringEntity(param.toString());
			se.setContentEncoding("UTF-8");
			se.setContentType("application/json");
			httpPost.setEntity(se);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			String response = EntityUtils.toString(entity, "utf-8");
			if(httpResponse.getStatusLine().getStatusCode()  != 201){
				Log.d("editusererror", response);
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
