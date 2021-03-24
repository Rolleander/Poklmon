package com.broll.pokleditor.gui.components;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.broll.pokllib.poklmon.ElementType;


public class TypeBox extends JPanel{

	private JComboBox<String> disp;

	public TypeBox(String text)
	{
		ElementType[] values = ElementType.values();
		String[] names=new String[values.length];
		for(int i=0; i<values.length; i++)
		{
			names[i]=values[i].getName();
		}
		disp=new JComboBox<String>(names);		
		
		add(new JLabel(text));
		add(disp);
	}
	
	public ElementType getType() {
		int index=disp.getSelectedIndex();
		return ElementType.values()[index];
	}
	
	public void setType(ElementType type) {
		disp.setSelectedIndex(type.ordinal());	
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		
		disp.setEnabled(enabled);
	}
}
