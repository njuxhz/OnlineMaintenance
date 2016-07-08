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
import com.user.User;

public class UserManagerActivity extends BaseActivity implements OnClickListener{

	public static final int BACK = 1;
	public static final int END = 2;
	public static final int UPDATE_USER = 1;
	
	private User user;
	private TextView usertype, nickname;
	private Button revisepasswd, ret, logout;
	private String utype;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case UPDATE_USER:
				CharSequence stra = utype;
				CharSequence strb = user.nickname;
				usertype.setText(stra);
				nickname.setText(strb);
				break;
			default: break;
			}
		}
	};
	
	@Override
	protected void getView() {
		// TODO Auto-generated method stub
		super.getView();
		usertype = (TextView) findViewById(R.id.usertypeT);
		nickname = (TextView) findViewById(R.id.nicknameT);
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
		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("user");
		setContentView(R.layout.user_manage_layout);
		utype = user.type();
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