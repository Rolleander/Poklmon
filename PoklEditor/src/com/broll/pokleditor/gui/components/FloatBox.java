package com.broll.pokleditor.gui.components;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FloatBox extends JPanel{

	private JTextField  field;
	
	public FloatBox(String name, int width)
	{
		field=new JTextField(width);
		add(new JLabel(name));
		add(field);
	}
	
	public float getValue()
	{
		return Float.parseFloat(field.getText());
	}
	
	public void setValue(float value)
	{
		field.setText(""+value);
	}
}
