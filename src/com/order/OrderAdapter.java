package com.order;

import java.text.SimpleDateFormat;
import java.util.List;

import com.example.onlinemaintenance.R;
import com.user.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class OrderAdapter extends ArrayAdapter<Order>{
	private int resourceId;
	
	public OrderAdapter(Context context, int textViewResourceId, List<Order> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Order order = getItem(position);
		View view;
		ViewHolder viewHolder;
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.orderid = (TextView) view.findViewById(R.id.order_id);
			viewHolder.ordertimestamp = (TextView) view.findViewById(R.id.order_time);
			view.setTag(viewHolder);
		}else{
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.orderid.setText(""+order.id);
		SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		java.util.Date dt = new java.util.Date(Long.parseLong(order.timestamp));  
		String sDateTime = sdf.format(dt);
		viewHolder.ordertimestamp.setText(sDateTime);
		return view;
	}
	
	class ViewHolder{
		TextView orderid, ordertimestamp;
	}
}
