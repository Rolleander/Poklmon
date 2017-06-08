package com.broll.pokleditor.gui.components;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.broll.pokllib.object.ObjectDirection;


public class ObjectViewDirectionBox extends JPanel{

	private JComboBox<String> disp;

	public ObjectViewDirectionBox(String text)
	{
		ObjectDirection[] values = ObjectDirection.values();
		String[] names=new String[values.length];
		for(int i=0; i<values.length; i++)
		{
			names[i]=values[i].name();
		}
		disp=new JComboBox<String>(names);		
		
		add(new JLabel(text));
		add(disp);
	}
	
	public ObjectDirection getDirection() {
		int index=disp.getSelectedIndex();
		return ObjectDirection.values()[index];
	}
	
	public void setDirection(ObjectDirection type) {
		disp.setSelectedIndex(type.ordinal());	
	}
	

}
