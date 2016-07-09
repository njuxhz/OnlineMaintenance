package com.order;

import java.io.Serializable;

public class Order implements Serializable{
	public String timestamp, company, name, address, tel;
	public int id = 0, salerid = 0, engineerid = 0;
	public int score = 0;
	public int status = 0;//1δ��		2�ѽ�		3�����	4�����
	public String repairtime, series, feedback;
	public String photourl1, photourl2, photourl3;
	
	public Order(int id, String name, String tel, String company, String address){
		this.id = id;
		this.name = name;
		this.tel = tel;
		this.company = company;
		this.address = address;
		this.status = 1;
		this.timestamp = "" + System.currentTimeMillis();
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
		if(engineerid != 0){
			ret = "����ʦ" + engineerid;
		}else ret = "*";
		return ret;
	}
	
	public String getsaler() {
		// TODO Auto-generated method stub
		String ret = null;
		if(salerid != 0){
			ret = "����Ա" + salerid;
		}else ret = "*";
		return ret;
	}
}
