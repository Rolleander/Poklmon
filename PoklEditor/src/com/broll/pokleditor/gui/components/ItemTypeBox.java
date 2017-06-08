package com.broll.pokleditor.gui.components;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.broll.pokllib.attack.AttackType;
import com.broll.pokllib.item.ItemType;
import com.broll.pokllib.poklmon.EXPLearnTypes;
import com.broll.pokllib.poklmon.ElementType;


public class ItemTypeBox extends JPanel{

	private JComboBox<String> disp;

	public ItemTypeBox(String text)
	{
		ItemType[] values = ItemType.values();
		String[] names=new String[values.length];
		for(int i=0; i<values.length; i++)
		{
			names[i]=values[i].name();
		}
		disp=new JComboBox<String>(names);		
		
		add(new JLabel(text));
		add(disp);
	}
	
	public ItemType getType() {
		int index=disp.getSelectedIndex();
		return ItemType.values()[index];
	}
	
	public void setType(ItemType type) {
		disp.setSelectedIndex(type.ordinal());	
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		
		disp.setEnabled(enabled);
	}
}
