package com.activity;

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
import android.widget.EditText;
import android.widget.TextView;

import com.activitymanager.BaseActivity;
import com.example.onlinemaintenance.R;
import com.user.User;

public class UserInfoActivity extends BaseActivity implements OnClickListener{

	private static final int UPDATE_INFO = 1;
	public static final int DELIVER = 1;
	public static final int ENGINEER = 2;
	public static final int SALER = 3;
	public static final int ADMIN = 4;
	
	private User user;
	private TextView usertype, nickname;
	private Button revisepasswd, logout, checkorder, checkuser;
	private String utype;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case UPDATE_INFO:
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
		revisepasswd.setOnClickListener(this);
		logout = (Button) findViewById(R.id.logoutBT);
		logout.setOnClickListener(this);
		checkorder = (Button) findViewById(R.id.checkorderBT);
		checkorder.setOnClickListener(this);
		utype = user.type();
		checkuser = (Button) findViewById(R.id.usermanagerBT);
		if(user.mode == ADMIN){
			checkuser.setOnClickListener(this);
			checkuser.setVisibility(View.VISIBLE);
		}else{
			checkuser.setVisibility(View.GONE);
		}
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
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		user = (User)intent.getSerializableExtra("user");
		setContentView(R.layout.user_info_layout);
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
		Intent intent;
		switch(v.getId()){
		case R.id.revisepasswdBT:
			AlertDialog.Builder dialog = new AlertDialog.Builder(UserInfoActivity.this);
			final EditText passwd = new EditText(this);
			dialog.setTitle("Revise Your Password!").setView(passwd);
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					user.passwd = passwd.getText().toString();
					user.updatepasswd();
				}
			});
			dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			});
			dialog.show();
			break;
		case R.id.logoutBT:
			finish();
			break;
		case R.id.checkorderBT:
			intent = new Intent(UserInfoActivity.this, OrderActivity.class);
			intent.putExtra("user", user);
			startActivity(intent);
			break;
		case R.id.usermanagerBT:
			break;
		default: break;
		}
	}
}
