package com.broll.pokleditor.panel;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.broll.pokleditor.map.MapPanel;

public class EditorPanel extends JPanel{

	private Toolbar toolbar=new Toolbar();
	public static MapPanel map=new MapPanel();
	
	public EditorPanel()
	{
		setLayout(new BorderLayout());
		add(toolbar.getBar(),BorderLayout.NORTH);
		add(map,BorderLayout.CENTER);
	}
	
}
