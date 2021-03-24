package com.broll.poklmon.menu.inventar;

import com.broll.pokllib.item.Item;


public class InventarItem {

	private Item item;
	private int count;

	public InventarItem(Item item, int count) {
		this.item = item;
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public Item getItem() {
		return item;
	}
}
