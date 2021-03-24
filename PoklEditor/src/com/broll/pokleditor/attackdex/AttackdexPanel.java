package com.broll.pokleditor.attackdex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;

import com.broll.pokleditor.data.PoklData;
import com.broll.pokleditor.gui.components.SaveListener;
import com.broll.pokleditor.gui.graphics.GraphicLoader;
import com.broll.pokleditor.window.EditorWindow;
import com.broll.pokllib.attack.Attack;

public class AttackdexPanel extends JDialog {

	private AttackList attacklist;
	private AttackEditPanel attackEditPanel = new AttackEditPanel(new SaveListener() {
		
		@Override
		public void saveChanges() {
			saveLastChanges();
		}
	});
	private int lastID;

	public AttackdexPanel() {

		setModal(true);
		setTitle("Attack Database");
		  setIconImage(GraphicLoader.loadImage("fire.png"));
		setSize(new Dimension(1000, 600));
		setMinimumSize(new Dimension(1000, 600));
		setLocationRelativeTo(EditorWindow.frame);

		attackEditPanel.setVisible(false);
		attacklist = new AttackList(this);

		setLayout(new BorderLayout());
		attacklist.setPreferredSize(new Dimension(150, 0));

		add(attacklist, BorderLayout.WEST);
		add(attackEditPanel, BorderLayout.CENTER);

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
		attackEditPanel.setVisible(true);
		attackEditPanel.setAttack(PoklData.loadAttack(id));
	}

	public void saveLastChanges() {
		Attack last = attackEditPanel.getAttack();
		if (last != null) {
			attackEditPanel.save();
			attacklist.updateListEntry(last.getName(), lastID);
			PoklData.saveAttack(last, lastID);
		}
	}

	public void addNewAttack(int id) {
		saveLastChanges();
		Attack newAttack = AttackGenerator.newAtack();
		lastID = id;
		attackEditPanel.setVisible(true);
		attackEditPanel.setAttack(newAttack);
		PoklData.saveAttack(newAttack, id);
	}
}
