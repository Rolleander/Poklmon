package com.broll.pokleditor.gui.components;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class FrameBox extends JPanel{

	private JComboBox<String> frame;
	
	public FrameBox(String text)
	{
		String[] levels=new String[999];
		for(int i=0; i<levels.length; i++)
		{
			levels[i]="Frame. "+i;
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
