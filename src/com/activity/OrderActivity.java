package com.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.activitymanager.BaseActivity;
import com.example.onlinemaintenance.R;
import com.order.Order;
import com.order.OrderAdapter;
import com.user.User;

public class OrderActivity extends BaseActivity{
	
	public static final int BACK = 1;
	public static final int END = 2;
	
	private List<Order> orderList = new ArrayList<Order>();
	private ListView listView;
	private OrderAdapter adapter;
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		user = (User)intent.getSerializableExtra("user");
		setContentView(R.layout.tab_layout);
		initOrder();//************
		adapter = new OrderAdapter(OrderActivity.this, R.layout.single_order, orderList);
		getView();
	}

	private void initOrder() {//*********************
		// TODO Auto-generated method stub
		Order o1 = new Order(21, "order1", "123456");
		Order o2 = new Order(22, "order2", "654321");
		orderList.add(o1);
		orderList.add(o2);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void getView() {
		// TODO Auto-generated method stub
		super.getView();
		listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(adapter);
	}
}
