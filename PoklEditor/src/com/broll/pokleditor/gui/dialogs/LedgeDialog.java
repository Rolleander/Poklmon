package com.broll.pokleditor.gui.dialogs;

import javax.swing.JOptionPane;

import com.broll.pokllib.object.ObjectDirection;

public class LedgeDialog {

	public static ObjectDirection showLedgeDialog() {

		String[] options = new String[4];
		for (int i = 0; i < 4; i++) {
			options[i] = ObjectDirection.values()[i].name();
		}
		int select = JOptionPane.showOptionDialog(null, "", "Ledge Direction", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, 0);
		return ObjectDirection.values()[select];

	}

}
