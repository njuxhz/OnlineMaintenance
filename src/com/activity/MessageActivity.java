package com.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class MessageActivity extends BaseActivity implements OnClickListener{
	
	public static final int OK = 1;
	public static final int BACK = 2;
	
	public static final int UPDATE_LIST = 1;
	
	public static final int DELIVER = 1;
	public static final int ENGINEER = 2;
	public static final int SALER = 3;
	public static final int ADMIN = 4;
	
	public static final int ALL = 0;
	
	private List<Order> orderList = new ArrayList<Order>();
	private ListView listView;
	private OrderAdapter adapter;
	private Button ret, all;
	private User user;
	public int listmode = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		user = (User)intent.getSerializableExtra("user");
		setContentView(R.layout.message);
		initOrder();
		adapter = new OrderAdapter(MessageActivity.this, R.layout.single_order, orderList);
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
		all = (Button) findViewById(R.id.messageallBT);
		all.setOnClickListener(this);
		listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MessageActivity.this, EditOrderActivity.class);
				intent.putExtra("user", user);
				Order order = (Order) listView.getItemAtPosition(position);
				intent.putExtra("order", order);
				startActivityForResult(intent, 1);
			}});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.orderretBT:
			listmode = -1;
			finish();
			break;
		case R.id.messageallBT: listmode = ALL; break;
		default: break;
		}
		if(listmode != -1){
			changeData();
		}
	}
	
	private void changeData() {
		// TODO Auto-generated method stub
		orderList.clear();
		switch(listmode){
		case ALL:
			new AsyncTask <Void, Void, GetOrder>(){
				@Override
				protected GetOrder doInBackground(Void... params) {
					// TODO Auto-generated method stub
					GetOrder getorder = new GetOrder(user);
					getorder.setmachstatemessage("ALL");
					return getorder;
				}
				@Override
				protected void onPostExecute(GetOrder result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					for(Order o : result.orderList){
						orderList.add(o);
					}
					adapter.notifyDataSetChanged();
				}
			}.execute();
			break;
		}
	}

	private void initOrder() {
		// TODO Auto-generated method stub
		orderList.clear();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1){
			changeData();
			adapter.notifyDataSetChanged();
		}
	}
}
