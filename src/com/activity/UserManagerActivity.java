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

public class UserManagerActivity extends BaseActivity implements OnClickListener{

	public static final int BACK = 1;
	public static final int END = 2;
	public static final int UPDATE_USER = 1;
	public static final int DELIVER = 1;
	public static final int ENGINEER = 2;
	public static final int SALER = 3;
	public static final int ADMIN = 4;
	
	private String user;
	private int mode;
	private TextView usertype, username;
	private Button revisepasswd, ret, logout;
	private String utype;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case UPDATE_USER:
				CharSequence stra = user;
				CharSequence strb = utype;
				username.setText(stra);
				usertype.setText(strb);
				break;
			default: break;
			}
		}
	};
	
	@Override
	protected void getView() {
		// TODO Auto-generated method stub
		super.getView();
		username = (TextView) findViewById(R.id.usernameT);
		usertype = (TextView) findViewById(R.id.usertypeT);
		revisepasswd = (Button) findViewById(R.id.revisepasswdBT);
		ret = (Button) findViewById(R.id.retBT);
		logout = (Button) findViewById(R.id.logoutBT);
		revisepasswd.setOnClickListener(this);
		ret.setOnClickListener(this);
		logout.setOnClickListener(this);
		new Thread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.what = UPDATE_USER;
				handler.sendMessage(message);
			}
		}).start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("1","0");
		Intent intent = getIntent();
		user = intent.getStringExtra("user");
		mode = intent.getIntExtra("mode", 0);
		Log.d("1","1");
		setContentView(R.layout.user_manage_layout);
		switch(mode){
		case DELIVER:
			utype="派单员";break;
		case ENGINEER:
			utype="工程师";break;
		case SALER:
			utype="销售员";break;
		case ADMIN:
			utype="管理员";break;
		default: break;
		}
		Log.d("1","2");
		getView();
		Log.d("1","3");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch(v.getId()){
		case R.id.revisepasswdBT:
			break;
		case R.id.retBT:
			setResult(BACK, intent);
			finish();
			break;
		case R.id.logoutBT:
			setResult(END, intent);
			finish();
			break;
		default: break;
		}
	}
}