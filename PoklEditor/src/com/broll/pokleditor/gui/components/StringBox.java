package com.broll.pokleditor.gui.components;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StringBox extends JPanel{

	private JTextField field;
	
	public StringBox(String name, int size)
	{
	    setLayout(new FlowLayout(FlowLayout.LEFT));
		field=new JTextField(size);
		add(new JLabel(name));
		add(field);
	}
	
	public String getText()
	{
		return field.getText();
	}
	
	public void setText(String text)
	{
		field.setText(text);
	}
}
