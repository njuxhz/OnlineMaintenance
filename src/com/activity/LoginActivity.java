package com.activity;

import com.activiti.UserConnect;
import com.activitymanager.BaseActivity;
import com.example.onlinemaintenance.R;
import com.user.User;

import android.content.Intent;
import android.os.AsyncTask;
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
		Log.d("1", "1");
		UserConnect usercnt = new UserConnect();
		Log.d("1", "2");
		return usercnt.auth(user, passwd);
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
			Log.d("1", "1");
			new mythread().execute();
			Log.d("1", "2");
			break;
		default: break;
		}
	}
	
	class mythread extends AsyncTask<Void, Void, User>{
		@Override
		protected User doInBackground(Void... params) {
			// TODO Auto-generated method stub
			User authuser = isAuthen();
			return authuser;
		}

		@Override
		protected void onPostExecute(User result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result != null){
				Log.d("1", "3");
				Intent intent = new Intent(LoginActivity.this, UserInfoActivity.class);
				intent.putExtra("user", result);
				startActivity(intent);
			}
			Log.d("1", "4");
		}
	}
}
