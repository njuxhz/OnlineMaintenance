package com.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.activiti.OrderConnect;
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
	
	private static final int OK = 1;
	private static final int BACK = 2;
	private static final int DELETE = 3;
	private static final int UPDATE = 4;
	private static final int COMPLETE = 5;
	
	private User user;
	private Order order;
	private EditText company, name, address, tel, state, score, engineer, saler;
	private Button delete, ret, confirm, caller, audit, take, takecancel, complete, photo;
	private String oldscore;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case UPDATE_INFO:
				CharSequence strcompany, strname, straddress, strtel, strstate, strscore, strengineer, strsaler;
				strcompany = order.company;
				strname = order.name;
				straddress = order.address;
				strtel = order.tel;
				String bufscore = "" + order.score;
				oldscore = order.score;
				strscore = bufscore;
				company.setText(strcompany);
				name.setText(strname);
				address.setText(straddress);
				tel.setText(strtel);
				score.setText(strscore);
				String bufstate = order.getstate();
				strstate = bufstate;
				state.setText(strstate);
				strengineer = order.getengineer();
				engineer.setText(strengineer);
				strsaler = order.getsaler();
				saler.setText(strsaler);
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
		tel = (EditText) findViewById(R.id.telET);
		state = (EditText) findViewById(R.id.stateET);
		score = (EditText) findViewById(R.id.scoreET);
		saler = (EditText) findViewById(R.id.salerET);
		engineer = (EditText) findViewById(R.id.engineerET);
		delete = (Button) findViewById(R.id.editdeleteBT);
		delete.setOnClickListener(this);
		ret = (Button) findViewById(R.id.editretBT);
		ret.setOnClickListener(this);
		confirm = (Button) findViewById(R.id.editconfirmBT);
		confirm.setOnClickListener(this);
		caller = (Button) findViewById(R.id.editcallBT);
		caller.setOnClickListener(this);
		audit = (Button) findViewById(R.id.editauditBT);
		audit.setOnClickListener(this);
		take = (Button) findViewById(R.id.edittakeBT);
		take.setOnClickListener(this);
		takecancel = (Button) findViewById(R.id.edittakecancelBT);
		takecancel.setOnClickListener(this);
		complete = (Button) findViewById(R.id.editcompleteBT);
		complete.setOnClickListener(this);
		photo = (Button) findViewById(R.id.editphotoBT);
		photo.setOnClickListener(this);
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

	private void permission() {//1未接		2已接		3已完成	4已审核
		// TODO Auto-generated method stub
		switch(user.mode){
		case DELIVER:
			if(order.status != 1){
				disableall();
				score.setEnabled(true);
				ret.setVisibility(View.VISIBLE);
				caller.setVisibility(View.VISIBLE);
			}else{
				delete.setVisibility(View.GONE);
				take.setVisibility(View.GONE);
				takecancel.setVisibility(View.GONE);
				complete.setVisibility(View.GONE);
				photo.setVisibility(View.GONE);
			}
			break;
		case ENGINEER:
			disableall();
			if(order.status == 1){
				ret.setVisibility(View.VISIBLE);
				caller.setVisibility(View.VISIBLE);
				take.setVisibility(View.VISIBLE);
			}else if(order.status == 2){
				ret.setVisibility(View.VISIBLE);
				caller.setVisibility(View.VISIBLE);
				take.setVisibility(View.VISIBLE);
				takecancel.setVisibility(View.VISIBLE);
				complete.setVisibility(View.VISIBLE);
				photo.setVisibility(View.VISIBLE);
			}else{
				ret.setVisibility(View.VISIBLE);
				caller.setVisibility(View.VISIBLE);
			}
			break;
		case SALER:
			disableall();
			if(order.status == 3){
				ret.setVisibility(View.VISIBLE);
				caller.setVisibility(View.VISIBLE);
				audit.setVisibility(View.VISIBLE);
			}else{
				ret.setVisibility(View.VISIBLE);
				caller.setVisibility(View.VISIBLE);
			}
			break;
		case ADMIN:
			disableall();
			delete.setVisibility(View.VISIBLE);
			ret.setVisibility(View.VISIBLE);
			caller.setVisibility(View.VISIBLE);
			break;
		}
	}

	private void disableall() {
		// TODO Auto-generated method stub
		company.setEnabled(false);
		name.setEnabled(false);
		address.setEnabled(false);
		tel.setEnabled(false);
		state.setEnabled(false);
		score.setEnabled(false);
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
	protected void getContent() {
		// TODO Auto-generated method stub
		super.getContent();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		OrderConnect ordercnt = new OrderConnect(user);
		Intent intent = new Intent();
		String orderid = order.id;
		String processid = order.processid;
		AlertDialog.Builder dialog = null;
		final record myrecord = new record();
		switch(v.getId()){
		case R.id.editretBT:
			setResult(BACK, intent);
			finish();
			break;
		case R.id.editdeleteBT:
			ordercnt.delete(order.id);
			dialog = new AlertDialog.Builder(EditOrderActivity.this);
			dialog.setTitle("The order has been deleted!");
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			});
			dialog.show();
			intent.putExtra("orderid", orderid);
			setResult(DELETE, intent);
			finish();
			break;
		case R.id.editconfirmBT:
			order.company = company.getText().toString();
			order.name = name.getText().toString();
			order.address = address.getText().toString();
			order.tel = tel.getText().toString();
			order.score = score.getText().toString();
			String isedi = null;
			if(oldscore.equals(order.score)) isedi = "0";
			else isedi = "1";
			ordercnt.update(order.id, 6, "Company", company.getText().toString(), 
										 "Name", name.getText().toString(), 
										 "Address", address.getText().toString(), 
										 "Tel", tel.getText().toString(), 
										 "Score", score.getText().toString(), 
										 "Isedit", isedi);
			dialog = new AlertDialog.Builder(EditOrderActivity.this);
			dialog.setTitle("The order has been updated!");
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			});
			dialog.show();
			intent.putExtra("orderid", orderid);
			intent.putExtra("neworder", order);
			setResult(UPDATE, intent);
			finish();
			break;
		case R.id.editcallBT:
			intent = new Intent(Intent.ACTION_DIAL);
			intent.setData(Uri.parse("tel:" + tel.getText().toString()));
            startActivity(intent); 
			break;
		case R.id.editcompleteBT:
			final EditText series_feedback = new EditText(this);
			dialog = new AlertDialog.Builder(EditOrderActivity.this).setView(series_feedback);
			dialog.setTitle("Input Series and Comments, seperate them by '&'!");
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String total = series_feedback.getText().toString();
					String[] sourceStrArray = total.split("&");
					myrecord.myseries = sourceStrArray[0];
					myrecord.myfeedback = sourceStrArray[1];
				}
			});
			dialog.show();
			ordercnt.update(orderid, 2, "Series", myrecord.myseries,
										"Feedback", myrecord.myfeedback);
		case R.id.editauditBT:
			ordercnt.completetask(orderid);
			showdialog("Your request has been submitted!", COMPLETE);
			break;
		case R.id.edittakeBT:
			ordercnt.completetask(orderid);
			String neworderid1 = ordercnt.findnewid(processid);
			ordercnt.claimtask(neworderid1, 1);
			String neworderid2 = ordercnt.findnewid(processid);
			final EditText ondoor = new EditText(this);
			dialog = new AlertDialog.Builder(EditOrderActivity.this).setView(ondoor);
			dialog.setTitle("Input Series and Comments, seperate them by '&'!");
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					myrecord.myondoor = ondoor.getText().toString();
				}
			});
			ordercnt.update(neworderid2, 2, "Engineerid", user.name,
											"Ondoor", myrecord.myondoor);
			showdialog("Your request has been submitted!", COMPLETE);
			break;
		case R.id.edittakecancelBT:
			ordercnt.claimtask(orderid, 0);
			String neworderid = ordercnt.findnewid(processid);
			ordercnt.update(neworderid, 2, "Engineerid", "*",
											"Ondoor", "*");
			showdialog("Your request has been submitted!", COMPLETE);
			break;
		case R.id.editphotoBT:
			break;
		}
	}
	
	public void showdialog(String title, int resultcode){
		AlertDialog.Builder dialog = new AlertDialog.Builder(EditOrderActivity.this);
		dialog.setTitle(title);
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		});
		Intent intent = new Intent();
		dialog.show();
		setResult(resultcode, intent);
		finish();
	}
	
	class record{
		String myseries, myfeedback, myondoor;
	}
}
