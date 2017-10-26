package com.broll.pokleditor.map;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.broll.pokleditor.data.PoklDataUtil;
import com.broll.pokleditor.gui.components.ObjectList;
import com.broll.pokleditor.gui.graphics.GraphicLoader;

public class MapList extends JPanel{

	private ObjectList list;

	public MapList(final MapPanel mappanel) {

		list=new ObjectList();
		list.initList(PoklDataUtil.getAllMapNames());
		
		list.addSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index=list.getSelectedID();
				mappanel.view(index);
			}
		});
		
		add(list,BorderLayout.CENTER);

		JButton add = new JButton("New Map",GraphicLoader.loadIcon("plus.png"));
		add(add, BorderLayout.SOUTH);

		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id=list.addEntry("New Map");
				mappanel.addNewMap(id);
				
			}
		});

	}


    public void updateListEntry(String name, int id) {
        list.updateEntry(id, name);
    }
	
	
}
