package com.broll.pokleditor.itemdex;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.broll.pokleditor.data.PoklDataUtil;
import com.broll.pokleditor.gui.components.ObjectList;
import com.broll.pokleditor.gui.graphics.GraphicLoader;

public class ItemList extends JPanel {

	private ObjectList list;

	public ItemList(final ItemdexPanel itemindex) {

		this.setBorder(BorderFactory.createEtchedBorder());
		
		this.setBorder(BorderFactory.createEtchedBorder());
		
	    list=new ObjectList();
		list.initList(PoklDataUtil.getAllItemNames());
		
		list.addSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index=list.getSelectedID();
				itemindex.view(index);
			}
		});
		
		add(list,BorderLayout.CENTER);
		
		JButton add = new JButton("New Item",GraphicLoader.loadIcon("plus.png"));
		add(add, BorderLayout.SOUTH);

		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id=list.addEntry("New Item");
				itemindex.addNewitem(id);
			}
		});

	}

	public void updateListEntry(String name, int id) {
		list.updateEntry(id, name);
	}
}
