package com.order;

public class Order {
	public String timestamp, company, name, address, tel;
	public int id = 0, salerid = 0, engineerid = 0;
	public int score = 0;
	public int status = 0;//1δ��		2�ѽ�		3�����	4�����
	
	public Order(int id, String name, String tel){
		this.id = id;
		this.name = name;
		this.tel = tel;
	}
}
