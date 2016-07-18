package com.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.activiti.GetUser;
import com.activiti.OrderConnect;
import com.activitymanager.BaseActivity;
import com.example.onlinemaintenance.R;
import com.order.Order;
import com.user.User;

public class EditOrderActivity extends BaseActivity implements OnClickListener{

	private static final int UPDATE_INFO = 1;
	
	private static final int CHOOSE_PHOTO = 1;
	
	public static final int DELIVER = 1;
	public static final int ENGINEER = 2;
	public static final int SALER = 3;
	public static final int ADMIN = 4;
	
	private static final int OK = 1;
	private static final int BACK = 2;
	
	private User user;
	private Order order;
	private GetUser getuser;
	private EditText company, name, address, tel, state, score;
	private Button delete, ret, confirm, caller, audit, take, takecancel, complete, photo, more;
	private EditText installid, warehouseid;
	private Spinner isdeliver, isdebug, isondoor, iswarehouse;
	private List<String> deliverlist = new ArrayList<String>();
	private List<String> debuglist = new ArrayList<String>();
	private List<String> ondoorlist = new ArrayList<String>();
	private List<String> warehouselist = new ArrayList<String>();
	private ArrayAdapter<String> deliveradapter, debugadapter, ondooradapter, warehouseadapter;
	private String selectdeliver, selectdebug, selectondoor, selectwarehouse;
	private Spinner engineer, saler;
	private List<String> engineerlist = new ArrayList<String>();
	private List<String> salerlist = new ArrayList<String>();
	private ArrayAdapter<String> engineeradapter, saleradapter;
	private String selectengineer, selectsaler;
	private String oldscore, imagePath = null;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case UPDATE_INFO:
				CharSequence strcompany, strname, straddress, strtel, strstate, strscore;
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
				if(order.engineerid == "*") engineer.setSelection(0, true);
				else{
					int position = 0;
					for(String str : engineerlist){
						if(str.equals(order.engineerid)){
							engineer.setSelection(position, true);
							break;
						}
						position++;
					}
				}
				if(order.salerid == "*") saler.setSelection(0, true);
				else{
					int position = 0;
					for(String str : salerlist){
						if(str.equals(order.salerid)){
							saler.setSelection(position, true);
							break;
						}
						position++;
					}
				}
				isdeliver.setSelection(order.spinner(order.isdeliver), true);
				isdebug.setSelection(order.spinner(order.isdebug), true);
				isondoor.setSelection(order.spinner(order.isondoor), true);
				iswarehouse.setSelection(order.spinner(order.iswarehouse), true);
				CharSequence strinstallid, strwarehouseid;
				strinstallid = order.installid;
				strwarehouseid = order.warehouseid;
				installid.setText(strinstallid);
				warehouseid.setText(strwarehouseid);
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
		engineer = (Spinner)findViewById(R.id.engineerSP);
		saler = (Spinner)findViewById(R.id.salerSP);
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
		more = (Button) findViewById(R.id.editdetailBT);
		more.setOnClickListener(this);
		installid = (EditText) findViewById(R.id.installidET);
		warehouseid = (EditText) findViewById(R.id.warehouseidET);
		isdeliver = (Spinner)findViewById(R.id.isdeliverSP);
		isdebug = (Spinner)findViewById(R.id.isdebugSP);
		isondoor = (Spinner)findViewById(R.id.isondoorSP);
		iswarehouse = (Spinner)findViewById(R.id.iswarehouseSP);
		setspinner();
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
				selectdeliver = order.isdeliver;
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
				selectdebug = order.isdeliver;
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
				selectondoor = order.isondoor;
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
				selectwarehouse = order.iswarehouse;
			}    
        });
		
		engineerlist.clear(); salerlist.clear();
		engineeradapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, engineerlist);
		saleradapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, salerlist);
		
		new AsyncTask <Void, Void, Void>(){
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				getuser = new GetUser("kermit", "kermit");
				return null;
			}
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				engineerlist.add("*");
				for(User userr : getuser.userList){
					if(userr.mode == ENGINEER){
						engineerlist.add(userr.id);
					}
				}
				engineeradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				engineer.setAdapter(engineeradapter);
				engineer.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
					@Override
					public void onItemSelected(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						selectengineer = engineeradapter.getItem(position);
					}
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
						selectengineer = "*";
					}    
		        });
				
				salerlist.add("*");
				for(User userr : getuser.userList){
					if(userr.mode == SALER){
						salerlist.add(userr.id);
					}
				}
				saleradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				saler.setAdapter(saleradapter);
				saler.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
					@Override
					public void onItemSelected(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						selectsaler = saleradapter.getItem(position);
					}
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
						selectsaler = "*";
					}    
		        });
				
				engineeradapter.notifyDataSetChanged();
				saleradapter.notifyDataSetChanged();
				
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
		}.execute();
	}
	
	private void permission() {//1未接		2已接		3已完成	4已审核
		// TODO Auto-generated method stub
		switch(user.mode){
		case DELIVER:
			if(order.status == 1){
				state.setEnabled(false);
				take.setVisibility(View.GONE);
				takecancel.setVisibility(View.GONE);
				complete.setVisibility(View.GONE);
				photo.setVisibility(View.GONE);
				audit.setVisibility(View.GONE);
			}else if(order.status == 2){
				if(order.engineerid.equals("*")){
					state.setEnabled(false);
					take.setVisibility(View.GONE);
					takecancel.setVisibility(View.GONE);
					complete.setVisibility(View.GONE);
					photo.setVisibility(View.GONE);
					audit.setVisibility(View.GONE);
				}else{
					disableall();
					score.setEnabled(true);
					ret.setVisibility(View.VISIBLE);
					confirm.setVisibility(View.VISIBLE);
					caller.setVisibility(View.VISIBLE);
					delete.setVisibility(View.VISIBLE);
				}
			}else{
				disableall();
				score.setEnabled(true);
				ret.setVisibility(View.VISIBLE);
				confirm.setVisibility(View.VISIBLE);
				caller.setVisibility(View.VISIBLE);
				delete.setVisibility(View.VISIBLE);
			}
			break;
		case ENGINEER:
			disableall();
			if(order.status == 1){
				ret.setVisibility(View.VISIBLE);
				caller.setVisibility(View.VISIBLE);
				take.setVisibility(View.VISIBLE);
			}else if(order.status == 2){
				if(order.engineerid.equals("*")){
					ret.setVisibility(View.VISIBLE);
					caller.setVisibility(View.VISIBLE);
					take.setVisibility(View.VISIBLE);
				}else{
					ret.setVisibility(View.VISIBLE);
					caller.setVisibility(View.VISIBLE);
					takecancel.setVisibility(View.VISIBLE);
					complete.setVisibility(View.VISIBLE);
					photo.setVisibility(View.VISIBLE);
				}
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
		if(order.status == 1){
			more.setVisibility(View.GONE);
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
		installid.setEnabled(false);
		warehouseid.setEnabled(false);
		isdeliver.setEnabled(false);
		isdebug.setEnabled(false);
		isondoor.setEnabled(false);
		iswarehouse.setEnabled(false);
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
		switch(v.getId()){
		case R.id.editretBT:
			Intent intentback = new Intent();
			setResult(BACK, intentback);
			finish();
			break;
		case R.id.editdeleteBT:
			new AsyncTask <String, Void, Void>(){
				@Override
				protected Void doInBackground(String... params) {
					// TODO Auto-generated method stub
					OrderConnect ordercnt = new OrderConnect(user);
					ordercnt.deletetask(order.id);
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
		case R.id.editconfirmBT:
			order.company = company.getText().toString();
			order.name = name.getText().toString();
			order.address = address.getText().toString();
			order.tel = tel.getText().toString();
			order.score = score.getText().toString();
			String isedi = null;
			if(oldscore.equals(order.score)) isedi = "0";
			else isedi = "1";
			new AsyncTask <String, Void, String>(){
				@Override
				protected String doInBackground(String... params) {
					// TODO Auto-generated method stub
					OrderConnect ordercnt = new OrderConnect(user);
					if((!selectengineer.equals("*")) && (!selectsaler.equals("*"))){
						ordercnt.update(order.processid, 16, "Company", params[0], 
							 							"Name", params[1], 
							 							"Address", params[2], 
							 							"Tel", params[3], 
							 							"Score", params[4], 
							 							"Isedit", params[5],
							 							"Timestamp", "" + System.currentTimeMillis(),
							 							"Isdeliver", selectdeliver, "Isdebug", selectdebug, "Isondoor", selectondoor, "Iswarehouse", selectwarehouse,
														"Installid", installid.getText().toString(), "Warehouseid", warehouseid.getText().toString(),
														"Engineerid", params[6],
														"Salerid", params[7],
														"Isaccepted", "0");
						return order.id;
					}else return "-1";
					
				}
				@Override
				protected void onPostExecute(String result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					if(!result.equals("-1")){
						Toast.makeText(getBaseContext(), "Update Successfully!", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						setResult(OK, intent);
						finish();
					}else{
						Toast.makeText(getBaseContext(), "Update Error!", Toast.LENGTH_SHORT).show();
					}
				}
			}.execute(company.getText().toString(), name.getText().toString(), 
					address.getText().toString(), tel.getText().toString(), score.getText().toString(), isedi,
					selectengineer, selectsaler);
			break;
		case R.id.editcallBT:
			Intent intentcall = new Intent();
			intentcall = new Intent(Intent.ACTION_DIAL);
			intentcall.setData(Uri.parse("tel:" + tel.getText().toString()));
            startActivity(intentcall); 
			break;
		case R.id.editcompleteBT:
			myThread mythreadcomplete = new myThread(2);
			break;
		case R.id.editauditBT:
			new AsyncTask <Void, Void, Void>(){
				@Override
				protected Void doInBackground(Void... params) {
					// TODO Auto-generated method stub
					OrderConnect ordercnt = new OrderConnect(user);
					ordercnt.update(order.processid, 1, "Timestamp", "" + System.currentTimeMillis());
					ordercnt.completetask(order.id);
					return null;
				}
				@Override
				protected void onPostExecute(Void result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					Toast.makeText(getBaseContext(), "Request Done Successfully!", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					setResult(OK, intent);
					finish();
				}
			}.execute();
			break;
		case R.id.edittakeBT:
			myThread mythreadtake = new myThread(1);
			break;
		case R.id.edittakecancelBT:
			new AsyncTask <Void, Void, Void>(){
				@Override
				protected Void doInBackground(Void... params) {
					// TODO Auto-generated method stub
					OrderConnect ordercnt = new OrderConnect(user);
					//ordercnt.claimtask(order.id, 0);
					ordercnt.update(order.processid, 3, "Ondoor", "*",
														"Isaccepted", "0",
														"Timestamp", "" + System.currentTimeMillis());
					return null;
				}
				@Override
				protected void onPostExecute(Void result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					Toast.makeText(getBaseContext(), "Request Done Successfully!", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					setResult(OK, intent);
					finish();
				}
			}.execute();
			break;
		case R.id.editphotoBT:
			Intent photointent = new Intent("android.intent.action.GET_CONTENT");
			photointent.setType("image/*");
			startActivityForResult(photointent, CHOOSE_PHOTO);
			break;
		case R.id.editdetailBT:
			Intent moreintent = new Intent(EditOrderActivity.this, OrderMoreActivity.class);
			moreintent.putExtra("order", order);
			startActivity(moreintent);
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == CHOOSE_PHOTO){
			if(resultCode == RESULT_OK){
				if(Build.VERSION.SDK_INT >= 19){
					handleImageOnKitKat(data);
					new AsyncTask <String, Void, Void>(){
						@Override
						protected Void doInBackground(String... params) {
							// TODO Auto-generated method stub
							OrderConnect ordercnt = new OrderConnect(user);
							if(params[0] != null){
								String path = ordercnt.uploadimage(imagePath, order.id, order.processid);
								if(path != null){
									int nextpic = Integer.parseInt(order.picindex);
									int updatepicindex = nextpic + 1;
									if(updatepicindex == 4) updatepicindex = 1;
									switch(nextpic){
									case 1:
										ordercnt.update(order.processid, 2, "Photourl1", path,
																			"Picindex", "" + updatepicindex);
										break;
									case 2:
										ordercnt.update(order.processid, 2, "Photourl2", path,
																			"Picindex", "" + updatepicindex);
										break;
									case 3:
										ordercnt.update(order.processid, 2, "Photourl3", path,
																			"Picindex", "" + updatepicindex);
										break;
									}
								}
							}
							return null;
						}
						@Override
						protected void onPostExecute(Void result) {
							// TODO Auto-generated method stub
							super.onPostExecute(result);
							Toast.makeText(getBaseContext(), "Upload Photo Successfully!", Toast.LENGTH_SHORT).show();
							Intent intent = new Intent();
							setResult(OK, intent);
							finish();
						}
					}.execute(imagePath);
				}
			}
		}
	}

	private void handleImageOnKitKat(Intent data) {
		// TODO Auto-generated method stub
		Uri uri = data.getData();
		if(DocumentsContract.isDocumentUri(this, uri)){
			String docId = DocumentsContract.getDocumentId(uri);
			if("com.android.providers.media.documents".equals(uri.getAuthority())){
				String id = docId.split(":")[1];
				String selection = MediaStore.Images.Media._ID + "=" + id;
				imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
			}else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
				Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
				imagePath = getImagePath(contentUri, null);
			}else if("content".equalsIgnoreCase(uri.getScheme())){
				imagePath = getImagePath(uri, null);
			}
		}
	}

	private String getImagePath(Uri uri, String selection) {
		// TODO Auto-generated method stub
		String path = null;
		Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
		if(cursor != null){
			if(cursor.moveToFirst()){
				path = cursor.getString(cursor.getColumnIndex(Media.DATA));
			}
			cursor.close();
		}
		return path;
	}
	
	class myThread{
		private int mode;
		private String a, b;
		public myThread(int mod){
			this.mode = mod;
			AlertDialog.Builder dialog = new AlertDialog.Builder(EditOrderActivity.this);
			if(this.mode == 1){
				Toast.makeText(getBaseContext(), "Please Input Ondoor Time!", Toast.LENGTH_SHORT).show();
				dialog.setTitle("Please Input Ondoor Time!");
			}
			else{
				Toast.makeText(getBaseContext(), "Input Series and Comments, seperate them by '&'!", Toast.LENGTH_SHORT).show();
				dialog.setTitle("Input Series and Comments, seperate them by '&'!");
			}
			final EditText mytext = new EditText(EditOrderActivity.this);
			dialog = new AlertDialog.Builder(EditOrderActivity.this).setView(mytext);
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if(mode == 1){
						a = mytext.getText().toString();
					}else{
						String total = mytext.getText().toString();
						String[] sourceStrArray = total.split("&");
						a = sourceStrArray[0];
						b = sourceStrArray[1];
					}
					new AsyncTask <Void, Void, Void>(){
						@Override
						protected Void doInBackground(Void... arg0) {
							// TODO Auto-generated method stub
							OrderConnect ordercnt = new OrderConnect(user);
							if(mode == 1){
								ordercnt.update(order.processid, 3, "Ondoor", a,
																	"Isaccepted", "1",
																	"Timestamp", "" + System.currentTimeMillis());
							}else{
								ordercnt.update(order.processid, 4, "Series", a,
																	"Feedback", b,
																	"Isaccepted", "1",
																	"Timestamp", "" + System.currentTimeMillis());
							}
							if(mode == 1){
							}else{
								ordercnt.completetask(order.id);
							}
							return null;
						}
						@Override
						protected void onPostExecute(Void result) {
							// TODO Auto-generated method stub
							super.onPostExecute(result);
							Toast.makeText(getBaseContext(), "Request Done Successfully!", Toast.LENGTH_SHORT).show();
							Intent intent = new Intent();
							setResult(OK, intent);
							finish();
						}
						@Override
						protected void onPreExecute() {
							// TODO Auto-generated method stub
							super.onPreExecute();
						}
					}.execute();
				}
			});
			dialog.show();
		}
	}
}
