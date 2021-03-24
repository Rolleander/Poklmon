package com.broll.pokleditor.attackdex;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.broll.pokleditor.data.PoklDataUtil;
import com.broll.pokleditor.gui.components.ObjectList;
import com.broll.pokleditor.gui.graphics.GraphicLoader;

public class AttackList extends JPanel {

	private ObjectList list;

	public AttackList(final AttackdexPanel atkdex) {

		this.setBorder(BorderFactory.createEtchedBorder());
		
		this.setBorder(BorderFactory.createEtchedBorder());
		
	    list=new ObjectList();
		list.initList(PoklDataUtil.getAllAttackNames());
		
		list.addSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index=list.getSelectedID();
				atkdex.view(index);
			}
		});
		
		add(list,BorderLayout.CENTER);
		
		JButton add = new JButton("New Attack",GraphicLoader.loadIcon("plus.png"));
		add(add, BorderLayout.SOUTH);

		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id=list.addEntry("New Attack");
				atkdex.addNewAttack(id);
			}
		});

	}

	public void updateListEntry(String name, int id) {
		list.updateEntry(id, name);
	}
}
