package com.broll.pokleditor.gui.components;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.broll.pokllib.attack.AttackType;


public class AttackTypeBox extends JPanel{

	private JComboBox<String> disp;

	public AttackTypeBox(String text)
	{
		AttackType[] values = AttackType.values();
		String[] names=new String[values.length];
		for(int i=0; i<values.length; i++)
		{
			names[i]=values[i].name();
		}
		disp=new JComboBox<String>(names);		
		
		add(new JLabel(text));
		add(disp);
	}
	
	public AttackType getType() {
		int index=disp.getSelectedIndex();
		return AttackType.values()[index];
	}
	
	public void setType(AttackType type) {
		disp.setSelectedIndex(type.ordinal());	
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		
		disp.setEnabled(enabled);
	}
}
