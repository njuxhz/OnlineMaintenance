package com.activity;

import com.activitymanager.BaseActivity;
import com.example.onlinemaintenance.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends BaseActivity implements OnClickListener{

	private EditText userET, passwdET;
	private String user, passwd;
	private Button login;
	
	@Override
	protected void getView() {
		// TODO Auto-generated method stub
		super.getView();
		userET = (EditText) findViewById(R.id.userET);
		passwdET = (EditText) findViewById(R.id.passwdET);
		login = (Button) findViewById(R.id.loginBT);
		user = userET.getText().toString();
		passwd = passwdET.getText().toString();
	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		super.setView();
		login.setOnClickListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hello_world_layout);
		getView();
		setView();
	}

	private boolean isAuthen(String user, EditText passwdET) {
		// TODO Auto-generated method stub
		return true;
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
		case R.id.loginBT:
			if(isAuthen(user, passwdET)){
				Intent intent = new Intent(LoginActivity.this, UserInfoActivity.class);
				intent.putExtra("user", user);
				intent.putExtra("passwd", passwd);
				startActivity(intent);
			}
			break;
		default: break;
		}
	}
}
