package com.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
	private EditText name, passwd;
	private Spinner mode;
	private List<String> list = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private String select;
	private Button ret, confirm, delete;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case UPDATE_INFO:
				if(showmode == 1){
					name.setText("");
					mode.setSelection(0, true);
					passwd.setText("");
				}else if (showmode == 2){
					CharSequence strname, strpasswd;
					strname = user.id;
					strpasswd = user.passwd;
					name.setText(strname);
					mode.setSelection(user.spinner(user.mode), true);
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
		mode = (Spinner) findViewById(R.id.usertypeSP);
		passwd = (EditText) findViewById(R.id.usereditpasswdET);
		ret = (Button) findViewById(R.id.usereditretBT);
		ret.setOnClickListener(this);
		confirm = (Button) findViewById(R.id.usereditconfirmBT);
		confirm.setOnClickListener(this);
		delete = (Button) findViewById(R.id.usereditdeleteBT);
		delete.setOnClickListener(this);
		if(showmode == 1) delete.setVisibility(View.GONE);
		new Thread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.what = UPDATE_INFO;
				handler.sendMessage(message);
			}
		}).start();
		
		list.add("*");list.add("Deliver");list.add("Engineer");list.add("Saler");list.add("Admin");
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mode.setAdapter(adapter);
		mode.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(adapter.getItem(position).equals("Deliver")){
					select = "1";
				}if(adapter.getItem(position).equals("Engineer")){
					select = "2";
				}if(adapter.getItem(position).equals("Saler")){
					select = "3";
				}if(adapter.getItem(position).equals("Admin")){
					select = "4";
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				if(showmode == 2){
					select = "" + user.spinner(user.mode);
				}else{
					select = "2";
				}
			}    
        });
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.usereditretBT:
			Intent intent = new Intent();
			setResult(BACK, intent);
			finish();
			break;
		case R.id.usereditconfirmBT:
			new AsyncTask <String, Void, Integer>(){
				@Override
				protected Integer doInBackground(String... params) {
					// TODO Auto-generated method stub
					int ok = 1;
					UserConnect usercnt = new UserConnect();
					if(showmode == 1){
						if((!usercnt.isoccur(params[0])) && (!params[0].equals(""))){
							usercnt.create(params[0], params[1], params[2]);
						}else{
							ok = 0;
						}
					}else{
						if(user.id.equals(params[0])){
							if(usercnt.edit(user, params[1], params[2])){
								user.mode = Integer.parseInt(params[1]);
								user.passwd = params[2];
								user.getNickname();
							}
						}else{
							usercnt.delete(user);
							if((!usercnt.isoccur(params[0])) && (!params[0].equals(""))){
								usercnt.create(params[0], params[1], params[2]);
							}else{
								ok = 0;
							}
						}
					}
					return ok;
				}
				@Override
				protected void onPostExecute(Integer result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					if(result == 1){
						if(showmode == 1) Toast.makeText(getBaseContext(), "Create Successfully!", Toast.LENGTH_SHORT).show();
						else Toast.makeText(getBaseContext(), "Revise Successfully!", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						setResult(OK, intent);
						finish();
					}else{
						Toast.makeText(getBaseContext(), "Sign Up Error!", Toast.LENGTH_SHORT).show();
					}
				}
			}.execute(name.getText().toString(), select, passwd.getText().toString());
			break;
		case R.id.usereditdeleteBT:
			new AsyncTask <Void, Void, Void>(){
				@Override
				protected Void doInBackground(Void... params) {
					// TODO Auto-generated method stub
					UserConnect usercnt = new UserConnect();
					usercnt.delete(user);
					return null;
				}
				@Override
				protected void onPostExecute(Void result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					Toast.makeText(getBaseContext(), "Delete Successfully!", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					setResult(OK, intent);
					finish();
				}
			}.execute();
			break;
		}
	}
}
