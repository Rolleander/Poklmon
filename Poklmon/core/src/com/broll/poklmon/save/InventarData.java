package com.broll.poklmon.save;

import java.util.HashMap;

public class InventarData {

	private HashMap<Integer,ItemEntry> items=new HashMap<Integer,ItemEntry>();
	private int money;
	
	
	public InventarData()
	{
		
	}
	
	public HashMap<Integer, ItemEntry> getItems() {
		return items;
	}
	
	public int getMoney() {
		return money;
	}
	
	public void setMoney(int money) {
		this.money = money;
	}
}
