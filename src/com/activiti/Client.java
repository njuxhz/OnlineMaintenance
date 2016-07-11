package com.activiti;

import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.jaxrs.client.WebClient;
	
public class Client {
	
	public static final String BASE_REST_URL = "http://121.43.109.179/activiti-rest/service/";
	
	public static WebClient createClient(String url){
		WebClient client= WebClient.create(BASE_REST_URL + url);
		String auth = "Basic " + Base64Utility.encode("kermit:kermit".getBytes());
		client.header("Authorization", auth);
		return client;
	}
}
