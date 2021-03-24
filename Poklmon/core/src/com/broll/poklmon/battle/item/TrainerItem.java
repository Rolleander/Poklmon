package com.broll.poklmon.battle.item;

public class TrainerItem {

	private int id, count;

	public TrainerItem(int id) {
		this.id = id;
		count = 1;
	}

	public TrainerItem(int id, int count) {
		this.id = id;
		this.count = count;
	}

	public boolean use() {
		count--;
		return count <= 0;
	}

	public int getId() {
		return id;
	}

	public void add(int i) {
		count+=i;
	}

}
