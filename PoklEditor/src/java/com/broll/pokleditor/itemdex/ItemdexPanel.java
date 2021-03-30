package com.broll.pokleditor.itemdex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;

import com.broll.pokleditor.data.PoklData;
import com.broll.pokleditor.gui.GraphicLoader;
import com.broll.pokleditor.window.EditorWindow;
import com.broll.pokllib.item.Item;
import com.broll.pokllib.item.ItemType;

public class ItemdexPanel extends JDialog {

	private ItemList itemlist;
	private ItemEditPanel itemEditPanel = new ItemEditPanel();
	private int lastID;

	public ItemdexPanel() {

		setModal(true);
		setTitle("Item Database");
		  setIconImage(GraphicLoader.loadImage("key.png"));
		setSize(new Dimension(1000, 600));
		setMinimumSize(new Dimension(1000, 600));
		setLocationRelativeTo(EditorWindow.frame);

		itemEditPanel.setVisible(false);
		itemlist = new ItemList(this);

		setLayout(new BorderLayout());
		itemlist.setPreferredSize(new Dimension(150, 0));

		add(itemlist, BorderLayout.WEST);
		add(itemEditPanel, BorderLayout.CENTER);

		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				saveLastChanges();
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
	}

	public void open() {
		this.setVisible(true);
	}

	public void view(int id) {
		saveLastChanges();
		lastID = id;
		itemEditPanel.setVisible(true);
		itemEditPanel.setItem(PoklData.loadItem(id));
	}

	public void saveLastChanges() {
		Item last = itemEditPanel.getItem();
		if (last != null) {
			itemEditPanel.save();
			itemlist.updateListEntry(last.getName(), lastID);
			PoklData.saveItem(last, lastID);
		}
	}

	public void addNewitem(int id) {
		saveLastChanges();
		Item newItem=new Item();
		newItem.setName("New Item");
		newItem.setType(ItemType.OTHER);
		
		lastID = id;
		itemEditPanel.setVisible(true);
		itemEditPanel.setItem(newItem);
		PoklData.saveItem(newItem, id);
	}
}
