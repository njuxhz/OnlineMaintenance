package com.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.activitymanager.BaseActivity;
import com.example.onlinemaintenance.R;
import com.order.Order;
import com.order.OrderAdapter;
import com.order.OrderFragment;
import com.user.User;

public class OrderActivity extends BaseActivity implements OnClickListener{
	
	public static final int BACK = 1;
	
	private Button[] orderbt;
	private Button ret;
	private User user;
	public int listmode = -1;
	public int[] permission;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		user = (User)intent.getSerializableExtra("user");
		setContentView(R.layout.tab_manager);
		getView();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	protected void getView() {
		// TODO Auto-generated method stub
		orderbt = new Button[5];
		ret = (Button) findViewById(R.id.orderretBT);
		ret.setOnClickListener(this);
		orderbt[0] = (Button) findViewById(R.id.orderallBT);
		orderbt[1] = (Button) findViewById(R.id.order1BT);
		orderbt[2] = (Button) findViewById(R.id.order2BT);
		orderbt[3] = (Button) findViewById(R.id.order3BT);
		orderbt[4] = (Button) findViewById(R.id.order4BT);
		for(int i = 0; i < 5; i++) orderbt[i].setOnClickListener(this);
		permission = new int[5];
		for(int i = 0; i < 5; i++) permission[i] = -1;
		switch(user.mode){
		case 1:
			permission[0] = permission[1] = permission[2] = permission[3] = permission[4] = 1;
			break;
		case 2:
			permission[1] = permission[2] = 1;
			break;
		case 3:
			permission[3] = permission[4] = 1;
			break;
		case 4:
			permission[0] = permission[1] = permission[2] = permission[3] = permission[4] = 1;
			break;
		}
		for(int i = 0; i < 5; i++){
			if(permission[i] != -1){
				orderbt[i].setVisibility(View.VISIBLE);
			}else orderbt[i].setVisibility(View.GONE);
		} 
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.orderretBT:
			Intent intent = new Intent();
			setResult(BACK, intent);
			finish();
			break;
		case R.id.orderallBT: listmode = 0; break;
		case R.id.order1BT: listmode = 1; break;
		case R.id.order2BT: listmode = 2; break;
		case R.id.order3BT: listmode = 3; break;
		case R.id.order4BT: listmode = 4; break;
		default: break;
		}
		/*OrderFragment f = new OrderFragment();
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.frag, f);
		transaction.commit();*/
		FragmentManager m_fm_text_entry_activity = getFragmentManager();
        OrderFragment m_f_text_entry_activity = (OrderFragment) m_fm_text_entry_activity.findFragmentById(R.id.frag);
        if(m_f_text_entry_activity == null)
        {
            m_f_text_entry_activity = new OrderFragment();
            m_fm_text_entry_activity.beginTransaction().add(R.id.frag, m_f_text_entry_activity).commit();
            m_fm_text_entry_activity.beginTransaction().show(m_f_text_entry_activity);
        }
	}
}
