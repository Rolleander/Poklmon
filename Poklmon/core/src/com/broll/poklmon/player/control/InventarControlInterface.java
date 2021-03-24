package com.broll.poklmon.player.control;

import com.broll.pokllib.item.ItemType;
import com.broll.poklmon.battle.item.InventarItemInstance;

import java.util.List;

public interface InventarControlInterface {
	
	

	public void addItem(int itemID);
	
	public boolean hasItemsOfType(ItemType type);
	
	public boolean hasItem(int itemID);
	
	public int getItemCount(int itemID);
	
	public void useItem(int itemID);
	
	public void addMoney(int money);
		
	public int getMoney();
	
	public boolean hasEnoughMoney(int moneyCheck);
	
	public List<InventarItemInstance> getAllItems();
}
