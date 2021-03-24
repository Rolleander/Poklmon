package com.broll.pokleditor.gui.components;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class FrameDurationBox extends JPanel{

	private JComboBox<String> frame;
	
	public FrameDurationBox(String text)
	{
		String[] levels=new String[100];
		for(int i=0; i<levels.length; i++)
		{
			levels[i]=i+" Frames";
		}
		frame=new JComboBox<String>(levels);		
		
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
