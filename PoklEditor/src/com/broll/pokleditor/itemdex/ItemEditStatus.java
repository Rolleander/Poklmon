package com.broll.pokleditor.itemdex;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.broll.pokleditor.gui.components.IntBox;
import com.broll.pokleditor.gui.components.ItemTypeBox;
import com.broll.pokleditor.gui.components.StringBox;
import com.broll.pokllib.item.Item;

public class ItemEditStatus extends JPanel{

	private Item item;
	private StringBox name=new StringBox("Name",20); 
	private ItemTypeBox type=new ItemTypeBox("Type");
	private IntBox price=new IntBox("Price", 5);
	
	public ItemEditStatus()
	{
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		add(name);
		add(type);
		add(price);
	}
	
	
	public void setItem(Item item) {
		this.item = item;
		name.setText(item.getName());
		type.setType(item.getType());
		price.setValue(item.getPrice());
	}
	
	public void save()
	{
		item.setName(name.getText());
		item.setType(type.getType());
		item.setPrice(price.getValue());
	}
	
	
}
