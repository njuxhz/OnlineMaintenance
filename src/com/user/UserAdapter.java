package com.user;

import java.util.List;

import com.example.onlinemaintenance.R;
import com.user.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class UserAdapter extends ArrayAdapter<User>{
	private int resourceId;
	
	public UserAdapter(Context context, int textViewResourceId, List<User> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		User user = getItem(position);
		View view;
		ViewHolder viewHolder;
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.userid = (TextView) view.findViewById(R.id.user_id);
			viewHolder.usermode = (TextView) view.findViewById(R.id.user_mode);
			viewHolder.userpasswd = (TextView) view.findViewById(R.id.user_passwd);
			view.setTag(viewHolder);
		}else{
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.userid.setText(user.id);
		viewHolder.usermode.setText(user.type());
		viewHolder.userpasswd.setText(user.passwd);
		return view;
	}
	
	class ViewHolder{
		TextView userid, usermode, userpasswd;
	}
}
