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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.activiti.GetOrder;
import com.activiti.GetUser;
import com.activiti.OrderConnect;
import com.activitymanager.BaseActivity;
import com.example.onlinemaintenance.R;
import com.order.Order;
import com.user.User;
import com.user.UserAdapter;

public class ScoreActivity extends BaseActivity implements OnClickListener{

	private static final int UPDATE_INFO = 1;
	private static final int UPDATE_SCORE = 2;
	private static final int UPDATE_MONTH = 3;
	
	public static final int DELIVER = 1;
	public static final int ENGINEER = 2;
	public static final int SALER = 3;
	public static final int ADMIN = 4;
	
	private Button m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12;
	private int monthmode, totalscore;
	private Button ret;
	private EditText score, selectmonth;
	private ListView listView;
	private User user;
	private List<User> userList = new ArrayList<User>();
	private UserAdapter adapter;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case UPDATE_INFO:
				score.setText("0");
				selectmonth.setText("0");
				break;
			case UPDATE_MONTH:
				CharSequence strmonth;
				strmonth = "" + monthmode;
				selectmonth.setText(strmonth);
			case UPDATE_SCORE:
				CharSequence strscore;
				strscore = "" + totalscore;
				score.setText(strscore);
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
		setContentView(R.layout.score);
		userList.clear();
		adapter = new UserAdapter(ScoreActivity.this, R.layout.single_user, userList);
		getView();
		initUser();
	}

	private void initUser() {
		// TODO Auto-generated method stub
		userList.clear();
		new AsyncTask <String, Void, Void>(){
			@Override
			protected Void doInBackground(String... arg0) {
				// TODO Auto-generated method stub
				if((user.mode == ADMIN) || (user.mode == DELIVER)){
					GetUser getuser = new GetUser(arg0[0], arg0[1]);
					for(User usr : getuser.userList){
						if((usr.mode == ENGINEER) || (usr.mode == SALER)){
							userList.add(usr);
						}
					}
				}else if((user.mode == ENGINEER) || (user.mode == SALER)){
					userList.add(user);
				}
				return null;
			}
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				adapter.notifyDataSetChanged();
			}
		}.execute(user.id, user.passwd);
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
		monthmode = -1;
		ret = (Button) findViewById(R.id.scoreretBT);
		ret.setOnClickListener(this);
		m1 = (Button) findViewById(R.id.m1BT);m1.setOnClickListener(this);
		m2 = (Button) findViewById(R.id.m2BT);m2.setOnClickListener(this);
		m3 = (Button) findViewById(R.id.m3BT);m3.setOnClickListener(this);
		m4 = (Button) findViewById(R.id.m4BT);m4.setOnClickListener(this);
		m5 = (Button) findViewById(R.id.m5BT);m5.setOnClickListener(this);
		m6 = (Button) findViewById(R.id.m6BT);m6.setOnClickListener(this);
		m7 = (Button) findViewById(R.id.m7BT);m7.setOnClickListener(this);
		m8 = (Button) findViewById(R.id.m8BT);m8.setOnClickListener(this);
		m9 = (Button) findViewById(R.id.m9BT);m9.setOnClickListener(this);
		m10 = (Button) findViewById(R.id.m10BT);m10.setOnClickListener(this);
		m11 = (Button) findViewById(R.id.m11BT);m11.setOnClickListener(this);
		m12 = (Button) findViewById(R.id.m12BT);m12.setOnClickListener(this);
		score = (EditText) findViewById(R.id.totalscoreET);
		score.setEnabled(false);
		selectmonth = (EditText) findViewById(R.id.selectmonthET);
		selectmonth.setEnabled(false);
		listView = (ListView) findViewById(R.id.user_list_view);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				new AsyncTask <Integer, Void, Void>(){
					@Override
					protected Void doInBackground(Integer... params) {
						// TODO Auto-generated method stub
						User userr = (User) listView.getItemAtPosition(params[0]);
						OrderConnect ordercnt = new OrderConnect(userr);
						totalscore = ordercnt.calscore(monthmode);
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
								message.what = UPDATE_SCORE;
								handler.sendMessage(message);
							}
						}).start();
					}
				}.execute(position);
			}});
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.scoreretBT:
			monthmode = -1;
			finish();
			break;
		case R.id.m1BT: monthmode = 1; break;
		case R.id.m2BT: monthmode = 2; break;
		case R.id.m3BT: monthmode = 3; break;
		case R.id.m4BT: monthmode = 4; break;
		case R.id.m5BT: monthmode = 5; break;
		case R.id.m6BT: monthmode = 6; break;
		case R.id.m7BT: monthmode = 7; break;
		case R.id.m8BT: monthmode = 8; break;
		case R.id.m9BT: monthmode = 9; break;
		case R.id.m10BT: monthmode = 10; break;
		case R.id.m11BT: monthmode = 11; break;
		case R.id.m12BT: monthmode = 12; break;
		}
		if(monthmode != -1){
			new Thread(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Message message = new Message();
					message.what = UPDATE_MONTH;
					handler.sendMessage(message);
				}
			}).start();
		}
	}
}
