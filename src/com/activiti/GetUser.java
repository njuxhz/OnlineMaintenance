package com.activiti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class GetUser {

	public static final String KERMIT_REST_URL = "http://121.43.109.179/activiti-rest/service/";
	public String REST_URL;
	
	public  List<User> userList;

	public GetUser(String id, String passwd) {
		// TODO Auto-generated constructor stub
		userList = new ArrayList<User>();
		REST_URL = KERMIT_REST_URL + "identity/users";
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(id, passwd);
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
				JSONArray jsonArray = jsonObjectdata.getJSONArray("data");
				for(int i = 0; i < jsonArray.length(); i++){
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String userid, usermode, userpasswd;
					userid = jsonObject.getString("id");
					usermode = jsonObject.getString("lastName");
					userpasswd = jsonObject.getString("firstName");
					User newuser = new User(userid, Integer.parseInt(usermode), userpasswd);
					userList.add(newuser);
				}
			}else{
				Log.d("getusererror", response);
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
