package com.broll.pokleditor.pokldex;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.broll.pokleditor.gui.components.IntBox;
import com.broll.pokleditor.gui.components.LevelBox;
import com.broll.pokleditor.gui.components.PoklmonBox;
import com.broll.pokleditor.gui.components.PoklmonGraphicBox;
import com.broll.pokleditor.gui.components.StringBox;
import com.broll.pokllib.poklmon.Poklmon;

public class PoklEditGraphics extends JPanel {

	private Poklmon poklmon;
	private PoklmonGraphicBox graphic = new PoklmonGraphicBox();

	public PoklEditGraphics() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(graphic);

	}

	public void setPoklmon(Poklmon poklmon) {
		this.poklmon = poklmon;
		graphic.setImage(poklmon.getGraphicName());
	}

	public void save() {
		poklmon.setGraphicName(graphic.getImage());
	}

}
