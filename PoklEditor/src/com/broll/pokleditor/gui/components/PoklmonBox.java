package com.broll.pokleditor.gui.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.broll.pokleditor.data.PoklDataUtil;
import com.broll.pokleditor.gui.graphics.GraphicLoader;

public class PoklmonBox extends JPanel {

	public final static String nopoke = "NONE";
	private int poklmon;
	private JLabel pokename = new JLabel(nopoke);

	public PoklmonBox(String text) {
		pokename.setForeground(Color.BLUE);
		add(new JLabel(text));
		add(pokename);
		JButton select = GraphicLoader.newIconButton("setting_tools.png");
		add(select);

		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				ArrayList<String> names = PoklDataUtil.getAllPoklmonNames();
				names.add(0, nopoke);
				int index = poklmon+1;
				int select = ListSelection.openSelection(names, index);

				poklmon = select - 1;
				pokename.setText(names.get(select));

			}
		});
	}

	public int getPoklmon() {
		return poklmon;
	}

	public void setPoklmon(int poklmon) {
		this.poklmon = poklmon;
		if (poklmon == -1) {
			pokename.setText(nopoke);
		} else {
			pokename.setText(PoklDataUtil.getAllPoklmonNames().get(poklmon));
		}
	}
}
