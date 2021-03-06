package com.broll.pokleditor.gui.components;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.broll.pokleditor.data.PoklDataUtil;

public class OptionalAttackBox extends JPanel {

	private JComboBox<String> attack = new JComboBox<String>();

	public OptionalAttackBox(String text) {

		add(new JLabel(text));
		add(attack);
		updateBox();
	}

	public void updateBox() {
		attack.removeAllItems();
		attack.addItem("-No Attack-");
		for (String name : PoklDataUtil.getAllAttackNames()) {
			attack.addItem(name);
		}
	}

	public int getAttack() {
		return attack.getSelectedIndex() - 1;
	}

	public void setAttack(int atk) {
		updateBox();
		attack.setSelectedIndex(atk + 1);
	}
}
