package com.broll.pokleditor.gui.components;

import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class LevelBox extends JPanel{

	private JComboBox<String> level;
	
	public LevelBox(String text)
	{
		String[] levels=new String[101];
		for(int i=0; i<levels.length; i++)
		{
			levels[i]="Lvl. "+i;
		}
		level=new JComboBox<String>(levels);		
		
		add(new JLabel(text));
		add(level);
	}
	
	public int getLevel()
	{
		return level.getSelectedIndex();
	}
	
	public void setLevel(int lvl)
	{
		level.setSelectedIndex(lvl);
	}
	
	public void setListener(ActionListener action)
	{
	    level.addActionListener(action);
	}
}
