package com.order;

import java.util.ArrayList;
import java.util.List;

import com.activity.OrderActivity;
import com.example.onlinemaintenance.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class OrderFragment extends Fragment{
	
	private List<Order> orderList = new ArrayList<Order>();
	private ListView listView;
	private OrderAdapter adapter;
	private int id;
	
	public OrderFragment(){
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.tab_layout, container,false);
		return view;
	}
}
