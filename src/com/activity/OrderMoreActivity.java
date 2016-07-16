package com.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.activiti.OrderConnect;
import com.activitymanager.BaseActivity;
import com.example.onlinemaintenance.R;
import com.order.Order;

public class OrderMoreActivity extends BaseActivity implements OnClickListener{
	
	private static final int UPDATE_INFO = 1;
	private static final int UPDATE_INFO1 = 2;
	private static final int UPDATE_INFO2 = 3;
	private static final int UPDATE_INFO3 = 4;
	
	private Order order;
	private EditText ondoor, series, feedback;
	private ImageView photo1, photo2, photo3;
	private Button ret;
	private Bitmap pho1, pho2, pho3;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case UPDATE_INFO:
				CharSequence strondoor, strseries, strfeedback;
				strondoor = order.repairtime;
				ondoor.setText(strondoor);
				strseries = order.series;
				Log.d("series", order.series);
				series.setText(strseries);
				strfeedback = order.feedback;
				feedback.setText(strfeedback);
				break;
			case UPDATE_INFO1:
				photo1.setImageBitmap(pho1);
				break;
			case UPDATE_INFO2:
				photo2.setImageBitmap(pho2);
				break;
			case UPDATE_INFO3:
				photo3.setImageBitmap(pho3);
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
		order = (Order)intent.getSerializableExtra("order");
		setContentView(R.layout.more_order);
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
		ret = (Button) findViewById(R.id.moreretBT);
		ondoor = (EditText) findViewById(R.id.ondoorET);
		series = (EditText) findViewById(R.id.seriesET);
		feedback = (EditText) findViewById(R.id.feedbackET);
		ondoor.setEnabled(false);
		series.setEnabled(false);
		feedback.setEnabled(false);
		photo1 = (ImageView) findViewById(R.id.photo1IV);
		photo2 = (ImageView) findViewById(R.id.photo2IV);
		photo3 = (ImageView) findViewById(R.id.photo3IV);
		ret.setOnClickListener(this);
		photo1.setVisibility(View.GONE);
		photo2.setVisibility(View.GONE);
		photo3.setVisibility(View.GONE);
		new Thread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.what = UPDATE_INFO;
				handler.sendMessage(message);
			}
		}).start();
		if(!order.photourl1.equals("*")){
			photo1.setVisibility(View.VISIBLE);
			new AsyncTask <Void, Void, Void>(){
				@Override
				protected Void doInBackground(Void... arg0) {
					// TODO Auto-generated method stub
					OrderConnect ordercnt = new OrderConnect();
					pho1 = ordercnt.getfile(order.photourl1 + "/content");
					return null;
				}
				@Override
				protected void onPostExecute(Void result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					new Thread(new Runnable(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Message message = new Message();
							message.what = UPDATE_INFO1;
							handler.sendMessage(message);
						}
					}).start();
				}
			}.execute();
		}
		if(!order.photourl2.equals("*")){
			photo2.setVisibility(View.VISIBLE);
			new AsyncTask <Void, Void, Void>(){
				@Override
				protected Void doInBackground(Void... arg0) {
					// TODO Auto-generated method stub
					OrderConnect ordercnt = new OrderConnect();
					pho2 = ordercnt.getfile(order.photourl2 + "/content");
					return null;
				}
				@Override
				protected void onPostExecute(Void result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					new Thread(new Runnable(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Message message = new Message();
							message.what = UPDATE_INFO2;
							handler.sendMessage(message);
						}
					}).start();
				}
			}.execute();
		}
		if(!order.photourl3.equals("*")){
			photo3.setVisibility(View.VISIBLE);
			new AsyncTask <Void, Void, Void>(){
				@Override
				protected Void doInBackground(Void... arg0) {
					// TODO Auto-generated method stub
					OrderConnect ordercnt = new OrderConnect();
					pho3 = ordercnt.getfile(order.photourl3 + "/content");
					return null;
				}
				@Override
				protected void onPostExecute(Void result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					new Thread(new Runnable(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Message message = new Message();
							message.what = UPDATE_INFO3;
							handler.sendMessage(message);
						}
					}).start();
				}
			}.execute();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.moreretBT:
			finish();
			break;
		}
	}
}
