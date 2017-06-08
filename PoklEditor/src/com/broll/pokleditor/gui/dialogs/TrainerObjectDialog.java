package com.broll.pokleditor.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.broll.pokleditor.gui.components.IntRangeBox;
import com.broll.pokleditor.gui.components.LevelBox;
import com.broll.pokleditor.gui.components.TrainerPoklmonBox;
import com.broll.pokleditor.window.VerticalLayout;

public class TrainerObjectDialog extends JPanel {

	private JTextField name = new JTextField(15);
	private JTextField intro = new JTextField(30);
	private JTextField outro = new JTextField(30);
	private JTextField money = new JTextField(7);
	private IntRangeBox range = new IntRangeBox("View Range:", 0, 20);

	private JPanel poklBox = new JPanel(new VerticalLayout());
	private List<TrainerPoklmonBox> pokls = new ArrayList<TrainerPoklmonBox>();

	public TrainerObjectDialog() {

		setLayout(new BorderLayout());
		money.setText("500");
		intro.setText("Auf zum Kampf!");
		outro.setText("Das war ein guter Kampf!");
		name.setText("Hans");
		JPanel top = new JPanel(new VerticalLayout());
		top.add(new JLabel("Name:"));
		top.add(name);
		top.add(new JLabel("Intro-Text: (On Trigger)"));
		top.add(intro);
		top.add(new JLabel("Outro-Text: (On Battle End)"));
		top.add(outro);
		top.add(new JLabel("Price Money:"));
		top.add(money);
		top.add(range);
		top.add(new JLabel("Poklmon:"));
		add(top, BorderLayout.NORTH);
		add(poklBox, BorderLayout.CENTER);
		poklBox.setBorder(BorderFactory.createEtchedBorder());
		JButton addPokl = new JButton("Add Poklmon");
		add(addPokl, BorderLayout.SOUTH);
		addPokl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pokls.size() < 6) {
					TrainerPoklmonBox box = new TrainerPoklmonBox();
					pokls.add(box);
					poklBox.add(box);
					box.setRemoveListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							pokls.remove(box);
							poklBox.remove(box);
							revalidate();
							repaint();
						}
					});
					revalidate();
					repaint();
				}
			}
		});
		// add 1 pokl
		addPokl.doClick();
		setPreferredSize(new Dimension(1100, 500));
	}

	public String getObjectName() {
		return name.getText();
	}

	public String getInitScript() {
		String init = "";
		init = "if(!init.isKnown()){\nself.setViewTriggerRange(" + range.getValue() + ")\n}";
		return init;
	}

	public String getTriggerScript() {
		String trigger = "";
		trigger = "if(object.isKnown()){\ndialog.text(\"" + outro.getText() + "\")\n}\n";
		trigger += "else {\ndialog.text(\"" + intro.getText() + "\")\n";
		trigger += "battle.initBattle()\n";
		for (TrainerPoklmonBox box : pokls) {
			int id = box.getPoklmon();
			int lvl = box.getLevel();
			int[] atks = box.getAttacks();
			trigger += "battle.addTrainerPoklmon(" + id + "," + lvl + "," + atks[0] + "," + atks[1] + "," + atks[2]
					+ "," + atks[3] + ")\n";
		}
		trigger += "if(battle.startTrainerBattle(self.getName(),\"" + outro.getText() + "\"," + money.getText() + ")){\n";
		trigger += "object.setKnown()\n";
		trigger += "self.setViewTriggerRange(0)\n";
		trigger += "}\n";
		trigger += "}\n";
		return trigger;
	}

	public static TrainerObjectDialog openDialog() {
		TrainerObjectDialog dialog = new TrainerObjectDialog();
		JOptionPane.showMessageDialog(null, dialog, "New Trainer", JOptionPane.PLAIN_MESSAGE);
		return dialog;
	}

}
