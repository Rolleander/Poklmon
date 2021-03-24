package com.broll.pokleditor.gui.components;

import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SizeBox extends JPanel{

	
	private static String[] size={"2","1","0.1","0.25","0.5","0.75","1.25","1.5","1.75","2","2.25","2.5","2.75","3","4","5","10"};
	private JComboBox<String> sizes;
	
	public SizeBox(String name)
	{
		setLayout(new FlowLayout(FlowLayout.LEFT));
		 sizes=new JComboBox<String>(size);
			
		 add(new JLabel(name));
		 add(sizes);
	}
	
	public float getFloat()
	{
		return Float.parseFloat(size[sizes.getSelectedIndex()]);
	}
}
