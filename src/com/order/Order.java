package com.order;

import java.io.Serializable;

public class Order implements Serializable{
	
	public static final int ALL = 0;
	public static final int UNACCEPTED = 1;
	public static final int ACCEPTED = 2;
	public static final int COMPLETED = 3;
	public static final int CHECKED = 4;
	
	public String timestamp, company, name, address, tel;
	public String id , salerid, engineerid, score, processid;
	public int status = 0;									//1δ��		2�ѽ�		3�����	4�����
	public String repairtime, series, feedback;
	public String photourl1, photourl2, photourl3;
	
	public Order(String id, String processid, String name, String tel, String company, String address, String status, String score, String timestamp){
		this.id = id;
		this.processid = processid;
		this.name = name;
		this.tel = tel;
		this.company = company;
		this.address = address;
		if(status.equalsIgnoreCase("UnAcceptedOrder")) this.status = 1;
		else if(status.equalsIgnoreCase("AcceptedOrder")) this.status = 2;
		else if(status.equalsIgnoreCase("CompletedOrder")) this.status = 3;
		else if(status.equalsIgnoreCase("CheckedOrder")) this.status = 4;
		this.score = score;
		this.timestamp = timestamp;
		this.engineerid = this.salerid = null;
	}

	public String getstate() {
		// TODO Auto-generated method stub
		String ret = null;
		switch(status){
		case 1:
			ret = "δ��";
			break;
		case 2:
			ret = "�ѽ�";
			break;
		case 3:
			ret = "�����";
			break;
		case 4:
			ret = "�����";
			break;
		default: ret = ""; break;
		}
		return ret;
	}

	public String getengineer() {
		// TODO Auto-generated method stub
		String ret = null;
		if(!engineerid.equals("*")){
			ret = "����ʦ" + engineerid;
		}else ret = "*";
		return ret;
	}
	
	public String getsaler() {
		// TODO Auto-generated method stub
		String ret = null;
		if(!salerid.equals("*")){
			ret = "����Ա" + salerid;
		}else ret = "*";
		return ret;
	}

	public void setengineerid(String engineer_id) {
		// TODO Auto-generated method stub
		engineerid = engineer_id;
	}

	public void setsalerid(String saler_id) {
		// TODO Auto-generated method stub
		salerid = saler_id;
	}
}
