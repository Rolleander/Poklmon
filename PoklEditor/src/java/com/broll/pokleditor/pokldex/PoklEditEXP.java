package com.broll.pokleditor.pokldex;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.broll.pokleditor.gui.GraphicLoader;
import com.broll.pokleditor.gui.components.EXPTypeBox;
import com.broll.pokleditor.gui.components.IntBox;
import com.broll.pokllib.poklmon.Poklmon;

public class PoklEditEXP extends JPanel {

	private Poklmon poklmon;
	private IntBox basexp=new IntBox("Base-EXP", 3);
	private EXPTypeBox expbox=new EXPTypeBox("EXP-Increase-Type");
	private IntBox cacherate=new IntBox("Cacherate", 3);

	public PoklEditEXP() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(basexp);
		add(expbox);
		add(cacherate);
		JButton locator = GraphicLoader.newIconButton("location_pin.png");
		locator.addActionListener(event-> PoklAreaFinder.locate(poklmon));
		add(locator);
	}

	public void setPoklmon(Poklmon poklmon) {
		this.poklmon = poklmon;
		basexp.setValue(poklmon.getExpBasePoints());
		expbox.setType(poklmon.getExpLearnType());
		cacherate.setValue(poklmon.getCatchRate());
	}

	public void save() {
		poklmon.setExpLearnType(expbox.getType());
		poklmon.setExpBasePoints(basexp.getValue());
		poklmon.setCatchRate(cacherate.getValue());
	}
}
