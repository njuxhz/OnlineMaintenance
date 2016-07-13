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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.activiti.GetOrder;
import com.activitymanager.BaseActivity;
import com.example.onlinemaintenance.R;
import com.order.Order;
import com.order.OrderAdapter;
import com.user.User;

public class OrderActivity extends BaseActivity implements OnClickListener{
	
	public static final int OK = 1;
	public static final int BACK = 2;
	private static final int DELETE = 3;
	private static final int UPDATE = 4;
	private static final int COMPLETE = 5;
	
	public static final int UPDATE_LIST = 1;
	
	public static final int DELIVER = 1;
	public static final int ENGINEER = 2;
	public static final int SALER = 3;
	public static final int ADMIN = 4;
	
	public static final int ALL = 0;
	public static final int UNACCEPTED = 1;
	public static final int ACCEPTED = 2;
	public static final int COMPLETED = 3;
	public static final int CHECKED = 4;
	
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
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(OrderActivity.this, EditOrderActivity.class);
				intent.putExtra("user", user);
				Order order = (Order) listView.getItemAtPosition(position);
				intent.putExtra("user", user);
				intent.putExtra("order", order);
				startActivityForResult(intent, 1);
			}});
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
		case R.id.orderallBT: listmode = ALL; break;
		case R.id.order1BT: listmode = UNACCEPTED; break;
		case R.id.order2BT: listmode = ACCEPTED; break;
		case R.id.order3BT: listmode = COMPLETED; break;
		case R.id.order4BT: listmode = CHECKED; break;
		default: break;
		}
		if(listmode != -1){
			changeData();
		}
	}
	
	private void changeData() {
		// TODO Auto-generated method stub
		orderList.clear();
		for(int i = (listmode*10); i < (listmode*10+5); i++){//************************
			Order o = new Order("" + i, "" + i, "order" + i, "" + i + "order", "10086", "*", "UnAcceptedOrder", "0", "7.13", "*", "*");
			orderList.add(o);
		}
		GetOrder getorder = new GetOrder(user);
		switch(listmode){
		case ALL:
			getorder.setmachstate("ALL");
			break;
		case UNACCEPTED:
			getorder.setmachstate("UnAcceptedOrder");
			break;
		case ACCEPTED:
			getorder.setmachstate("AcceptedOrder");
			break;
		case COMPLETED:
			getorder.setmachstate("CompletedOrder");
			break;
		case CHECKED:
			getorder.setmachstate("CheckedOrder");
			break;
		}
		for(Order o : getorder.orderList){
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
		if(requestCode == 1){
			if(resultCode == OK){
				adapter.notifyDataSetChanged();
			}else if(resultCode == DELETE){
				String orderid = data.getStringExtra("orderid");
				for(int i = 0; i < orderList.size();i++){
					Order o = orderList.get(i);
					if(o.id.equals(orderid)){
						orderList.remove(i);
						break;
					}
				}
				adapter.notifyDataSetChanged();
			}else if(resultCode == UPDATE){
				String orderid = data.getStringExtra("orderid");
				Order oo = (Order) data.getSerializableExtra("neworder");
				for(int i = 0; i < orderList.size();i++){
					Order o = orderList.get(i);
					if(o.id.equals(orderid)){
						orderList.remove(i);
						break;
					}
				}
				orderList.add(oo);
				adapter.notifyDataSetChanged();
			}else if(resultCode == COMPLETE){
				changeData();
				adapter.notifyDataSetChanged();
			}
		}
	}
}
