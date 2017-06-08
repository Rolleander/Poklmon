package com.broll.pokleditor.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.broll.pokleditor.data.PoklDataUtil;
import com.broll.pokleditor.map.MapPreviewPanel;
import com.broll.pokleditor.window.VerticalLayout;
import com.broll.pokllib.main.PoklLib;
import com.broll.pokllib.map.MapData;
import com.broll.pokllib.object.ObjectDirection;

public class TeleportLocationDialog extends JPanel {

	private JCheckBox doStep = new JCheckBox("Step forward");
	private JComboBox<String> maps;
	private JComboBox<String> dir;
	private MapPreviewPanel map;

	public TeleportLocationDialog() {

		map = new MapPreviewPanel();
		loadMap(0);
		setLayout(new BorderLayout());

		add(new JScrollPane(map), BorderLayout.CENTER);

		JPanel right = new JPanel(new VerticalLayout());
		String[] dirs = new String[5];
		dirs[0] = "Keep Direction";
		for (int i = 0; i < 4; i++) {
			dirs[i + 1] = ObjectDirection.values()[i].name();
		}
		dir = new JComboBox<String>(dirs);

		right.add(new JLabel("Map:"));

		maps = new JComboBox<>(PoklDataUtil.getAllMapNames().toArray(new String[0]));
		maps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadMap(maps.getSelectedIndex());
			}
		});
		right.add(maps);

		right.add(new JLabel("New Player Direction:"));
		right.add(dir);
		right.add(doStep);

		add(right, BorderLayout.WEST);

	}

	private void loadMap(int id) {
		try {
			MapData data = new MapData(PoklLib.data().readMap(id));
			map.setMap(data);
			revalidate();
			repaint();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private String getTeleportCommand() {
		String t = "player.teleportPlayer";
		int sel = dir.getSelectedIndex();
		int mapId = map.getMapId();
		int x = map.getSelectionX();
		int y = map.getSelectionY();
		String args = mapId + "," + x + "," + y;
		if (sel > 0) {
			String dir = "ObjectDirection." + ObjectDirection.values()[sel - 1].name();
			if (doStep.isSelected()) {
				t += "Step";
			}
			t += "(" + args + "," + dir + ")";
		} else {
			t += "(" + args + ")";
		}
		return t;
	}

	public static String showTeleportDialog() {

		TeleportLocationDialog dialog = new TeleportLocationDialog();
		JOptionPane.showMessageDialog(null, dialog, "Teleport Location", JOptionPane.PLAIN_MESSAGE);
		return dialog.getTeleportCommand();

	}

}
