package com.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.activiti.GetOrder;
import com.activiti.GetUser;
import com.activitymanager.BaseActivity;
import com.example.onlinemaintenance.R;
import com.order.Order;
import com.order.OrderAdapter;
import com.user.User;
import com.user.UserAdapter;

public class OrderActivity extends BaseActivity implements OnClickListener{
	
	private static final int UPDATE_INFO = 1;
	
	public static final int OK = 1;
	public static final int BACK = 2;
	
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
	private ListView orderlistView;
	private OrderAdapter orderadapter;
	private Button ret, all, check1, check2, check3, check4;
	private EditText tobecontinued;
	private ListView userlistView;
	private List<User> userList = new ArrayList<User>();
	private UserAdapter useradapter;
	private User user;
	public int listmode = -1;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case UPDATE_INFO:
				if(listmode == -1) tobecontinued.setText("未选");
				else if(listmode == 0) tobecontinued.setText("所有");
				else if(listmode == 1) tobecontinued.setText("未接");
				else if(listmode == 2) tobecontinued.setText("已接");
				else if(listmode == 3) tobecontinued.setText("已完成");
				else if(listmode == 4) tobecontinued.setText("已审核");
				break;
			default: break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		user = (User)intent.getSerializableExtra("user");
		setContentView(R.layout.tab_manager);
		getView();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	protected void getView() {
		// TODO Auto-generated method stub
		listmode = -1;
		ret = (Button) findViewById(R.id.orderretBT);
		ret.setOnClickListener(this);
		tobecontinued = (EditText) findViewById(R.id.tobecontinuedET);
		tobecontinued.setEnabled(false);
		permission();
		new Thread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.what = UPDATE_INFO;
				handler.sendMessage(message);
			}
		}).start();
		
		userList.clear();
		useradapter = new UserAdapter(OrderActivity.this, R.layout.single_user, userList);
		initUser();
		userlistView = (ListView) findViewById(R.id.user_list_view);
		userlistView.setAdapter(useradapter);
		userlistView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				User userr = (User) userlistView.getItemAtPosition(position);
				changeData(userr);
			}});
		
		orderList.clear();
		orderadapter = new OrderAdapter(OrderActivity.this, R.layout.single_order, orderList);
		orderlistView = (ListView) findViewById(R.id.list_view);
		orderlistView.setAdapter(orderadapter);
		orderlistView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(OrderActivity.this, EditOrderActivity.class);
				intent.putExtra("user", user);
				Order order = (Order) orderlistView.getItemAtPosition(position);
				intent.putExtra("order", order);
				startActivityForResult(intent, 1);
			}});
	}

	private void initUser() {
		// TODO Auto-generated method stub
		userList.clear();
		new AsyncTask <String, Void, Void>(){
			@Override
			protected Void doInBackground(String... arg0) {
				// TODO Auto-generated method stub
				if((user.mode == ADMIN) || (user.mode == DELIVER)){
					GetUser getuser = new GetUser(arg0[0], arg0[1]);
					for(User usr : getuser.userList){
						if((usr.mode == ENGINEER) || (usr.mode == SALER)){
							userList.add(usr);
						}
					}
				}else if((user.mode == ENGINEER) || (user.mode == SALER)){
					userList.add(user);
				}
				return null;
			}
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				useradapter.notifyDataSetChanged();
			}
		}.execute(user.id, user.passwd);
	}

	private void permission() {
		// TODO Auto-generated method stub
		all = (Button) findViewById(R.id.orderallBT);
		check1 = (Button) findViewById(R.id.order1BT);
		check2 = (Button) findViewById(R.id.order2BT);
		check3 = (Button) findViewById(R.id.order3BT);
		check4 = (Button) findViewById(R.id.order4BT);
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
			new Thread(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Message message = new Message();
					message.what = UPDATE_INFO;
					handler.sendMessage(message);
				}
			}).start();
		}
	}
	
	private void changeData(final User userr) {
		// TODO Auto-generated method stub
		orderList.clear();
		switch(listmode){
		case ALL:
			new AsyncTask <Void, Void, GetOrder>(){
				@Override
				protected GetOrder doInBackground(Void... params) {
					// TODO Auto-generated method stub
					GetOrder getorder = new GetOrder(user);
					getorder.setmachstate("ALL");
					return getorder;
				}
				@Override
				protected void onPostExecute(GetOrder result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					for(Order o : result.orderList){
						if((o.engineerid.equals(userr.id)) || (o.salerid.equals(userr.id))){
							orderList.add(o);
						}
					}
					Collections.sort(orderList);
					orderadapter.notifyDataSetChanged();
				}
			}.execute();
			break;
		case UNACCEPTED:
			new AsyncTask <Void, Void, GetOrder>(){
				@Override
				protected GetOrder doInBackground(Void... params) {
					// TODO Auto-generated method stub
					GetOrder getorder = new GetOrder(user);
					getorder.setmachstate("UnAcceptedOrder");
					return getorder;
				}
				@Override
				protected void onPostExecute(GetOrder result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					for(Order o : result.orderList){
						if(o.isaccepted.equals("0")){
							if((o.engineerid.equals(userr.id)) || (o.salerid.equals(userr.id))){
								orderList.add(o);
							}
						}
					}
					Collections.sort(orderList);
					orderadapter.notifyDataSetChanged();
				}
			}.execute();
			break;
		case ACCEPTED:
			new AsyncTask <Void, Void, GetOrder>(){
				@Override
				protected GetOrder doInBackground(Void... params) {
					// TODO Auto-generated method stub
					GetOrder getorder = new GetOrder(user);
					getorder.setmachstate("UnAcceptedOrder");
					return getorder;
				}
				@Override
				protected void onPostExecute(GetOrder result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					for(Order o : result.orderList){
						if(o.isaccepted.equals("1")){
							if((o.engineerid.equals(userr.id)) || (o.salerid.equals(userr.id))){
								orderList.add(o);
							}
						}
					}
					Collections.sort(orderList);
					orderadapter.notifyDataSetChanged();
				}
			}.execute();
			break;
		case COMPLETED:
			new AsyncTask <Void, Void, GetOrder>(){
				@Override
				protected GetOrder doInBackground(Void... params) {
					// TODO Auto-generated method stub
					GetOrder getorder = new GetOrder(user);
					getorder.setmachstate("CompletedOrder");
					return getorder;
				}
				@Override
				protected void onPostExecute(GetOrder result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					for(Order o : result.orderList){
						if((o.engineerid.equals(userr.id)) || (o.salerid.equals(userr.id))){
							orderList.add(o);
						}
					}
					Collections.sort(orderList);
					orderadapter.notifyDataSetChanged();
				}
			}.execute();
			break;
		case CHECKED:
			new AsyncTask <Void, Void, GetOrder>(){
				@Override
				protected GetOrder doInBackground(Void... params) {
					// TODO Auto-generated method stub
					GetOrder getorder = new GetOrder(user);
					getorder.setmachstate("CheckedOrder");
					return getorder;
				}
				@Override
				protected void onPostExecute(GetOrder result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					for(Order o : result.orderList){
						if((o.engineerid.equals(userr.id)) || (o.salerid.equals(userr.id))){
							orderList.add(o);
						}
					}
					Collections.sort(orderList);
					orderadapter.notifyDataSetChanged();
				}
			}.execute();
			break;
		}
		orderadapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1){
			orderList.clear();
			orderadapter.notifyDataSetChanged();
		}
	}
}
