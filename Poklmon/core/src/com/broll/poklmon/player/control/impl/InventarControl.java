package com.broll.poklmon.player.control.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.broll.pokllib.item.ItemType;
import com.broll.poklmon.battle.item.InventarItemInstance;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.player.PlayerGameData;
import com.broll.poklmon.player.control.InventarControlInterface;
import com.broll.poklmon.save.InventarData;
import com.broll.poklmon.save.ItemEntry;

public class InventarControl implements InventarControlInterface {

	private InventarData inventar;
	private DataContainer data;

	public InventarControl(DataContainer data, PlayerGameData playerData) {
		inventar = playerData.getGameVariables().getInventar();
		this.data = data;
	}

	@Override
	public void addItem(int itemID) {
		if (hasItem(itemID)) {
			// add count
			ItemEntry itemEntry = inventar.getItems().get(itemID);
			int count = itemEntry.getCount();
			itemEntry.setCount(count + 1);
		} else {
			// add new
			inventar.getItems().put(itemID, new ItemEntry());
		}
	}

	@Override
	public boolean hasItem(int itemID) {
		return inventar.getItems().containsKey(itemID);
	}

	@Override
	public int getItemCount(int itemID) {
		if (hasItem(itemID)) {
			return inventar.getItems().get(itemID).getCount();
		}
		return 0;
	}

	@Override
	public void useItem(int itemID) {
		if (hasItem(itemID)) {
			ItemEntry itemEntry = inventar.getItems().get(itemID);
			int count = itemEntry.getCount();
			count = count - 1;
			if (count > 0) {
				itemEntry.setCount(count);
			} else {
				// remove entry
				inventar.getItems().remove(itemID);
			}
		}
	}

	@Override
	public void addMoney(int money) {
		int newMoney = money + inventar.getMoney();
		if (newMoney < 0) {
			newMoney = 0;
		}
		inventar.setMoney(newMoney);
	}

	@Override
	public boolean hasEnoughMoney(int moneyCheck) {
		return inventar.getMoney() >= moneyCheck;
	}

	@Override
	public int getMoney() {
		return inventar.getMoney();
	}

	@Override
	public List<InventarItemInstance> getAllItems() {
		List<InventarItemInstance> items = new ArrayList<InventarItemInstance>();
		Iterator<Integer> iterator = inventar.getItems().keySet().iterator();
		while (iterator.hasNext()) {
			Integer id = iterator.next();
			int count = inventar.getItems().get(id).getCount();
			items.add(new InventarItemInstance(id, count));
		}
		return items;
	}

	@Override
	public boolean hasItemsOfType(ItemType type) {
		List<InventarItemInstance> items = getAllItems();
		for (int i = 0; i < items.size(); i++) {
			int id = items.get(i).getId();
			if (data.getItems().getItem(id).getType() == type) {
				return true;
			}
		}
		return false;
	}

}
