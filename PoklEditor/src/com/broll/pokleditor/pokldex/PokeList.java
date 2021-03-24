package com.broll.pokleditor.pokldex;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.broll.pokleditor.data.PoklDataUtil;
import com.broll.pokleditor.gui.components.ObjectList;
import com.broll.pokleditor.gui.graphics.GraphicLoader;

public class PokeList extends JPanel {

	private ObjectList list;
	
	public PokeList(final PokldexPanel pokldex) {

		
	    list=new ObjectList();
		list.initList(PoklDataUtil.getAllPoklmonNames());
		
		list.addSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index=list.getSelectedID();
			
				pokldex.view(index);
			}
		});
		
		add(list,BorderLayout.CENTER);
		this.setBorder(BorderFactory.createEtchedBorder());
	

		JButton add = new JButton("New Poklmon",GraphicLoader.loadIcon("plus.png"));
		add(add, BorderLayout.SOUTH);

		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id=list.addEntry("New Pokemon");
				pokldex.addNewPokemon(id);			
			}
		});	
	}

	public void updateListEntry(String name, int id) {
		list.updateEntry(id, name);
	}

	
}
