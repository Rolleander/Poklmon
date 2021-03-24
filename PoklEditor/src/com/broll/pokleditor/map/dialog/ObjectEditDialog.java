package com.broll.pokleditor.map.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.broll.pokleditor.map.objects.MapObjectScript;
import com.broll.pokleditor.map.objects.MapObjectStatus;
import com.broll.pokllib.object.MapObject;

public class ObjectEditDialog {

	private MapObject object;
	private JDialog dialog;
	private MapObjectStatus status = new MapObjectStatus();
	private MapObjectScript script = new MapObjectScript();

	public ObjectEditDialog() {
		dialog = new JDialog();
		dialog.setModal(true);
		dialog.setTitle("Edit Object");
		dialog.setLocationByPlatform(true);
		dialog.setSize(1200, 800);
		dialog.setMinimumSize(new Dimension(600, 500));
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setLayout(new BorderLayout());
		dialog.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {
			}

			public void windowIconified(WindowEvent e) {
			}

			public void windowDeiconified(WindowEvent e) {
			}

			public void windowDeactivated(WindowEvent e) {
			}

			public void windowClosing(WindowEvent e) {
				save();
			}

			public void windowClosed(WindowEvent e) {
			}

			public void windowActivated(WindowEvent e) {
			}
		});
		dialog.add(status.getContent(), BorderLayout.NORTH);
		dialog.add(script.getContent(), BorderLayout.CENTER);
	}

	public void open(MapObject object) {
		status.setMapObject(object);
		script.setMapObject(object);
		dialog.setVisible(true);	
	}

	private void save() {
		status.save();
		script.save();
	}
}
