package com.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.activitymanager.BaseActivity;
import com.example.onlinemaintenance.R;
import com.order.Order;
import com.order.OrderAdapter;
import com.user.User;

public class OrderActivity extends BaseActivity implements OnClickListener{
	
	public static final int BACK = 1;
	public static final int UPDATE_LIST = 1;
	public static final int DELIVER = 1;
	public static final int ENGINEER = 2;
	public static final int SALER = 3;
	public static final int ADMIN = 4;
			
	private List<Order> orderList = new ArrayList<Order>();
	private ListView listView;
	private OrderAdapter adapter;
	private Button ret, all, check1, check2, check3, check4;
	private User user;
	public int listmode = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		user = (User)intent.getSerializableExtra("user");
		setContentView(R.layout.tab_manager);
		initOrder();//************
		adapter = new OrderAdapter(OrderActivity.this, R.layout.single_order, orderList);
		getView();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	protected void getView() {
		// TODO Auto-generated method stub
		ret = (Button) findViewById(R.id.orderretBT);
		ret.setOnClickListener(this);
		permission();
		listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(adapter);
	}

	private void permission() {
		// TODO Auto-generated method stub
		all = (Button) findViewById(R.id.orderallBT);
		check1 = (Button) findViewById(R.id.order1BT);
		check2 = (Button) findViewById(R.id.order2BT);
		check3 = (Button) findViewById(R.id.order3BT);
		check4 = (Button) findViewById(R.id.order4BT);
		Log.d("e", ""+user.mode);
		switch(user.mode){
		case DELIVER: case ADMIN:
			all.setOnClickListener(this);check1.setOnClickListener(this);
			check2.setOnClickListener(this);check3.setOnClickListener(this);
			check4.setOnClickListener(this);
			break;
		case ENGINEER:
			check1.setOnClickListener(this);check2.setOnClickListener(this);
			check3.setVisibility(View.GONE);check4.setVisibility(View.GONE);
			all.setVisibility(View.GONE);
			break;
		case SALER:
			check3.setOnClickListener(this);check4.setOnClickListener(this);
			check1.setVisibility(View.GONE);check2.setVisibility(View.GONE);
			all.setVisibility(View.GONE);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.orderretBT:
			listmode = -1;
			finish();
			break;
		case R.id.orderallBT: listmode = 0; break;
		case R.id.order1BT: listmode = 1; break;
		case R.id.order2BT: listmode = 2; break;
		case R.id.order3BT: listmode = 3; break;
		case R.id.order4BT: listmode = 4; break;
		default: break;
		}
		Log.d("1", ""+listmode);
		if(listmode != -1){
			changeData();
		}
	}
	
	private void changeData() {
		// TODO Auto-generated method stub
		orderList.clear();
		for(int i = (listmode*10); i < (listmode*10+5); i++){
			Order o = new Order(i, "order" + i, "" + i + "order");
			orderList.add(o);
		}
		adapter.notifyDataSetChanged();
	}

	private void initOrder() {//*********************
		// TODO Auto-generated method stub
		orderList.clear();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
}
