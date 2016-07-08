package com.order;

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
			viewHolder.orderusername = (TextView) view.findViewById(R.id.order_username);
			viewHolder.orderusertel = (TextView) view.findViewById(R.id.order_tel);
			view.setTag(viewHolder);
		}else{
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.orderid.setText(""+order.id);
		viewHolder.orderusername.setText(order.name);
		viewHolder.orderusertel.setText(order.tel);
		return view;
	}
	
	class ViewHolder{
		TextView orderid, orderusername, orderusertel;
	}
}
