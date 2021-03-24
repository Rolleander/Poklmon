package com.broll.pokleditor.gui.components;

import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TransparencyBox extends JPanel{

	
	private static String[] size={"1.0","0.1","0.2","0.3","0.4","0.5","0.6","0.7","0.8","0.9"};
	private JComboBox<String> sizes;
	
	public TransparencyBox(String name)
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
