package com.broll.pokleditor.pokldex;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.broll.pokleditor.gui.components.IntBox;
import com.broll.pokleditor.gui.components.TypeBox;
import com.broll.pokllib.poklmon.ElementType;
import com.broll.pokllib.poklmon.Poklmon;

public class PoklEditElement extends JPanel {

	private Poklmon poklmon;
	private IntBox pokedex=new IntBox("Pokldex#",3);
	private TypeBox type = new TypeBox("Element");
	private TypeBox type2 = new TypeBox("");
	private JCheckBox secondTyp = new JCheckBox("Secondary Element");

	public PoklEditElement() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(pokedex);
		add(type);
		add(secondTyp);
		add(type2);
		secondTyp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				type2.setEnabled(secondTyp.isSelected());
			}
		});
	}

	public void setPoklmon(Poklmon poklmon) {
		this.poklmon = poklmon;
		type.setType(poklmon.getBaseType());
		ElementType sectype = poklmon.getSecondaryType();
		if (sectype != null) {
			secondTyp.setSelected(true);
			type2.setEnabled(true);
			
			type2.setType(sectype);
		} else {
			secondTyp.setSelected(false);
			type2.setType(ElementType.NORMAL);
			type2.setEnabled(false);
		}
		pokedex.setValue(poklmon.getPokldexNumber());
	}

	public void save() {
		ElementType sectype = null;
		if (secondTyp.isSelected()) {
			sectype = type2.getType();
		}
		poklmon.setBaseType(type.getType());
		poklmon.setSecondaryType(sectype);
		poklmon.setPokldexNumber(pokedex.getValue());
	}
}
