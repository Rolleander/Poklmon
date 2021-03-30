package com.broll.pokleditor.gui.components;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.broll.pokleditor.gui.GraphicLoader;

public class AttackLearnBox extends JPanel {

	private LevelBox level = new LevelBox("Learn at");
	private AttackBox attack = new AttackBox("");

	public AttackLearnBox() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
		add(level);
		add(attack);

	}

	public void setActionListener(ActionListener l) {
		JButton kill = GraphicLoader.newIconButton("cross.png");
		kill.addActionListener(l);
		add(kill);
	}

	public int getLevel() {
		return level.getLevel();
	}

	public int getAttack() {
		return attack.getAttack();
	}

	public void setLevel(int lvl) {
		level.setLevel(lvl);
	}

	public void setAttack(int atk) {
		attack.setAttack(atk);
	}
}
