package com.broll.pokleditor.pokldex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;

import com.broll.pokleditor.data.PoklData;
import com.broll.pokleditor.gui.graphics.GraphicLoader;
import com.broll.pokleditor.window.EditorWindow;
import com.broll.pokllib.poklmon.Poklmon;

public class PokldexPanel extends JDialog {

	private PokeList pokelist;
	private PoklEditPanel poklEditPanel;
	private int lastID;
	
	public PokldexPanel() {
		setModal(true);
		setTitle("Poklmon Database");
		setIconImage(GraphicLoader.loadImage("poklball.png"));
		setSize(new Dimension(1000, 600));
		setMinimumSize(new Dimension(1000, 600));
		setLocationRelativeTo(EditorWindow.frame);

		poklEditPanel = new PoklEditPanel();
		poklEditPanel.setVisible(false);
		pokelist = new PokeList(this);

		setLayout(new BorderLayout());
		pokelist.setPreferredSize(new Dimension(150, 0));

		add(pokelist, BorderLayout.WEST);
		add(poklEditPanel, BorderLayout.CENTER);

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
		poklEditPanel.setVisible(true);
		poklEditPanel.setPoklmon(PoklData.loadPoklmon(id));
	}

	public void saveLastChanges() {
		Poklmon last = poklEditPanel.getPoklmon();
		if (last != null) {
			poklEditPanel.save();
			pokelist.updateListEntry(last.getName(), lastID);
			PoklData.savePoklmon(last, lastID);
		}
	}

	public void addNewPokemon(int id) {
		saveLastChanges();
		Poklmon newpoklmon = PoklmonGenerator.newPoklmon();
		// add to pokldex
		lastID = id;
		poklEditPanel.setVisible(true);
		poklEditPanel.setPoklmon(newpoklmon);
		PoklData.savePoklmon(newpoklmon, id);
	}

}
