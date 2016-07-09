package com.user;

import java.io.Serializable;

public class User implements Serializable{
	
	public static final int DELIVER = 1;
	public static final int ENGINEER = 2;
	public static final int SALER = 3;
	public static final int ADMIN = 4;
			
	public String name = null;
	public int mode = 0;
	public String nickname, passwd;
	
	public User(String name, int mode, String passwd){
		this.name = name;
		this.mode = mode;
		this.passwd = passwd;
		getNickname();
	}
	
	private void getNickname() {
		// TODO Auto-generated method stub
		nickname = type() + name;
	}

	public String type(){
		String ret = null;
		switch(mode){
		case DELIVER:
			ret="派单员";break;
		case ENGINEER:
			ret="工程师";break;
		case SALER:
			ret="销售员";break;
		case ADMIN:
			ret="管理员";break;
		default: break;
		}
		return ret;
	}

	public void updatepasswd() {//******************
		// TODO Auto-generated method stub
		
	}
}
