package com.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.activitymanager.BaseActivity;
import com.example.onlinemaintenance.R;
import com.order.Order;
import com.order.OrderAdapter;
import com.user.User;
import com.user.UserAdapter;

public class UserManageActivity extends BaseActivity implements OnClickListener{

	public static final int OK = 1;
	
	private List<User> userList = new ArrayList<User>();
	private UserAdapter adapter;
	private ListView listView;
	private Button ret, add;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_manage);
		initUser();//************
		adapter = new UserAdapter(UserManageActivity.this, R.layout.single_user, userList);
		getView();
	}

	private void initUser() {//**************
		// TODO Auto-generated method stub
		userList.clear();
		userList.add(new User("1", 1, "22"));
		userList.add(new User("5", 4, "44"));
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
		ret = (Button) findViewById(R.id.usermanageretBT);
		ret.setOnClickListener(this);
		add = (Button) findViewById(R.id.usernewBT);
		add.setOnClickListener(this);
		listView = (ListView) findViewById(R.id.user_list_view);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(UserManageActivity.this, EditUserActivity.class);
				User user = (User) listView.getItemAtPosition(position);
				intent.putExtra("mode", 2);
				intent.putExtra("user", user);
				startActivityForResult(intent, 2);
			}});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.usermanageretBT:
			finish();
			break;
		case R.id.usernewBT:
			Intent intent = new Intent(UserManageActivity.this, EditUserActivity.class);
			intent.putExtra("mode", 1);
			startActivityForResult(intent, 1);
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == OK){
			adapter.notifyDataSetChanged();
		}
	}
}
