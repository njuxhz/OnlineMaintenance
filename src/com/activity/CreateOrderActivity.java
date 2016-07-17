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

import com.activiti.OrderConnect;
import com.activitymanager.BaseActivity;
import com.example.onlinemaintenance.R;
import com.user.User;

public class CreateOrderActivity extends BaseActivity implements OnClickListener{

	private static final int UPDATE_INFO = 1;
	
	private User user;
	private EditText company, name, address, tel, state, score, engineer, saler;
	private Button ret, complete;
	private Spinner isdeliver, isdebug, isondoor, iswarehouse;
	private List<String> deliverlist = new ArrayList<String>();
	private List<String> debuglist = new ArrayList<String>();
	private List<String> ondoorlist = new ArrayList<String>();
	private List<String> warehouselist = new ArrayList<String>();
	private ArrayAdapter<String> deliveradapter, debugadapter, ondooradapter, warehouseadapter;
	private String selectdeliver, selectdebug, selectondoor, selectwarehouse;
	private EditText installid, warehouseid;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case UPDATE_INFO:
				CharSequence strstate, strengineer, strsaler, strinstallid, strwarehouseid;
				strstate = "Î´½Ó";
				state.setText(strstate);
				strengineer = "*";
				engineer.setText(strengineer);
				strsaler = "*";
				saler.setText(strsaler);
				strinstallid = strwarehouseid = "*";
				installid.setText(strinstallid);
				warehouseid.setText(strwarehouseid);
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
		setContentView(R.layout.new_order);
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
		ret = (Button) findViewById(R.id.editretBT);
		ret.setOnClickListener(this);
		complete = (Button) findViewById(R.id.editcompleteBT);
		complete.setOnClickListener(this);
		state.setEnabled(false);
		engineer.setEnabled(false);
		saler.setEnabled(false);
		isdeliver = (Spinner)findViewById(R.id.isdeliverSP);
		isdebug = (Spinner)findViewById(R.id.isdebugSP);
		isondoor = (Spinner)findViewById(R.id.isondoorSP);
		iswarehouse = (Spinner)findViewById(R.id.iswarehouseSP);
		installid = (EditText) findViewById(R.id.installidET);
		warehouseid = (EditText) findViewById(R.id.warehouseidET);
		setspinner();
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

	private void setspinner() {
		// TODO Auto-generated method stub
		deliverlist.add("*");deliverlist.add("Yes");deliverlist.add("No");
		deliveradapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, deliverlist);
		deliveradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		isdeliver.setAdapter(deliveradapter);
		isdeliver.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(deliveradapter.getItem(position).equals("Yes")){
					selectdeliver = "1";
				}else selectdeliver = "0";
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				selectdeliver = "*";
			}    
        });
		
		debuglist.add("*");debuglist.add("Yes");debuglist.add("No");
		debugadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, debuglist);
		debugadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		isdebug.setAdapter(debugadapter);
		isdebug.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(debugadapter.getItem(position).equals("Yes")){
					selectdebug = "1";
				}else selectdebug = "0";
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				selectdebug = "*";
			}    
        });
		
		ondoorlist.add("*");ondoorlist.add("Yes");ondoorlist.add("No");
		ondooradapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ondoorlist);
		ondooradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		isondoor.setAdapter(ondooradapter);
		isondoor.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(ondooradapter.getItem(position).equals("Yes")){
					selectondoor = "1";
				}else selectondoor = "0";
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				selectondoor = "*";
			}    
        });
		
		warehouselist.add("*");warehouselist.add("Yes");warehouselist.add("No");
		warehouseadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, warehouselist);
		warehouseadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		iswarehouse.setAdapter(warehouseadapter);
		iswarehouse.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(warehouseadapter.getItem(position).equals("Yes")){
					selectwarehouse = "1";
				}else selectwarehouse = "0";
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				selectwarehouse = "*";
			}    
        });
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.editretBT:
			finish();
			break;
		case R.id.editcompleteBT:
			if((!company.getText().toString().equals("")) 
					&& (!name.getText().toString().equals("")) 
					&& (!address.getText().toString().equals("")) 
					&& (!tel.getText().toString().equals("")) 
					&& (!score.getText().toString().equals(""))){
				new AsyncTask <Void, Void, Void>(){
					@Override
					protected Void doInBackground(Void... params) {
						// TODO Auto-generated method stub
						OrderConnect ordercnt = new OrderConnect(user);
						ordercnt.createorder(22, "Name", name.getText().toString(), 
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
												"Isedit", "0",
												"Isdeliver", selectdeliver, "Isdebug", selectdebug, "Isondoor", selectondoor, "Iswarehouse", selectwarehouse,
												"Installid", installid.getText().toString(), "Warehouseid", warehouseid.getText().toString());
						return null;
					}
					@Override
					protected void onPostExecute(Void result) {
						// TODO Auto-generated method stub
						super.onPostExecute(result);
						Toast.makeText(getBaseContext(), "Create Successfully!", Toast.LENGTH_SHORT).show();
					}
				}.execute();
				finish();
			}else{
				Toast.makeText(getBaseContext(), "Please Input Required Items!", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}
}
