package com.activiti;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.apache.cxf.attachment.ContentDisposition;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        WebClient client = createClient("repository/deployments");

        InputStream resourceAsStream = test.class.getClassLoader().getResourceAsStream("diagrams/leave.bpmn");
        client.type("multipart/form-data");
        ContentDisposition cd = new ContentDisposition("form-data;name=bpmn;filename=leave.bpmn;");
        Attachment att = new Attachment("leave.bpmn", resourceAsStream, cd);
        MultipartBody body = new MultipartBody(att);
        Response response = client.post(body);

        // 转换并输出响应结果
        printResult("部署流程", response);
	}

	public static final String BASE_REST_URL = "http://121.43.109.179/activiti-rest/service/";
	private static ObjectMapper objectMapper = new ObjectMapper();
	private static boolean formateOutputJson = true;
	
	public static WebClient createClient(String url){
		WebClient client= WebClient.create(BASE_REST_URL + url);
		String auth = "Basic " + Base64Utility.encode("kermit:kermit".getBytes());
		client.header("Authorization", auth);
		return client;
	}
	
	public static void printJsonString(String phase, String json){
		System.out.println("\n+++ 发送请求[" + phase + "] +++");
		System.out.println(json);
	}
	
	public static JsonNode printResult(String phase, Response response){
        System.out.println("\n=== " + phase + " ===");
        try {
            InputStream stream = (InputStream) response.getEntity();
            int available = 0;
            available = stream.available();

            if (available == 0) {
                System.out.println("nothing returned, response code: " + response.getStatus());
                return null;
            }
            JsonNode responseNode = objectMapper.readTree(stream);
            if (formateOutputJson) {
                System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseNode));
            } else {
                System.out.println(objectMapper.writeValueAsString(responseNode));
            }
            return responseNode;
        } catch (IOException e) {
            System.err.println("catch an exception: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
	}
}
