package com.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.activiti.OrderConnect;
import com.activitymanager.BaseActivity;
import com.example.onlinemaintenance.R;
import com.user.User;

public class CreateOrderActivity extends BaseActivity implements OnClickListener{

	private static final int UPDATE_INFO = 1;
	
	private User user;
	private EditText company, name, address, tel, state, score, engineer, saler;
	private Button delete, ret, confirm, caller, audit, take, takecancel, complete, photo;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case UPDATE_INFO:
				CharSequence strstate, strengineer, strsaler;
				strstate = "Î´½Ó";
				state.setText(strstate);
				strengineer = "*";
				engineer.setText(strengineer);
				strsaler = "*";
				saler.setText(strsaler);
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
		user = (User)intent.getSerializableExtra("user");
		setContentView(R.layout.edit_order);
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
		company = (EditText) findViewById(R.id.companyET);
		name = (EditText) findViewById(R.id.nameET);
		address = (EditText) findViewById(R.id.addressET);
		tel = (EditText) findViewById(R.id.telET);
		state = (EditText) findViewById(R.id.stateET);
		score = (EditText) findViewById(R.id.scoreET);
		saler = (EditText) findViewById(R.id.salerET);
		engineer = (EditText) findViewById(R.id.engineerET);
		delete = (Button) findViewById(R.id.editdeleteBT);
		ret = (Button) findViewById(R.id.editretBT);
		ret.setOnClickListener(this);
		confirm = (Button) findViewById(R.id.editconfirmBT);
		caller = (Button) findViewById(R.id.editcallBT);
		audit = (Button) findViewById(R.id.editauditBT);
		take = (Button) findViewById(R.id.edittakeBT);
		takecancel = (Button) findViewById(R.id.edittakecancelBT);
		complete = (Button) findViewById(R.id.editcompleteBT);
		complete.setOnClickListener(this);
		photo = (Button) findViewById(R.id.editphotoBT);
		disableall();
		ret.setVisibility(View.VISIBLE);
		complete.setVisibility(View.VISIBLE);
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
	
	private void disableall() {
		// TODO Auto-generated method stub
		state.setEnabled(false);
		saler.setEnabled(false);
		engineer.setEnabled(false);
		delete.setVisibility(View.GONE);
		ret.setVisibility(View.GONE);
		confirm.setVisibility(View.GONE);
		caller.setVisibility(View.GONE);
		audit.setVisibility(View.GONE);
		take.setVisibility(View.GONE);
		takecancel.setVisibility(View.GONE);
		complete.setVisibility(View.GONE);
		photo.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.editretBT:
			finish();
			break;
		case R.id.editcompleteBT:
			new AsyncTask <Void, Void, Void>(){
				@Override
				protected Void doInBackground(Void... params) {
					// TODO Auto-generated method stub
					OrderConnect ordercnt = new OrderConnect(user);
					ordercnt.createorder(16, "Name", name.getText().toString(), 
											"Company", company.getText().toString(), 
											"Tel", tel.getText().toString(),
											"Address", address.getText().toString(), 
											"Score", score.getText().toString(),
											"Engineerid", "*",
											"Salerid", "*",
											"Ondoor", "*",
											"Series", "*",
											"Feedback", "*",
											"Photourl1", "*", "Photourl2", "*", "Photourl3", "*",
											"Picindex", "1",
											"Isdel", "0",
											"Isedit", "0");
					return null;
				}
				@Override
				protected void onPostExecute(Void result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					Toast.makeText(getBaseContext(), "Create Successfully!", Toast.LENGTH_LONG).show();
				}
			}.execute();
			finish();
			break;
		}
	}
}
