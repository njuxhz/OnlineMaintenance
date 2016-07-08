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

	public static final int UPDATE_USER = 1;
	
	private String user, passwd;
	private TextView usertype, username;
	private Button revisepasswd, logout;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case UPDATE_USER:
				CharSequence str = user;
				username.setText(str);
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
		revisepasswd = (Button) findViewById(R.id.revisepasswdBT);
		logout = (Button) findViewById(R.id.logoutBT);
	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		super.setView();
		revisepasswd.setOnClickListener(this);
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
		setContentView(R.layout.user_manage_layout);
		Intent intent = getIntent();
		user = intent.getStringExtra("user");
		Log.d("myuser", user);
		passwd = intent.getStringExtra("passwd");
		getView();
		setView();
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
		case R.id.revisepasswdBT:
			break;
		case R.id.logoutBT:
			finish();
			break;
		default: break;
		}
	}
}
