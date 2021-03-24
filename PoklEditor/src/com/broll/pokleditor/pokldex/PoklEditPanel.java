package com.broll.pokleditor.pokldex;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.broll.pokleditor.gui.components.StringBox;
import com.broll.pokllib.poklmon.Poklmon;

public class PoklEditPanel extends JPanel{

	
	private Poklmon poklmon;
	private PoklEditStatus status=new PoklEditStatus();
	private PoklEditAttributes attributes=new PoklEditAttributes();
	private PoklEditElement element=new PoklEditElement();
	private PoklEditEXP exp=new PoklEditEXP();
	private PoklEditGraphics graphics=new  PoklEditGraphics();
	private StringBox description=new StringBox("Pokldex Eintrag:", 50);
	
	public PoklEditPanel()
	{
		setLayout(new BorderLayout());
		JPanel north=new JPanel();
		north.setLayout(new BorderLayout());
		JPanel northCenter=new JPanel();
		northCenter.setLayout(new GridLayout(4, 1));
		northCenter.add(status);
		northCenter.add(element);
		northCenter.add(exp);
		northCenter.add(description);
		north.add(northCenter,BorderLayout.CENTER);
		north.add(graphics,BorderLayout.WEST);
		add(north,BorderLayout.NORTH);
		add(attributes,BorderLayout.CENTER);
		
	}
	
	
	public void setPoklmon(Poklmon poklmon)
	{
		this.poklmon=poklmon;
		status.setPoklmon(poklmon);
		attributes.setPoklmon(poklmon);
		element.setPoklmon(poklmon);
		exp.setPoklmon(poklmon);
		graphics.setPoklmon(poklmon);
		description.setText(poklmon.getDescription());
		revalidate();
	}

	public Poklmon getPoklmon() {
		return poklmon;
	}

	public void save() {
		status.save();
		attributes.save();
		element.save();
		exp.save();
		graphics.save();
		poklmon.setDescription(description.getText());
	}
	
}
