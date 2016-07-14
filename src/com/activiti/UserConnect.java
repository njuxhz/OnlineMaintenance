package com.activiti;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
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
		//HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
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

	public void modifier(String id, String passwd) {
		// TODO Auto-generated method stub
		
	}
}
