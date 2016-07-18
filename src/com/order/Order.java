package com.order;

import java.io.Serializable;

public class Order implements Serializable, Comparable{
	
	public static final int ALL = 0;
	public static final int UNACCEPTED = 1;
	public static final int ACCEPTED = 2;
	public static final int COMPLETED = 3;
	public static final int CHECKED = 4;
	
	public String timestamp, company, name, address, tel;
	public String id , salerid, engineerid, score, processid;
	public int status = 0;									//1δ��		2�ѽ�		3�����	4�����
	public String repairtime, series, feedback;
	public String photourl1, photourl2, photourl3, picindex;
	public String installid, warehouseid, isdeliver, isdebug, isondoor, iswarehouse;
	public String isaccepted;
	
	public Order(String id, String processid, String name, String tel, String company, 
				String address, String status, String score, String timestamp, String engineerid, 
				String salerid, String photourl1, String photourl2, String photourl3, String picindex,
				String repairtime, String series, String feedback,
				String isdeliver, String isdebug, String isondoor, String iswarehouse,
				String installid, String warehouseid, String isaccepted){
		this.id = id;
		this.processid = processid;
		this.name = name;
		this.tel = tel;
		this.company = company;
		this.address = address;
		this.engineerid = engineerid;
		this.salerid = salerid;
		this.score = score;
		this.timestamp = timestamp;
		this.photourl1 = photourl1;
		this.photourl2 = photourl2;
		this.photourl3 = photourl3;
		this.picindex = picindex;
		this.repairtime = repairtime;
		this.series = series;
		this.feedback = feedback;
		this.installid = installid;
		this.warehouseid = warehouseid;
		this.isdeliver = isdeliver;
		this.isdebug = isdebug;
		this.isondoor = isondoor;
		this.iswarehouse = iswarehouse;
		this.isaccepted = isaccepted;
		if(status.equalsIgnoreCase("UnAcceptedOrder")){
			if(isaccepted.equals("0")){
				this.status = 1;
			}
			else this.status = 2;
		}else if(status.equalsIgnoreCase("CompletedOrder")) this.status = 3;
		else if(status.equalsIgnoreCase("CheckedOrder")) this.status = 4;
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

	public void setengineerid(String engineer_id) {
		// TODO Auto-generated method stub
		engineerid = engineer_id;
	}

	public void setsalerid(String saler_id) {
		// TODO Auto-generated method stub
		salerid = saler_id;
	}

	public int spinner(String position) {
		// TODO Auto-generated method stub
		if(position.equals("1")) return 1;
		else if(position.equals("0")) return 2;
		return 0;
	}

	@Override
	public int compareTo(Object another) {
		// TODO Auto-generated method stub
		Long long1 = Long.parseLong(timestamp);
		Order anotherorder = (Order) another;
		Long long2 = Long.parseLong(anotherorder.timestamp);
		return long1.compareTo(long2);
	}
}
