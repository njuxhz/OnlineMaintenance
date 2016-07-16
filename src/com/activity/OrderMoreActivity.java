package com.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
				series.setText(strseries);
				strfeedback = order.feedback;
				feedback.setText(strfeedback);
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
