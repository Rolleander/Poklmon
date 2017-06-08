package com.broll.pokleditor.gui.components;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.broll.pokleditor.data.PoklData;
import com.broll.pokleditor.gui.graphics.GraphicLoader;
import com.broll.pokllib.poklmon.AttackLearnEntry;
import com.broll.pokllib.poklmon.Poklmon;

public class TrainerPoklmonBox extends JPanel {

	private PoklmonBox poklmon = new PoklmonBox("");
	private LevelBox level = new LevelBox("");
	private OptionalAttackBox[] attacks = new OptionalAttackBox[4];

	public TrainerPoklmonBox() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(poklmon);
		add(level);
		for (int i = 0; i < 4; i++) {
			attacks[i] = new OptionalAttackBox("");
			add(attacks[i]);
		}

		JButton rand = new JButton("Default Attacks");
		rand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int lvl = level.getLevel();
				int id = poklmon.getPoklmon();
				Poklmon data = PoklData.loadPoklmon(id);
				List<AttackLearnEntry> atks = data.getAttackList().getAttacks();
				int nr = 0;
				for (int i = atks.size() - 1; i > 0; i--) {
					if (atks.get(i).getLearnLevel() <= lvl) {
						attacks[nr].setAttack(atks.get(i).getAttackNumber());
						nr++;
						if (nr > 3) {
							return;
						}
					}
				}

			}
		});
		add(rand);
	}

	public void setRemoveListener(ActionListener l) {
		JButton kill = GraphicLoader.newIconButton("cross.png");
		kill.addActionListener(l);
		add(kill);
	}

	public int[] getAttacks() {
		int[] atks = new int[attacks.length];
		for (int i = 0; i < attacks.length; i++) {
			atks[i] = attacks[i].getAttack();
		}
		return atks;
	}

	public int getLevel() {
		return level.getLevel();
	}

	public int getPoklmon() {
		return poklmon.getPoklmon();
	}

}
