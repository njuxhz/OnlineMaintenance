package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.activiti.UserConnect;
import com.activitymanager.BaseActivity;
import com.example.onlinemaintenance.R;
import com.user.User;

public class EditUserActivity extends BaseActivity implements OnClickListener{
	
	private static final int UPDATE_INFO = 1;
	
	private static final int OK = 1;
	private static final int BACK = 2;
	
	private User user;
	private int showmode;
	private EditText name, mode, passwd;
	private Button ret, confirm, delete;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case UPDATE_INFO:
				if(showmode == 1){
					name.setText("");
					mode.setText("");
					passwd.setText("");
				}else if (showmode == 2){
					CharSequence strname, strmode, strpasswd;
					strname = user.id;
					strmode = "" + user.mode;
					strpasswd = user.passwd;
					name.setText(strname);
					mode.setText(strmode);
					passwd.setText(strpasswd);
				}
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
		showmode = intent.getIntExtra("mode", 1);
		if(showmode == 2){
			user = (User)intent.getSerializableExtra("user");
		}
		setContentView(R.layout.edit_user);
		getView();
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
		name = (EditText) findViewById(R.id.usereditnameET);
		mode = (EditText) findViewById(R.id.useredittypeET);
		passwd = (EditText) findViewById(R.id.usereditpasswdET);
		ret = (Button) findViewById(R.id.usereditretBT);
		ret.setOnClickListener(this);
		confirm = (Button) findViewById(R.id.usereditconfirmBT);
		confirm.setOnClickListener(this);
		delete = (Button) findViewById(R.id.usereditdeleteBT);
		delete.setOnClickListener(this);
		if(showmode == 2) delete.setVisibility(View.GONE);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		UserConnect usercnt = new UserConnect();
		switch(v.getId()){
		case R.id.usereditretBT:
			setResult(BACK, intent);
			break;
		case R.id.usereditconfirmBT:
			setResult(OK, intent);
			//usercnt.createuser();
			break;
		case R.id.usereditdeleteBT:
			setResult(OK, intent);
			break;
		}
		finish();
	}
}
