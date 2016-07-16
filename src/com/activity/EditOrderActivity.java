package com.activity;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
	private EditText company, name, address, tel, state, score, engineer, saler;
	private Button delete, ret, confirm, caller, audit, take, takecancel, complete, photo, more;
	private String oldscore, imagePath = null;
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
		more = (Button) findViewById(R.id.editdetailBT);
		more.setOnClickListener(this);
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
			if(order.status == 1){
				take.setVisibility(View.GONE);
				takecancel.setVisibility(View.GONE);
				complete.setVisibility(View.GONE);
				photo.setVisibility(View.GONE);
			}else if(order.status == 2){
				if(order.engineerid.equals("*")){
					take.setVisibility(View.GONE);
					takecancel.setVisibility(View.GONE);
					complete.setVisibility(View.GONE);
					photo.setVisibility(View.GONE);
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
		Intent intent = new Intent();
		switch(v.getId()){
		case R.id.editretBT:
			setResult(BACK, intent);
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
					ordercnt.update(order.processid, 7, "Company", params[0], 
							 							"Name", params[1], 
							 							"Address", params[2], 
							 							"Tel", params[3], 
							 							"Score", params[4], 
							 							"Isedit", params[5],
							 							"Timestamp", "" + System.currentTimeMillis());
					return order.id;
				}
				@Override
				protected void onPostExecute(String result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					Toast.makeText(getBaseContext(), "Update Successfully!", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					setResult(OK, intent);
					finish();
				}
			}.execute(company.getText().toString(), name.getText().toString(), address.getText().toString(), tel.getText().toString(), score.getText().toString(), isedi);
			break;
		case R.id.editcallBT:
			intent = new Intent(Intent.ACTION_DIAL);
			intent.setData(Uri.parse("tel:" + tel.getText().toString()));
            startActivity(intent); 
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
					ordercnt.update(order.processid, 2, "Salerid", user.id,
														"Timestamp", "" + System.currentTimeMillis());
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
					ordercnt.claimtask(order.id, 0);
					ordercnt.update(order.processid, 2, "Engineerid", "*",
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
			if(this.mode == 1) dialog.setTitle("Please Input Ondoor Time!");
			else dialog.setTitle("Input Series and Comments, seperate them by '&'!");
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
																	"Engineerid", user.id,
																	"Timestamp", "" + System.currentTimeMillis());
							}else{
								ordercnt.update(order.processid, 3, "Series", a,
																	"Feedback", b,
																	"Timestamp", "" + System.currentTimeMillis());
							}
							if(mode == 1){
								if(ordercnt.isunaccepted(order.id)){
									ordercnt.completetask(order.id);
									String newid = ordercnt.findnewid(order.processid);
									ordercnt.claimtask(newid, 1);
								}else{
									ordercnt.claimtask(order.id, 1);
								}
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
