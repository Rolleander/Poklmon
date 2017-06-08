package com.broll.pokleditor.gui.components;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ShakestrengthBox extends JPanel{

	private JComboBox<String> frame;
	public static String[] values={"WEAK","MIDDLE","STRONG","EXTREM"};
	
	public ShakestrengthBox(String text)
	{

		frame=new JComboBox<String>(values);		
		
		add(new JLabel(text));
		add(frame);
	}
	
	public int getFrame()
	{
		return frame.getSelectedIndex();
	}
	
	public void setFrame(int lvl)
	{
		frame.setSelectedIndex(lvl);
	}
	
}
