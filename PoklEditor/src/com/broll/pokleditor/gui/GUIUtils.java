package com.broll.pokleditor.gui;

import javax.swing.JComboBox;

public class GUIUtils {

	
	public static JComboBox<String> getLevelBox()
	{
		String[] levels=new String[100];
		for(int i=0; i<levels.length; i++)
		{
			levels[i]="Lvl. "+i;
		}
		JComboBox<String> box=new JComboBox<String>(levels);		
		return box;
	}
	
}
