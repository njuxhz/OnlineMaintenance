package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.activitymanager.BaseActivity;
import com.example.onlinemaintenance.R;

public class UserInfoActivity extends BaseActivity implements OnClickListener{

	public static final int BACK = 1;
	public static final int END = 2;
	public static final int DELIVER = 1;
	public static final int ENGINEER = 2;
	public static final int SALER = 3;
	public static final int ADMIN = 4;
	
	private String user, passwd;
	private int mode;
	private Button usermanager, check1, check2;
	
	@Override
	protected void getView() {
		// TODO Auto-generated method stub
		super.getView();
		usermanager = (Button) findViewById(R.id.usermanagerBT);
		usermanager.setOnClickListener(this);
		switch(mode){
		case DELIVER: case ENGINEER: case SALER:
			check1 = (Button) findViewById(R.id.check1BT);
			check2 = (Button) findViewById(R.id.check2BT);
			check1.setOnClickListener(this);
			check2.setOnClickListener(this);
			break;
		default: break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		user = intent.getStringExtra("user");
		passwd = intent.getStringExtra("passwd");
		mode = intent.getIntExtra("mode", 0);
		switch(mode){
		case DELIVER:
			setContentView(R.layout.deliver_page_layout);
			break;
		case ENGINEER:
			setContentView(R.layout.engineer_page_layout);
			break;
		case SALER:
			setContentView(R.layout.sale_page_layout);
			break;
		case ADMIN:
			setContentView(R.layout.admin_page_layout);
			break;
		default: break;
		}
		getView();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.usermanagerBT:
			Intent intent = new Intent(UserInfoActivity.this, UserManagerActivity.class);
			intent.putExtra("user", user);
			intent.putExtra("mode", mode);
			startActivityForResult(intent, 1);
			break;
		case R.id.check1BT:
			break;
		case R.id.check2BT:
			break;
		default: break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(resultCode){
		case BACK:
			break;
		case END:
			finish();
			break;
		default: break;
		}
	}
}
