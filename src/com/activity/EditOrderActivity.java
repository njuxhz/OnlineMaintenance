package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.activitymanager.BaseActivity;
import com.example.onlinemaintenance.R;
import com.order.Order;
import com.user.User;

public class EditOrderActivity extends BaseActivity implements OnClickListener{

	private static final int UPDATE_INFO = 1;
	public static final int DELIVER = 1;
	public static final int ENGINEER = 2;
	public static final int SALER = 3;
	public static final int ADMIN = 4;
	
	private User user;
	private Order order;
	private EditText company, name, address, state, score;
	private Spinner engineer, saler;
	private Button delete, ret, confirm, caller;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case UPDATE_INFO:
				CharSequence strcompany, strname, straddress, strstate, strscore;
				strcompany = order.company;
				strname = order.name;
				straddress = order.address;
				String bufscore = "" + order.score;
				strscore = bufscore;
				company.setText(strcompany);
				name.setText(strname);
				address.setText(straddress);
				score.setText(strscore);
				String bufstate = order.getstate();
				strstate = bufstate;
				state.setText(strstate);
				permission();
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
		order = (Order)intent.getSerializableExtra("order");
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
		state = (EditText) findViewById(R.id.stateET);
		score = (EditText) findViewById(R.id.scoreET);
		saler = (Spinner) findViewById(R.id.salerSP);
		engineer = (Spinner) findViewById(R.id.engineerSP);
		delete = (Button) findViewById(R.id.editdeleteBT);
		ret = (Button) findViewById(R.id.editretBT);
		confirm = (Button) findViewById(R.id.editconfirmBT);
		caller = (Button) findViewById(R.id.editcallBT);
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

	private void permission() {
		// TODO Auto-generated method stub
		switch(user.mode){
		}
	}

	@Override
	protected void getContent() {
		// TODO Auto-generated method stub
		super.getContent();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
