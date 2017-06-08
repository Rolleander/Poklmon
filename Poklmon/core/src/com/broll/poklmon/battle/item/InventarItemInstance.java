package com.broll.poklmon.battle.item;

public class InventarItemInstance {

	private int id, count;

	public InventarItemInstance(int id, int count) {
		this.id = id;
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
