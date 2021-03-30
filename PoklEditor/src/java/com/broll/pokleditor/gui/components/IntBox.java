package com.broll.pokleditor.gui.components;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class IntBox extends JPanel{

	private JTextField  field;
	
	public IntBox(String name, int width)
	{
		field=new JTextField(width);
		
		add(new JLabel(name));
		add(field);
	}
	
	public int getValue()
	{
		return Integer.parseInt(field.getText());
	}
	
	public void setValue(int value)
	{
	    
		field.setText(""+value);
	}
}
