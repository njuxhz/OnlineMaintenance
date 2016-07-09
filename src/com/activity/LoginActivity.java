package com.activity;

import com.activitymanager.BaseActivity;
import com.example.onlinemaintenance.R;
import com.user.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
		login.setOnClickListener(this);
	}
	
	@Override
	protected void getContent() {
		// TODO Auto-generated method stub
		super.getContent();
		user = userET.getText().toString();
		passwd = passwdET.getText().toString();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hello_world_layout);
		getView();
	}

	private User isAuthen() {
		// TODO Auto-generated method stub
		if(Integer.parseInt(user) <= 4){//**********
			User authuser = new User(Integer.parseInt(user), Integer.parseInt(user), user, passwd);
			return authuser;
		}
		return null;
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
			getContent();
			User authuser = isAuthen();
			if(authuser != null){
				Intent intent = new Intent(LoginActivity.this, UserInfoActivity.class);
				intent.putExtra("user", authuser);
				startActivity(intent);
			}
			break;
		default: break;
		}
	}
}
