package com.broll.pokleditor.gui.components;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.broll.pokllib.poklmon.EXPLearnTypes;


public class EXPTypeBox extends JPanel{

	private JComboBox<String> disp;

	public EXPTypeBox(String text)
	{
		EXPLearnTypes[] values = EXPLearnTypes.values();
		String[] names=new String[values.length];
		for(int i=0; i<values.length; i++)
		{
			names[i]=values[i].name();
		}
		disp=new JComboBox<String>(names);		
		
		add(new JLabel(text));
		add(disp);
	}
	
	public EXPLearnTypes getType() {
		int index=disp.getSelectedIndex();
		return EXPLearnTypes.values()[index];
	}
	
	public void setType(EXPLearnTypes type) {
		disp.setSelectedIndex(type.ordinal());	
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		
		disp.setEnabled(enabled);
	}
}
