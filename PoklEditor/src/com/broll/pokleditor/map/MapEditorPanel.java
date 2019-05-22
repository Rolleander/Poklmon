package com.broll.pokleditor.map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.broll.pokllib.map.MapData;
import com.broll.pokllib.object.MapObject;

public class MapEditorPanel extends JPanel {

	public final static Color background = new Color(50, 50, 50);
	private MapData map;
	private MapTileEditor editor;
	private MapEditSettings settings;

	public MapEditorPanel() {
		editor = new MapTileEditor();
		settings=new MapEditSettings();
	
		setLayout(new BorderLayout());

		JScrollPane scroll = new JScrollPane(editor);
		scroll.setBackground(background);
		scroll.getVerticalScrollBar().setUnitIncrement(32);
		scroll.getHorizontalScrollBar().setUnitIncrement(32);
		add(scroll, BorderLayout.CENTER);
		add(settings,BorderLayout.SOUTH);
	}

	public void openMap(MapData data) {
		map = data;
		if(map.getFile().getObjects()==null)
		{
		    map.getFile().setObjects(new ArrayList<MapObject>());
		}
		System.out.println("OpenMap: "+data.getFile().getName());
		editor.setMap(data);
		settings.setMap(data);
		revalidate();
		repaint();
	}

	public void save() {
		editor.save();
		settings.save();
	}

	public MapData getMap() {
		return map;
	}
}
