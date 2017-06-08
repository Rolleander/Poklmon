package com.broll.pokleditor.gui.components;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

public class ListSelection extends JPanel {

	private JList<String> list;

	public ListSelection(ArrayList<String> items, int selected) {
		setLayout(new BorderLayout());

		list = new JList<String>(items.toArray(new String[0]));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(selected);
		JScrollPane scroll = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scroll, BorderLayout.CENTER);

	}

	public int getSelectedIndex() {
		return list.getSelectedIndex();
	}

	public static int openSelection(ArrayList<String> items, int selected) {
		ListSelection list = new ListSelection(items, selected);

		JOptionPane.showMessageDialog(null, list, "Selection", JOptionPane.PLAIN_MESSAGE);

		int sel = list.getSelectedIndex();
		if (sel < 0) {
			sel = 0;
		}

		return sel;
	}
}
