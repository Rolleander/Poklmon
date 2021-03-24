package com.broll.pokleditor.gui.components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.broll.pokleditor.data.PoklDataUtil;
import com.broll.pokleditor.gui.graphics.GraphicLoader;

public class AttackBox extends JPanel {

	private JLabel attack = new JLabel();
	private JButton change;
	private int selected;

	public AttackBox(String text) {
		attack.setMinimumSize(new Dimension(150,0));
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		attack.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		change= GraphicLoader.newIconButton("data_grid.png");
		add(new JLabel(text));
		add(attack);
		add(change);
		change.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = ScriptEntityList.showList(PoklDataUtil.getAllAttackNames(), selected);
				attack.setText(PoklDataUtil.getAllAttackNames().get(selected));
			}
		});

	}

	public int getAttack() {
		return selected;
	}

	public void setAttack(int atk) {
		this.selected = atk;
		attack.setText(PoklDataUtil.getAllAttackNames().get(selected));
	}
}
