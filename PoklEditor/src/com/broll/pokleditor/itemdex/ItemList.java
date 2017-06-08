package com.broll.pokleditor.itemdex;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.broll.pokleditor.data.PoklData;
import com.broll.pokleditor.data.PoklDataUtil;
import com.broll.pokleditor.gui.components.ObjectList;
import com.broll.pokleditor.gui.graphics.GraphicLoader;
import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.poklmon.Poklmon;

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
