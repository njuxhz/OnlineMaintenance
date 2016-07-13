package com.activiti;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.order.Order;
import com.user.User;

public class GetOrder {
	
	public String matchstate;
	public List<Order> orderList;
	public User user;
	
	public GetOrder(User userr) {
		// TODO Auto-generated constructor stub
		user = userr;
	}

	public void setmachstate(String str){
		matchstate = str;
		orderList = new ArrayList<Order>();
		orderList.clear();
		OrderConnect ordercnt = new OrderConnect(user);
		String taskdataarray = ordercnt.gettask();
		try {
			JSONArray jsonArray = new JSONArray(taskdataarray);
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String compare = jsonObject.getString("name");
				if(str.equalsIgnoreCase("ALL") || str.equals(compare)){
					String processInstanceId = jsonObject.getString("processInstanceId");
					ordercnt.getattri(processInstanceId);
					Order order = new Order(jsonObject.getString("id"), processInstanceId, ordercnt.name, ordercnt.tel, ordercnt.company, 
											ordercnt.address, compare, ordercnt.score, ordercnt.timestamp, ordercnt.engineerid, ordercnt.salerid);
					orderList.add(order);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
