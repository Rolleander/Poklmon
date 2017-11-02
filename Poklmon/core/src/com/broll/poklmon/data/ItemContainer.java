package com.broll.poklmon.data;

import com.broll.pokllib.item.Item;

import java.util.List;

public class ItemContainer {

	private List<Item> items;

	public ItemContainer(List<Item> items) {
		this.items = items;
	}

	public Item getItem(int id) {
		if (id > -1 && id < items.size()) {
			return items.get(id);
		} else {
			try {
				throw new DataException("False Item ID! (" + id + ")");
			} catch (DataException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public int getNumberOfItems() {
		return items.size();
	}

}
