package com.broll.pokleditor.window;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.broll.pokleditor.animationdex.AnimationdexPanel;
import com.broll.pokleditor.attackdex.AttackdexPanel;
import com.broll.pokleditor.attackstatistic.AttackStatisticPanel;
import com.broll.pokleditor.gui.GraphicLoader;
import com.broll.pokleditor.itemdex.ItemdexPanel;
import com.broll.pokleditor.main.PoklEditorMain;
import com.broll.pokleditor.panel.EditorPanel;
import com.broll.pokleditor.pokldex.PokldexPanel;

public class EditorWindow {

	public static JFrame frame;
	private static PokldexPanel pokldexPanel;
	private static AttackdexPanel attackdexPanel;
	private static AnimationdexPanel animationdexPanel;
	private static AttackStatisticPanel attackStatisticPanel;
	private static ItemdexPanel itemdexPanel;

	public EditorWindow() {
		frame = new JFrame("PoklEditor");
		frame.setIconImage(GraphicLoader.loadImage("icon.png"));
		frame.setSize(new Dimension(1200, 800));
		frame.setMinimumSize(new Dimension(1200, 800));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent arg0) {
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				int answer = JOptionPane.showConfirmDialog(frame, "Save before closing?", "PoklEditor",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
				switch (answer) {
				case JOptionPane.YES_OPTION:
					writeSaveData(); // falltrough
				case JOptionPane.NO_OPTION:
					// close
					frame.dispose();
					System.exit(0);
				}
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
			}

			@Override
			public void windowActivated(WindowEvent arg0) {
			}
		});
	}

	public void open(JPanel panel) {
		pokldexPanel = new PokldexPanel();
		attackdexPanel = new AttackdexPanel();
		animationdexPanel = new AnimationdexPanel();
		attackStatisticPanel = new AttackStatisticPanel();
		itemdexPanel = new ItemdexPanel();
		frame.setContentPane(panel);
		frame.setVisible(true);
		LoadingWindow.close();
	}

	public static void writeSaveData() {
		// update map
		EditorPanel.map.save();
		// save data into db file
		PoklEditorMain.writeSaveData();
	}

	public static void writeDebugData(){
		// update map
		EditorPanel.map.save();
		// save data into db file
		PoklEditorMain.writeDebugData();
	}

	public static void repaint() {
		frame.repaint();
	}

	public static void repaintAnimations() {
		animationdexPanel.repaint();
	}

	public static void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void openPokldex() {
		pokldexPanel.open();
	}

	public static void openAttackdex() {
		attackdexPanel.open();
	}

	public static void openItemdex() {
		itemdexPanel.open();
	}

	public static void openAnimationdex() {
		animationdexPanel.open();
	}

	public static void openAttackStatistic() {
		attackStatisticPanel.open();
	}

}
