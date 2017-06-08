package com.broll.pokleditor.gui.components;

import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class IntRangeBox extends JPanel{

	private JComboBox<String> value;
	private int start;
	
	public IntRangeBox(String text,int start, int end)
	{
		this.start=start;
		String[] vals=new String[end-start];
		for(int i=start; i<end; i++)
		{
			vals[i]=""+(start+i);
		}
		value=new JComboBox<String>(vals);		
		
		add(new JLabel(text));
		add(value);
	}
	
	public int getValue()
	{
		return start+value.getSelectedIndex();
	}
	


}
