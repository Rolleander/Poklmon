package com.broll.pokleditor.itemdex;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.broll.pokllib.item.Item;

public class ItemEditPanel extends JPanel{

	private Item item;
	private ItemEditStatus status=new ItemEditStatus();
	private ItemEditScript script=new ItemEditScript();
	
	public ItemEditPanel()
	{
		//JPanel p=new JPanel();
		//p.setLayout(new GridLayout(2, 1));
		//p.add(status);

		
		setLayout(new BorderLayout());
		add(status,BorderLayout.NORTH);
		add(script,BorderLayout.CENTER);
	}
	
	public void setItem(Item item) {
		this.item = item;
		status.setItem(item);
		
		script.setItem(item);
	}
	
	public void save()
	{
		status.save();
	
		script.save();
	}
	
	public Item getItem() {
		return item;
	}
	
}
